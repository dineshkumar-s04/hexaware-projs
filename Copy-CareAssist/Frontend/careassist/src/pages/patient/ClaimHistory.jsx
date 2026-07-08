import { useEffect, useState } from "react";
import { getPatientClaims } from "../../services/claim/claimService";

function ClaimHistory() {

    const [claims, setClaims] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        loadClaims();

    }, []);

    const loadClaims = async () => {

        try {

            setLoading(true);

            const patientId = localStorage.getItem("patientId");

            const data = await getPatientClaims(patientId);

            setClaims(data);

        }
        catch (err) {

            console.error(err);

            setError("Failed to load claim history.");

        }
        finally {

            setLoading(false);

        }

    };

    if (loading) {

        return (

            <div className="container mt-5 text-center">

                <div
                    className="spinner-border text-primary"
                    role="status"
                >
                    <span className="visually-hidden">
                        Loading...
                    </span>
                </div>

                <p className="mt-3">
                    Loading claim history...
                </p>

            </div>

        );

    }

    return (

        <div className="container mt-4">

            <div className="mb-4">

                <h2 className="fw-bold">
                    Claim History
                </h2>

                <p className="text-muted mb-0">
                    View the status of all insurance claims you have submitted.
                </p>

            </div>

            {

                error &&

                <div className="alert alert-danger">

                    {error}

                </div>

            }

            <div className="card shadow-sm">

                <div className="card-body">

                    {

                        claims.length === 0 ?

                            (

                                <div className="alert alert-info mb-0">

                                    No claims found.

                                </div>

                            )

                            :

                            (

                                <div className="table-responsive">

                                    <table className="table table-hover align-middle">

                                        <thead className="table-dark">

                                            <tr>

                                                <th>Claim ID</th>
                                                <th>Invoice ID</th>
                                                <th>Claim Amount</th>
                                                <th>Diagnosis</th>
                                                <th>Claim Date</th>
                                                <th>Status</th>
                                                <th>Approved Date</th>

                                            </tr>

                                        </thead>

                                        <tbody>

                                            {

                                                claims.map((claim) => (

                                                    <tr key={claim.claimId}>

                                                        <td>
                                                            {claim.claimId}
                                                        </td>

                                                        <td>
                                                            {claim.invoiceId}
                                                        </td>

                                                        <td>
                                                            ₹{Number(claim.claimAmount).toLocaleString()}
                                                        </td>

                                                        <td>
                                                            {claim.diagnosis}
                                                        </td>

                                                        <td>
                                                            {new Date(claim.claimDate).toLocaleDateString()}
                                                        </td>

                                                        <td>

                                                            <span
                                                                className={`badge ${

                                                                    claim.status === "APPROVED"
                                                                        ? "bg-success"

                                                                        : claim.status === "REJECTED"
                                                                            ? "bg-danger"

                                                                            : "bg-warning text-dark"

                                                                    }`}
                                                            >

                                                                {claim.status}

                                                            </span>

                                                        </td>

                                                        <td>

                                                            {

                                                                claim.approvedDate

                                                                    ? new Date(
                                                                        claim.approvedDate
                                                                    ).toLocaleDateString()

                                                                    : "-"

                                                            }

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

            </div>

        </div>

    );

}

export default ClaimHistory;