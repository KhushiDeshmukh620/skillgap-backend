
import { useEffect, useState } from "react";
import "./AiJobRoleGenerator.css";

const API_URL = "http://localhost:8080";

const LEVELS = [
  "BEGINNER",
  "INTERMEDIATE",
  "ADVANCED",
];

const PRIORITIES = [
  "ESSENTIAL",
  "IMPORTANT",
  "OPTIONAL",
];

const RESOURCE_TYPES = [
  "Documentation",
  "Tutorial",
  "YouTube",
];

function createEmptyResources() {
  return [
    {
      title: "",
      resourceType: "Documentation",
      resourceUrl: "",
    },
    {
      title: "",
      resourceType: "Tutorial",
      resourceUrl: "",
    },
    {
      title: "",
      resourceType: "YouTube",
      resourceUrl: "",
    },
  ];
}

// Search links are generated locally.
// AI-generated URLs are deliberately not used.
function getResourceUrl(skillName, resource) {
  const query = `${skillName} ${resource?.title || ""}`.trim();

  if (resource?.resourceType === "YouTube") {
    return `https://www.youtube.com/results?search_query=${encodeURIComponent(query)}`;
  }

  return `https://www.google.com/search?q=${encodeURIComponent(query)}`;
}

function getResourceDescription(resource) {
  const title = resource?.title || "this learning resource";

  switch (resource?.resourceType) {
    case "Documentation":
      return `Explore official concepts, features, and practical usage of ${title}.`;

    case "YouTube":
      return `Watch video explanations and practical demonstrations of ${title}.`;

    default:
      return `Follow step-by-step lessons and examples to learn ${title}.`;
  }
}

