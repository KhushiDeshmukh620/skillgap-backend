
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  ArrowRight,
  BookOpen,
  BrainCircuit,
  ChevronRight,
  Compass,
  FileSearch,
  GraduationCap,
  LayoutDashboard,
  LogOut,
  Menu,
  Route,
  ShieldCheck,
  Sparkles,
  Target,
  UserRound,
  X
} from "lucide-react";

import CareerAnalytics from "../components/CareerAnalytics";

import "./Dashboard.css";

const dashboardFeatures = [
  {
    title: "AI Resume Analysis",
    description:
      "Upload your resume and discover how your skills align with your target career.",
    path: "/analyze",
    action: "Analyze Resume",
    icon: FileSearch,
    accent: "orange"
  },
  {
    title: "My Skills",
    description:
      "Manage your technical skills and track your current proficiency levels.",
    path: "/my-skills",
    action: "Manage Skills",
    icon: BrainCircuit,
    accent: "violet"
  },
  {
    title: "Skill Gap Analysis",
    description:
      "Identify the skills you already have and the ones you need to develop.",
    path: "/skill-gap",
    action: "Explore Skill Gaps",
    icon: Target,
    accent: "blue"
  },
  {
    title: "Learning Resources",
    description:
      "Find recommended learning materials to improve your technical skills.",
    path: "/learning-resources",
    action: "Explore Resources",
    icon: BookOpen,
    accent: "green"
  },
  {
    title: "Career Roadmap",
    description:
      "Follow a personalized learning journey toward your engineering career.",
    path: "/career-roadmap",
    action: "View Roadmap",
    icon: Route,
    accent: "pink"
  }
];

const sidebarLinks = [
  {
    label: "Dashboard",
    path: "/dashboard",
    icon: LayoutDashboard
  },
  {
    label: "Analyze Resume",
    path: "/analyze",
    icon: FileSearch
  },
  {
    label: "My Skills",
    path: "/my-skills",
    icon: BrainCircuit
  },
  {
    label: "Skill Gap Analysis",
    path: "/skill-gap",
    icon: Target
  },
  {
    label: "Learning Resources",
    path: "/learning-resources",
    icon: BookOpen
  },
  {
    label: "Career Roadmap",
    path: "/career-roadmap",
    icon: Route
  }
];

