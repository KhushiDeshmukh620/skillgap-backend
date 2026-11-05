import { useEffect, useState } from "react";
import { GraduationCap, BriefcaseBusiness } from "lucide-react";

const API = "http://localhost:8080";

function DomainJobRoleSelector({
  selectedJobRoleId,
  onJobRoleChange
}) {
  const [domains, setDomains] = useState([]);
  const [roles, setRoles] = useState([]);
  const [domainId, setDomainId] = useState("");
  const [loadingDomains, setLoadingDomains] = useState(true);
  const [loadingRoles, setLoadingRoles] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const controller = new AbortController();

    async function loadDomains() {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch(
          `${API}/api/engineering-domains`,
          {
            headers: {
              Authorization: `Bearer ${token}`
            },
            signal: controller.signal
          }
        );

        if (!response.ok) {
          throw new Error("Unable to load engineering branches.");
        }

        const data = await response.json();
        setDomains(Array.isArray(data) ? data : []);
      } catch (err) {
        if (err.name !== "AbortError") {
          setError(err.message);
        }
      } finally {
        if (!controller.signal.aborted) {
          setLoadingDomains(false);
        }
      }
    }

    loadDomains();

    return () => controller.abort();
  }, []);

  useEffect(() => {
    if (!domainId) {
      setRoles([]);
      setLoadingRoles(false);
      return;
    }

    const controller = new AbortController();

    async function loadRoles() {
      setLoadingRoles(true);
      setError("");

      try {
        const token = localStorage.getItem("token");

        const response = await fetch(
          `${API}/api/job-roles/domain/${domainId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`
            },
            signal: controller.signal
          }
        );

        if (!response.ok) {
          throw new Error("Unable to load job roles.");
        }

        const data = await response.json();
        setRoles(Array.isArray(data) ? data : []);
      } catch (err) {
        if (err.name !== "AbortError") {
          setRoles([]);
          setError(err.message);
        }
      } finally {
        if (!controller.signal.aborted) {
          setLoadingRoles(false);
        }
      }
    }

    loadRoles();

    return () => controller.abort();
  }, [domainId]);

  const handleDomainChange = (event) => {
    setDomainId(event.target.value);
    setRoles([]);
    setError("");
    onJobRoleChange("");
  };

  return (
    <div className="sg-resume-selector-grid">
      <div className="sg-resume-selector-field">
        <label htmlFor="resume-domain">
          <GraduationCap size={18} />
          Engineering Branch
        </label>

        <select
          id="resume-domain"
          value={domainId}
          onChange={handleDomainChange}
          disabled={loadingDomains}
          required
        >
          <option value="">
            {loadingDomains
              ? "Loading branches..."
              : "Select Engineering Branch"}
          </option>

          {domains.map((domain) => (
            <option key={domain.id} value={domain.id}>
              {domain.name}
            </option>
          ))}
        </select>
      </div>

      <div className="sg-resume-selector-field">
        <label htmlFor="resume-role">
          <BriefcaseBusiness size={18} />
          Target Job Role
        </label>

        <select
          id="resume-role"
          value={selectedJobRoleId || ""}
          onChange={(event) =>
            onJobRoleChange(event.target.value)
          }
          disabled={!domainId || loadingRoles}
          required
        >
          <option value="">
            {loadingRoles
              ? "Loading job roles..."
              : !domainId
                ? "Select branch first"
                : "Select Target Job Role"}
          </option>

          {roles.map((role) => (
            <option key={role.id} value={role.id}>
              {role.name}
            </option>
          ))}
        </select>
      </div>

      {error && (
        <p className="sg-resume-selector-error" role="alert">
          {error}
        </p>
      )}
    </div>
  );
}

export default DomainJobRoleSelector;