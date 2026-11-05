
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  ArrowLeft,
  ArrowRight,
  BookOpen,
  Check,
  CheckCircle2,
  ChevronDown,
  ChevronUp,
  Circle,
  Compass,
  ExternalLink,
  FileText,
  Flag,
  GraduationCap,
  Map,
  PlayCircle,
  RotateCcw,
  Search,
  Sparkles,
  Target,
  TrendingUp,
  Zap
} from "lucide-react";

import DomainJobRoleSelector from "../components/DomainJobRoleSelector";
import "./CareerRoadmap.css";

const API_BASE = "http://localhost:8080";

const STATUS_LABELS = {
  MISSING: "Not Learned",
  LEVEL_GAP: "Needs Improvement",
  MATCHED: "Matched"
};

function getResourceDetails(resource, skillName) {
  const title = String(resource?.title || "Learning Resource");
  const type = String(resource?.resourceType || "Resource");
  const rawUrl = String(resource?.resourceUrl || "").trim();

  const isVideo =
    /youtube|video/i.test(type) ||
    /youtube|video/i.test(title);

  const isDocumentation =
    /documentation|docs|official/i.test(type) ||
    /documentation|docs|official/i.test(title);

  let url = "";
  let isSearch = false;

  try {
    const parsed = new URL(rawUrl);

    if (parsed.protocol === "https:" || parsed.protocol === "http:") {
      url = parsed.href;

      const hostname = parsed.hostname.toLowerCase();
      const pathname = parsed.pathname.toLowerCase();

      isSearch =
        (hostname === "www.google.com" ||
          hostname === "google.com") &&
        pathname === "/search";

      isSearch =
        isSearch ||
        ((hostname === "www.youtube.com" ||
          hostname === "youtube.com") &&
          pathname === "/results");
    }
  } catch {
    // Invalid or missing URL: use a safe search fallback.
  }

  if (!url) {
    const query = encodeURIComponent(`${skillName} ${title}`);

    url = isVideo
      ? `https://www.youtube.com/results?search_query=${query}`
      : `https://www.google.com/search?q=${query}`;

    isSearch = true;
  }

  return {
    title,
    type: isVideo
      ? "Video Tutorial"
      : isDocumentation
      ? "Documentation"
      : /tutorial|course/i.test(type)
      ? "Tutorial / Course"
      : type,
    url,
    isSearch,
    Icon: isVideo
      ? PlayCircle
      : isDocumentation
      ? FileText
      : BookOpen
  };
}

