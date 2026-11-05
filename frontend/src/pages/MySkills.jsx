
import { useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  ArrowLeft,
  ArrowRight,
  BrainCircuit,
  CheckCircle2,
  ChevronDown,
  Layers3,
  Plus,
  Search,
  Sparkles,
  Target,
  Trash2,
  TrendingUp,
  Zap
} from "lucide-react";

import "./MySkills.css";

const API = "http://localhost:8080";

const LEVELS = {
  BEGINNER: {
    value: 33,
    label: "Beginner"
  },
  INTERMEDIATE: {
    value: 66,
    label: "Intermediate"
  },
  ADVANCED: {
    value: 100,
    label: "Advanced"
  }
};

function MySkills() {
  const navigate = useNavigate();

  const [skills, setSkills] = useState([]);
  const [userSkills, setUserSkills] = useState([]);

  const [selectedSkillId, setSelectedSkillId] = useState("");
  const [proficiencyLevel, setProficiencyLevel] =
    useState("BEGINNER");

  const [search, setSearch] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(true);
  const [deletingSkillId, setDeletingSkillId] = useState(null);

  const handleUnauthorized = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName");
    navigate("/login", { replace: true });
  };

  // Common authenticated API function
  const apiFetch = async (path, options = {}) => {
    const token = localStorage.getItem("token");

    if (!token) {
      handleUnauthorized();
      throw new Error("Please log in again.");
    }

    const response = await fetch(`${API}${path}`, {
      ...options,
      headers: {
        ...options.headers,
        Authorization: `Bearer ${token}`
      }
    });

    if (response.status === 401) {
      handleUnauthorized();
      throw new Error("Your session has expired.");
    }

    if (!response.ok) {
      let details = `Request failed (${response.status}).`;

      try {
        const data = await response.json();
        details =
          data.message ||
          data.detail ||
          data.error ||
          details;
      } catch {
        // Some API errors have no JSON response.
      }

      throw new Error(details);
    }

    // DELETE endpoint returns HTTP 204 No Content.
    if (response.status === 204) {
      return null;
    }

    const contentType =
      response.headers.get("content-type") || "";

    if (contentType.includes("application/json")) {
      return response.json();
    }

    return null;
  };

  // Load all available skills and student's skills
  const loadSkills = async () => {
    setFetching(true);
    setError("");

    try {
      const [available, current] = await Promise.all([
        apiFetch("/api/skills"),
        apiFetch("/api/user-skills")
      ]);

      const availableSkills = Array.isArray(available)
        ? available
        : [];

      const currentSkills = Array.isArray(current)
        ? current
        : [];

      setSkills(availableSkills);
      setUserSkills(currentSkills);

      setSelectedSkillId((previous) => {
        const exists = availableSkills.some(
          (skill) =>
            String(skill.id) === String(previous)
        );

        return exists
          ? previous
          : availableSkills.length > 0
          ? String(availableSkills[0].id)
          : "";
      });
    } catch (err) {
      setError(err.message);
    } finally {
      setFetching(false);
    }
  };

  useEffect(() => {
    loadSkills();
  }, []);

  // Add or update a skill
  const handleSaveSkill = async (event) => {
    event.preventDefault();

    if (!selectedSkillId) {
      setError("Please select a skill.");
      return;
    }

    setLoading(true);
    setMessage("");
    setError("");

    try {
      await apiFetch(
        `/api/user-skills/${selectedSkillId}/${proficiencyLevel}`,
        {
          method: "POST"
        }
      );

      await loadSkills();

      setMessage(
        "Your skill has been saved successfully."
      );
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Delete skill from the logged-in user's profile
  const handleDeleteSkill = async (skill) => {
    const confirmed = window.confirm(
      `Are you sure you want to remove "${skill.skillName}" from your profile?\n\nThis will not delete the skill from the main Skills database.`
    );

    if (!confirmed) {
      return;
    }

    // Use skillId, not the UserSkill record's id.
    const skillId = skill.skillId;

    if (skillId == null) {
      setError(
        "Cannot delete this skill because its skill ID is missing."
      );
      return;
    }

    setDeletingSkillId(skillId);
    setMessage("");
    setError("");

    try {
      await apiFetch(
        `/api/user-skills/${skillId}`,
        {
          method: "DELETE"
        }
      );

      // Immediately remove the deleted skill from the UI.
      setUserSkills((previous) =>
        previous.filter(
          (item) =>
            String(item.skillId) !== String(skillId)
        )
      );

      // Refresh the collection and statistics.
      await loadSkills();

      setMessage(
        `"${skill.skillName}" was removed from your profile.`
      );
    } catch (err) {
      setError(
        err.message || "Failed to delete skill."
      );
    } finally {
      setDeletingSkillId(null);
    }
  };

  // Search skills
  const filteredSkills = useMemo(() => {
    const query = search.trim().toLowerCase();

    return userSkills.filter((skill) =>
      `${skill.skillName || ""} ${skill.category || ""}`
        .toLowerCase()
        .includes(query)
    );
  }, [userSkills, search]);

  // Dashboard statistics
  const advancedCount = userSkills.filter(
    (skill) =>
      skill.proficiencyLevel === "ADVANCED"
  ).length;

  const intermediateCount = userSkills.filter(
    (skill) =>
      skill.proficiencyLevel === "INTERMEDIATE"
  ).length;

  const categories = new Set(
    userSkills
      .map((skill) => skill.category)
      .filter(Boolean)
  ).size;

  const userName =
    localStorage.getItem("userName") || "Learner";

  const firstName =
    userName.trim().split(/\s+/)[0];

  return (
    <div className="sg-skills-page">
      <div className="sg-skills-shell">

        {/* TOP NAVIGATION */}
        <div className="sg-skills-topbar">
          <Link
            to="/dashboard"
            className="sg-skills-back"
          >
            <ArrowLeft size={17} />
            Back to Dashboard
          </Link>

          <div className="sg-skills-topbar-status">
            <span className="sg-skills-status-dot" />
            SKILL INTELLIGENCE WORKSPACE
          </div>
        </div>

        {/* HERO SECTION */}
        <section className="sg-skills-hero">
          <div className="sg-skills-hero-copy">

            <div className="sg-skills-eyebrow">
              <Sparkles size={15} />
              YOUR PERSONAL GROWTH ENGINE
            </div>

            <h1>
              Your skills.
              <br />
              <span>Your competitive edge.</span>
            </h1>

            <p>
              Build your technical profile, measure your
              expertise, and move closer to your dream
              career.
            </p>

            <div className="sg-skills-hero-person">
              <BrainCircuit size={18} />
              Skill profile for{" "}
              <strong>{firstName}</strong>
            </div>
          </div>

          {/* Animated visual */}
          <div
            className="sg-skills-hero-art"
            aria-hidden="true"
          >
            <div className="sg-skills-orbit sg-skills-orbit-one" />
            <div className="sg-skills-orbit sg-skills-orbit-two" />

            <div className="sg-skills-orbit-core">
              <BrainCircuit size={49} />
            </div>

            <span className="sg-skills-floating-icon sg-skills-float-one">
              <Zap size={21} />
            </span>

            <span className="sg-skills-floating-icon sg-skills-float-two">
              <TrendingUp size={21} />
            </span>
          </div>
        </section>

        {/* OVERVIEW HEADING */}
        <div className="sg-skills-section-label">
          <span>YOUR SKILL OVERVIEW</span>
          <h2>Progress at a glance</h2>
        </div>

        {/* SKILL STATISTICS */}
        <section className="sg-skills-stats">
          {[
            {
              label: "Total Skills",
              value: userSkills.length,
              icon: Layers3,
              tone: "orange"
            },
            {
              label: "Advanced Skills",
              value: advancedCount,
              icon: Zap,
              tone: "violet"
            },
            {
              label: "Intermediate Skills",
              value: intermediateCount,
              icon: TrendingUp,
              tone: "blue"
            },
            {
              label: "Skill Categories",
              value: categories,
              icon: CheckCircle2,
              tone: "green"
            }
          ].map(
            ({ label, value, icon: Icon, tone }) => (
              <article
                className={`sg-skills-stat sg-skills-tone-${tone}`}
                key={label}
              >
                <div className="sg-skills-stat-icon">
                  <Icon size={22} />
                </div>

                <span>{label}</span>

                <strong>
                  {fetching ? "—" : value}
                </strong>
              </article>
            )
          )}
        </section>

        {/* MAIN WORKSPACE */}
        <div className="sg-skills-workspace">

          {/* ADD / UPDATE SKILL */}
          <section className="sg-skills-panel sg-skills-editor">

            <div className="sg-skills-panel-heading">
              <div className="sg-skills-panel-icon">
                <Plus size={21} />
              </div>

              <div>
                <span>SKILL BUILDER</span>
                <h2>Add or update skill</h2>
                <p>
                  Keep your technical expertise up to date.
                </p>
              </div>
            </div>

            <form onSubmit={handleSaveSkill}>

              <label className="sg-skills-field">
                <span>Choose a skill</span>

                <div className="sg-skills-select-wrap">
                  <select
                    value={selectedSkillId}
                    onChange={(e) =>
                      setSelectedSkillId(e.target.value)
                    }
                    disabled={
                      fetching ||
                      skills.length === 0 ||
                      loading
                    }
                  >
                    {skills.length === 0 && (
                      <option value="">
                        No skills available
                      </option>
                    )}

                    {skills.map((skill) => (
                      <option
                        key={skill.id}
                        value={String(skill.id)}
                      >
                        {skill.name}
                      </option>
                    ))}
                  </select>

                  <ChevronDown size={18} />
                </div>
              </label>

              {/* PROFICIENCY LEVEL */}
              <div className="sg-skills-field">
                <span>Proficiency level</span>

                <div className="sg-skills-level-options">
                  {Object.keys(LEVELS).map((level) => (
                    <button
                      type="button"
                      key={level}
                      disabled={loading}
                      className={
                        "sg-skills-level-option" +
                        (proficiencyLevel === level
                          ? " sg-skills-level-active"
                          : "")
                      }
                      onClick={() =>
                        setProficiencyLevel(level)
                      }
                    >
                      <span className="sg-skills-level-indicator" />
                      {LEVELS[level].label}
                    </button>
                  ))}
                </div>
              </div>

              {/* SAVE BUTTON */}
              <button
                className="sg-skills-save"
                type="submit"
                disabled={
                  loading ||
                  fetching ||
                  !selectedSkillId
                }
              >
                <Plus size={19} />

                {loading
                  ? "Saving skill..."
                  : "Save My Skill"}

                {!loading && (
                  <ArrowRight size={18} />
                )}
              </button>
            </form>

            {/* SUCCESS MESSAGE */}
            {message && (
              <div
                className="sg-skills-success"
                role="status"
              >
                <CheckCircle2 size={18} />
                {message}
              </div>
            )}

            {/* ERROR MESSAGE */}
            {error && (
              <div
                className="sg-skills-error"
                role="alert"
              >
                {error}
              </div>
            )}
          </section>

          {/* SKILL COLLECTION */}
          <section className="sg-skills-panel sg-skills-library">

            <div className="sg-skills-library-heading">
              <div>
                <span>YOUR TECHNICAL PROFILE</span>
                <h2>My skill collection</h2>
                <p>
                  {userSkills.length} skills in your
                  learning portfolio
                </p>
              </div>

              {/* SEARCH */}
              <div className="sg-skills-search">
                <Search size={18} />

                <input
                  type="search"
                  placeholder="Search skills..."
                  value={search}
                  onChange={(e) =>
                    setSearch(e.target.value)
                  }
                  aria-label="Search your skills"
                />
              </div>
            </div>

            {/* LOADING / EMPTY / SKILLS */}
            {fetching ? (
              <div className="sg-skills-empty">
                Loading your skill collection...
              </div>
            ) : filteredSkills.length === 0 ? (
              <div className="sg-skills-empty">
                <BrainCircuit size={35} />

                <h3>
                  {search
                    ? "No matching skills"
                    : "Start building your profile"}
                </h3>

                <p>
                  {search
                    ? "Try searching for another skill."
                    : "Add your first skill to start tracking your growth."}
                </p>
              </div>
            ) : (
              <div className="sg-skills-grid">

                {filteredSkills.map((skill) => {
                  const level =
                    LEVELS[skill.proficiencyLevel] ||
                    LEVELS.BEGINNER;

                  const isDeleting =
                    String(deletingSkillId) ===
                    String(skill.skillId);

                  return (
                    <article
                      className="sg-skills-card"
                      key={skill.id}
                    >

                      {/* CARD HEADER */}
                      <div className="sg-skills-card-top">
                        <div className="sg-skills-card-symbol">
                          <BrainCircuit size={21} />
                        </div>

                        <span
                          className={`sg-skills-badge sg-skills-badge-${skill.proficiencyLevel?.toLowerCase()}`}
                        >
                          {level.label}
                        </span>
                      </div>

                      <h3>{skill.skillName}</h3>

                      <p>
                        {skill.category ||
                          "Technical Skill"}
                      </p>

                      {/* PROFICIENCY BAR */}
                      <div className="sg-skills-progress-label">
                        <span>Proficiency</span>
                        <strong>
                          {level.value}%
                        </strong>
                      </div>

                      <div
                        className="sg-skills-progress-track"
                        role="progressbar"
                        aria-valuenow={level.value}
                        aria-valuemin={0}
                        aria-valuemax={100}
                        aria-label={`${skill.skillName} proficiency`}
                      >
                        <div
                          className="sg-skills-progress-fill"
                          style={{
                            width: `${level.value}%`
                          }}
                        />
                      </div>

                      {/* UPDATE BUTTON */}
                      <button
                        type="button"
                        className="sg-skills-card-edit"
                        disabled={loading || isDeleting}
                        onClick={() => {
                          const selected = skills.find(
                            (item) =>
                              String(item.id) ===
                                String(skill.skillId) ||
                              item.name === skill.skillName
                          );

                          if (selected) {
                            setSelectedSkillId(
                              String(selected.id)
                            );

                            setProficiencyLevel(
                              skill.proficiencyLevel
                            );

                            window.scrollTo({
                              top: 450,
                              behavior: "smooth"
                            });
                          }
                        }}
                      >
                        Update proficiency
                        <ArrowRight size={15} />
                      </button>

                      {/* DELETE BUTTON */}
                      <button
                        type="button"
                        className="sg-skills-card-delete"
                        disabled={
                          loading ||
                          deletingSkillId !== null
                        }
                        onClick={() =>
                          handleDeleteSkill(skill)
                        }
                      >
                        <Trash2 size={16} />

                        {isDeleting
                          ? "Deleting..."
                          : "Delete Skill"}
                      </button>

                    </article>
                  );
                })}

              </div>
            )}
          </section>
        </div>

        {/* NEXT STEP */}
        <section className="sg-skills-next">
          <div className="sg-skills-next-icon">
            <Target size={24} />
          </div>

          <div>
            <span>WHAT'S NEXT?</span>

            <h2>
              See how your skills match your dream career.
            </h2>

            <p>
              Discover missing skills and unlock a
              personalized learning roadmap.
            </p>
          </div>

          <Link to="/skill-gap">
            Analyze Skill Gap
            <ArrowRight size={17} />
          </Link>
        </section>

      </div>
    </div>
  );
}

export default MySkills;
