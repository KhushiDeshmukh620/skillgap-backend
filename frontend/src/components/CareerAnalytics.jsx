import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  ArrowRight,
  BarChart3,
  BrainCircuit,
  CheckCircle2,
  CircleAlert,
  Compass,
  LoaderCircle,
  ShieldCheck,
  Sparkles,
  Target,
  TrendingUp,
  Zap
} from "lucide-react";

const API = "http://localhost:8080";

function clampPercentage(value) {
  const number = Number(value);

  if (!Number.isFinite(number)) return 0;

  return Math.min(100, Math.max(0, number));
}

function getCareerInsight(matchPercentage, totalSkills, missingCount) {
  if (totalSkills === 0) {
    return {
      title: "Your career intelligence is getting ready",
      description:
        "This career does not have any required skills configured yet. Explore another career or check back when its requirements are available.",
      label: "AWAITING CAREER DATA",
      level: "neutral"
    };
  }

  if (matchPercentage === 100) {
    return {
      title: "Excellent! You've matched your target career.",
      description:
        "You've recorded all the required skills for this career. Continue improving your proficiency and building practical projects to strengthen your readiness.",
      label: "SKILLS MATCHED",
      level: "success"
    };
  }

  if (matchPercentage >= 75) {
    return {
      title: "You're getting closer to your career goal!",
      description:
        `You've already built a strong foundation. Focus on your remaining ${missingCount} missing skills and any proficiency gaps to continue progressing.`,
      label: "STRONG PROGRESS",
      level: "success"
    };
  }

  if (matchPercentage >= 40) {
    return {
      title: "You're building a strong career foundation.",
      description:
        "You've already developed some important skills. Follow your personalized learning roadmap to strengthen your knowledge and close the remaining gaps.",
      label: "BUILDING MOMENTUM",
      level: "progress"
    };
  }

  return {
    title: "Your career journey starts here!",
    description:
      `You have ${missingCount} missing skills to explore for this career. Start with your recommended learning resources and follow your personalized roadmap one step at a time.`,
    label: "GROWTH OPPORTUNITY",
    level: "growth"
  };
}

function AnimatedProgress({
  label,
  value,
  description,
  color = "orange"
}) {
  const percentage = clampPercentage(value);

  return (
    <div className={`sg-intelligence-progress sg-intelligence-${color}`}>
      <div className="sg-intelligence-progress-heading">
        <div>
          <span>{label}</span>
          <small>{description}</small>
        </div>

        <strong>{percentage.toFixed(1).replace(/\.0$/, "")}%</strong>
      </div>

      <div
        className="sg-intelligence-progress-track"
        role="progressbar"
        aria-label={label}
        aria-valuemin={0}
        aria-valuemax={100}
        aria-valuenow={percentage}
      >
        <div
          className="sg-intelligence-progress-fill"
          style={{ width: `${percentage}%` }}
        />
      </div>
    </div>
  );
}

