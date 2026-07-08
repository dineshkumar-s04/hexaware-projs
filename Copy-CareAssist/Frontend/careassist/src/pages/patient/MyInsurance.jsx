import { useEffect, useState } from "react";
import { getPatientEnrollments } from "../../services/patient/patientService";

function MyInsurance() {

    const [enrollments, setEnrollments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        const fetchEnrollments = async () => {

            try {

                const patientId = localStorage.getItem("patientId");

                const data = await getPatientEnrollments(patientId);

                setEnrollments(data);

            } catch (err) {

                console.error(err);
                setError("Failed to load insurance details.");

            } finally {

                setLoading(false);

            }

        };

        fetchEnrollments();

    }, []);

    if (loading) {
        return (

            <div className="container mt-4 text-center">

                <div
                    className="spinner-border text-primary"
                    role="status"
                >
                    <span className="visually-hidden">
                        Loading...
                    </span>
                </div>

                <p className="mt-3 text-muted">
                    Loading your insurance details...
                </p>

            </div>

        );
    }

    if (error) {

        return (

            <div className="container mt-4">

                <div className="alert alert-danger text-center">

                    <h5 className="mb-2">
                        Unable to Load Insurance Details
                    </h5>

                    <p className="mb-0">
                        {error}
                    </p>

                </div>

            </div>

        );

    }

    return (

        <div className="container mt-4">

            <div className="mb-4">

                <h2 className="fw-bold text-primary">
                    💳 My Insurance
                </h2>

                <p className="text-muted mb-0">
                    View your enrolled insurance plans, coverage details, and policy status.
                </p>

            </div>

            {
                enrollments.length === 0 ?

                    (
                        <div className="alert alert-info text-center">

                            <h5 className="mb-2">
                                No Insurance Plans Found
                            </h5>

                            <p className="mb-0">
                                You haven't enrolled in any insurance plan yet.
                            </p>

                        </div>
                    )

                    :

                    (

                        <div className="table-responsive">

                            <table className="table table-hover table-striped align-middle">

                                <thead className="table-primary">

                                    <tr>
                                        <th>Enrollment ID</th>
                                        <th>Plan Name</th>
                                        <th>Company</th>
                                        <th>Coverage</th>
                                        <th>Premium</th>
                                        <th>Enrollment Date</th>
                                        <th>Expiry Date</th>
                                        <th className="text-center">Status</th>
                                    </tr>

                                </thead>

                                <tbody>

                                    {

                                        enrollments.map((enrollment) => (

                                            <tr key={enrollment.enrollmentId}>

                                                <td>{enrollment.enrollmentId}</td>
                                                <td>{enrollment.planName}</td>
                                                <td>{enrollment.companyName}</td>
                                                <td>
                                                    ₹ {Number(enrollment.coverageAmount).toLocaleString("en-IN")}
                                                </td>

                                                <td>
                                                    ₹ {Number(enrollment.premium).toLocaleString("en-IN")}
                                                </td>
                                                <td>
                                                    {new Date(enrollment.enrollmentDate).toLocaleDateString("en-IN")}
                                                </td>

                                                <td>
                                                    {new Date(enrollment.expiryDate).toLocaleDateString("en-IN")}
                                                </td>
                                                <td className="text-center">

                                                    <span
                                                        className={
                                                            enrollment.status === "ACTIVE"
                                                                ? "badge bg-success"
                                                                : enrollment.status === "EXPIRED"
                                                                ? "badge bg-danger"
                                                                : "badge bg-secondary"
                                                        }
                                                    >
                                                        {enrollment.status}
                                                    </span>

                                                </td>

                                            </tr>

                                        ))

                                    }

                                </tbody>

                            </table>
                        </div>

                    )

            }

        </div>

    );

}

export default MyInsurance;