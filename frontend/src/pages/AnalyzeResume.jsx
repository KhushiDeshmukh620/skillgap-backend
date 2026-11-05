
import { useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  ArrowLeft, ArrowRight, BookOpen, BrainCircuit,
  CheckCircle2, CloudUpload, Compass, ExternalLink,
  FileCheck2, FileSearch, FileText, GraduationCap,
  LoaderCircle, SearchCheck, Sparkles, Target,
  UploadCloud, X, Zap
} from "lucide-react";

import DomainJobRoleSelector from "../components/DomainJobRoleSelector";
import "./AnalyzeResume.css";

const API = "http://localhost:8080";
const MAX_FILE_SIZE = 5 * 1024 * 1024;

function AnalyzeResume() {
  const navigate = useNavigate();
  const fileInputRef = useRef(null);

  const [jobRoleId, setJobRoleId] = useState("");
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [dragging, setDragging] = useState(false);

  const selectFile = (selectedFile) => {
    setMessage("");
    setResult(null);

    if (!selectedFile) {
      setFile(null);
      return;
    }

    if (
      selectedFile.type !== "application/pdf" &&
      !selectedFile.name.toLowerCase().endsWith(".pdf")
    ) {
      setFile(null);
      setMessage("Please select a PDF resume only.");

      if (fileInputRef.current) {
        fileInputRef.current.value = "";
      }
      return;
    }

    if (selectedFile.size > MAX_FILE_SIZE) {
      setFile(null);
      setMessage("Resume file must be smaller than 5 MB.");

      if (fileInputRef.current) {
        fileInputRef.current.value = "";
      }
      return;
    }

    setFile(selectedFile);
  };

  const handleAnalyze = async (event) => {
    event.preventDefault();

    if (!jobRoleId) {
      setMessage("Please select a target job role.");
      return;
    }

    if (!file) {
      setMessage("Please upload your resume PDF.");
      return;
    }

    if (file.size > MAX_FILE_SIZE) {
      setMessage("Resume file must be smaller than 5 MB.");
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    setMessage("");
    setResult(null);
    setLoading(true);

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch(
        `${API}/api/resume/skill-gap/${jobRoleId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`
          },
          body: formData
        }
      );

      if (response.status === 401) {
        localStorage.removeItem("token");
        localStorage.removeItem("userName");
        navigate("/login");
        return;
      }

      if (response.status === 413) {
        throw new Error(
          "Resume file must be smaller than 5 MB."
        );
      }

      const raw = await response.text();
      let data = {};

      try {
        data = raw ? JSON.parse(raw) : {};
      } catch {
        throw new Error("Unexpected response from the server.");
      }

      if (!response.ok) {
        throw new Error(
          data.message || "Resume analysis failed."
        );
      }

      setResult(data);
    } catch (error) {
      setMessage(
        error.message || "Could not analyze your resume."
      );
    } finally {
      setLoading(false);
    }
  };

  const match = result
    ? Math.min(
        100,
        Math.max(0, Number(result.matchPercentage) || 0)
      )
    : 0;

  const matched = Array.isArray(result?.matchedSkills)
    ? result.matchedSkills
    : [];

  const missing = Array.isArray(result?.missingSkills)
    ? result.missingSkills
    : [];

  const other = Array.isArray(result?.otherExtractedSkills)
    ? result.otherExtractedSkills
    : [];

  const recommendations = Array.isArray(result?.recommendations)
    ? result.recommendations
    : [];

  const roadmap = Array.isArray(result?.learningRoadmap)
    ? result.learningRoadmap
    : [];

  const displayName =
    localStorage.getItem("userName") || "Learner";

  return (
    <div className="sg-resume-page">
      <div className="sg-resume-shell">

        {/* TOP BAR */}

        <header className="sg-resume-topbar">
          <Link to="/dashboard" className="sg-resume-back">
            <ArrowLeft size={17} />
            Back to Dashboard
          </Link>

          <span className="sg-resume-top-status">
            <span className="sg-resume-live-dot" />
            AI RESUME INTELLIGENCE
          </span>
        </header>

        {/* ANIMATED HERO */}

        <section className="sg-resume-hero">
          <div className="sg-resume-hero-copy">
            <span className="sg-resume-eyebrow">
              <Sparkles size={16} />
              AI-POWERED CAREER ANALYSIS
            </span>

            <h1>
              Your resume.
              <br />
              <span>Your future, decoded.</span>
            </h1>

            <p>
              Transform your resume into actionable career
              intelligence. Discover your strengths,
              identify missing skills, and unlock your
              personalized growth journey.
            </p>

            <div className="sg-resume-hero-tag">
              <BrainCircuit size={17} />
              Powered by AI
              <span className="sg-resume-tag-divider" />
              <strong>{displayName}</strong>
            </div>
          </div>

          <div
            className="sg-resume-hero-visual"
            aria-hidden="true"
          >
            <div className="sg-resume-orbit sg-resume-orbit-outer" />
            <div className="sg-resume-orbit sg-resume-orbit-inner" />

            <div className="sg-resume-orbit-core">
              <BrainCircuit size={49} />
            </div>

            <span className="sg-resume-float-icon sg-resume-float-one">
              <FileSearch size={22} />
            </span>

            <span className="sg-resume-float-icon sg-resume-float-two">
              <Target size={22} />
            </span>

            <span className="sg-resume-float-icon sg-resume-float-three">
              <GraduationCap size={20} />
            </span>
          </div>
        </section>

        {/* RESUME SCANNER */}

        <div className="sg-resume-section-heading">
          <span>RESUME INTELLIGENCE WORKSPACE</span>
          <h2>Analyze your career potential</h2>
          <p>
            Choose your engineering branch and target job role,
            then upload your resume to discover your skill gap.
          </p>
        </div>

        <section
          className="sg-resume-workspace"
          style={{
            display: "flex",
            justifyContent: "center"
          }}
        >
          <div
            className="sg-resume-form-panel"
            style={{
              width: "100%",
              maxWidth: "900px"
            }}
          >
            <div className="sg-resume-panel-heading">
              <div className="sg-resume-panel-icon">
                <FileSearch size={23} />
              </div>

              <div>
                <span>START YOUR ANALYSIS</span>
                <h2>AI Resume Scanner</h2>
                <p>
                  Discover how your skills match your dream career.
                </p>
              </div>
            </div>

            <form onSubmit={handleAnalyze}>

              {/* BRANCH AND JOB ROLE */}

              <div className="sg-resume-role-section">
                <DomainJobRoleSelector
                  selectedJobRoleId={jobRoleId}
                  onJobRoleChange={(id) => {
                    setJobRoleId(id);
                    setResult(null);
                    setMessage("");
                  }}
                />
              </div>

              {/* RESUME UPLOAD */}

              <div className="sg-resume-upload-section">
                <div className="sg-resume-field-title">
                  <FileText size={17} />
                  <span>Upload your resume</span>
                </div>

                <div
                  className={
                    "sg-resume-dropzone" +
                    (dragging ? " sg-resume-dragging" : "") +
                    (file ? " sg-resume-has-file" : "")
                  }
                  onDragOver={(event) => {
                    event.preventDefault();
                    setDragging(true);
                  }}
                  onDragLeave={(event) => {
                    event.preventDefault();
                    setDragging(false);
                  }}
                  onDrop={(event) => {
                    event.preventDefault();
                    setDragging(false);
                    selectFile(event.dataTransfer.files[0]);
                  }}
                >
                  <input
                    ref={fileInputRef}
                    type="file"
                    accept=".pdf,application/pdf"
                    className="sg-resume-file-input"
                    aria-label="Upload resume PDF"
                    onChange={(event) => {
                      selectFile(event.target.files[0]);
                    }}
                  />

                  {file ? (
                    <div className="sg-resume-selected-file">
                      <div className="sg-resume-file-icon">
                        <FileCheck2 size={29} />
                      </div>

                      <div className="sg-resume-file-info">
                        <strong>{file.name}</strong>
                        <span>
                          {(file.size / 1024).toFixed(1)} KB
                          {" • "}
                          Ready for analysis
                        </span>
                      </div>

                      <button
                        type="button"
                        className="sg-resume-remove-file"
                        aria-label="Remove selected resume"
                        onClick={() => {
                          setFile(null);
                          setResult(null);

                          if (fileInputRef.current) {
                            fileInputRef.current.value = "";
                          }
                        }}
                      >
                        <X size={19} />
                      </button>
                    </div>
                  ) : (
                    <>
                      <div className="sg-resume-upload-icon">
                        <CloudUpload size={32} />
                      </div>

                      <h3>Drop your resume here</h3>
                      <p>or choose a PDF from your device</p>

                      <button
                        type="button"
                        className="sg-resume-browse-button"
                        onClick={() =>
                          fileInputRef.current?.click()
                        }
                      >
                        <UploadCloud size={17} />
                        Browse Files
                      </button>

                      <small>PDF format only • Maximum 5 MB</small>
                    </>
                  )}
                </div>
              </div>

              {message && (
                <div
                  className="sg-resume-error"
                  role="alert"
                >
                  {message}
                </div>
              )}

              {/* ANALYZE BUTTON */}

              <button
                type="submit"
                className="sg-resume-analyze-button"
                disabled={loading || !jobRoleId || !file}
              >
                {loading ? (
                  <>
                    <LoaderCircle
                      size={20}
                      className="sg-resume-loading-icon"
                    />
                    AI is analyzing your resume...
                  </>
                ) : (
                  <>
                    <Sparkles size={20} />
                    Analyze My Resume
                    <ArrowRight size={19} />
                  </>
                )}
              </button>
            </form>
          </div>
        </section>

        {/* ANALYSIS RESULTS */}

        {result && (
          <section className="sg-resume-results">
            <div className="sg-resume-results-heading">
              <div>
                <span>AI ANALYSIS COMPLETE</span>
                <h2>Your career intelligence report</h2>
                <p>
                  Target role:{" "}
                  <strong>{result.targetRole}</strong>
                </p>
              </div>

              <div className="sg-resume-complete-badge">
                <CheckCircle2 size={17} />
                Analysis Complete
              </div>
            </div>

            {/* MATCH SUMMARY */}

            <div className="sg-resume-summary">
              <article className="sg-resume-match-card">
                <div className="sg-resume-card-kicker">
                  <Target size={18} />
                  RESUME SKILL MATCH
                </div>

                <div
                  className="sg-resume-match-ring"
                  style={{
                    "--sg-resume-match": `${match}%`
                  }}
                >
                  <div className="sg-resume-match-inner">
                    <strong>{match}%</strong>
                    <span>Career Match</span>
                  </div>
                </div>

                <p>
                  Your resume matches {result.matchedSkillsCount}
                  {" of "}
                  {result.totalRequiredSkills} required skills.
                </p>
              </article>

              <div className="sg-resume-summary-stats">
                <article className="sg-resume-stat-card">
                  <div className="sg-resume-stat-icon sg-resume-green">
                    <CheckCircle2 size={24} />
                  </div>

                  <span>Matched Skills</span>
                  <strong>
                    {result.matchedSkillsCount}
                    {" / "}
                    {result.totalRequiredSkills}
                  </strong>

                  <p>Skills detected for your target role</p>
                </article>

                <article className="sg-resume-stat-card">
                  <div className="sg-resume-stat-icon sg-resume-orange">
                    <Zap size={24} />
                  </div>

                  <span>Missing Skills</span>
                  <strong>{missing.length}</strong>

                  <p>Career requirements not detected</p>
                </article>

                <article className="sg-resume-stat-card">
                  <div className="sg-resume-stat-icon sg-resume-violet">
                    <BrainCircuit size={24} />
                  </div>

                  <span>Other Extracted Skills</span>
                  <strong>{other.length}</strong>

                  <p>Additional skills found in your resume</p>
                </article>
              </div>
            </div>

            {/* SKILL COMPARISON */}

            <div className="sg-resume-result-panel">
              <div className="sg-resume-result-panel-heading">
                <div>
                  <span>SKILL INTELLIGENCE</span>
                  <h2>Resume skill comparison</h2>
                  <p>
                    See which skills your resume demonstrates
                    and which ones you should develop.
                  </p>
                </div>

                <BrainCircuit size={27} />
              </div>

              <div className="sg-resume-skill-columns">
                <article className="sg-resume-skill-list sg-resume-matched">
                  <div className="sg-resume-skill-list-heading">
                    <CheckCircle2 size={21} />
                    <h3>Matched Skills</h3>
                    <span>{matched.length}</span>
                  </div>

                  {matched.length ? (
                    <div className="sg-resume-tags">
                      {matched.map((skill, index) => (
                        <span key={`${skill}-${index}`}>
                          <CheckCircle2 size={14} />
                          {skill}
                        </span>
                      ))}
                    </div>
                  ) : (
                    <p>No matched skills found.</p>
                  )}
                </article>

                <article className="sg-resume-skill-list sg-resume-missing">
                  <div className="sg-resume-skill-list-heading">
                    <Zap size={21} />
                    <h3>Missing Skills</h3>
                    <span>{missing.length}</span>
                  </div>

                  {missing.length ? (
                    <div className="sg-resume-tags">
                      {missing.map((skill, index) => (
                        <span key={`${skill}-${index}`}>
                          <Zap size={14} />
                          {skill}
                        </span>
                      ))}
                    </div>
                  ) : (
                    <p>No missing skills detected.</p>
                  )}
                </article>
              </div>

              {other.length > 0 && (
                <div className="sg-resume-other-skills">
                  <h3>
                    <SearchCheck size={19} />
                    Other skills detected by AI
                  </h3>

                  <div className="sg-resume-tags">
                    {other.map((skill, index) => (
                      <span key={`${skill}-${index}`}>
                        {skill}
                      </span>
                    ))}
                  </div>
                </div>
              )}
            </div>

            {/* LEARNING RESOURCES */}

            <div className="sg-resume-result-panel">
              <div className="sg-resume-result-panel-heading">
                <div>
                  <span>PERSONALIZED LEARNING</span>
                  <h2>Recommended resources</h2>
                  <p>
                    Build the missing skills required
                    for your target career.
                  </p>
                </div>

                <BookOpen size={27} />
              </div>

              {recommendations.length > 0 ? (
                <div className="sg-resume-resource-grid">
                  {recommendations.map((recommendation, index) => (
                    <article
                      className="sg-resume-resource-card"
                      key={recommendation.skill?.id || index}
                    >
                      <div className="sg-resume-resource-icon">
                        <BookOpen size={23} />
                      </div>

                      <h3>
                        {recommendation.skill?.name ||
                          "Learning Resources"}
                      </h3>

                      {recommendation.resources?.length > 0 ? (
                        <div className="sg-resume-resource-links">
                          {recommendation.resources.map(
                            (resource, resourceIndex) => (
                              <a
                                key={resource.id || resourceIndex}
                                href={resource.resourceUrl}
                                target="_blank"
                                rel="noopener noreferrer"
                              >
                                <span>
                                  {resource.learningOrder}.{" "}
                                  {resource.title}
                                </span>
                                <ExternalLink size={16} />
                              </a>
                            )
                          )}
                        </div>
                      ) : (
                        <p className="sg-resume-no-resource">
                          Resources have not been added
                          for this skill yet.
                        </p>
                      )}
                    </article>
                  ))}
                </div>
              ) : (
                <p className="sg-resume-no-resource">
                  No learning resources required.
                </p>
              )}
            </div>

            {/* LEARNING ROADMAP */}

            <div className="sg-resume-result-panel">
              <div className="sg-resume-result-panel-heading">
                <div>
                  <span>YOUR GROWTH JOURNEY</span>
                  <h2>Personalized learning roadmap</h2>
                  <p>
                    Follow your recommended priorities
                    to improve career readiness.
                  </p>
                </div>

                <Compass size={27} />
              </div>

              {roadmap.length > 0 ? (
                <div className="sg-resume-roadmap">
                  {roadmap.map((item, index) => (
                    <article
                      className="sg-resume-roadmap-item"
                      key={item.skillId || index}
                    >
                      <div className="sg-resume-roadmap-number">
                        {item.priority}
                      </div>

                      <div className="sg-resume-roadmap-content">
                        <span>
                          PRIORITY {item.priority}
                        </span>

                        <h3>{item.skillName}</h3>

                        <p>
                          Status:{" "}
                          <strong>{item.status}</strong>
                        </p>
                      </div>
                    </article>
                  ))}
                </div>
              ) : (
                <div className="sg-resume-roadmap-complete">
                  <CheckCircle2 size={30} />
                  <h3>All required skills matched!</h3>
                  <p>
                    Your resume matches the recorded
                    requirements for this role.
                  </p>
                </div>
              )}
            </div>

            {/* NEXT STEP */}

            <div className="sg-resume-next">
              <div className="sg-resume-next-icon">
                <GraduationCap size={27} />
              </div>

              <div>
                <span>CONTINUE YOUR CAREER JOURNEY</span>
                <h2>Turn insights into progress.</h2>
                <p>
                  Explore learning resources and keep
                  strengthening your technical skills.
                </p>
              </div>

              <Link to="/learning-resources">
                Explore Learning
                <ArrowRight size={18} />
              </Link>
            </div>
          </section>
        )}
      </div>
    </div>
  );
}

export default AnalyzeResume;
