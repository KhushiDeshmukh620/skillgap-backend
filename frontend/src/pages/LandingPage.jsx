
import { Link } from "react-router-dom";
import {
  ArrowRight,
  BrainCircuit,
  ChartNoAxesCombined,
  Compass,
  FileSearch,
  Sparkles,
  Target,
  Zap
} from "lucide-react";

import "./LandingPage.css";

const features = [
  {
    icon: FileSearch,
    title: "AI Resume Intelligence",
    description:
      "Discover the skills identified in your resume and understand your current strengths."
  },
  {
    icon: Target,
    title: "Skill Gap Analysis",
    description:
      "Compare your skills with job-role requirements and identify what to improve."
  },
  {
    icon: Compass,
    title: "Personalized Career Roadmaps",
    description:
      "Explore structured learning recommendations for your target career."
  },
  {
    icon: ChartNoAxesCombined,
    title: "Career Intelligence",
    description:
      "Understand skill proficiency, role alignment, and learning priorities."
  }
];

function LandingPage() {
  return (
    <div className="landing">
      <header className="landing-navbar">
        <Link to="/" className="landing-brand">
          <span className="landing-brand-icon">
            <BrainCircuit size={23} />
          </span>
          <span>Skill<span className="landing-accent">Gap</span> AI</span>
        </Link>

        <nav className="landing-nav-links" aria-label="Main navigation">
          <a href="#features">Features</a>
          <a href="#how-it-works">How It Works</a>
          <a href="#vision">Our Vision</a>
        </nav>

        <div className="landing-nav-actions">
          <Link to="/login" className="landing-login">Log In</Link>
          <Link to="/register" className="landing-nav-cta">
            Get Started <ArrowRight size={16} />
          </Link>
        </div>
      </header>

      <main>
        <section className="landing-hero">
          <div className="landing-orb landing-orb-one" />
          <div className="landing-orb landing-orb-two" />

          <div className="landing-hero-content">
            <div className="landing-eyebrow">
              <Sparkles size={15} />
              AI-POWERED CAREER INTELLIGENCE
            </div>

            <h1>
              Your Skills.<br />
              Your Future.<br />
              <span>Decoded.</span>
            </h1>

            <p>
              Discover where you stand, identify the skills
              holding you back, and build a smarter path
              toward your engineering career.
            </p>

            <div className="landing-hero-actions">
              <Link to="/register" className="landing-primary-button">
                Start Your Journey <ArrowRight size={19} />
              </Link>
              <a href="#how-it-works" className="landing-secondary-button">
                Explore the Platform
              </a>
            </div>

            <div className="landing-hero-caption">
              <Zap size={15} />
              From skill discovery to career direction.
            </div>
          </div>

          <div className="landing-hero-visual" aria-hidden="true">
            <div className="landing-visual-ring landing-ring-one" />
            <div className="landing-visual-ring landing-ring-two" />
            <div className="landing-visual-ring landing-ring-three" />

            <div className="landing-visual-core">
              <BrainCircuit size={58} strokeWidth={1.4} />
            </div>

            <div className="landing-floating-card landing-card-top">
              <span className="landing-status-dot" />
              AI Skill Intelligence
            </div>

            <div className="landing-floating-card landing-card-bottom">
              <Target size={18} />
              Career Alignment
            </div>
          </div>
        </section>

        <section id="features" className="landing-section landing-features">
          <div className="landing-section-heading">
            <span className="landing-kicker">THE PLATFORM</span>
            <h2>Intelligence for your next career move.</h2>
            <p>
              One connected platform to understand your skills,
              uncover gaps, and explore what to learn next.
            </p>
          </div>

          <div className="landing-feature-grid">
            {features.map(({ icon: Icon, title, description }) => (
              <article className="landing-feature-card" key={title}>
                <div className="landing-feature-icon">
                  <Icon size={25} />
                </div>
                <h3>{title}</h3>
                <p>{description}</p>
                <ArrowRight className="landing-feature-arrow" size={19} />
              </article>
            ))}
          </div>
        </section>

        <section id="how-it-works" className="landing-section landing-process">
          <div className="landing-section-heading">
            <span className="landing-kicker">YOUR JOURNEY</span>
            <h2>Clarity in three steps.</h2>
          </div>

          <div className="landing-process-grid">
            {[
              ["01", "Discover", "Upload your resume and identify your skills."],
              ["02", "Analyze", "Compare your profile with career requirements."],
              ["03", "Grow", "Follow personalized learning recommendations."]
            ].map(([number, title, description]) => (
              <div className="landing-process-step" key={number}>
                <span>{number}</span>
                <h3>{title}</h3>
                <p>{description}</p>
              </div>
            ))}
          </div>
        </section>

        <section id="vision" className="landing-final-section">
          <div className="landing-final-glow" />
          <div className="landing-final-content">
            <span className="landing-kicker">YOUR FUTURE STARTS HERE</span>
            <h2>Stop guessing.<br />Start growing.</h2>
            <p>
              Turn your existing skills into a clear direction
              for your engineering career.
            </p>
            <Link to="/register" className="landing-primary-button">
              Build My Career Roadmap <ArrowRight size={19} />
            </Link>
          </div>
        </section>
      </main>

      <footer className="landing-footer">
        <span>SkillGap AI</span>
        <span>AI-Powered Engineering Career Intelligence</span>
      </footer>
    </div>
  );
}

export default LandingPage;