export default function CareerAnalytics() {
  const navigate = useNavigate();

  const [domains, setDomains] = useState([]);
  const [domainId, setDomainId] = useState("");

  const [roles, setRoles] = useState([]);
  const [roleId, setRoleId] = useState("");

  const [result, setResult] = useState(null);

  const [loading, setLoading] = useState(false);
  const [domainsLoading, setDomainsLoading] = useState(true);
  const [rolesLoading, setRolesLoading] = useState(false);

  const [error, setError] = useState("");

  const analysisControllerRef = useRef(null);
  const analysisRequestIdRef = useRef(0);

  function cancelAnalysis() {
    analysisRequestIdRef.current += 1;

    if (analysisControllerRef.current) {
      analysisControllerRef.current.abort();
      analysisControllerRef.current = null;
    }

    setLoading(false);
  }

  async function apiGet(path, signal) {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login", { replace: true });
      throw new Error("Please log in again.");
    }

    const response = await fetch(`${API}${path}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`
      },
      signal
    });

    if (response.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("userName");

      navigate("/login", { replace: true });

      throw new Error("Your session has expired. Please log in again.");
    }

    if (!response.ok) {
      throw new Error(
        `Unable to load career information (${response.status}).`
      );
    }

    return response.json();
  }

  // Load engineering domains.
  useEffect(() => {
    const controller = new AbortController();

    async function loadDomains() {
      setDomainsLoading(true);

      try {
        const data = await apiGet(
          "/api/engineering-domains",
          controller.signal
        );

        if (controller.signal.aborted) return;

        setDomains(Array.isArray(data) ? data : []);
      } catch (err) {
        if (!controller.signal.aborted && err.name !== "AbortError") {
          setError(err.message);
        }
      } finally {
        if (!controller.signal.aborted) {
          setDomainsLoading(false);
        }
      }
    }

    loadDomains();

    return () => controller.abort();
  }, [navigate]);

  // Load job roles when engineering domain changes.
  useEffect(() => {
    const controller = new AbortController();

    setRoles([]);
    setRoleId("");
    setResult(null);
    setError("");

    if (!domainId) {
      setRolesLoading(false);
      return () => controller.abort();
    }

    async function loadRoles() {
      setRolesLoading(true);

      try {
        const data = await apiGet(
          `/api/job-roles/domain/${domainId}`,
          controller.signal
        );

        if (controller.signal.aborted) return;

        setRoles(Array.isArray(data) ? data : []);
      } catch (err) {
        if (!controller.signal.aborted && err.name !== "AbortError") {
          setError(err.message);
        }
      } finally {
        if (!controller.signal.aborted) {
          setRolesLoading(false);
        }
      }
    }

    loadRoles();

    return () => controller.abort();
  }, [domainId, navigate]);

  // Abort pending analysis when the component unmounts.
  useEffect(() => {
    return () => {
      analysisRequestIdRef.current += 1;
      analysisControllerRef.current?.abort();
    };
  }, []);

  async function analyzeCareer() {
    if (!domainId || !roleId || loading) return;

    cancelAnalysis();

    const controller = new AbortController();
    const requestId = analysisRequestIdRef.current;

    analysisControllerRef.current = controller;

    setLoading(true);
    setError("");
    setResult(null);

    try {
      const data = await apiGet(
        `/api/skill-gap/${roleId}`,
        controller.signal
      );

      if (
        controller.signal.aborted ||
        requestId !== analysisRequestIdRef.current
      ) {
        return;
      }

      setResult(data);
    } catch (err) {
      if (
        !controller.signal.aborted &&
        requestId === analysisRequestIdRef.current &&
        err.name !== "AbortError"
      ) {
        setError(err.message);
      }
    } finally {
      if (requestId === analysisRequestIdRef.current) {
        analysisControllerRef.current = null;
        setLoading(false);
      }
    }
  }

  function handleDomainChange(event) {
    cancelAnalysis();

    setDomainId(event.target.value);
    setRoleId("");
    setRoles([]);
    setResult(null);
    setError("");
  }

  function handleRoleChange(event) {
    cancelAnalysis();

    setRoleId(event.target.value);
    setResult(null);
    setError("");
  }

  const totalSkills = result
    ? Number(result.totalRequiredSkills) || 0
    : 0;

  const matchedSkills = result
    ? Number(result.matchedSkills) || 0
    : 0;

  const matchPercentage = result
    ? clampPercentage(result.matchPercentage)
    : 0;

  const proficiencyScore = result
    ? clampPercentage(result.proficiencyScore)
    : 0;

  const proficiencyGaps = Array.isArray(result?.proficiencyGaps)
    ? result.proficiencyGaps
    : [];

  const missingCount = proficiencyGaps.filter(
    (gap) => gap.status === "MISSING"
  ).length;

  const levelGapCount = proficiencyGaps.filter(
    (gap) => gap.status === "LEVEL_GAP"
  ).length;

  const insight = result
    ? getCareerInsight(
        matchPercentage,
        totalSkills,
        missingCount
      )
    : null;

  const statistics = result
    ? [
        {
          label: "Skill Match",
          value: `${matchPercentage.toFixed(1).replace(/\.0$/, "")}%`,
          icon: Target,
          accent: "orange",
          description: "Overall skill coverage"
        },
        {
          label: "Proficiency Score",
          value: `${proficiencyScore.toFixed(1).replace(/\.0$/, "")}%`,
          icon: TrendingUp,
          accent: "purple",
          description: "Current proficiency alignment"
        },
        {
          label: "Matched Skills",
          value: `${matchedSkills} / ${totalSkills}`,
          icon: CheckCircle2,
          accent: "green",
          description: "Skills already recorded"
        },
        {
          label: "Missing Skills",
          value: String(missingCount),
          icon: BarChart3,
          accent: "blue",
          description: "Skills not yet recorded"
        }
      ]
    : [];

  return (
    <section className="sg-analytics sg-intelligence">
      {/* SECTION HEADER */}
      <div className="sg-analytics-header sg-intelligence-header">
        <div>
          <span className="sg-analytics-kicker">
            <Sparkles size={14} />
            LIVE CAREER INTELLIGENCE
          </span>

          <h2>Your career analytics</h2>

          <p>
            Discover how your current skills compare with your
            target career using real-time skill intelligence.
          </p>
        </div>

        <div className="sg-intelligence-live-badge">
          <span className="sg-intelligence-live-dot" />
          LIVE ANALYTICS
        </div>
      </div>

      {/* CAREER SELECTION */}
      <div className="sg-analytics-controls">
        <label>
          Engineering Domain

          <select
            value={domainId}
            onChange={handleDomainChange}
            disabled={domainsLoading}
          >
            <option value="" disabled>
              {domainsLoading
                ? "Loading Engineering Domains..."
                : "Select Engineering Domain"}
            </option>

            {domains.map((domain) => (
              <option
                key={domain.id}
                value={String(domain.id)}
              >
                {domain.name}
              </option>
            ))}
          </select>
        </label>

        <label>
          Target Job Role

          <select
            value={roleId}
            onChange={handleRoleChange}
            disabled={!domainId || rolesLoading || roles.length === 0}
          >
            <option value="" disabled>
              {!domainId
                ? "Select Engineering Domain First"
                : rolesLoading
                  ? "Loading Job Roles..."
                  : roles.length === 0
                    ? "No Job Roles Available"
                    : "Select Job Role"}
            </option>

            {roles.map((role) => (
              <option
                key={role.id}
                value={String(role.id)}
              >
                {role.name}
              </option>
            ))}
          </select>
        </label>

        <button
          type="button"
          onClick={analyzeCareer}
          disabled={
            !domainId ||
            !roleId ||
            loading ||
            rolesLoading
          }
          aria-busy={loading}
        >
          {loading ? (
            <>
              <LoaderCircle
                size={18}
                className="sg-intelligence-spinner"
              />
              Analyzing...
            </>
          ) : (
            <>
              <Zap size={18} />
              Analyze Career
              <ArrowRight size={17} />
            </>
          )}
        </button>
      </div>

      {/* ERROR MESSAGE */}
      {error && (
        <div className="sg-intelligence-error" role="alert">
          <CircleAlert size={19} />
          <span>{error}</span>
        </div>
      )}

      {/* LOADING STATE */}
      {loading && (
        <div
          className="sg-intelligence-loading"
          role="status"
          aria-live="polite"
        >
          <div className="sg-intelligence-loading-icon">
            <BrainCircuit size={28} />
          </div>

          <div>
            <h3>Analyzing your career readiness...</h3>
            <p>
              Comparing your skills with the selected career requirements.
            </p>
          </div>

          <LoaderCircle
            size={22}
            className="sg-intelligence-spinner"
          />
        </div>
      )}

      {/* EMPTY STATE */}
      {!result && !error && !loading && (
        <div className="sg-intelligence-empty">
          <div className="sg-intelligence-empty-icon">
            <Compass size={25} />
          </div>

          <h3>Your career intelligence awaits</h3>

          <p>
            Select your engineering domain and target job role,
            then analyze your career to unlock personalized insights.
          </p>
        </div>
      )}

      {/* ANALYSIS RESULTS */}
      {result && !loading && (
        <div className="sg-intelligence-results">
          {/* TARGET ROLE */}
          <div className="sg-intelligence-target">
            <div>
              <span>
                <Target size={15} />
                YOUR TARGET CAREER
              </span>

              <h3>{result.targetRole}</h3>
            </div>

            <div className="sg-intelligence-result-badge">
              <ShieldCheck size={15} />
              ANALYSIS COMPLETE
            </div>
          </div>

          {/* STATISTICS */}
          <div className="sg-analytics-stats">
            {statistics.map(
              ({
                label,
                value,
                icon: Icon,
                accent,
                description
              }) => (
                <article
                  key={label}
                  className={`sg-analytics-stat sg-intelligence-stat sg-intelligence-stat-${accent}`}
                >
                  <div className="sg-intelligence-stat-top">
                    <div className="sg-intelligence-stat-icon">
                      <Icon size={22} />
                    </div>

                    <span className="sg-intelligence-stat-indicator" />
                  </div>

                  <span className="sg-intelligence-stat-label">
                    {label}
                  </span>

                  <strong>{value}</strong>

                  <small>{description}</small>
                </article>
              )
            )}
          </div>

          {/* PROGRESS VISUALIZATION */}
          <div className="sg-intelligence-progress-panel">
            <div className="sg-intelligence-panel-heading">
              <div>
                <span>PERFORMANCE OVERVIEW</span>
                <h3>Your career readiness</h3>
              </div>

              <TrendingUp size={22} />
            </div>

            <AnimatedProgress
              label="Skill Match"
              value={matchPercentage}
              description="Skills aligned with your target career"
              color="orange"
            />

            <AnimatedProgress
              label="Proficiency Score"
              value={proficiencyScore}
              description="Alignment with required proficiency levels"
              color="purple"
            />

            <div className="sg-intelligence-progress-footer">
              <span>
                <CheckCircle2 size={15} />
                {matchedSkills} matched skills
              </span>

              <span>
                <Target size={15} />
                {missingCount} missing skills
              </span>

              {levelGapCount > 0 && (
                <span>
                  <TrendingUp size={15} />
                  {levelGapCount} proficiency gaps
                </span>
              )}
            </div>
          </div>

          {/* PERSONALIZED INSIGHT */}
          <div
            className={`sg-intelligence-insight sg-intelligence-insight-${insight.level}`}
          >
            <div className="sg-intelligence-insight-icon">
              <Sparkles size={25} />
            </div>

            <div className="sg-intelligence-insight-content">
              <span>{insight.label}</span>

              <h3>{insight.title}</h3>

              <p>{insight.description}</p>

              <div className="sg-intelligence-insight-actions">
                <Link
                  to="/career-roadmap"
                  className="sg-intelligence-primary-link"
                >
                  Explore Learning Roadmap
                  <ArrowRight size={17} />
                </Link>

                <Link
                  to="/skill-gap"
                  className="sg-intelligence-secondary-link"
                >
                  Full Skill Gap Analysis
                  <ArrowRight size={16} />
                </Link>
              </div>
            </div>
          </div>
        </div>
      )}
    </section>
  );
}