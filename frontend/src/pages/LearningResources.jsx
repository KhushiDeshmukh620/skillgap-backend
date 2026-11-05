
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  ArrowLeft, ArrowRight, BookOpen, BrainCircuit,
  CheckCircle2, Compass, ExternalLink, GraduationCap,
  Layers3, LibraryBig, Search, Sparkles, Target, Zap
} from "lucide-react";
import DomainJobRoleSelector from "../components/DomainJobRoleSelector";
import "./LearningResources.css";

const API_BASE = "http://localhost:8080";

function LearningResources() {
  const navigate = useNavigate();

  const [jobRoleId, setJobRoleId] = useState("");
  const [skillResources, setSkillResources] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [loaded, setLoaded] = useState(false);
  const [search, setSearch] = useState("");

  const fetchData = async (endpoint, token) => {
    const response = await fetch(`${API_BASE}${endpoint}`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (response.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("userName");
      localStorage.removeItem("userRole");
      navigate("/login");
      throw new Error("Session expired. Please log in again.");
    }

    if (!response.ok) {
      let errorMessage = `Request failed (${response.status})`;
      try {
        const errorData = await response.json();
        errorMessage =
          errorData.message || errorData.detail || errorMessage;
      } catch {
        // Keep default error message.
      }
      throw new Error(errorMessage);
    }

    return response.json();
  };

  const handleGetResources = async () => {
    if (!jobRoleId) {
      setMessage("Please select a target job role.");
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    setLoading(true);
    setLoaded(false);
    setMessage("");
    setSkillResources([]);
    setSearch("");

    try {
      const skills = await fetchData(
        `/api/job-roles/${jobRoleId}/skills`,
        token
      );

      if (!Array.isArray(skills)) {
        throw new Error("Invalid skills response from server.");
      }

      if (skills.length === 0) {
        setMessage("No skills have been assigned to this job role.");
        setLoaded(true);
        return;
      }

      const results = await Promise.all(
        skills.map(async (skill) => {
          const resources = await fetchData(
            `/api/resources/skill/${skill.id}`,
            token
          );

          return {
            skillId: skill.id,
            skillName: skill.name,
            resources: Array.isArray(resources) ? resources : []
          };
        })
      );

      setSkillResources(results);
      setLoaded(true);
    } catch (error) {
      setMessage(error.message || "Unable to load learning resources.");
    } finally {
      setLoading(false);
    }
  };

  const getResourceLink = (resource, skillName) => {
    const url = String(resource.resourceUrl || "").trim();

    if (/^https?:\/\/\S+$/i.test(url)) return url;

    const query = encodeURIComponent(
      `${skillName} ${resource.title}`
    );

    if (
      String(resource.resourceType || "")
        .toLowerCase()
        .includes("youtube")
    ) {
      return `https://www.youtube.com/results?search_query=${query}`;
    }

    return `https://www.google.com/search?q=${query}`;
  };

  const totalResources = skillResources.reduce(
    (sum, item) => sum + item.resources.length,
    0
  );

  const skillsWithResources = skillResources.filter(
    (item) => item.resources.length > 0
  );

  const filteredSkills = skillsWithResources
    .map((item) => ({
      ...item,
      resources: item.resources.filter((resource) =>
        `${item.skillName} ${resource.title} ${resource.resourceType || ""}`
          .toLowerCase()
          .includes(search.toLowerCase().trim())
      )
    }))
    .filter((item) => item.resources.length > 0);

  return (
    <div className="lr-page">
      <main className="lr-shell">
        <header className="lr-topbar">
          <button
            type="button"
            className="lr-back"
            onClick={() => navigate("/dashboard")}
          >
            <ArrowLeft size={17} />
            Back to Dashboard
          </button>

          <span className="lr-live">
            <span className="lr-live-dot" />
            AI LEARNING INTELLIGENCE
          </span>
        </header>

        <section className="lr-hero">
          <div className="lr-hero-copy">
            <span className="lr-eyebrow">
              <Sparkles size={16} />
              YOUR PERSONAL LEARNING HUB
            </span>

            <h1>
              Learn the skills.
              <br />
              <span>Build your future.</span>
            </h1>

            <p>
              Unlock curated learning paths, practical tutorials,
              documentation, and career-focused resources to
              turn your ambitions into real technical skills.
            </p>

            <div className="lr-hero-tag">
              <BrainCircuit size={17} />
              Personalized learning for your career
            </div>
          </div>

          <div className="lr-hero-visual" aria-hidden="true">
            <div className="lr-orbit lr-orbit-outer" />
            <div className="lr-orbit lr-orbit-inner" />

            <div className="lr-orbit-core">
              <BookOpen size={48} />
            </div>

            <span className="lr-orbit-icon lr-orbit-icon-one">
              <GraduationCap size={23} />
            </span>

            <span className="lr-orbit-icon lr-orbit-icon-two">
              <Target size={22} />
            </span>

            <span className="lr-orbit-icon lr-orbit-icon-three">
              <Zap size={20} />
            </span>
          </div>
        </section>

        <section className="lr-panel">
          <div className="lr-panel-heading">
            <div>
              <span>CAREER EXPLORER</span>
              <h2>Choose your learning destination</h2>
              <p>
                Select your engineering branch and target career
                to explore recommended learning resources.
              </p>
            </div>

            <div className="lr-heading-icon">
              <Compass size={25} />
            </div>
          </div>

          <div className="lr-selector">
            <DomainJobRoleSelector
              selectedJobRoleId={jobRoleId}
              onJobRoleChange={(id) => {
                setJobRoleId(id);
                setSkillResources([]);
                setLoaded(false);
                setMessage("");
                setSearch("");
              }}
            />
          </div>

          <button
            type="button"
            className="lr-primary-button"
            onClick={handleGetResources}
            disabled={loading || !jobRoleId}
          >
            <LibraryBig size={19} />
            {loading ? "Loading Resources..." : "Explore Learning Resources"}
            {!loading && <ArrowRight size={18} />}
          </button>

          {message && (
            <p className="lr-error" role="alert">{message}</p>
          )}
        </section>

        {!loaded && !loading && (
          <section className="lr-empty">
            <div className="lr-empty-icon">
              <BookOpen size={29} />
            </div>
            <h2>Your learning library awaits.</h2>
            <p>
              Choose a career and explore the skills and
              learning materials available for your journey.
            </p>
          </section>
        )}

        {loaded && (
          <div className="lr-results">
            <div className="lr-results-heading">
              <div>
                <span>PERSONALIZED LEARNING LIBRARY</span>
                <h2>Explore your learning resources</h2>
                <p>
                  Everything you need to build the skills
                  required for your target career.
                </p>
              </div>

              <div className="lr-ready">
                <CheckCircle2 size={17} />
                Resources Loaded
              </div>
            </div>

            <div className="lr-stats">
              <article className="lr-stat">
                <div className="lr-stat-icon orange">
                  <Layers3 size={23} />
                </div>
                <span>Required Skills</span>
                <strong>{skillResources.length}</strong>
                <p>Skills defined for your selected career</p>
              </article>

              <article className="lr-stat">
                <div className="lr-stat-icon purple">
                  <BookOpen size={23} />
                </div>
                <span>Available Resources</span>
                <strong>{totalResources}</strong>
                <p>Saved tutorials, videos and documentation</p>
              </article>

              <article className="lr-stat">
                <div className="lr-stat-icon green">
                  <GraduationCap size={23} />
                </div>
                <span>Skills With Resources</span>
                <strong>{skillsWithResources.length}</strong>
                <p>Skills with learning materials available</p>
              </article>
            </div>

            <section className="lr-panel lr-library">
              <div className="lr-panel-heading">
                <div>
                  <span>KNOWLEDGE LIBRARY</span>
                  <h2>Learning materials</h2>
                  <p>
                    Find resources organized by skill,
                    topic and learning format.
                  </p>
                </div>

                <div className="lr-heading-icon">
                  <LibraryBig size={25} />
                </div>
              </div>

              {totalResources > 0 && (
                <div className="lr-search">
                  <Search size={20} />
                  <input
                    type="search"
                    placeholder="Search skills, tutorials or resources..."
                    value={search}
                    onChange={(event) => setSearch(event.target.value)}
                    aria-label="Search learning resources"
                  />
                </div>
              )}

              {totalResources === 0 ? (
                <div className="lr-empty lr-inner-empty">
                  <BookOpen size={30} />
                  <h3>No learning resources available</h3>
                  <p>
                    Learning materials have not yet been
                    saved for this job role.
                  </p>
                </div>
              ) : filteredSkills.length === 0 ? (
                <div className="lr-empty lr-inner-empty">
                  <Search size={30} />
                  <h3>No matching resources</h3>
                  <p>Try searching for a different skill or topic.</p>
                </div>
              ) : (
                <div className="lr-grid">
                  {filteredSkills.map((item) => (
                    <article
                      className="lr-skill-card"
                      key={item.skillId}
                    >
                      <div className="lr-skill-top">
                        <div className="lr-skill-icon">
                          <BookOpen size={23} />
                        </div>

                        <span className="lr-count">
                          {item.resources.length} Resources
                        </span>
                      </div>

                      <h3>{item.skillName}</h3>

                      <p className="lr-skill-description">
                        Explore learning materials to develop
                        your knowledge of {item.skillName}.
                      </p>

                      <div className="lr-resource-list">
                        {item.resources.map((resource, index) => (
                          <a
                            key={resource.id ?? index}
                            className="lr-resource-link"
                            href={getResourceLink(
                              resource,
                              item.skillName
                            )}
                            target="_blank"
                            rel="noopener noreferrer"
                          >
                            <div>
                              <strong>{resource.title}</strong>
                              <span>
                                {resource.resourceType ||
                                  "Learning Resource"}
                              </span>
                            </div>
                            <ExternalLink size={17} />
                          </a>
                        ))}
                      </div>
                    </article>
                  ))}
                </div>
              )}
            </section>
          </div>
        )}
      </main>
    </div>
  );
}

export default LearningResources;
