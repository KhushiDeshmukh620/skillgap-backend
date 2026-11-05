
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  ArrowLeft,
  ArrowRight,
  BrainCircuit,
  CheckCircle2,
  Eye,
  EyeOff,
  LockKeyhole,
  Mail,
  ShieldCheck,
  Sparkles,
  Target
} from "lucide-react";

import "./Login.css";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (loading) return;

    setMessage("");
    setLoading(true);

    try {
      const response = await fetch(
        "http://localhost:8080/api/users/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: email,
            password: password
          })
        }
      );

      const data = await response.json();

      if (!response.ok) {

        throw new Error(data.message || "Login failed");
      }
        console.log("Login response role:", data.role);
      if (!data.token) {
        throw new Error("Login response did not contain a token.");
      }


localStorage.setItem("token", data.token);
localStorage.setItem("userName", data.name);
localStorage.setItem("userRole", data.role);

if (data.role === "ADMIN") {
  navigate("/admin", { replace: true });
} else {
  navigate("/dashboard", { replace: true });
}


    } catch (error) {
      setMessage(error.message || "Unable to log in. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="premium-login">
      <div className="premium-login-visual">
        <div className="premium-login-glow" />

        <Link to="/" className="premium-login-brand">
          <span className="premium-login-brand-icon">
            <BrainCircuit size={25} />
          </span>
          <span>
            Skill<span>Gap</span> AI
          </span>
        </Link>

        <div className="premium-login-visual-content">
          <div className="premium-login-eyebrow">
            <Sparkles size={16} />
            AI-POWERED CAREER INTELLIGENCE
          </div>

          <h1>
            Your potential.<br />
            <span>Unlocked.</span>
          </h1>

          <p>
            Discover your strengths, identify skill gaps,
            and take the next step toward your dream career.
          </p>

          <div className="premium-login-orbit" aria-hidden="true">
            <div className="premium-login-ring premium-login-ring-one" />
            <div className="premium-login-ring premium-login-ring-two" />

            <div className="premium-login-orbit-center">
              <BrainCircuit size={43} />
            </div>

            <div className="premium-login-orbit-label premium-login-orbit-label-one">
              <Target size={16} />
              Skill Analysis
            </div>

            <div className="premium-login-orbit-label premium-login-orbit-label-two">
              <CheckCircle2 size={16} />
              Career Growth
            </div>
          </div>
        </div>

        <div className="premium-login-visual-footer">
          <ShieldCheck size={17} />
          Your career journey starts here
        </div>
      </div>

      <div className="premium-login-form-side">
        <Link to="/" className="premium-login-back">
          <ArrowLeft size={17} />
          Back to Home
        </Link>

        <div className="premium-login-form-container">
          <div className="premium-login-mobile-brand">
            <BrainCircuit size={25} />
            <span>SkillGap AI</span>
          </div>

          <div className="premium-login-heading">
            <div className="premium-login-welcome-icon">
              <Sparkles size={23} />
            </div>

            <h2>Welcome back<span>.</span></h2>
            <p>
              Sign in to continue building your future.
            </p>
          </div>

          <form onSubmit={handleSubmit} className="premium-login-form">
            <div className="premium-login-field">
              <label htmlFor="login-email">Email Address</label>

              <div className="premium-login-input-wrap">
                <Mail size={19} />

                <input
                  id="login-email"
                  type="email"
                  placeholder="Enter your email address"
                  value={email}
                  onChange={(event) => setEmail(event.target.value)}
                  autoComplete="email"
                  required
                />
              </div>
            </div>

            <div className="premium-login-field">
              <label htmlFor="login-password">Password</label>

              <div className="premium-login-input-wrap">
                <LockKeyhole size={19} />

                <input
                  id="login-password"
                  type={showPassword ? "text" : "password"}
                  placeholder="Enter your password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  autoComplete="current-password"
                  required
                />

                <button
                  type="button"
                  className="premium-login-password-toggle"
                  onClick={() => setShowPassword(!showPassword)}
                  aria-label={showPassword ? "Hide password" : "Show password"}
                  aria-pressed={showPassword}
                >
                  {showPassword ? <EyeOff size={19} /> : <Eye size={19} />}
                </button>
              </div>
            </div>

            {message && (
              <p className="premium-login-error" role="alert">
                {message}
              </p>
            )}

            <button
              type="submit"
              className="premium-login-submit"
              disabled={loading}
            >
              {loading ? (
                "Signing in..."
              ) : (
                <>
                  Sign In
                  <ArrowRight size={19} />
                </>
              )}
            </button>
          </form>

          <p className="premium-login-register">
            Don't have an account?{" "}
            <Link to="/register">Create an account</Link>
          </p>

          <div className="premium-login-bottom-note">
            <ShieldCheck size={16} />
            Secure authentication
          </div>
        </div>

        <div className="premium-login-copyright">
          © {new Date().getFullYear()} SkillGap AI
        </div>
      </div>
    </div>
  );
}

export default Login;
