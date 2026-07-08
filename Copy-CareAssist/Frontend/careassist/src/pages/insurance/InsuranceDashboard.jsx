import { Link, useNavigate } from "react-router-dom";

function InsuranceDashboard() {

    const navigate = useNavigate();

    const email = localStorage.getItem("email");

    const handleLogout = () => {

        localStorage.clear();
        navigate("/");

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-3">
                Insurance Dashboard
            </h2>

            <div className="alert alert-success">

                Welcome <strong>{email}</strong>

            </div>

            <div className="row">

                <div className="col-md-6 mb-4">

                    <div className="card shadow">

                        <div className="card-body text-center">

                            <h5>Review Pending Claims</h5>

                            <p>
                                View and process pending insurance claims.
                            </p>

                            <Link
                                to="/insurance/pending-claims"
                                className="btn btn-primary"
                            >
                                Open
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-6 mb-4">

                    <div className="card shadow">

                        <div className="card-body text-center">

                            <h5>Processed Claims</h5>

                            <p>
                                View approved and rejected claims.
                            </p>

                            <Link
                                to="/insurance/processed-claims"
                                className="btn btn-success"
                            >
                                View
                            </Link>

                        </div>

                    </div>

                </div>

            </div>

            <button
                className="btn btn-danger"
                onClick={handleLogout}
            >
                Logout
            </button>

        </div>

    );

}

export default InsuranceDashboard;