function AiJobRoleGenerator() {
  const [inputRole, setInputRole] = useState("");
  const [draft, setDraft] = useState(null);
  const [domains, setDomains] = useState([]);
  const [selectedDomain, setSelectedDomain] = useState("");
  const [saveMode, setSaveMode] = useState("create");
  const [existingRoleId, setExistingRoleId] = useState("");
  const [generating, setGenerating] = useState(false);
  const [saving, setSaving] = useState(false);
  const [loadingDomains, setLoadingDomains] = useState(true);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [savedRoleId, setSavedRoleId] = useState(null);

  async function apiRequest(path, options = {}) {
    const token = localStorage.getItem("token");

    if (!token) {
      throw new Error("Please log in again.");
    }

    const response = await fetch(`${API_URL}${path}`, {
      ...options,
      headers: {
        Authorization: `Bearer ${token}`,
        ...(options.body
          ? { "Content-Type": "application/json" }
          : {}),
        ...options.headers,
      },
    });

    if (!response.ok) {
      if (response.status === 401) {
        throw new Error("Session expired. Please log in again.");
      }

      if (response.status === 403) {
        throw new Error("Administrator permission is required.");
      }

      let message = `Request failed (${response.status}).`;

      try {
        const data = await response.json();
        message =
          data.message ||
          data.detail ||
          data.error ||
          message;
      } catch {
        // Keep default error message.
      }

      throw new Error(message);
    }

    return response.json();
  }

  useEffect(() => {
    let cancelled = false;

    async function loadDomains() {
      try {
        const data = await apiRequest("/api/engineering-domains");

        if (!cancelled) {
          setDomains(Array.isArray(data) ? data : []);
        }
      } catch (err) {
        if (!cancelled) {
          setError(err.message);
        }
      } finally {
        if (!cancelled) {
          setLoadingDomains(false);
        }
      }
    }

    loadDomains();

    return () => {
      cancelled = true;
    };
  }, []);

  async function generateRole(event) {
    event.preventDefault();

    if (!inputRole.trim()) {
      setError("Please enter a job role name.");
      return;
    }

    setGenerating(true);
    setError("");
    setSuccess("");
    setDraft(null);
    setSavedRoleId(null);

    try {
      const data = await apiRequest(
        "/api/admin/ai-job-roles/generate",
        {
          method: "POST",
          body: JSON.stringify({
            roleName: inputRole.trim(),
          }),
        }
      );

      if (
        typeof data.description !== "string" ||
        !Array.isArray(data.requiredSkills)
      ) {
        throw new Error(
          "AI returned an invalid job role profile."
        );
      }

      const requiredSkills = data.requiredSkills.map((skill) => {
        const aiResources = Array.isArray(skill.learningResources)
          ? skill.learningResources
          : [];

        // Always display exactly three resources.
        // Keep AI titles and types, but ignore its direct URLs.
        const learningResources = Array.from(
          { length: 3 },
          (_, index) => {
            const resource = aiResources[index] || {};
            const resourceType = RESOURCE_TYPES.includes(
              resource.resourceType
            )
              ? resource.resourceType
              : RESOURCE_TYPES[index];

            return {
              title: resource.title || "",
              resourceType,
              resourceUrl: "",
            };
          }
        );

        return {
          skillName: skill.skillName || "",
          category: skill.category || "Technical Skills",

          requiredLevel: LEVELS.includes(skill.requiredLevel)
            ? skill.requiredLevel
            : "INTERMEDIATE",

          priority: PRIORITIES.includes(skill.priority)
            ? skill.priority
            : "IMPORTANT",

          relevanceExplanation:
            skill.relevanceExplanation || "",

          learningResources,
        };
      });

      setDraft({
        roleName: data.roleName || inputRole.trim(),
        description: data.description,
        requiredSkills,
      });
    } catch (err) {
      setError(err.message);
    } finally {
      setGenerating(false);
    }
  }

  function updateDraft(field, value) {
    setDraft((previous) => ({
      ...previous,
      [field]: value,
    }));
  }

  function updateSkill(index, field, value) {
    setDraft((previous) => ({
      ...previous,
      requiredSkills: previous.requiredSkills.map(
        (skill, i) =>
          i === index
            ? { ...skill, [field]: value }
            : skill
      ),
    }));
  }

  function updateResource(skillIndex, resourceIndex, field, value) {
    setDraft((previous) => ({
      ...previous,
      requiredSkills: previous.requiredSkills.map(
        (skill, i) => {
          if (i !== skillIndex) {
            return skill;
          }

          const resources = skill.learningResources.map(
            (resource, j) =>
              j === resourceIndex
                ? { ...resource, [field]: value }
                : resource
          );

          return {
            ...skill,
            learningResources: resources,
          };
        }
      ),
    }));
  }

  function removeSkill(index) {
    setDraft((previous) => ({
      ...previous,
      requiredSkills: previous.requiredSkills.filter(
        (_, i) => i !== index
      ),
    }));
  }

  function addSkill() {
    setDraft((previous) => ({
      ...previous,
      requiredSkills: [
        ...previous.requiredSkills,
        {
          skillName: "",
          category: "Technical Skills",
          requiredLevel: "BEGINNER",
          priority: "IMPORTANT",
          relevanceExplanation: "",
          learningResources: createEmptyResources(),
        },
      ],
    }));
  }

  async function approveAndSave() {
    setError("");
    setSuccess("");

    if (!draft || !draft.roleName.trim()) {
      setError("Job role name is required.");
      return;
    }

    if (saveMode === "create" && !selectedDomain) {
      setError("Please select an engineering domain.");
      return;
    }

    const roleId = Number(existingRoleId);

    if (
      saveMode === "update" &&
      (!Number.isSafeInteger(roleId) || roleId <= 0)
    ) {
      setError("Enter a valid existing Job Role ID.");
      return;
    }

    if (draft.requiredSkills.length === 0) {
      setError("Add at least one required skill.");
      return;
    }

    const cleanedSkills = draft.requiredSkills.map((skill) => ({
      skillName: String(skill.skillName || "").trim(),
      category: String(skill.category || "").trim(),
      requiredLevel: skill.requiredLevel,
      priority: skill.priority,
      relevanceExplanation: String(
        skill.relevanceExplanation || ""
      ).trim(),

      learningResources: (
        Array.isArray(skill.learningResources)
          ? skill.learningResources
          : []
      ).map((resource) => ({
        title: String(resource.title || "").trim(),
        resourceType: String(
          resource.resourceType || "Tutorial"
        ).trim(),

        // Search URLs are generated when displaying resources.
        // Do not save AI-generated or manually entered direct URLs.
        resourceUrl: "",
      })),
    }));

    if (cleanedSkills.some((skill) => !skill.skillName)) {
      setError("Every skill must have a name.");
      return;
    }

    if (cleanedSkills.some((skill) => !skill.category)) {
      setError("Every skill must have a category.");
      return;
    }

    if (
      cleanedSkills.some(
        (skill) => !LEVELS.includes(skill.requiredLevel)
      )
    ) {
      setError("Invalid proficiency level.");
      return;
    }

    if (
      cleanedSkills.some(
        (skill) => !PRIORITIES.includes(skill.priority)
      )
    ) {
      setError("Every skill must have a valid priority.");
      return;
    }

    if (
      cleanedSkills.some(
        (skill) => !skill.relevanceExplanation
      )
    ) {
      setError("Every skill needs a relevance explanation.");
      return;
    }

    const names = cleanedSkills.map((skill) =>
      skill.skillName.toLowerCase()
    );

    if (new Set(names).size !== names.length) {
      setError("Remove duplicate skill names before saving.");
      return;
    }

    for (const skill of cleanedSkills) {
      if (skill.learningResources.length !== 3) {
        setError(
          `"${skill.skillName}" must have exactly three learning resources.`
        );
        return;
      }

      for (const resource of skill.learningResources) {
        if (!resource.title) {
          setError(
            `Enter a title for all three learning resources under "${skill.skillName}".`
          );
          return;
        }

        if (!RESOURCE_TYPES.includes(resource.resourceType)) {
          setError(
            `Choose a valid resource type for "${skill.skillName}".`
          );
          return;
        }
      }
    }

    const isUpdate = saveMode === "update";

    const resourceCount = cleanedSkills.reduce(
      (total, skill) =>
        total + skill.learningResources.length,
      0
    );

    const confirmed = window.confirm(
      isUpdate
        ? `Update existing Job Role ID ${roleId}?\n\n` +
          `Skills submitted: ${cleanedSkills.length}\n` +
          `Learning resources submitted: ${resourceCount}\n\n` +
          "Existing skills not included will be retained."
        : `Approve and save "${draft.roleName}"?\n\n` +
          `Skills: ${cleanedSkills.length}\n` +
          `Learning resources: ${resourceCount}`
    );

    if (!confirmed) {
      return;
    }

    setSaving(true);

    try {
      const path = isUpdate
        ? `/api/admin/ai-job-roles/${roleId}`
        : "/api/admin/ai-job-roles/approve";

      const result = await apiRequest(path, {
        method: isUpdate ? "PUT" : "POST",
        body: JSON.stringify({
          roleName: draft.roleName.trim(),
          description: draft.description.trim(),
          engineeringDomainId: selectedDomain
            ? Number(selectedDomain)
            : null,
          requiredSkills: cleanedSkills,
        }),
      });

      setSavedRoleId(result.jobRoleId);

      setSuccess(
        isUpdate
          ? `Existing job role "${result.roleName}" ` +
              `(ID: ${result.jobRoleId}) updated successfully. ` +
              `${result.savedSkills} skill requirements processed.`
          : `Job role "${result.roleName}" saved successfully ` +
              `with ${result.savedSkills} required skills. ` +
              `Job Role ID: ${result.jobRoleId}`
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  }

  const locked = saving || savedRoleId !== null;

  const totalResources = draft
    ? draft.requiredSkills.reduce(
        (total, skill) =>
          total + (skill.learningResources?.length || 0),
        0
      )
    : 0;

  const essentialCount = draft
    ? draft.requiredSkills.filter(
        (skill) => skill.priority === "ESSENTIAL"
      ).length
    : 0;

  const importantCount = draft
    ? draft.requiredSkills.filter(
        (skill) => skill.priority === "IMPORTANT"
      ).length
    : 0;

  const optionalCount = draft
    ? draft.requiredSkills.filter(
        (skill) => skill.priority === "OPTIONAL"
      ).length
    : 0;

  return (
    <div className="aij-container">
      {/* HERO */}
      <section className="aij-hero">
        <div>
          <span className="aij-eyebrow">
            AI CAREER INTELLIGENCE
          </span>

          <h2>✨ AI Job Role Generator</h2>

          <p>
            Generate, review, and approve technical
            career requirements using Qwen AI.
          </p>
        </div>

        <div className="aij-hero-icon">✨</div>
      </section>

      {/* GENERATE ROLE */}
      <section className="aij-panel">
        <h3>01 / Generate Job Role</h3>

        <p>
          Enter the career role you want AI to analyze.
          AI will determine the relevant skills and proficiency
          requirements based on the job responsibilities.
        </p>

        <form onSubmit={generateRole}>
          <label htmlFor="aij-input-role">
            Job Role Name
          </label>

          <input
            id="aij-input-role"
            value={inputRole}
            onChange={(e) => setInputRole(e.target.value)}
            placeholder="e.g. Cloud Support Engineer"
            maxLength={150}
            disabled={generating || saving}
            required
          />

          <button
            type="submit"
            disabled={generating || saving}
          >
            {generating
              ? "✨ Generating with Qwen..."
              : "✨ Generate AI Recommendations"}
          </button>
        </form>

        {generating && (
          <p className="aij-loading" role="status">
            Qwen is preparing the job role profile.
            This may take some time.
          </p>
        )}
      </section>

      {/* ERROR */}
      {error && (
        <div className="aij-error" role="alert">
          {error}
        </div>
      )}

      {/* SUCCESS */}
      {success && (
        <div className="aij-success" role="status">
          ✓ {success}
        </div>
      )}

      {draft && (
        <>
          {/* REVIEW JOB ROLE */}
          <section className="aij-panel">
            <span className="aij-eyebrow">
              ADMINISTRATOR REVIEW
            </span>

            <h3>02 / Review Job Role</h3>

            <div className="aij-review-form">
              <div>
                <label htmlFor="aij-review-name">
                  Job Role Name
                </label>

                <input
                  id="aij-review-name"
                  value={draft.roleName}
                  onChange={(e) =>
                    updateDraft("roleName", e.target.value)
                  }
                  maxLength={255}
                  disabled={locked}
                />
              </div>

              <div>
                <label htmlFor="aij-review-description">
                  Job Description
                </label>

                <textarea
                  id="aij-review-description"
                  value={draft.description}
                  onChange={(e) =>
                    updateDraft("description", e.target.value)
                  }
                  rows={4}
                  disabled={locked}
                />
              </div>

              <div>
                <label htmlFor="aij-review-domain">
                  Engineering Domain
                </label>

                <select
                  id="aij-review-domain"
                  value={selectedDomain}
                  onChange={(e) =>
                    setSelectedDomain(e.target.value)
                  }
                  disabled={loadingDomains || locked}
                >
                  <option value="">
                    -- Select Engineering Domain --
                  </option>

                  {domains.map((domain) => (
                    <option key={domain.id} value={domain.id}>
                      {domain.name}
                    </option>
                  ))}
                </select>

                {saveMode === "update" && (
                  <p>
                    The existing job role's engineering
                    domain will be preserved.
                  </p>
                )}
              </div>
            </div>

            {/* SUMMARY */}
            <div className="aij-summary">
              <div>
                <span>Required Skills</span>
                <strong>{draft.requiredSkills.length}</strong>
              </div>

              <div>
                <span>Learning Resources</span>
                <strong>{totalResources}</strong>
              </div>

              <div>
                <span>Essential Skills</span>
                <strong>{essentialCount}</strong>
              </div>

              <div>
                <span>Important Skills</span>
                <strong>{importantCount}</strong>
              </div>

              <div>
                <span>Optional Skills</span>
                <strong>{optionalCount}</strong>
              </div>

              <div>
                <span>Review Status</span>
                <strong>
                  {savedRoleId !== null ? "Approved" : "Pending"}
                </strong>
              </div>
            </div>
          </section>

          {/* REVIEW SKILLS */}
          <section className="aij-panel">
            <div className="aij-section-header">
              <div>
                <h3>03 / Review Skill Requirements</h3>

                <p>
                  Review proficiency, priority, relevance,
                  and three learning resources for each skill.
                </p>
              </div>

              <button
                type="button"
                onClick={addSkill}
                disabled={locked}
              >
                + Add Skill
              </button>
            </div>

            <div className="aij-skills-grid">
              {draft.requiredSkills.map((skill, index) => (
                <article
                  className="aij-skill-card"
                  key={index}
                >
                  <div className="aij-skill-header">
                    <h4>Skill {index + 1}</h4>

                    <button
                      type="button"
                      className="aij-remove"
                      onClick={() => removeSkill(index)}
                      disabled={locked}
                    >
                      Remove
                    </button>
                  </div>

                  <label>Skill Name</label>

                  <input
                    value={skill.skillName}
                    onChange={(e) =>
                      updateSkill(index, "skillName", e.target.value)
                    }
                    maxLength={255}
                    disabled={locked}
                  />

                  <label>Category</label>

                  <input
                    value={skill.category}
                    onChange={(e) =>
                      updateSkill(index, "category", e.target.value)
                    }
                    disabled={locked}
                  />

                  <label>Required Proficiency</label>

                  <select
                    value={skill.requiredLevel}
                    onChange={(e) =>
                      updateSkill(
                        index,
                        "requiredLevel",
                        e.target.value
                      )
                    }
                    disabled={locked}
                  >
                    {LEVELS.map((level) => (
                      <option key={level} value={level}>
                        {level}
                      </option>
                    ))}
                  </select>

                  <label>Skill Priority</label>

                  <select
                    value={skill.priority}
                    onChange={(e) =>
                      updateSkill(index, "priority", e.target.value)
                    }
                    disabled={locked}
                  >
                    {PRIORITIES.map((priority) => (
                      <option key={priority} value={priority}>
                        {priority}
                      </option>
                    ))}
                  </select>

                  <label>Why is this skill relevant?</label>

                  <textarea
                    value={skill.relevanceExplanation}
                    onChange={(e) =>
                      updateSkill(
                        index,
                        "relevanceExplanation",
                        e.target.value
                      )
                    }
                    rows={3}
                    maxLength={1000}
                    placeholder="Explain how this skill is used in this job role..."
                    disabled={locked}
                  />

                  {/* LEARNING RESOURCES */}
                                {/* LEARNING RESOURCES */}
                                <h5>Resources</h5>

                                <div className="aij-resource-list">
                                  {skill.learningResources.map(
                                    (resource, resourceIndex) => (
                                      <div
                                        key={resourceIndex}
                                        className="aij-resource-link-item"
                                      >
                                        <a
                                          href={getResourceUrl(
                                            skill.skillName,
                                            resource
                                          )}
                                          target="_blank"
                                          rel="noopener noreferrer"
                                          className="aij-resource-link"
                                        >
                                          {resource.title ||
                                            `Resource ${resourceIndex + 1}`} ↗
                                        </a>
                                      </div>
                                    )
                                  )}
                                </div>
                              </article>
                            ))}
                          </div>
                        </section>

          {/* APPROVAL */}
          <section className="aij-panel aij-approval-panel">
            <span className="aij-eyebrow">
              FINAL APPROVAL
            </span>

            <h3>04 / Save Job Role to MySQL</h3>

            <p>
              Choose whether to create a new job role
              or update an existing one.
            </p>

            <div className="aij-review-form">
              <div>
                <label htmlFor="aij-save-mode">
                  Save Operation
                </label>

                <select
                  id="aij-save-mode"
                  value={saveMode}
                  onChange={(e) => {
                    setSaveMode(e.target.value);
                    setError("");
                  }}
                  disabled={locked}
                >
                  <option value="create">
                    Create New Job Role
                  </option>

                  <option value="update">
                    Update Existing Job Role
                  </option>
                </select>
              </div>

              {saveMode === "update" && (
                <div>
                  <label htmlFor="aij-existing-role-id">
                    Existing Job Role ID
                  </label>

                  <input
                    id="aij-existing-role-id"
                    type="number"
                    min="1"
                    step="1"
                    value={existingRoleId}
                    onChange={(e) =>
                      setExistingRoleId(e.target.value)
                    }
                    placeholder="Enter existing Job Role ID"
                    disabled={locked}
                  />

                  <p>
                    Enter the ID of the job role you
                    want to update.
                  </p>
                </div>
              )}
            </div>

            {saveMode === "update" ? (
              <p>
                Existing skill requirements will be retained.
                Matching skills will have their proficiency,
                priority, and relevance updated, and missing
                skills will be added.
              </p>
            ) : (
              <p>
                Saving will create a new job role, reuse
                existing skills where names match, and assign
                proficiency, priority, and relevance requirements.
              </p>
            )}

            <p>
              Learning resource suggestions will also be
              submitted for saving with their associated skills.
            </p>

            {savedRoleId === null ? (
              <button
                type="button"
                className="aij-save-button"
                onClick={approveAndSave}
                disabled={saving || generating}
              >
                {saving
                  ? "Saving to MySQL..."
                  : saveMode === "update"
                  ? "✓ Update Existing Job Role"
                  : "✓ Approve & Save to MySQL"}
              </button>
            ) : (
              <div className="aij-success">
                ✓ Saved successfully. Job Role ID: {savedRoleId}
              </div>
            )}
          </section>
        </>
      )}
    </div>
  );
}

export default AiJobRoleGenerator;
