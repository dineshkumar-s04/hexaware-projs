import { Link } from "react-router-dom";

function PatientDashboard() {

    const email = localStorage.getItem("email");

    return (

        <div className="container mt-4">

            <div className="mb-4">

                <h2 className="fw-bold text-primary">
                    🏥 Patient Dashboard
                </h2>

                <p className="text-muted mb-0">
                    Welcome back,
                    <strong> {email}</strong>
                </p>

                <small className="text-secondary">
                    Manage your insurance, invoices and medical claims from one place.
                </small>

            </div>

            <div className="row g-4">

                <div className="col-md-6 mb-4">

                    <div className="card shadow-sm h-100 border-0">
                        
                        <div className="card-body d-flex flex-column text-center">

                            <div className="display-4 mb-3">
                                🛡️
                            </div>

                            <h5 className="fw-bold">
                                Insurance Plans
                            </h5>

                            <p className="text-muted flex-grow-1">
                                Browse all available insurance plans and choose the one that best fits your healthcare needs.
                            </p>

                            <Link
                                to="/patient/plans"
                                className="btn btn-primary mt-auto"
                            >
                                View Plans
                            </Link>

                        </div>

                    </div>

                </div>                

                <div className="col-md-6 mb-4">

                    <div className="card shadow-sm h-100 border-0">

                        <div className="card-body d-flex flex-column text-center">

                            <div className="display-4 mb-3">
                                💳
                            </div>

                            <h5 className="fw-bold">
                                My Insurance
                            </h5>

                            <p className="text-muted flex-grow-1">
                                View your enrolled insurance plan, coverage details, and policy status.
                            </p>

                            <Link
                                to="/patient/my-insurance"
                                className="btn btn-success mt-auto"
                            >
                                View Insurance
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-6 mb-4">

                    <div className="card shadow-sm h-100 border-0">
                        
                        <div className="card-body d-flex flex-column text-center">

                            <div className="display-4 mb-3">
                                📄
                            </div>

                            <h5 className="fw-bold">
                                My Invoices
                            </h5>

                            <p className="text-muted flex-grow-1">
                                Access all generated invoices and review your billing information.
                            </p>

                            <Link
                                to="/patient/invoice"
                                className="btn btn-warning mt-auto"
                            >
                                View Invoices
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-6 mb-4">

                    <div className="card shadow-sm h-100 border-0">
                        
                        <div className="card-body d-flex flex-column text-center">

                            <div className="display-4 mb-3">
                                📝
                            </div>

                            <h5 className="fw-bold">
                                Submit Claim
                            </h5>

                            <p className="text-muted flex-grow-1">
                                Submit a new medical insurance claim with your invoice and treatment details.
                            </p>

                            <Link
                                to="/patient/submit-claim"
                                className="btn btn-danger mt-auto"
                            >
                                Submit Claim
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-6 mb-4">

                    <div className="card shadow-sm h-100 border-0">

                        <div className="card-body d-flex flex-column text-center">

                            <div className="display-4 mb-3">
                                📊
                            </div>

                            <h5 className="fw-bold">
                                Claim History
                            </h5>

                            <p className="text-muted flex-grow-1">
                                Track the status of all your submitted insurance claims in one place.
                            </p>

                            <Link
                                to="/patient/claim-history"
                                className="btn btn-info mt-auto"
                            >
                                View History
                            </Link>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default PatientDashboard;