import { Link } from "react-router-dom";

function ProviderDashboard() {

    const email = localStorage.getItem("email");

    return (

        <div className="container mt-4">

            <h2 className="mb-4">
                Provider Dashboard
            </h2>

            <div className="alert alert-success">
                Welcome <strong>{email}</strong>
            </div>

            <div className="row">

                <div className="col-md-6 mb-3">

                    <div className="card shadow-sm">

                        <div className="card-body">

                            <h5>Generate Invoice</h5>

                            <p>
                                Create a new invoice for a patient.
                            </p>

                            <Link
                                to="/provider/generate-invoice"
                                className="btn btn-primary"
                            >
                                Generate Invoice
                            </Link>

                        </div>

                    </div>

                </div>

                <div className="col-md-6 mb-3">

                    <div className="card shadow-sm">

                        <div className="card-body">

                            <h5>View Generated Invoices</h5>

                            <p>
                                View all invoices created by you.
                            </p>

                            <Link
                                to="/provider/invoices"
                                className="btn btn-success"
                            >
                                View Invoices
                            </Link>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default ProviderDashboard;