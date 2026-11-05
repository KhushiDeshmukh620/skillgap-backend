
import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import JobRoleRequirements from "../components/JobRoleRequirements";
import LearningResourceManagement from "../components/LearningResourceManagement";
import AiJobRoleGenerator from "../components/AiJobRoleGenerator";

import "./AdminDashboard.css";

const API_URL = "http://localhost:8080";

const SECTIONS = [
  ["domains", "🏛️", "Engineering Domains"],
  ["roles", "💼", "Job Roles"],
  ["requirements", "🎯", "Skill Requirements"],
  ["skills", "🧠", "Skills & Proficiency"],
  ["resources", "📚", "Learning Resources"],
  ["ai", "✨", "AI Role Generator"],
];

function AdminDashboard() {
  const navigate = useNavigate();

  // ============================================
  // NAVIGATION AND DATA
  // ============================================

  const [activeSection, setActiveSection] = useState("domains");

  const [domains, setDomains] = useState([]);
  const [jobRoles, setJobRoles] = useState([]);
  const [skills, setSkills] = useState([]);

  const [domainsLoading, setDomainsLoading] = useState(true);
  const [rolesLoading, setRolesLoading] = useState(true);
  const [skillsLoading, setSkillsLoading] = useState(true);

  // ============================================
  // ENGINEERING DOMAIN FORM
  // ============================================

  const [domainName, setDomainName] = useState("");
  const [domainDescription, setDomainDescription] = useState("");
  const [editingDomainId, setEditingDomainId] = useState(null);

  // ============================================
  // JOB ROLE FORM
  // ============================================

  const [roleName, setRoleName] = useState("");
  const [roleDescription, setRoleDescription] = useState("");
  const [roleDomainId, setRoleDomainId] = useState("");
  const [editingRoleId, setEditingRoleId] = useState(null);

  // ============================================
  // SKILL FORM
  // ============================================

  const [skillName, setSkillName] = useState("");
  const [skillCategory, setSkillCategory] = useState("");
  const [editingSkillId, setEditingSkillId] = useState(null);

  // ============================================
  // REQUEST AND MESSAGE STATE
  // ============================================

  const [saving, setSaving] = useState(false);
  const [deletingId, setDeletingId] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const busy = saving || deletingId !== null;

  const clearMessages = () => {
    setError("");
    setSuccess("");
  };

  // ============================================
  // AUTHENTICATION
  // ============================================

  const handleUnauthorized = useCallback(() => {
    localStorage.removeItem("token");
    navigate("/login", { replace: true });
  }, [navigate]);

  // ============================================
  // API REQUEST HELPER
  // ============================================

  const apiRequest = useCallback(
    async (path, options = {}) => {
      const token = localStorage.getItem("token");

      if (!token) {
        handleUnauthorized();
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
        handleUnauthorized();
        throw new Error("Your session has expired.");
      }

      if (response.status === 403) {
        throw new Error(
          "Access denied. Administrator permission is required."
        );
      }

      if (!response.ok) {
        let message = `Request failed (${response.status}).`;

        try {
          const data = await response.json();

          message =
            data?.message ||
            data?.detail ||
            data?.error ||
            message;
        } catch {
          // Preserve the default error message.
        }

        throw new Error(message);
      }

      if (response.status === 204) {
        return null;
      }

      const text = await response.text();

      return text ? JSON.parse(text) : null;
    },
    [handleUnauthorized]
  );

  // ============================================
  // LOAD ENGINEERING DOMAINS
  // ============================================

  const loadDomains = useCallback(async () => {
    setDomainsLoading(true);

    try {
      const data = await apiRequest("/api/engineering-domains");

      setDomains(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message);
    } finally {
      setDomainsLoading(false);
    }
  }, [apiRequest]);

  // ============================================
  // LOAD JOB ROLES
  // ============================================

  const loadJobRoles = useCallback(async () => {
    setRolesLoading(true);

    try {
      const data = await apiRequest("/api/job-roles/admin");

      setJobRoles(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message);
    } finally {
      setRolesLoading(false);
    }
  }, [apiRequest]);

  // ============================================
  // LOAD SKILLS
  // ============================================

  const loadSkills = useCallback(async () => {
    setSkillsLoading(true);

    try {
      const data = await apiRequest("/api/skills");

      setSkills(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message);
    } finally {
      setSkillsLoading(false);
    }
  }, [apiRequest]);

  // ============================================
  // LOAD ALL DASHBOARD DATA ON PAGE OPEN
  // ============================================

  useEffect(() => {
    loadDomains();
    loadJobRoles();
    loadSkills();
  }, [loadDomains, loadJobRoles, loadSkills]);

  // ============================================
  // ENGINEERING DOMAIN ACTIONS
  // ============================================

  const resetDomainForm = () => {
    setEditingDomainId(null);
    setDomainName("");
    setDomainDescription("");
  };

  const editDomain = (domain) => {
    setEditingDomainId(domain.id);
    setDomainName(domain.name || "");
    setDomainDescription(domain.description || "");

    clearMessages();

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const saveDomain = async (event) => {
    event.preventDefault();

    const name = domainName.trim();

    if (!name) {
      setError("Please enter an engineering domain name.");
      return;
    }

    const isEditing = editingDomainId !== null;

    setSaving(true);
    clearMessages();

    try {
      await apiRequest(
        isEditing
          ? `/api/engineering-domains/${editingDomainId}`
          : "/api/engineering-domains",
        {
          method: isEditing ? "PUT" : "POST",
          body: JSON.stringify({
            name,
            description: domainDescription.trim(),
          }),
        }
      );

      resetDomainForm();

      await loadDomains();

      setSuccess(
        isEditing
          ? "Engineering domain updated successfully."
          : "Engineering domain added successfully."
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  const deleteDomain = async (domain) => {
    const confirmed = window.confirm(
      `Delete "${domain.name}"?\n\n` +
        "Domains linked to job roles cannot be deleted."
    );

    if (!confirmed) {
      return;
    }

    setDeletingId(domain.id);
    clearMessages();

    try {
      await apiRequest(
        `/api/engineering-domains/${domain.id}`,
        {
          method: "DELETE",
        }
      );

      if (editingDomainId === domain.id) {
        resetDomainForm();
      }

      await loadDomains();

      setSuccess(
        "Engineering domain deleted successfully."
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setDeletingId(null);
    }
  };

  // ============================================
  // JOB ROLE ACTIONS
  // ============================================

  const resetRoleForm = () => {
    setEditingRoleId(null);
    setRoleName("");
    setRoleDescription("");
    setRoleDomainId("");
  };

  const editRole = (role) => {
    setEditingRoleId(role.id);
    setRoleName(role.name || "");
    setRoleDescription(role.description || "");

    setRoleDomainId(
      role.engineeringDomainId != null
        ? String(role.engineeringDomainId)
        : ""
    );

    clearMessages();

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const saveRole = async (event) => {
    event.preventDefault();

    const name = roleName.trim();

    if (!name) {
      setError("Please enter a job role name.");
      return;
    }

    if (!roleDomainId) {
      setError("Please select an engineering domain.");
      return;
    }

    const isEditing = editingRoleId !== null;

    setSaving(true);
    clearMessages();

    try {
      await apiRequest(
        isEditing
          ? `/api/job-roles/${editingRoleId}`
          : "/api/job-roles",
        {
          method: isEditing ? "PUT" : "POST",
          body: JSON.stringify({
            name,
            description: roleDescription.trim(),
            engineeringDomain: {
              id: Number(roleDomainId),
            },
          }),
        }
      );

      resetRoleForm();

      await loadJobRoles();

      setSuccess(
        isEditing
          ? "Job role updated successfully."
          : "Job role added successfully."
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  const deleteRole = async (role) => {
    const confirmed = window.confirm(
      `Delete job role "${role.name}"?\n\n` +
        "Job roles with assigned skills cannot be deleted."
    );

    if (!confirmed) {
      return;
    }

    setDeletingId(role.id);
    clearMessages();

    try {
      await apiRequest(`/api/job-roles/${role.id}`, {
        method: "DELETE",
      });

      if (editingRoleId === role.id) {
        resetRoleForm();
      }

      await loadJobRoles();

      setSuccess("Job role deleted successfully.");
    } catch (err) {
      setError(err.message);
    } finally {
      setDeletingId(null);
    }
  };

  // ============================================
  // SKILL ACTIONS
  // ============================================

  const resetSkillForm = () => {
    setEditingSkillId(null);
    setSkillName("");
    setSkillCategory("");
  };

  const editSkill = (skill) => {
    setEditingSkillId(skill.id);
    setSkillName(skill.name || "");
    setSkillCategory(skill.category || "");

    clearMessages();

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const saveSkill = async (event) => {
    event.preventDefault();

    const name = skillName.trim();

    if (!name) {
      setError("Please enter a skill name.");
      return;
    }

    const isEditing = editingSkillId !== null;

    setSaving(true);
    clearMessages();

    try {
      await apiRequest(
        isEditing
          ? `/api/skills/${editingSkillId}`
          : "/api/skills",
        {
          method: isEditing ? "PUT" : "POST",
          body: JSON.stringify({
            name,
            category: skillCategory.trim(),
          }),
        }
      );

      resetSkillForm();

      await loadSkills();

      setSuccess(
        isEditing
          ? "Skill updated successfully."
          : "Skill added successfully."
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  const deleteSkill = async (skill) => {
    const confirmed = window.confirm(
      `Delete skill "${skill.name}"?\n\n` +
        "Skills linked to users, job roles, or learning " +
        "resources cannot be deleted."
    );

    if (!confirmed) {
      return;
    }

    setDeletingId(skill.id);
    clearMessages();

    try {
      await apiRequest(`/api/skills/${skill.id}`, {
        method: "DELETE",
      });

      if (editingSkillId === skill.id) {
        resetSkillForm();
      }

      await loadSkills();

      setSuccess("Skill deleted successfully.");
    } catch (err) {
      setError(err.message);
    } finally {
      setDeletingId(null);
    }
  };

  // ============================================
  // SIDEBAR NAVIGATION
  // ============================================

  const changeSection = (section) => {
    setActiveSection(section);
    clearMessages();
  };

  const currentSection = SECTIONS.find(
    ([id]) => id === activeSection
  );

  // ============================================
  // ADMIN COMMAND CENTER UI
  // ============================================

  return (
    <div className="admin-layout">

      {/* ======================================
          LEFT SIDEBAR
      ====================================== */}

      <aside className="admin-sidebar">

        <div className="admin-brand">
          SkillGap <span>AI</span>
        </div>

        <p className="admin-sidebar-label">
          ADMIN WORKSPACE
        </p>

        <nav className="admin-nav">
          {SECTIONS.map(([section, icon, label]) => (
            <button
              key={section}
              type="button"
              className={`admin-nav-button ${
                activeSection === section ? "active" : ""
              }`}
              onClick={() => changeSection(section)}
              aria-current={
                activeSection === section
                  ? "page"
                  : undefined
              }
            >
              <span>{icon}</span>
              <span>{label}</span>
            </button>
          ))}
        </nav>

        <div style={{ marginTop: 32 }}>
          <button
            type="button"
            className="admin-nav-button"
            onClick={() => navigate("/dashboard")}
          >
            <span>←</span>
            <span>Back to Dashboard</span>
          </button>
        </div>

      </aside>

      {/* ======================================
          MAIN CONTENT
      ====================================== */}

      <main className="admin-main">

        {/* HEADER */}

        <header className="admin-header">
          <div>
            <p
              style={{
                color: "#fb923c",
                fontSize: 12,
                fontWeight: 700,
                letterSpacing: 2,
                marginBottom: 10,
              }}
            >
              SKILLGAP AI / ADMINISTRATION
            </p>

            <h1>Admin Command Center</h1>

            <p>
              Manage engineering careers, skills,
              and learning intelligence.
            </p>
          </div>

          <span
            style={{
              border: "1px solid #475569",
              borderRadius: 24,
              padding: "9px 15px",
              color: "#cbd5e1",
              whiteSpace: "nowrap",
            }}
          >
            🛡️ Administrator
          </span>
        </header>

        {/* ======================================
            DASHBOARD STATISTICS
        ====================================== */}

        <div className="admin-stats-grid">

          {/* ENGINEERING DOMAINS */}

          <div className="admin-stat-card">
            <span>🏛️ Engineering Domains</span>

            <strong>
              {domainsLoading ? "..." : domains.length}
            </strong>

            <small>Registered branches</small>
          </div>

          {/* JOB ROLES */}

          <div className="admin-stat-card">
            <span>💼 Job Roles</span>

            <strong>
              {rolesLoading ? "..." : jobRoles.length}
            </strong>

            <small>Registered career roles</small>
          </div>

          {/* SKILLS */}

          <div className="admin-stat-card">
            <span>🧠 Skills</span>

            <strong>
              {skillsLoading ? "..." : skills.length}
            </strong>

            <small>Available skills</small>
          </div>

        </div>

        {/* ======================================
            CURRENT MANAGEMENT SECTION
        ====================================== */}

        <div className="admin-section-heading">
          <div>
            <p>MANAGEMENT WORKSPACE</p>

            <h2>
              {currentSection?.[1]} {currentSection?.[2]}
            </h2>
          </div>
        </div>

        {/* ERROR MESSAGE */}

        {error && (
          <p className="error-message" role="alert">
            {error}
          </p>
        )}

        {/* SUCCESS MESSAGE */}

        {success && (
          <p className="admin-success" role="status">
            ✓ {success}
          </p>
        )}

        {/* ======================================
            ENGINEERING DOMAINS
        ====================================== */}

        {activeSection === "domains" && (
          <>

            {/* ADD OR EDIT DOMAIN */}

            <section className="admin-panel">
              <h2>
                {editingDomainId !== null
                  ? "Edit Engineering Domain"
                  : "Add Engineering Domain"}
              </h2>

              <form onSubmit={saveDomain}>

                <div className="form-group">
                  <label htmlFor="domain-name">
                    Domain Name
                  </label>

                  <input
                    id="domain-name"
                    type="text"
                    maxLength={255}
                    value={domainName}
                    onChange={(e) =>
                      setDomainName(e.target.value)
                    }
                    placeholder="e.g. Chemical Engineering"
                    required
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="domain-description">
                    Description
                  </label>

                  <textarea
                    id="domain-description"
                    rows={3}
                    value={domainDescription}
                    onChange={(e) =>
                      setDomainDescription(e.target.value)
                    }
                    placeholder="Describe this engineering domain"
                  />
                </div>

                <div className="admin-actions">
                  <button
                    className="analyze-button"
                    type="submit"
                    disabled={busy}
                  >
                    {saving
                      ? "Saving..."
                      : editingDomainId !== null
                        ? "Update Domain"
                        : "+ Add Domain"}
                  </button>

                  {editingDomainId !== null && (
                    <button
                      type="button"
                      onClick={resetDomainForm}
                      disabled={busy}
                    >
                      Cancel Edit
                    </button>
                  )}
                </div>

              </form>
            </section>

            {/* DOMAIN LIST */}

            <section className="admin-panel">

              <div className="admin-list-header">
                <h2>Engineering Domains</h2>

                <button
                  type="button"
                  onClick={() => {
                    clearMessages();
                    loadDomains();
                  }}
                  disabled={domainsLoading || busy}
                >
                  ↻ Refresh
                </button>
              </div>

              {domainsLoading ? (
                <p>Loading engineering domains...</p>
              ) : domains.length === 0 ? (
                <p>No engineering domains found.</p>
              ) : (
                <div className="admin-record-grid">
                  {domains.map((domain) => (
                    <article
                      className="admin-record-card"
                      key={domain.id}
                    >
                      <h3>{domain.name}</h3>

                      <p>
                        {domain.description ||
                          "No description added."}
                      </p>

                      <div className="admin-actions">

                        <button
                          type="button"
                          onClick={() => editDomain(domain)}
                          disabled={busy}
                        >
                          Edit
                        </button>

                        <button
                          type="button"
                          className="admin-delete-button"
                          onClick={() => deleteDomain(domain)}
                          disabled={busy}
                        >
                          {deletingId === domain.id
                            ? "Deleting..."
                            : "Delete"}
                        </button>

                      </div>
                    </article>
                  ))}
                </div>
              )}

            </section>
          </>
        )}

        {/* ======================================
            JOB ROLES
        ====================================== */}

        {activeSection === "roles" && (
          <>

            {/* ADD OR EDIT JOB ROLE */}

            <section className="admin-panel">
              <h2>
                {editingRoleId !== null
                  ? "Edit Job Role"
                  : "Add Job Role"}
              </h2>

              <form onSubmit={saveRole}>

                <div className="form-group">
                  <label htmlFor="role-name">
                    Job Role Name
                  </label>

                  <input
                    id="role-name"
                    type="text"
                    maxLength={255}
                    value={roleName}
                    onChange={(e) =>
                      setRoleName(e.target.value)
                    }
                    placeholder="e.g. Frontend Developer"
                    required
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="role-description">
                    Description
                  </label>

                  <textarea
                    id="role-description"
                    rows={3}
                    value={roleDescription}
                    onChange={(e) =>
                      setRoleDescription(e.target.value)
                    }
                    placeholder="Describe this job role"
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="role-domain">
                    Engineering Domain
                  </label>

                  <select
                    id="role-domain"
                    value={roleDomainId}
                    onChange={(e) =>
                      setRoleDomainId(e.target.value)
                    }
                    required
                  >
                    <option value="">
                      -- Select Engineering Domain --
                    </option>

                    {domains.map((domain) => (
                      <option
                        key={domain.id}
                        value={domain.id}
                      >
                        {domain.name}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="admin-actions">
                  <button
                    className="analyze-button"
                    type="submit"
                    disabled={busy || domainsLoading}
                  >
                    {saving
                      ? "Saving..."
                      : editingRoleId !== null
                        ? "Update Job Role"
                        : "+ Add Job Role"}
                  </button>

                  {editingRoleId !== null && (
                    <button
                      type="button"
                      onClick={resetRoleForm}
                      disabled={busy}
                    >
                      Cancel Edit
                    </button>
                  )}
                </div>

              </form>
            </section>

            {/* JOB ROLE LIST */}

            <section className="admin-panel">

              <div className="admin-list-header">
                <h2>Existing Job Roles</h2>

                <button
                  type="button"
                  onClick={() => {
                    clearMessages();
                    loadJobRoles();
                  }}
                  disabled={rolesLoading || busy}
                >
                  ↻ Refresh
                </button>
              </div>

              {rolesLoading ? (
                <p>Loading job roles...</p>
              ) : jobRoles.length === 0 ? (
                <p>No job roles found.</p>
              ) : (
                <div className="admin-record-grid">
                  {jobRoles.map((role) => (
                    <article
                      className="admin-record-card"
                      key={role.id}
                    >
                      <h3>{role.name}</h3>

                      <p>
                        {role.description ||
                          "No description added."}
                      </p>

                      <p>
                        <strong>
                          Engineering Domain:{" "}
                        </strong>

                        {role.engineeringDomainName ||
                          "Not assigned"}
                      </p>

                      <div className="admin-actions">

                        <button
                          type="button"
                          onClick={() => editRole(role)}
                          disabled={busy}
                        >
                          Edit
                        </button>

                        <button
                          type="button"
                          className="admin-delete-button"
                          onClick={() => deleteRole(role)}
                          disabled={busy}
                        >
                          {deletingId === role.id
                            ? "Deleting..."
                            : "Delete"}
                        </button>

                      </div>
                    </article>
                  ))}
                </div>
              )}

            </section>
          </>
        )}

        {/* ======================================
            JOB ROLE SKILL REQUIREMENTS
        ====================================== */}

        {activeSection === "requirements" && (
          <JobRoleRequirements />
        )}

        {/* ======================================
            SKILLS AND PROFICIENCY
        ====================================== */}

        {activeSection === "skills" && (
          <>

            {/* ADD OR EDIT SKILL */}

            <section className="admin-panel">
              <h2>
                {editingSkillId !== null
                  ? "Edit Skill"
                  : "Add Skill"}
              </h2>

              <form onSubmit={saveSkill}>

                <div className="form-group">
                  <label htmlFor="skill-name">
                    Skill Name
                  </label>

                  <input
                    id="skill-name"
                    type="text"
                    maxLength={255}
                    value={skillName}
                    onChange={(e) =>
                      setSkillName(e.target.value)
                    }
                    placeholder="e.g. JavaScript"
                    required
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="skill-category">
                    Skill Category
                  </label>

                  <input
                    id="skill-category"
                    type="text"
                    value={skillCategory}
                    onChange={(e) =>
                      setSkillCategory(e.target.value)
                    }
                    placeholder="e.g. Frontend"
                  />
                </div>

                <div className="admin-actions">
                  <button
                    className="analyze-button"
                    type="submit"
                    disabled={busy}
                  >
                    {saving
                      ? "Saving..."
                      : editingSkillId !== null
                        ? "Update Skill"
                        : "+ Add Skill"}
                  </button>

                  {editingSkillId !== null && (
                    <button
                      type="button"
                      onClick={resetSkillForm}
                      disabled={busy}
                    >
                      Cancel Edit
                    </button>
                  )}
                </div>

              </form>
            </section>

            {/* SKILL LIST */}

            <section className="admin-panel">

              <div className="admin-list-header">
                <h2>Existing Skills</h2>

                <button
                  type="button"
                  onClick={() => {
                    clearMessages();
                    loadSkills();
                  }}
                  disabled={skillsLoading || busy}
                >
                  ↻ Refresh
                </button>
              </div>

              {skillsLoading ? (
                <p>Loading skills...</p>
              ) : skills.length === 0 ? (
                <p>No skills found.</p>
              ) : (
                <div className="admin-record-grid">
                  {skills.map((skill) => (
                    <article
                      className="admin-record-card"
                      key={skill.id}
                    >
                      <h3>{skill.name}</h3>

                      <p>
                        <strong>Category: </strong>

                        {skill.category ||
                          "Not specified"}
                      </p>

                      <div className="admin-actions">

                        <button
                          type="button"
                          onClick={() => editSkill(skill)}
                          disabled={busy}
                        >
                          Edit
                        </button>

                        <button
                          type="button"
                          className="admin-delete-button"
                          onClick={() => deleteSkill(skill)}
                          disabled={busy}
                        >
                          {deletingId === skill.id
                            ? "Deleting..."
                            : "Delete"}
                        </button>

                      </div>
                    </article>
                  ))}
                </div>
              )}

            </section>
          </>
        )}

        {/* ======================================
            LEARNING RESOURCES MANAGEMENT
        ====================================== */}

        {activeSection === "resources" && (
          <LearningResourceManagement />
        )}

        {/* ======================================
            AI JOB ROLE GENERATOR
        ====================================== */}

        {activeSection === "ai" && (
          <AiJobRoleGenerator />
        )}

      </main>

    </div>
  );
}

export default AdminDashboard;