function Dashboard() {
  const navigate = useNavigate();
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const userName = localStorage.getItem("userName") || "User";
  const firstName = userName.trim().split(/\s+/)[0] || "User";

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName");
    navigate("/login", { replace: true });
  };

  return (
    <div className="sg-dashboard">
      <aside
        className={
          "sg-dashboard-sidebar" +
          (sidebarOpen ? " sg-dashboard-sidebar-open" : "")
        }
      >
        <Link
          to="/dashboard"
          className="sg-dashboard-logo"
          onClick={() => setSidebarOpen(false)}
        >
          <span className="sg-dashboard-logo-icon">
            <BrainCircuit size={25} />
          </span>
          <span>
            Skill<span>Gap</span> AI
          </span>
        </Link>

        <div className="sg-dashboard-sidebar-label">
          WORKSPACE
        </div>

        <nav className="sg-dashboard-nav" aria-label="Dashboard navigation">
          {sidebarLinks.map(({ label, path, icon: Icon }) => (
            <Link
              key={path}
              to={path}
              className={
                "sg-dashboard-nav-link" +
                (path === "/dashboard"
                  ? " sg-dashboard-nav-link-active"
                  : "")
              }
              onClick={() => setSidebarOpen(false)}
            >
              <Icon size={19} />
              <span>{label}</span>
              {path === "/dashboard" && (
                <span className="sg-dashboard-active-dot" />
              )}
            </Link>
          ))}
        </nav>

        <div className="sg-dashboard-sidebar-bottom">
          <div className="sg-dashboard-sidebar-tip">
            <Sparkles size={21} />
            <h3>Level up your career</h3>
            <p>
              Understand your skills and discover what to learn next.
            </p>
            <Link to="/career-roadmap">
              Explore Roadmap
              <ArrowRight size={15} />
            </Link>
          </div>

          <button
            type="button"
            className="sg-dashboard-logout"
            onClick={handleLogout}
          >
            <LogOut size={19} />
            Logout
          </button>
        </div>
      </aside>

      {sidebarOpen && (
        <button
          type="button"
          className="sg-dashboard-overlay"
          aria-label="Close navigation"
          onClick={() => setSidebarOpen(false)}
        />
      )}

      <div className="sg-dashboard-main">
        <header className="sg-dashboard-topbar">
          <div className="sg-dashboard-topbar-left">
            <button
              type="button"
              className="sg-dashboard-menu-button"
              onClick={() => setSidebarOpen(true)}
              aria-label="Open navigation"
            >
              <Menu size={23} />
            </button>

            <div className="sg-dashboard-breadcrumb">
              <LayoutDashboard size={17} />
              <ChevronRight size={15} />
              <span>Overview</span>
            </div>
          </div>

          <div className="sg-dashboard-topbar-right">
            <span className="sg-dashboard-topbar-status">
              <span />
              Career Workspace
            </span>

            <div className="sg-dashboard-profile">
              <span className="sg-dashboard-avatar">
                <UserRound size={19} />
              </span>
              <span className="sg-dashboard-profile-name">
                {userName}
              </span>
            </div>
          </div>
        </header>

        <main className="sg-dashboard-content">
          <section className="sg-dashboard-welcome">
            <div className="sg-dashboard-welcome-copy">
              <div className="sg-dashboard-eyebrow">
                <Sparkles size={15} />
                YOUR PERSONAL AI CAREER WORKSPACE
              </div>

              <h1>
                Welcome back, <span>{firstName}.</span>
                <span className="sg-dashboard-wave"> 👋</span>
              </h1>

              <p>
                Every great career starts with understanding your
                strengths. Explore your skills, identify gaps, and
                discover your next opportunity.
              </p>

              <div className="sg-dashboard-welcome-actions">
                <button
                  type="button"
                  className="sg-dashboard-primary-button"
                  onClick={() => navigate("/analyze")}
                >
                  <FileSearch size={19} />
                  Analyze My Resume
                  <ArrowRight size={18} />
                </button>

                <button
                  type="button"
                  className="sg-dashboard-secondary-button"
                  onClick={() => navigate("/career-roadmap")}
                >
                  Explore Roadmap
                  <ArrowRight size={17} />
                </button>
              </div>
            </div>

            <div
              className="sg-dashboard-hero-visual"
              aria-hidden="true"
            >
              <div className="sg-dashboard-hero-ring sg-dashboard-hero-ring-outer" />
              <div className="sg-dashboard-hero-ring sg-dashboard-hero-ring-inner" />

              <div className="sg-dashboard-hero-core">
                <BrainCircuit size={48} />
              </div>

              <div className="sg-dashboard-hero-spark sg-dashboard-hero-spark-one">
                <Target size={19} />
              </div>

              <div className="sg-dashboard-hero-spark sg-dashboard-hero-spark-two">
                <GraduationCap size={19} />
              </div>
            </div>
          </section>

        <CareerAnalytics />

          <div className="sg-dashboard-section-heading">
            <div>
              <span className="sg-dashboard-section-kicker">
                EXPLORE YOUR WORKSPACE
              </span>
              <h2>Your career toolkit</h2>
              <p>
                Everything you need to analyze, learn, and grow.
              </p>
            </div>

            <span className="sg-dashboard-tool-count">
              {dashboardFeatures.length} tools available
            </span>
          </div>

          <section
            className="sg-dashboard-feature-grid"
            aria-label="Career tools"
          >
            {dashboardFeatures.map(
              ({
                title,
                description,
                path,
                action,
                icon: Icon,
                accent
              }) => (
                <article
                  className={`sg-dashboard-feature-card sg-dashboard-accent-${accent}`}
                  key={path}
                >
                  <div className="sg-dashboard-card-icon">
                    <Icon size={25} />
                  </div>

                  <h3>{title}</h3>
                  <p>{description}</p>

                  <Link
                    to={path}
                    className="sg-dashboard-card-link"
                  >
                    {action}
                    <ArrowRight size={17} />
                  </Link>
                </article>
              )
            )}
          </section>

          <section className="sg-dashboard-journey">
            <div className="sg-dashboard-journey-icon">
              <Compass size={27} />
            </div>

            <div className="sg-dashboard-journey-copy">
              <span>YOUR NEXT STEP</span>
              <h2>Not sure where to begin?</h2>
              <p>
                Start by analyzing your resume. Then explore
                your skill gaps and build a personalized
                learning roadmap.
              </p>
            </div>

            <Link
              to="/analyze"
              className="sg-dashboard-journey-link"
            >
              Get Started
              <ArrowRight size={18} />
            </Link>
          </section>

          <footer className="sg-dashboard-footer">
            <span>© {new Date().getFullYear()} SkillGap AI</span>
            <span>
              <ShieldCheck size={15} />
              Your career growth companion
            </span>
          </footer>
        </main>
      </div>
    </div>
  );
}

export default Dashboard;
