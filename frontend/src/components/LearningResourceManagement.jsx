
import { useCallback, useEffect, useRef, useState } from "react";
import "./LearningResourceManagement.css";

const API_URL = "http://localhost:8080";

const RESOURCE_TYPES = [
  "Documentation",
  "Video",
  "Course",
  "Tutorial",
  "Article",
  "Practice",
];

function LearningResourceManagement() {
  const [skills, setSkills] = useState([]);
  const [selectedSkillId, setSelectedSkillId] = useState("");
  const [resources, setResources] = useState([]);

  const [title, setTitle] = useState("");
  const [resourceUrl, setResourceUrl] = useState("");
  const [resourceType, setResourceType] = useState("Documentation");
  const [learningOrder, setLearningOrder] = useState(1);

  const [editingResourceId, setEditingResourceId] = useState(null);
  const [deletingResourceId, setDeletingResourceId] = useState(null);

  const [skillsLoading, setSkillsLoading] = useState(true);
  const [resourcesLoading, setResourcesLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const formRef = useRef(null);
  const requestIdRef = useRef(0);

  const busy = saving || deletingResourceId !== null;

  const resetForm = () => {
    setEditingResourceId(null);
    setTitle("");
    setResourceUrl("");
    setResourceType("Documentation");
    setLearningOrder(1);
  };

  const apiRequest = useCallback(async (path, options = {}) => {
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

    if (response.status === 401) {
      throw new Error("Session expired. Please log in again.");
    }

    if (response.status === 403) {
      throw new Error("Administrator permission is required.");
    }

    if (!response.ok) {
      let message = `Request failed (${response.status}).`;

      try {
        const data = await response.json();

        message =
          data.message ||
          data.detail ||
          data.error ||
          message;
      } catch {
        // Keep the default error message.
      }

      throw new Error(message);
    }

    if (response.status === 204) {
      return null;
    }

    const text = await response.text();

    return text ? JSON.parse(text) : null;
  }, []);

  const loadResources = useCallback(
    async (skillId) => {
      const requestId = ++requestIdRef.current;

      if (!skillId) {
        setResources([]);
        setResourcesLoading(false);
        return;
      }

      setResourcesLoading(true);

      try {
        const data = await apiRequest(
          `/api/resources/skill/${skillId}`
        );

        if (requestId !== requestIdRef.current) {
          return;
        }

        const sorted = Array.isArray(data)
          ? [...data].sort(
              (a, b) =>
                (a.learningOrder ?? 9999) -
                (b.learningOrder ?? 9999)
            )
          : [];

        setResources(sorted);
      } catch (err) {
        if (requestId === requestIdRef.current) {
          setError(err.message);
          setResources([]);
        }
      } finally {
        if (requestId === requestIdRef.current) {
          setResourcesLoading(false);
        }
      }
    },
    [apiRequest]
  );

  useEffect(() => {
    let active = true;

    const loadSkills = async () => {
      setSkillsLoading(true);

      try {
        const data = await apiRequest("/api/skills");

        if (active) {
          setSkills(Array.isArray(data) ? data : []);
        }
      } catch (err) {
        if (active) {
          setError(err.message);
        }
      } finally {
        if (active) {
          setSkillsLoading(false);
        }
      }
    };

    loadSkills();

    return () => {
      active = false;
    };
  }, [apiRequest]);

  useEffect(() => {
    loadResources(selectedSkillId);

    return () => {
      requestIdRef.current += 1;
    };
  }, [selectedSkillId, loadResources]);

  const changeSkill = (event) => {
    if (busy) return;

    resetForm();
    setResources([]);
    setError("");
    setSuccess("");
    setSelectedSkillId(event.target.value);
  };

  const startEditing = (resource) => {
    if (busy) return;

    setEditingResourceId(resource.id);
    setTitle(resource.title || "");
    setResourceUrl(resource.resourceUrl || "");
    setResourceType(
      resource.resourceType || "Documentation"
    );
    setLearningOrder(resource.learningOrder ?? 1);

    setError("");
    setSuccess("");

    formRef.current?.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  };

  const cancelEditing = () => {
    if (busy) return;

    resetForm();
    setError("");
    setSuccess("");
  };

  const saveResource = async (event) => {
    event.preventDefault();

    if (busy) return;

    setError("");
    setSuccess("");

    if (!selectedSkillId) {
      setError("Please select a skill.");
      return;
    }

    if (!title.trim()) {
      setError("Please enter a resource title.");
      return;
    }

    let validUrl;

    try {
      validUrl = new URL(resourceUrl.trim());

      if (
        validUrl.protocol !== "https:" &&
        validUrl.protocol !== "http:"
      ) {
        throw new Error("Invalid URL protocol.");
      }
    } catch {
      setError("Please enter a valid HTTP or HTTPS URL.");
      return;
    }

    const order = Number(learningOrder);

    if (!Number.isInteger(order) || order < 1) {
      setError("Learning order must be a positive whole number.");
      return;
    }

    const isEditing = editingResourceId !== null;

    setSaving(true);

    try {
      const resourceData = {
        title: title.trim(),
        resourceUrl: validUrl.toString(),
        resourceType,
        learningOrder: order,
      };

      if (isEditing) {
        await apiRequest(
          `/api/resources/${editingResourceId}`,
          {
            method: "PUT",
            body: JSON.stringify(resourceData),
          }
        );
      } else {
        await apiRequest(
          `/api/resources/skill/${selectedSkillId}`,
          {
            method: "POST",
            body: JSON.stringify(resourceData),
          }
        );
      }

      resetForm();

      await loadResources(selectedSkillId);

      setSuccess(
        isEditing
          ? "Learning resource updated successfully!"
          : "Learning resource added successfully!"
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  const deleteResource = async (resource) => {
    if (busy) return;

    const confirmed = window.confirm(
      `Delete "${resource.title}"?\n\n` +
        "This action cannot be undone."
    );

    if (!confirmed) return;

    setDeletingResourceId(resource.id);
    setError("");
    setSuccess("");

    try {
      await apiRequest(
        `/api/resources/${resource.id}`,
        { method: "DELETE" }
      );

      if (editingResourceId === resource.id) {
        resetForm();
      }

      await loadResources(selectedSkillId);

      setSuccess("Learning resource deleted successfully!");
    } catch (err) {
      setError(err.message);
    } finally {
      setDeletingResourceId(null);
    }
  };

  const selectedSkill = skills.find(
    (skill) => String(skill.id) === selectedSkillId
  );

  return (
    <div className="lrm-container">
      {/* HEADER */}
      <div className="lrm-hero">
        <div>
          <span className="lrm-eyebrow">
            LEARNING INTELLIGENCE
          </span>

          <h2>Learning Resource Management</h2>

          <p>
            Curate high-quality learning resources
            for personalized career recommendations.
          </p>
        </div>

        <div className="lrm-hero-icon">📚</div>
      </div>

      {/* OVERVIEW */}
      <div className="lrm-stats">
        <div className="lrm-stat">
          <span>Available Skills</span>

          <strong>
            {skillsLoading ? "..." : skills.length}
          </strong>
        </div>

        <div className="lrm-stat">
          <span>Selected Skill Resources</span>

          <strong>
            {resourcesLoading ? "..." : resources.length}
          </strong>
        </div>
      </div>

      {/* MESSAGES */}
      {error && (
        <div
          className="lrm-alert lrm-error"
          role="alert"
        >
          {error}
        </div>
      )}

      {success && (
        <div
          className="lrm-alert lrm-success"
          role="status"
        >
          ✓ {success}
        </div>
      )}

      {/* SELECT SKILL */}
      <section className="lrm-panel">
        <div className="lrm-panel-heading">
          <h3>01 / Select Skill</h3>

          <p>
            Choose a skill to manage its learning resources.
          </p>
        </div>

        <label htmlFor="lrm-skill">
          Target Skill
        </label>

        <select
          id="lrm-skill"
          value={selectedSkillId}
          onChange={changeSkill}
          disabled={skillsLoading || busy}
        >
          <option value="">
            -- Select a Skill --
          </option>

          {[...skills]
            .sort((a, b) =>
              (a.name || "").localeCompare(b.name || "")
            )
            .map((skill) => (
              <option
                key={skill.id}
                value={skill.id}
              >
                {skill.name}
              </option>
            ))}
        </select>
      </section>

      {/* ADD / EDIT FORM */}
      <section
        className="lrm-panel"
        ref={formRef}
      >
        <div className="lrm-panel-heading">
          <h3>
            {editingResourceId !== null
              ? "02 / Edit Learning Resource"
              : "02 / Add Learning Resource"}
          </h3>

          <p>
            {editingResourceId !== null
              ? "Update the selected learning resource."
              : "Add documentation, tutorials, courses, or videos."}
          </p>
        </div>

        <form onSubmit={saveResource}>
          <div className="lrm-field">
            <label htmlFor="lrm-title">
              Resource Title
            </label>

            <input
              id="lrm-title"
              type="text"
              maxLength={255}
              value={title}
              onChange={(event) =>
                setTitle(event.target.value)
              }
              placeholder="e.g. JavaScript Fundamentals"
              required
              disabled={busy}
            />
          </div>

          <div className="lrm-field">
            <label htmlFor="lrm-url">
              Resource URL
            </label>

            <input
              id="lrm-url"
              type="url"
              value={resourceUrl}
              onChange={(event) =>
                setResourceUrl(event.target.value)
              }
              placeholder="https://developer.mozilla.org/..."
              required
              disabled={busy}
            />
          </div>

          <div className="lrm-form-grid">
            <div className="lrm-field">
              <label htmlFor="lrm-type">
                Resource Type
              </label>

              <select
                id="lrm-type"
                value={resourceType}
                onChange={(event) =>
                  setResourceType(event.target.value)
                }
                disabled={busy}
              >
                {!RESOURCE_TYPES.includes(resourceType) && (
                  <option value={resourceType}>
                    {resourceType}
                  </option>
                )}

                {RESOURCE_TYPES.map((type) => (
                  <option key={type} value={type}>
                    {type}
                  </option>
                ))}
              </select>
            </div>

            <div className="lrm-field">
              <label htmlFor="lrm-order">
                Learning Order
              </label>

              <input
                id="lrm-order"
                type="number"
                min="1"
                step="1"
                value={learningOrder}
                onChange={(event) =>
                  setLearningOrder(event.target.value)
                }
                required
                disabled={busy}
              />
            </div>
          </div>

          <div className="lrm-form-actions">
            <button
              type="submit"
              className="lrm-submit"
              disabled={
                busy ||
                !selectedSkillId ||
                resourcesLoading
              }
            >
              {saving
                ? "Saving..."
                : editingResourceId !== null
                  ? "✓ Update Learning Resource"
                  : "+ Add Learning Resource"}
            </button>

            {editingResourceId !== null && (
              <button
                type="button"
                className="lrm-cancel"
                onClick={cancelEditing}
                disabled={busy}
              >
                Cancel Edit
              </button>
            )}
          </div>
        </form>
      </section>

      {/* EXISTING RESOURCES */}
      <section className="lrm-panel">
        <div className="lrm-panel-heading">
          <h3>
            03 / Existing Learning Resources
          </h3>

          <p>
            {selectedSkill
              ? `Resources for ${selectedSkill.name}`
              : "Select a skill to view its resources."}
          </p>
        </div>

        {!selectedSkillId ? (
          <div className="lrm-empty">
            Select a skill above to view learning resources.
          </div>
        ) : resourcesLoading ? (
          <div className="lrm-empty">
            Loading resources...
          </div>
        ) : resources.length === 0 ? (
          <div className="lrm-empty">
            No resources added for this skill yet.
          </div>
        ) : (
          <div className="lrm-resource-list">
            {resources.map((resource) => (
              <article
                className="lrm-resource"
                key={resource.id}
              >
                <div className="lrm-order">
                  {resource.learningOrder ?? "—"}
                </div>

                <div className="lrm-resource-details">
                  <h4>{resource.title}</h4>

                  <span className="lrm-type">
                    {resource.resourceType ||
                      "Learning Resource"}
                  </span>

                  {resource.resourceUrl && (
                    <a
                      href={resource.resourceUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      Open Resource ↗
                    </a>
                  )}

                  <div className="lrm-resource-actions">
                    <button
                      type="button"
                      onClick={() =>
                        startEditing(resource)
                      }
                      disabled={busy}
                    >
                      ✏️ Edit
                    </button>

                    <button
                      type="button"
                      className="lrm-delete"
                      onClick={() =>
                        deleteResource(resource)
                      }
                      disabled={busy}
                    >
                      {deletingResourceId === resource.id
                        ? "Deleting..."
                        : "🗑️ Delete"}
                    </button>
                  </div>
                </div>
              </article>
            ))}
          </div>
        )}
      </section>
    </div>
  );
}

export default LearningResourceManagement;
