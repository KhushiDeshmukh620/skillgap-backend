
import { useEffect, useState } from "react";

const API = "http://localhost:8080";

function JobRoleRequirements() {
    const [roles, setRoles] = useState([]);
    const [skills, setSkills] = useState([]);
    const [roleId, setRoleId] = useState("");
    const [skillId, setSkillId] = useState("");
    const [level, setLevel] = useState("INTERMEDIATE");
    const [requirements, setRequirements] = useState([]);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [deletingSkillId, setDeletingSkillId] = useState(null);
    const [editingSkillId, setEditingSkillId] = useState(null);
    const [editLevel, setEditLevel] = useState("INTERMEDIATE");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const request = async (path, options = {}) => {
        const token = localStorage.getItem("token");

        if (!token) {
            throw new Error("Please log in again.");
        }

        const response = await fetch(`${API}${path}`, {
            ...options,
            headers: {
                Authorization: `Bearer ${token}`,
                ...options.headers
            }
        });

        if (response.status === 401) {
            localStorage.removeItem("token");
            localStorage.removeItem("userName");
            window.location.replace("/login");
            throw new Error("Session expired.");
        }

        if (response.status === 403) {
            throw new Error("Administrator permission is required.");
        }

        if (!response.ok) {
            let message = `Request failed (${response.status}).`;

            try {
                const data = await response.json();
                message =
                    data.message ||
                    data.detail ||
                    data.error ||
                    message;
            } catch {
                // Keep the default error message.
            }

            throw new Error(message);
        }

        if (response.status === 204) return null;

        return response.json();
    };

    const refreshRequirements = async () => {
        const data = await request(
            `/api/job-role-skills/${roleId}`
        );

        setRequirements(Array.isArray(data) ? data : []);
    };

    useEffect(() => {
        let active = true;

        const loadOptions = async () => {
            try {
                const [roleData, skillData] = await Promise.all([
                    request("/api/job-roles/admin"),
                    request("/api/skills")
                ]);

                if (!active) return;

                setRoles(Array.isArray(roleData) ? roleData : []);
                setSkills(Array.isArray(skillData) ? skillData : []);
            } catch (err) {
                if (active) setError(err.message);
            } finally {
                if (active) setLoading(false);
            }
        };

        loadOptions();

        return () => {
            active = false;
        };
    }, []);

    useEffect(() => {
        let active = true;

        setRequirements([]);
        setError("");
        setSuccess("");
        setEditingSkillId(null);

        if (!roleId) return;

        const loadRequirements = async () => {
            setLoading(true);

            try {
                const data = await request(
                    `/api/job-role-skills/${roleId}`
                );

                if (active) {
                    setRequirements(
                        Array.isArray(data) ? data : []
                    );
                }
            } catch (err) {
                if (active) setError(err.message);
            } finally {
                if (active) setLoading(false);
            }
        };

        loadRequirements();

        return () => {
            active = false;
        };
    }, [roleId]);

    // Add a new skill requirement
    const saveRequirement = async (event) => {
        event.preventDefault();

        if (!roleId || !skillId) {
            setError("Please select a job role and skill.");
            return;
        }

        setSaving(true);
        setError("");
        setSuccess("");

        try {
            await request(
                `/api/job-role-skills/${roleId}/${skillId}/${level}`,
                { method: "POST" }
            );

            await refreshRequirements();

            setSkillId("");
            setSuccess("Skill requirement saved successfully.");
        } catch (err) {
            setError(err.message);
        } finally {
            setSaving(false);
        }
    };

    // Start editing an existing requirement
    const startEditing = (item) => {
        setEditingSkillId(item.skillId);
        setEditLevel(item.requiredLevel);
        setError("");
        setSuccess("");
    };

    // Cancel editing
    const cancelEditing = () => {
        setEditingSkillId(null);
        setEditLevel("INTERMEDIATE");
        setError("");
    };

    // Update the proficiency level of an existing requirement
    const updateRequirement = async (skillIdToUpdate) => {
        if (!roleId || !skillIdToUpdate) return;

        setSaving(true);
        setError("");
        setSuccess("");

        try {
            await request(
                `/api/job-role-skills/${roleId}/${skillIdToUpdate}/${editLevel}`,
                { method: "PUT" }
            );

            await refreshRequirements();

            setEditingSkillId(null);
            setSuccess("Skill requirement updated successfully.");
        } catch (err) {
            setError(err.message);
        } finally {
            setSaving(false);
        }
    };

    // Remove a skill requirement
    const deleteRequirement = async (skill) => {
        if (!roleId || !skill?.id) return;

        const confirmed = window.confirm(
            `Remove "${skill.name}" from this job role?`
        );

        if (!confirmed) return;

        setDeletingSkillId(skill.id);
        setError("");
        setSuccess("");

        try {
            await request(
                `/api/job-role-skills/${roleId}/${skill.id}`,
                { method: "DELETE" }
            );

            await refreshRequirements();
            setSuccess("Skill requirement removed successfully.");
        } catch (err) {
            setError(err.message);
        } finally {
            setDeletingSkillId(null);
        }
    };

    return (
        <div className="result-card">
            <h2>🎯 Job Role Skill Requirements</h2>

            <p>
                Assign required skills and proficiency levels
                to engineering career roles.
            </p>

            <div className="form-group">
                <label htmlFor="requirement-role">
                    Select Job Role
                </label>

                <select
                    id="requirement-role"
                    value={roleId}
                    onChange={(event) => {
                        setRoleId(event.target.value);
                        setSkillId("");
                    }}
                >
                    <option value="">-- Select Job Role --</option>

                    {roles.map((role) => (
                        <option key={role.id} value={role.id}>
                            {role.name}
                        </option>
                    ))}
                </select>
            </div>

            {roleId && (
                <form onSubmit={saveRequirement}>
                    <div className="form-group">
                        <label htmlFor="requirement-skill">
                            Select Skill
                        </label>

                        <select
                            id="requirement-skill"
                            value={skillId}
                            onChange={(event) =>
                                setSkillId(event.target.value)
                            }
                            required
                        >
                            <option value="">-- Select Skill --</option>

                            {skills.map((skill) => (
                                <option
                                    key={skill.id}
                                    value={skill.id}
                                >
                                    {skill.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="requirement-level">
                            Required Proficiency
                        </label>

                        <select
                            id="requirement-level"
                            value={level}
                            onChange={(event) =>
                                setLevel(event.target.value)
                            }
                        >
                            <option value="BEGINNER">Beginner</option>
                            <option value="INTERMEDIATE">Intermediate</option>
                            <option value="ADVANCED">Advanced</option>
                        </select>
                    </div>

                    <button
                        className="analyze-button"
                        type="submit"
                        disabled={
                            saving ||
                            deletingSkillId !== null ||
                            editingSkillId !== null ||
                            !skillId
                        }
                    >
                        {saving
                            ? "Saving..."
                            : "Save Skill Requirement"}
                    </button>
                </form>
            )}

            {error && (
                <p className="error-message" role="alert">
                    {error}
                </p>
            )}

            {success && (
                <p role="status" style={{ color: "#22c55e" }}>
                    {success}
                </p>
            )}

            {loading && <p>Loading requirements...</p>}

            {roleId && !loading && (
                <div style={{ marginTop: "28px" }}>
                    <h3>Existing Skill Requirements</h3>

                    {requirements.length === 0 ? (
                        <p>
                            No skill requirements assigned yet.
                        </p>
                    ) : (
                        requirements.map((item) => (
                            <div
                                className="roadmap-item"
                                key={item.id}
                            >
                                <h4>
                                    {item.skillName || "Unknown Skill"}
                                </h4>

                                {editingSkillId === item.skillId ? (
                                    <div>
                                        <div className="form-group">
                                            <label
                                                htmlFor={`edit-level-${item.skillId}`}
                                            >
                                                Change Required Proficiency
                                            </label>

                                            <select
                                                id={`edit-level-${item.skillId}`}
                                                value={editLevel}
                                                onChange={(event) =>
                                                    setEditLevel(event.target.value)
                                                }
                                            >
                                                <option value="BEGINNER">
                                                    Beginner
                                                </option>
                                                <option value="INTERMEDIATE">
                                                    Intermediate
                                                </option>
                                                <option value="ADVANCED">
                                                    Advanced
                                                </option>
                                            </select>
                                        </div>

                                        <button
                                            type="button"
                                            className="analyze-button"
                                            disabled={saving}
                                            onClick={() =>
                                                updateRequirement(item.skillId)
                                            }
                                        >
                                            {saving
                                                ? "Updating..."
                                                : "Save Changes"}
                                        </button>

                                        <button
                                            type="button"
                                            disabled={saving}
                                            onClick={cancelEditing}
                                            style={{ marginLeft: "10px" }}
                                        >
                                            Cancel
                                        </button>
                                    </div>
                                ) : (
                                    <>
                                        <p>
                                            Required Level:{" "}
                                            <strong>
                                                {item.requiredLevel}
                                            </strong>
                                        </p>

                                        <button
                                            type="button"
                                            disabled={
                                                saving ||
                                                deletingSkillId !== null
                                            }
                                            onClick={() =>
                                                startEditing(item)
                                            }
                                        >
                                            Edit Requirement
                                        </button>

                                        <button
                                            type="button"
                                            disabled={
                                                saving ||
                                                deletingSkillId !== null
                                            }
                                            onClick={() =>
                                                deleteRequirement({
                                                    id: item.skillId,
                                                    name: item.skillName
                                                })
                                            }
                                            style={{
                                                color: "#ef4444",
                                                marginLeft: "10px"
                                            }}
                                        >
                                            {deletingSkillId === item.skillId
                                                ? "Removing..."
                                                : "Remove Requirement"}
                                        </button>
                                    </>
                                )}
                            </div>
                        ))
                    )}
                </div>
            )}
        </div>
    );
}

export default JobRoleRequirements;
