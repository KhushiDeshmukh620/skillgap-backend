
import "./App.css";

import { useEffect, useState } from "react";

import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import LandingPage from "./pages/LandingPage";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import AnalyzeResume from "./pages/AnalyzeResume";
import MySkillGap from "./pages/MySkillGap";
import MySkills from "./pages/MySkills";
import LearningResources from "./pages/LearningResources";
import CareerRoadmap from "./pages/CareerRoadmap";
import AdminDashboard from "./pages/AdminDashboard";

// Protect Admin Dashboard
function AdminRoute({ children }) {

    const [status, setStatus] = useState("checking");

    useEffect(() => {

        const controller = new AbortController();

        const verifyAdmin = async () => {

            const token = localStorage.getItem("token");

            if (!token) {
                setStatus("unauthorized");
                return;
            }

            try {

                const response = await fetch(
                    "http://localhost:8080/api/users/me",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        },
                        signal: controller.signal
                    }
                );

                if (response.status === 401) {
                    localStorage.removeItem("token");
                    setStatus("unauthorized");
                    return;
                }

                if (!response.ok) {
                    throw new Error("Unable to verify account.");
                }

                const user = await response.json();

                if (controller.signal.aborted) return;

                if (user.role === "ADMIN") {
                    setStatus("allowed");
                } else {
                    setStatus("forbidden");
                }

            } catch (error) {

                if (!controller.signal.aborted) {
                    setStatus("error");
                }

            }
        };

        verifyAdmin();

        return () => controller.abort();

    }, []);

    if (status === "checking") {
        return (
            <div className="analyze-page">
                <p>Verifying administrator access...</p>
            </div>
        );
    }

    if (status === "unauthorized") {
        return <Navigate to="/login" replace />;
    }

    if (status === "forbidden") {
        return <Navigate to="/dashboard" replace />;
    }

    if (status === "error") {
        return (
            <div className="analyze-page">
                <p className="error-message">
                    Could not verify administrator access.
                    Please refresh the page.
                </p>
            </div>
        );
    }

    return children;
}

function App() {

    return (
        <BrowserRouter>

            <Routes>

               <Route
                   path="/"
                   element={<LandingPage />}
               />

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/dashboard"
                    element={<Dashboard />}
                />

                <Route
                    path="/analyze"
                    element={<AnalyzeResume />}
                />

                <Route
                    path="/skill-gap"
                    element={<MySkillGap />}
                />

                <Route
                    path="/my-skills"
                    element={<MySkills />}
                />

                <Route
                    path="/learning-resources"
                    element={<LearningResources />}
                />

                <Route
                    path="/career-roadmap"
                    element={<CareerRoadmap />}
                />

                <Route
                    path="/admin"
                    element={
                        <AdminRoute>
                            <AdminDashboard />
                        </AdminRoute>
                    }
                />

                <Route
                    path="*"
                    element={
                        <Navigate
                            to="/login"
                            replace
                        />
                    }
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;