function CareerRoadmap() {
  const navigate = useNavigate();

  const [jobRoleId, setJobRoleId] = useState("");
  const [result, setResult] = useState(null);
  const [completedSkills, setCompletedSkills] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [savingSkillId, setSavingSkillId] = useState(null);
  const [expandedSkills, setExpandedSkills] = useState({});

  const handleUnauthorized = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName");
    localStorage.removeItem("userRole");
    navigate("/login");
  };

  const apiFetch = async (path, options = {}) => {
    const token = localStorage.getItem("token");

    if (!token) {
      handleUnauthorized();
      throw new Error("Please log in again.");
    }

    const response = await fetch(`${API_BASE}${path}`, {
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

    const data = await response.json().catch(() => null);

    if (!response.ok) {
      throw new Error(
        data?.message || `Request failed (${response.status}).`
      );
    }

    return data;
  };

  const handleGenerateRoadmap = async () => {
    if (!jobRoleId) {
      setMessage("Please select a target job role.");
      return;
    }

    setLoading(true);
    setMessage("");
    setResult(null);
    setCompletedSkills([]);
    setExpandedSkills({});

    try {
      const [roadmapData, progressData] = await Promise.all([
        apiFetch(`/api/skill-gap/${jobRoleId}`),
        apiFetch(`/api/roadmap-progress/${jobRoleId}`)
      ]);

      setResult(roadmapData);

      setCompletedSkills(
        Array.isArray(progressData)
          ? progressData.map(Number)
          : []
      );
    } catch (error) {
      setMessage(error.message || "Failed to load roadmap.");
    } finally {
      setLoading(false);
    }
  };

  const handleToggleCompleted = async (skillId) => {
    if (savingSkillId !== null) return;

    const id = Number(skillId);
    const currentlyCompleted = completedSkills.includes(id);
    const nextCompleted = !currentlyCompleted;

    setSavingSkillId(id);
    setMessage("");

    try {
      await apiFetch(
        `/api/roadmap-progress/${jobRoleId}/${id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            completed: nextCompleted
          })
        }
      );

      setCompletedSkills((previous) =>
        nextCompleted
          ? [...new Set([...previous, id])]
          : previous.filter((item) => item !== id)
      );
    } catch (error) {
      setMessage(error.message || "Could not save progress.");
    } finally {
      setSavingSkillId(null);
    }
  };

  const roadmap = Array.isArray(result?.learningRoadmap)
    ? result.learningRoadmap
    : [];

  const recommendations = Array.isArray(result?.recommendations)
    ? result.recommendations
    : [];

  const completedCount = roadmap.filter((item) =>
    completedSkills.includes(Number(item.skillId))
  ).length;

  const progressPercentage =
    roadmap.length > 0
      ? Math.round((completedCount / roadmap.length) * 100)
      : 100;

  const nextSkill = roadmap.find(
    (item) => !completedSkills.includes(Number(item.skillId))
  );

  const remainingCount = roadmap.length - completedCount;

  const getResourcesForSkill = (skillId) => {
    const recommendation = recommendations.find(
      (item) => Number(item.skill?.id) === Number(skillId)
    );

    return Array.isArray(recommendation?.resources)
      ? recommendation.resources
      : [];
  };

  const toggleResources = (skillId) => {
    setExpandedSkills((previous) => ({
      ...previous,
      [skillId]: !previous[skillId]
    }));
  };

  const scrollToSkill = (skillId) => {
    document
      .getElementById(`roadmap-skill-${skillId}`)
      ?.scrollIntoView({
        behavior: "smooth",
        block: "center"
      });
  };

  return (
    <div className="career-roadmap-page">
      <main className="career-roadmap-container">

        {/* TOP BAR */}

        <header className="career-roadmap-topbar">
          <button
            type="button"
            className="career-roadmap-back"
            onClick={() => navigate("/dashboard")}
          >
            <ArrowLeft size={17} />
            Back to Dashboard
          </button>

          <span className="career-roadmap-live">
            <span className="career-roadmap-live-dot" />
            YOUR CAREER JOURNEY
          </span>
        </header>

        {/* HERO */}

        <section className="career-roadmap-hero">
          <div className="career-roadmap-hero-content">
            <span className="career-roadmap-eyebrow">
              <Sparkles size={16} />
              PERSONALIZED CAREER JOURNEY
            </span>

            <h1>
              Every skill is a step.
              <br />
              <span>Every step moves you forward.</span>
            </h1>

            <p>
              Follow your personalized learning path,
              achieve new milestones, and track your
              progress toward your dream career.
            </p>

            <div className="career-roadmap-hero-tag">
              <Compass size={16} />
              Your roadmap to career readiness
            </div>
          </div>

          <div
            className="career-roadmap-hero-art"
            aria-hidden="true"
          >
            <div className="career-roadmap-art-line" />

            <div className="career-roadmap-art-node first">
              <Check size={20} />
            </div>

            <div className="career-roadmap-art-node second">
              <Zap size={22} />
            </div>

            <div className="career-roadmap-art-node third">
              <Flag size={23} />
            </div>
          </div>
        </section>

        {/* CAREER SELECTOR */}

        <section className="career-roadmap-panel">
          <div className="career-roadmap-panel-heading">
            <div>
              <span>CAREER EXPLORER</span>
              <h2>Choose your destination</h2>
              <p>
                Select your target job role to generate
                a personalized learning journey.
              </p>
            </div>

            <div className="career-roadmap-heading-icon">
              <Target size={24} />
            </div>
          </div>

          <div className="career-roadmap-selector">
            <DomainJobRoleSelector
              selectedJobRoleId={jobRoleId}
              onJobRoleChange={(id) => {
                setJobRoleId(id);
                setResult(null);
                setCompletedSkills([]);
                setExpandedSkills({});
                setMessage("");
              }}
            />
          </div>

          <button
            type="button"
            className="career-roadmap-button"
            onClick={handleGenerateRoadmap}
            disabled={loading || !jobRoleId}
          >
            <Map size={18} />
            {loading
              ? "Generating Roadmap..."
              : "Generate Career Roadmap"}
            {!loading && <ArrowRight size={17} />}
          </button>
        </section>

        {/* ERROR MESSAGE */}

        {message && (
          <p className="career-roadmap-error" role="alert">
            {message}
          </p>
        )}

        {/* EMPTY STATE */}

        {!result && !loading && (
          <section className="career-roadmap-empty">
            <div className="career-roadmap-empty-icon">
              <Compass size={30} />
            </div>

            <h2>Your journey starts here.</h2>

            <p>
              Select a career to discover your personalized
              learning milestones and track your progress.
            </p>
          </section>
        )}

        {/* RESULTS */}

        {result && (
          <div className="career-roadmap-results">

            <div className="career-roadmap-results-heading">
              <div>
                <span>YOUR PERSONALIZED ROADMAP</span>
                <h2>Your learning journey</h2>
                <p>
                  Target career:{" "}
                  <strong>{result.targetRole}</strong>
                </p>
              </div>

              <span className="career-roadmap-ready">
                <CheckCircle2 size={16} />
                Roadmap Ready
              </span>
            </div>

            {/* PROGRESS DASHBOARD */}

            <section className="career-roadmap-progress-dashboard">
              <div className="career-roadmap-progress-ring-card">
                <span className="career-roadmap-card-label">
                  <TrendingUp size={17} />
                  JOURNEY COMPLETION
                </span>

                <div
                  className="career-roadmap-ring"
                  style={{
                    "--roadmap-progress": `${progressPercentage}%`
                  }}
                  role="progressbar"
                  aria-label="Career roadmap completion"
                  aria-valuemin={0}
                  aria-valuemax={100}
                  aria-valuenow={progressPercentage}
                >
                  <div className="career-roadmap-ring-inner">
                    <strong>{progressPercentage}%</strong>
                    <span>Completed</span>
                  </div>
                </div>

                <p>
                  {completedCount} of {roadmap.length} learning
                  steps completed
                </p>
              </div>

              <div className="career-roadmap-metrics">

                <article className="career-roadmap-metric">
                  <div className="career-roadmap-metric-icon orange">
                    <Target size={22} />
                  </div>

                  <span>Skill Match</span>
                  <strong>{result.matchPercentage ?? 0}%</strong>
                  <p>Current match with your target career</p>
                </article>

                <article className="career-roadmap-metric">
                  <div className="career-roadmap-metric-icon purple">
                    <TrendingUp size={22} />
                  </div>

                  <span>Proficiency Score</span>
                  <strong>{result.proficiencyScore ?? 0}%</strong>
                  <p>Your current skill proficiency</p>
                </article>

                <article className="career-roadmap-metric">
                  <div className="career-roadmap-metric-icon blue">
                    <GraduationCap size={22} />
                  </div>

                  <span>Learning Steps</span>
                  <strong>{roadmap.length}</strong>
                  <p>Total milestones in your journey</p>
                </article>

                <article className="career-roadmap-metric">
                  <div className="career-roadmap-metric-icon green">
                    <CheckCircle2 size={22} />
                  </div>

                  <span>Steps Remaining</span>
                  <strong>{remainingCount}</strong>
                  <p>Milestones left to complete</p>
                </article>

              </div>
            </section>

            {/* NEXT SKILL */}

            {nextSkill ? (
              <section className="career-roadmap-next">
                <div className="career-roadmap-next-icon">
                  <Zap size={25} />
                </div>

                <div className="career-roadmap-next-content">
                  <span>UP NEXT IN YOUR JOURNEY</span>
                  <h3>{nextSkill.skillName}</h3>
                  <p>
                    Continue your learning journey with
                    this recommended next milestone.
                  </p>
                </div>

                <button
                  type="button"
                  className="career-roadmap-button"
                  onClick={() => scrollToSkill(nextSkill.skillId)}
                >
                  Continue Learning
                  <ArrowRight size={17} />
                </button>
              </section>
            ) : (
              <section className="career-roadmap-next finished">
                <div className="career-roadmap-next-icon">
                  <CheckCircle2 size={25} />
                </div>

                <div className="career-roadmap-next-content">
                  <span>JOURNEY PROGRESS</span>
                  <h3>
                    {roadmap.length === 0
                      ? "All skill requirements matched!"
                      : "All roadmap milestones completed!"}
                  </h3>
                  <p>
                    Your roadmap has no remaining learning steps.
                  </p>
                </div>
              </section>
            )}

            {/* MILESTONE TIMELINE */}

            <section className="career-roadmap-timeline-panel">
              <div className="career-roadmap-panel-heading">
                <div>
                  <span>YOUR LEARNING PATH</span>
                  <h2>Career milestones</h2>
                  <p>
                    Complete each milestone and watch your
                    progress grow.
                  </p>
                </div>

                <div className="career-roadmap-heading-icon">
                  <Flag size={24} />
                </div>
              </div>

              {roadmap.length > 0 ? (
                <div className="career-roadmap-timeline">
                  {roadmap.map((item, index) => {
                    const skillId = Number(item.skillId);

                    const completed =
                      completedSkills.includes(skillId);

                    const resources =
                      getResourcesForSkill(skillId);

                    const expanded =
                      Boolean(expandedSkills[skillId]);

                    const isNext =
                      nextSkill &&
                      Number(nextSkill.skillId) === skillId;

                    return (
                      <article
                        id={`roadmap-skill-${skillId}`}
                        key={skillId}
                        className={
                          "career-roadmap-step" +
                          (completed ? " completed" : "") +
                          (isNext ? " active" : "")
                        }
                      >
                        <div className="career-roadmap-marker">
                          {completed
                            ? <Check size={20} />
                            : item.priority ?? index + 1}
                        </div>

                        <div className="career-roadmap-step-content">
                          <div className="career-roadmap-step-top">
                            <span className="career-roadmap-priority">
                              MILESTONE {item.priority ?? index + 1}
                            </span>

                            <span
                              className={
                                "career-roadmap-status " +
                                (completed
                                  ? "done"
                                  : isNext
                                  ? "current"
                                  : "pending")
                              }
                            >
                              {completed
                                ? "Completed"
                                : isNext
                                ? "Up Next"
                                : "Pending"}
                            </span>
                          </div>

                          <h3>{item.skillName}</h3>

                          <p className="career-roadmap-step-description">
                            {item.status === "MISSING"
                              ? "Start with the fundamentals and build a strong foundation."
                              : "Strengthen your knowledge to reach the required proficiency level."}
                          </p>

                          <div className="career-roadmap-levels">
                            <div>
                              <span>Current Level</span>
                              <strong>
                                {item.currentLevel || "Not Learned"}
                              </strong>
                            </div>

                            <ArrowRight size={18} />

                            <div>
                              <span>Target Level</span>
                              <strong>
                                {item.requiredLevel || "Not Specified"}
                              </strong>
                            </div>
                          </div>

                          <div className="career-roadmap-step-footer">
                            <span className="career-roadmap-gap-status">
                              <Circle size={9} fill="currentColor" />
                              {STATUS_LABELS[item.status] ||
                                item.status}
                            </span>

                            <button
                              type="button"
                              className={
                                "career-roadmap-complete-button" +
                                (completed ? " is-completed" : "")
                              }
                              disabled={savingSkillId !== null}
                              onClick={() =>
                                handleToggleCompleted(item.skillId)
                              }
                            >
                              {savingSkillId === skillId ? (
                                "Saving..."
                              ) : completed ? (
                                <>
                                  <RotateCcw size={16} />
                                  Mark as Incomplete
                                </>
                              ) : (
                                <>
                                  <Check size={16} />
                                  Mark as Completed
                                </>
                              )}
                            </button>
                          </div>

                          {/* IMPROVED LEARNING RESOURCES */}

                          {resources.length > 0 && (
                            <div className="career-roadmap-resources">
                              <button
                                type="button"
                                className="career-roadmap-resource-toggle"
                                onClick={() => toggleResources(skillId)}
                                aria-expanded={expanded}
                              >
                                <span>
                                  <BookOpen size={17} />
                                  Learning Resources ({resources.length})
                                </span>

                                {expanded
                                  ? <ChevronUp size={18} />
                                  : <ChevronDown size={18} />}
                              </button>

                              {expanded && (
                                <div className="career-roadmap-resource-list">
                                  {resources.map((resource, resourceIndex) => {
                                    const details = getResourceDetails(
                                      resource,
                                      item.skillName
                                    );

                                    const ResourceIcon = details.Icon;

                                    return (
                                      <a
                                        key={resource.id ?? resourceIndex}
                                        className="career-roadmap-resource"
                                        href={details.url}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        title={
                                          details.isSearch
                                            ? "Opens search results"
                                            : "Opens learning resource"
                                        }
                                      >
                                        <span
                                          style={{
                                            display: "flex",
                                            alignItems: "center",
                                            gap: "13px",
                                            flex: 1,
                                            minWidth: 0
                                          }}
                                        >
                                          <span
                                            style={{
                                              width: 40,
                                              height: 40,
                                              flexShrink: 0,
                                              display: "grid",
                                              placeItems: "center",
                                              borderRadius: 11,
                                              background:
                                                "rgba(249,115,22,0.10)",
                                              color: "#fb923c"
                                            }}
                                          >
                                            <ResourceIcon size={19} />
                                          </span>

                                          <span
                                            style={{
                                              display: "flex",
                                              flexDirection: "column",
                                              alignItems: "flex-start",
                                              gap: 5,
                                              minWidth: 0
                                            }}
                                          >
                                            <strong
                                              style={{
                                                color: "#f8fafc",
                                                fontSize: 13,
                                                fontWeight: 700,
                                                overflowWrap: "anywhere"
                                              }}
                                            >
                                              {details.title}
                                            </strong>

                                            <span
                                              style={{
                                                display: "flex",
                                                alignItems: "center",
                                                gap: 6,
                                                flexWrap: "wrap",
                                                color: "#94a3b8",
                                                fontSize: 11
                                              }}
                                            >
                                              {details.type}
                                              <span aria-hidden="true">•</span>
                                              {details.isSearch ? (
                                                <>
                                                  <Search size={12} />
                                                  Search Results
                                                </>
                                              ) : (
                                                <>
                                                  <CheckCircle2 size={12} />
                                                  Direct Link
                                                </>
                                              )}
                                            </span>
                                          </span>
                                        </span>

                                        <ExternalLink size={16} />
                                      </a>
                                    );
                                  })}
                                </div>
                              )}
                            </div>
                          )}
                        </div>
                      </article>
                    );
                  })}
                </div>
              ) : (
                <div className="career-roadmap-empty inside">
                  <CheckCircle2 size={34} />
                  <h3>No skill gaps remaining</h3>
                  <p>
                    You currently meet the recorded skill
                    requirements for this target job role.
                  </p>
                </div>
              )}
            </section>
          </div>
        )}
      </main>
    </div>
  );
}

export default CareerRoadmap;
