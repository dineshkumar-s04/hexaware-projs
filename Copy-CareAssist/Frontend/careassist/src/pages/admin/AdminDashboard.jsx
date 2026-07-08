import { Link } from "react-router-dom";

function AdminDashboard() {

    const email = localStorage.getItem("email");

    return (

        <div className="container mt-4">

            <h2 className="mb-3">
                Admin Dashboard
            </h2>

            <div className="alert alert-primary">
                Welcome <strong>{email}</strong>
            </div>

            <div className="row mt-4">

                <div className="col-md-4 mb-4">

                    <div className="card shadow h-100">

                        <div className="card-body text-center">

                            <h4>👥 Users</h4>

                            <p>
                                View all registered users.
                            </p>

                            <Link
                                to="/admin/users"
                                className="btn btn-primary"
                            >
                                View Users
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-4">

                    <div className="card shadow h-100">

                        <div className="card-body text-center">

                            <h4>📄 Claims</h4>

                            <p>
                                View all submitted claims.
                            </p>

                            <Link
                                to="/admin/claims"
                                className="btn btn-success"
                            >
                                View Claims
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-4">

                    <div className="card shadow h-100">

                        <div className="card-body text-center">

                            <h4>💳 Payments</h4>

                            <p>
                                View all payment records.
                            </p>

                            <Link
                                to="/admin/payments"
                                className="btn btn-warning"
                            >
                                View Payments
                            </Link>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default AdminDashboard;