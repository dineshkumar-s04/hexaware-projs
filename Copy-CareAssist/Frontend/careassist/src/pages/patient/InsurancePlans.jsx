import { useEffect, useState } from "react";
import { getInsurancePlans, enrollInsurancePlan, getPatientEnrollments } from "../../services/patient/patientService";

function InsurancePlans() {

    const [plans, setPlans] = useState([]);
    
    const [enrolledPlans, setEnrolledPlans] = useState([]);

    const [loading, setLoading] = useState(true);
    
    useEffect(() => {

        loadPlans();

        loadEnrollments();

    }, []);

    

    const loadPlans = async () => {

        try {

            setLoading(true);

            const data = await getInsurancePlans();

            setPlans(data);

        } catch (error) {

            console.error(error);

            alert("Unable to load insurance plans.");

        } finally {

            setLoading(false);

        }

    };

    const loadEnrollments = async () => {

        try {

            const patientId = localStorage.getItem("patientId");

            const data = await getPatientEnrollments(patientId);

            setEnrolledPlans(data.map(item => item.planId));

        } catch (error) {

            console.error(error);

        }

    };

    const handleEnroll = async (planId) => {

        try {

            const patientId = localStorage.getItem("patientId");

            await enrollInsurancePlan(patientId, planId);

            alert("Insurance plan enrolled successfully.");

            await loadPlans();

            await loadEnrollments();

        } catch (error) {

            console.error(error);

            alert(error.response?.data?.message || "Enrollment failed.");

        }

    };

    return (

        <div className="container mt-4">

            <div className="mb-4">

                <h2 className="fw-bold text-primary">
                    🛡️ Insurance Plans
                </h2>

                <p className="text-muted mb-0">
                    Browse and enroll in the insurance plan that best suits your healthcare needs.
                </p>

            </div>

            {loading && (

                <div className="text-center my-5">

                    <div
                        className="spinner-border text-primary"
                        role="status"
                    >
                        <span className="visually-hidden">
                            Loading...
                        </span>
                    </div>

                    <p className="mt-3 text-muted">
                        Loading insurance plans...
                    </p>

                </div>

            )}

            {!loading && (

                <div className="table-responsive">

                    <table className="table table-hover table-striped align-middle">

                        <thead className="table-primary">

                            <tr>

                                <th>Plan Name</th>
                                <th>Coverage Amount</th>
                                <th>Premium</th>
                                <th>Description</th>
                                <th className="text-center">Action</th>

                            </tr>

                        </thead>

                        <tbody>

                            {plans.length === 0 ? (

                                <tr>

                                    <td
                                        colSpan="5"
                                        className="text-center py-4 text-muted"
                                    >
                                        No insurance plans are available at the moment.
                                    </td>

                                </tr>

                            ) : (

                                plans.map((plan) => (

                                    <tr key={plan.planId}>

                                        <td>{plan.planName}</td>

                                        <td>₹ {plan.coverageAmount}</td>

                                        <td>₹ {plan.premium}</td>

                                        <td>{plan.description}</td>

                                        <td className="text-center">

                                            <button
                                                className={
                                                    enrolledPlans.includes(plan.planId)
                                                        ? "btn btn-success btn-sm px-3"
                                                        : "btn btn-secondary btn-sm px-3"
                                                }
                                                disabled={enrolledPlans.includes(plan.planId)}
                                                onClick={() => handleEnroll(plan.planId)}
                                            >
                                                {enrolledPlans.includes(plan.planId)
                                                    ? "✓ Enrolled"
                                                    : "Enroll"}
                                            </button>

                                        </td>

                                    </tr>

                                ))

                            )}

                        </tbody>

                    </table>

                </div>

            )}

        </div>

    );

}

export default InsurancePlans;