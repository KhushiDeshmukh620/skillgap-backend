
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  ArrowLeft,
  ArrowRight,
  BookOpen,
  BrainCircuit,
  CheckCircle2,
  ChevronDown,
  Compass,
  ExternalLink,
  GraduationCap,
  Layers3,
  Sparkles,
  Target,
  TrendingUp,
  Zap
} from "lucide-react";

import "./MySkillGap.css";

const API = "http://localhost:8080";

const LEVEL_VALUES = {
  BEGINNER: 33,
  INTERMEDIATE: 66,
  ADVANCED: 100
};

const STATUS_LABELS = {
  MATCHED: "Matched",
  LEVEL_GAP: "Improve Level",
  MISSING: "Not Learned"
};

function MySkillGap() {
  const navigate = useNavigate();

  const [user, setUser] = useState(null);
  const [domains, setDomains] = useState([]);
  const [domainId, setDomainId] = useState("");
  const [jobRoles, setJobRoles] = useState([]);
  const [jobRoleId, setJobRoleId] = useState("");

  const [result, setResult] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [rolesLoading, setRolesLoading] = useState(false);

  const handleUnauthorized = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName");
    navigate("/login", { replace: true });
  };

  // Common authenticated GET request
  const getData = async (path, signal) => {
    const token = localStorage.getItem("token");

    if (!token) {
      handleUnauthorized();
      throw new Error("Please log in again.");
    }

    const response = await fetch(`${API}${path}`, {
      headers: {
        Authorization: `Bearer ${token}`
      },
      signal
    });

    if (response.status === 401) {
      handleUnauthorized();
      throw new Error("Session expired.");
    }

    if (!response.ok) {
      throw new Error(
        `Could not load data (${response.status}).`
      );
    }

    return response.json();
  };

  // Load logged-in user and engineering domains
  useEffect(() => {
    const controller = new AbortController();

    async function loadInitialData() {
      try {
        const [userData, domainData] = await Promise.all([
          getData("/api/users/me", controller.signal),
          getData(
            "/api/engineering-domains",
            controller.signal
          )
        ]);

        if (controller.signal.aborted) return;

        setUser(userData);
        setDomains(
          Array.isArray(domainData) ? domainData : []
        );
      } catch (error) {
        if (
          error.name !== "AbortError" &&
          !controller.signal.aborted
        ) {
          setMessage(error.message);
        }
      }
    }

    loadInitialData();

    return () => controller.abort();
  }, [navigate]);

  // Load job roles after selecting engineering domain
  useEffect(() => {
    setJobRoles([]);
    setJobRoleId("");
    setResult(null);
    setMessage("");

    if (!domainId) {
      setRolesLoading(false);
      return;
    }

    const controller = new AbortController();

    async function loadRoles() {
      setRolesLoading(true);

      try {
        const data = await getData(
          `/api/job-roles/domain/${domainId}`,
          controller.signal
        );

        if (controller.signal.aborted) return;

        setJobRoles(
          Array.isArray(data) ? data : []
        );
      } catch (error) {
        if (
          error.name !== "AbortError" &&
          !controller.signal.aborted
        ) {
          setMessage(error.message);
        }
      } finally {
        if (!controller.signal.aborted) {
          setRolesLoading(false);
        }
      }
    }

    loadRoles();

    return () => controller.abort();
  }, [domainId]);

  // Analyze skill gap
  const handleAnalyze = async () => {
    if (!domainId || !jobRoleId) {
      setMessage(
        "Please select an engineering branch and job role."
      );
      return;
    }

    setLoading(true);
    setMessage("");
    setResult(null);

    try {
      const data = await getData(
        `/api/skill-gap/${jobRoleId}`
      );

      setResult(data);
    } catch (error) {
      setMessage(
        error.message || "Skill gap analysis failed."
      );
    } finally {
      setLoading(false);
    }
  };

  // Existing backend percentages remain unchanged
  const match = result
    ? Math.min(
        100,
        Math.max(
          0,
          Number(result.matchPercentage) || 0
        )
      )
    : 0;

  const proficiency = result
    ? Math.min(
        100,
        Math.max(
          0,
          Number(result.proficiencyScore) || 0
        )
      )
    : 0;

  // Calculate accurate metrics from proficiency gaps
  const gaps = Array.isArray(result?.proficiencyGaps)
    ? result.proficiencyGaps
    : [];

  const acquiredCount = gaps.filter(
    (gap) =>
      gap.status === "MATCHED" ||
      gap.status === "LEVEL_GAP"
  ).length;

  const requiredLevelMetCount = gaps.filter(
    (gap) => gap.status === "MATCHED"
  ).length;

  const missingCount = gaps.filter(
    (gap) => gap.status === "MISSING"
  ).length;

  const improvementCount = gaps.filter(
    (gap) => gap.status === "LEVEL_GAP"
  ).length;

  return (
    <div className="sg-gap-page">
      <div className="sg-gap-shell">

        {/* TOP BAR */}
        <header className="sg-gap-topbar">
          <Link
            to="/dashboard"
            className="sg-gap-back"
          >
            <ArrowLeft size={17} />
            Back to Dashboard
          </Link>

          <span className="sg-gap-topbar-status">
            <span className="sg-gap-live-dot" />
            AI CAREER INTELLIGENCE
          </span>
        </header>

        {/* HERO */}
        <section className="sg-gap-hero">
          <div className="sg-gap-hero-copy">
            <span className="sg-gap-eyebrow">
              <Sparkles size={16} />
              YOUR PERSONAL CAREER INTELLIGENCE
            </span>

            <h1>
              Decode your skills.
              <br />
              <span>Design your future.</span>
            </h1>

            <p>
              Understand where you stand, uncover your
              skill gaps, and discover the path toward
              your target engineering career.
            </p>

            <div className="sg-gap-hero-tag">
              <BrainCircuit size={17} />
              Personalized analysis
              {user?.name && (
                <strong>for {user.name}</strong>
              )}
            </div>
          </div>

          {/* ANIMATED ORBIT VISUAL */}
          <div
            className="sg-gap-hero-visual"
            aria-hidden="true"
          >
            <div className="sg-gap-orbit sg-gap-orbit-outer" />
            <div className="sg-gap-orbit sg-gap-orbit-inner" />

            <div className="sg-gap-orbit-core">
              <BrainCircuit size={48} />
            </div>

            <span className="sg-gap-orbit-icon sg-gap-orbit-icon-one">
              <Target size={22} />
            </span>

            <span className="sg-gap-orbit-icon sg-gap-orbit-icon-two">
              <GraduationCap size={22} />
            </span>
          </div>
        </section>

        {/* CAREER EXPLORER */}
        <section className="sg-gap-selection">
          <div className="sg-gap-section-heading">
            <div>
              <span>CAREER EXPLORER</span>
              <h2>Choose your target career</h2>

              <p>
                Select your engineering domain and career
                to generate a personalized skill gap report.
              </p>
            </div>

            <div className="sg-gap-section-icon">
              <Compass size={24} />
            </div>
          </div>

          <div className="sg-gap-controls">

            {/* ENGINEERING DOMAIN */}
            <label className="sg-gap-field">
              <span>Engineering Domain</span>

              <div className="sg-gap-select-wrap">
                <select
                  value={domainId}
                  onChange={(e) => {
                    setDomainId(e.target.value);
                    setJobRoleId("");
                    setResult(null);
                  }}
                >
                  <option value="" disabled>
                    Select Branch
                  </option>

                  {domains.length === 0 ? (
                    <option value="" disabled>
                      No domains available
                    </option>
                  ) : (
                    domains.map((domain) => (
                      <option
                        key={domain.id}
                        value={String(domain.id)}
                      >
                        {domain.name}
                      </option>
                    ))
                  )}
                </select>

                <ChevronDown
                  size={18}
                  aria-hidden="true"
                />
              </div>
            </label>

            {/* TARGET JOB ROLE */}
            <label className="sg-gap-field">
              <span>Target Job Role</span>

              <div className="sg-gap-select-wrap">
                <select
                  value={jobRoleId}
                  disabled={
                    !domainId ||
                    rolesLoading ||
                    jobRoles.length === 0
                  }
                  onChange={(e) => {
                    setJobRoleId(e.target.value);
                    setResult(null);
                  }}
                >
                  <option value="" disabled>
                    {rolesLoading
                      ? "Loading job roles..."
                      : !domainId
                      ? "Select Branch First"
                      : jobRoles.length === 0
                      ? "No Job Roles Available"
                      : "Select Job Role"}
                  </option>

                  {jobRoles.map((role) => (
                    <option
                      key={role.id}
                      value={String(role.id)}
                    >
                      {role.name}
                    </option>
                  ))}
                </select>

                <ChevronDown
                  size={18}
                  aria-hidden="true"
                />
              </div>
            </label>

            {/* ANALYZE BUTTON */}
            <button
              className="sg-gap-analyze-button"
              type="button"
              onClick={handleAnalyze}
              disabled={
                loading ||
                rolesLoading ||
                !domainId ||
                !jobRoleId
              }
            >
              <Zap size={19} />

              {loading
                ? "Analyzing..."
                : "Analyze Skill Gap"}

              {!loading && (
                <ArrowRight size={17} />
              )}
            </button>
          </div>

          {message && (
            <p
              className="sg-gap-error"
              role="alert"
            >
              {message}
            </p>
          )}
        </section>

        {/* EMPTY STATE */}
        {!result && !loading && (
          <section className="sg-gap-empty">
            <div className="sg-gap-empty-icon">
              <Target size={28} />
            </div>

            <h2>
              Your career intelligence report awaits.
            </h2>

            <p>
              Choose a job role and click Analyze Skill Gap
              to reveal your personalized career insights.
            </p>
          </section>
        )}

        {/* ANALYSIS RESULTS */}
        {result && (
          <div className="sg-gap-results">

            <div className="sg-gap-results-title">
              <div>
                <span>YOUR PERSONALIZED REPORT</span>

                <h2>Career intelligence overview</h2>

                <p>
                  Target career:{" "}
                  <strong>{result.targetRole}</strong>
                </p>
              </div>

              <span className="sg-gap-report-ready">
                <CheckCircle2 size={16} />
                Analysis Complete
              </span>
            </div>

            {/* CAREER MATCH OVERVIEW */}
            <section className="sg-gap-overview">
              <article className="sg-gap-match-card">
                <div className="sg-gap-card-kicker">
                  <Target size={18} />
                  CAREER MATCH
                </div>

                <div
                  className="sg-gap-match-ring"
                  style={{
                    "--sg-match-value": `${match}%`
                  }}
                >
                  <div className="sg-gap-match-inner">
                    <strong>{match}%</strong>
                    <span>Skill Match</span>
                  </div>
                </div>

                <p>
                  {acquiredCount} of{" "}
                  {result.totalRequiredSkills} required
                  skills recorded in your profile.
                </p>
              </article>

              {/* METRIC CARDS */}
              <div className="sg-gap-metrics">

                {/* PROFICIENCY SCORE */}
                <article className="sg-gap-metric">
                  <div className="sg-gap-metric-icon sg-gap-violet">
                    <TrendingUp size={23} />
                  </div>

                  <span>Proficiency Score</span>
                  <strong>{proficiency}%</strong>

                  <div className="sg-gap-metric-track">
                    <div
                      style={{
                        width: `${proficiency}%`
                      }}
                    />
                  </div>
                </article>

                {/* SKILLS ACQUIRED */}
                <article className="sg-gap-metric">
                  <div className="sg-gap-metric-icon sg-gap-green">
                    <CheckCircle2 size={23} />
                  </div>

                  <span>Skills Acquired</span>

                  <strong>
                    {acquiredCount} /{" "}
                    {result.totalRequiredSkills}
                  </strong>

                  <p>
                    Required skills recorded in your profile
                  </p>

                  <div className="sg-gap-level-met">
                    <CheckCircle2 size={15} />
                    <span>
                      {requiredLevelMetCount} meeting
                      required level
                    </span>
                  </div>
                </article>

                {/* MISSING SKILLS */}
                <article className="sg-gap-metric">
                  <div className="sg-gap-metric-icon sg-gap-orange">
                    <Layers3 size={23} />
                  </div>

                  <span>Missing Skills</span>
                  <strong>{missingCount}</strong>

                  <p>Skills not yet recorded</p>
                </article>

                {/* SKILLS TO IMPROVE */}
                <article className="sg-gap-metric">
                  <div className="sg-gap-metric-icon sg-gap-blue">
                    <Zap size={23} />
                  </div>

                  <span>Skills to Improve</span>
                  <strong>{improvementCount}</strong>

                  <p>
                    Skills below required proficiency
                  </p>
                </article>

              </div>
            </section>

            {/* PROFICIENCY COMPARISON */}
            <section className="sg-gap-detail-panel">
              <div className="sg-gap-panel-heading">
                <div>
                  <span>SKILL INTELLIGENCE</span>
                  <h2>Proficiency comparison</h2>

                  <p>
                    Compare your current level with the
                    proficiency required for your career.
                  </p>
                </div>

                <BrainCircuit size={27} />
              </div>

              <div className="sg-gap-skills-grid">
                {gaps.map((gap, index) => {
                  const current =
                    LEVEL_VALUES[gap.currentLevel] || 0;

                  const required =
                    LEVEL_VALUES[gap.requiredLevel] || 0;

                  return (
                    <article
                      className="sg-gap-skill-card"
                      key={`${gap.skillName}-${index}`}
                    >
                      <div className="sg-gap-skill-top">
                        <div className="sg-gap-skill-symbol">
                          <BrainCircuit size={20} />
                        </div>

                        <span
                          className={`sg-gap-status sg-gap-status-${String(
                            gap.status
                          ).toLowerCase()}`}
                        >
                          {STATUS_LABELS[gap.status] ||
                            gap.status}
                        </span>
                      </div>

                      <h3>{gap.skillName}</h3>

                      <div className="sg-gap-level-labels">
                        <span>
                          Current:{" "}
                          <strong>
                            {gap.currentLevel ||
                              "Not Learned"}
                          </strong>
                        </span>

                        <span>
                          Required:{" "}
                          <strong>
                            {gap.requiredLevel}
                          </strong>
                        </span>
                      </div>

                      <div className="sg-gap-bar-label">
                        Your level
                      </div>

                      <div className="sg-gap-progress-track">
                        <div
                          className="sg-gap-progress-current"
                          style={{
                            width: `${current}%`
                          }}
                        />
                      </div>

                      <div className="sg-gap-bar-label">
                        Required level
                      </div>

                      <div className="sg-gap-progress-track">
                        <div
                          className="sg-gap-progress-required"
                          style={{
                            width: `${required}%`
                          }}
                        />
                      </div>
                    </article>
                  );
                })}
              </div>
            </section>

            {/* RECOMMENDED LEARNING RESOURCES */}
            <section className="sg-gap-detail-panel">
              <div className="sg-gap-panel-heading">
                <div>
                  <span>PERSONALIZED LEARNING</span>

                  <h2>
                    Recommended learning resources
                  </h2>

                  <p>
                    Curated learning materials to help you
                    close your skill gaps.
                  </p>
                </div>

                <BookOpen size={27} />
              </div>

              {result.recommendations?.length > 0 ? (
                <div className="sg-gap-resources-grid">
                  {result.recommendations.map(
                    (recommendation, index) => (
                      <article
                        className="sg-gap-resource-card"
                        key={index}
                      >
                        <div className="sg-gap-resource-icon">
                          <BookOpen size={23} />
                        </div>

                        <h3>
                          {recommendation.skill?.name ||
                            "Skill Resources"}
                        </h3>

                        {recommendation.resources?.length >
                        0 ? (
                          <div className="sg-gap-resource-links">
                            {recommendation.resources.map(
                              (resource) => (
                                <a
                                  key={resource.id}
                                  href={resource.resourceUrl}
                                  target="_blank"
                                  rel="noopener noreferrer"
                                >
                                  <span>
                                    {resource.title}
                                  </span>

                                  <ExternalLink size={16} />
                                </a>
                              )
                            )}
                          </div>
                        ) : (
                          <p className="sg-gap-no-resources">
                            Learning resources have not
                            been added for this skill yet.
                          </p>
                        )}
                      </article>
                    )
                  )}
                </div>
              ) : (
                <p className="sg-gap-no-resources">
                  No learning resources required.
                </p>
              )}
            </section>

            {/* PERSONALIZED LEARNING ROADMAP */}
            <section className="sg-gap-detail-panel">
              <div className="sg-gap-panel-heading">
                <div>
                  <span>YOUR CAREER JOURNEY</span>

                  <h2>Personalized learning roadmap</h2>

                  <p>
                    Follow these priorities to improve
                    your career readiness.
                  </p>
                </div>

                <Compass size={27} />
              </div>

              {result.learningRoadmap?.length > 0 ? (
                <div className="sg-gap-roadmap">
                  {result.learningRoadmap.map(
                    (item, index) => (
                      <article
                        className="sg-gap-roadmap-item"
                        key={`${item.skillName}-${index}`}
                      >
                        <div className="sg-gap-roadmap-marker">
                          {item.priority}
                        </div>

                        <div className="sg-gap-roadmap-content">
                          <span>
                            PRIORITY {item.priority}
                          </span>

                          <h3>{item.skillName}</h3>

                          <p>
                            Current:{" "}
                            {item.currentLevel ||
                              "Not Learned"}
                          </p>

                          <p>
                            Target: {item.requiredLevel}
                          </p>

                          <strong>
                            {STATUS_LABELS[item.status] ||
                              item.status}
                          </strong>
                        </div>
                      </article>
                    )
                  )}
                </div>
              ) : (
                <div className="sg-gap-roadmap-complete">
                  <CheckCircle2 size={30} />

                  <h3>All required skills matched!</h3>

                  <p>
                    You've met the recorded skill
                    requirements for this career.
                  </p>
                </div>
              )}
            </section>

            {/* CONTINUE JOURNEY */}
            <section className="sg-gap-next-step">
              <div className="sg-gap-next-icon">
                <GraduationCap size={27} />
              </div>

              <div>
                <span>CONTINUE YOUR JOURNEY</span>

                <h2>
                  Turn your skill gaps into career growth.
                </h2>

                <p>
                  Explore learning resources and keep
                  building your technical expertise.
                </p>
              </div>

              <Link to="/learning-resources">
                Explore Resources
                <ArrowRight size={18} />
              </Link>
            </section>

          </div>
        )}

      </div>
    </div>
  );
}

export default MySkillGap;
