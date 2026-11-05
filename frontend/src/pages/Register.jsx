
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
  Target,
  UserRound
} from "lucide-react";

import "./Register.css";

function Register() {
  const [name, setName] = useState("");
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
        "http://localhost:8080/api/users",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            name: name.trim(),
            email: email.trim(),
            password: password
          })
        }
      );

      const data = await response.json();

      if (!response.ok) {
        throw new Error(
          data.message || "Registration failed"
        );
      }

      alert(
        "Account created successfully. Please login."
      );

      navigate("/login");

    } catch (error) {
      setMessage(
        error.message || "Unable to register. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="premium-register">
      <div className="premium-register-visual">
        <div className="premium-register-glow" />

        <Link to="/" className="premium-register-brand">
          <span className="premium-register-brand-icon">
            <BrainCircuit size={25} />
          </span>
          <span>
            Skill<span>Gap</span> AI
          </span>
        </Link>

        <div className="premium-register-visual-content">
          <div className="premium-register-eyebrow">
            <Sparkles size={16} />
            YOUR CAREER JOURNEY STARTS HERE
          </div>

          <h1>
            Build skills.<br />
            <span>Shape your future.</span>
          </h1>

          <p>
            Discover your strengths, uncover skill gaps,
            and follow a smarter path toward your
            engineering career.
          </p>

          <div className="premium-register-orbit" aria-hidden="true">
            <div className="premium-register-ring premium-register-ring-one" />
            <div className="premium-register-ring premium-register-ring-two" />

            <div className="premium-register-orbit-center">
              <BrainCircuit size={43} />
            </div>

            <div className="premium-register-orbit-label premium-register-orbit-label-one">
              <Target size={16} />
              Discover Skills
            </div>

            <div className="premium-register-orbit-label premium-register-orbit-label-two">
              <CheckCircle2 size={16} />
              Unlock Growth
            </div>
          </div>
        </div>

        <div className="premium-register-visual-footer">
          <ShieldCheck size={17} />
          Discover. Analyze. Grow.
        </div>
      </div>

      <div className="premium-register-form-side">
        <Link to="/" className="premium-register-back">
          <ArrowLeft size={17} />
          Back to Home
        </Link>

        <div className="premium-register-form-container">
          <div className="premium-register-mobile-brand">
            <BrainCircuit size={25} />
            <span>SkillGap AI</span>
          </div>

          <div className="premium-register-heading">
            <div className="premium-register-welcome-icon">
              <Sparkles size={23} />
            </div>

            <h2>Create account<span>.</span></h2>

            <p>
              Join SkillGap AI and start your career journey.
            </p>
          </div>

          <form
            onSubmit={handleSubmit}
            className="premium-register-form"
          >
            <div className="premium-register-field">
              <label htmlFor="register-name">
                Full Name
              </label>

              <div className="premium-register-input-wrap">
                <UserRound size={19} />

                <input
                  id="register-name"
                  type="text"
                  placeholder="Enter your full name"
                  value={name}
                  onChange={(event) => setName(event.target.value)}
                  autoComplete="name"
                  required
                />
              </div>
            </div>

            <div className="premium-register-field">
              <label htmlFor="register-email">
                Email Address
              </label>

              <div className="premium-register-input-wrap">
                <Mail size={19} />

                <input
                  id="register-email"
                  type="email"
                  placeholder="Enter your email address"
                  value={email}
                  onChange={(event) => setEmail(event.target.value)}
                  autoComplete="email"
                  required
                />
              </div>
            </div>

            <div className="premium-register-field">
              <label htmlFor="register-password">
                Password
              </label>

              <div className="premium-register-input-wrap">
                <LockKeyhole size={19} />

                <input
                  id="register-password"
                  type={showPassword ? "text" : "password"}
                  placeholder="Minimum 6 characters"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  autoComplete="new-password"
                  minLength={6}
                  required
                />

                <button
                  type="button"
                  className="premium-register-password-toggle"
                  onClick={() => setShowPassword(!showPassword)}
                  aria-label={
                    showPassword ? "Hide password" : "Show password"
                  }
                  aria-pressed={showPassword}
                >
                  {showPassword ? (
                    <EyeOff size={19} />
                  ) : (
                    <Eye size={19} />
                  )}
                </button>
              </div>

              <span className="premium-register-hint">
                Use at least 6 characters.
              </span>
            </div>

            {message && (
              <p className="premium-register-error" role="alert">
                {message}
              </p>
            )}

            <button
              type="submit"
              className="premium-register-submit"
              disabled={loading}
            >
              {loading ? (
                "Creating Account..."
              ) : (
                <>
                  Create Account
                  <ArrowRight size={19} />
                </>
              )}
            </button>
          </form>

          <p className="premium-register-login">
            Already have an account?{" "}
            <Link to="/login">Sign In</Link>
          </p>

          <div className="premium-register-bottom-note">
            <ShieldCheck size={16} />
            Secure account registration
          </div>
        </div>

        <div className="premium-register-copyright">
          © {new Date().getFullYear()} SkillGap AI
        </div>
      </div>
    </div>
  );
}

export default Register;
