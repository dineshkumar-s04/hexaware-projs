import { useEffect, useState } from "react";
import { getPendingClaims, approveClaim, rejectClaim } from "../../services/insurance/insuranceService";

function PendingClaims() {

    const [claims, setClaims] = useState([]);

    const loadClaims = async () => {

        try {

            const data = await getPendingClaims();

            setClaims(data);

        } catch (error) {

            console.error("Error loading pending claims", error);

        }

    };

    const handleApprove = async (claimId) => {

        try {

            await approveClaim(claimId);

            alert("Claim approved successfully.");

            loadClaims();

        } catch (error) {

            console.error(error);

            alert("Unable to approve claim.");

        }

    };

    const handleReject = async (claimId) => {

        const reason = prompt("Enter rejection reason:");

        if (!reason || reason.trim() === "") {

            return;

        }

        try {

            await rejectClaim(claimId, reason);

            alert("Claim rejected successfully.");

            loadClaims();

        } catch (error) {

            console.error(error);

            alert("Unable to reject claim.");

        }

    };

    useEffect(() => {

        loadClaims();

    }, []);

    return (

        <div className="container mt-4">

            <h2 className="mb-4">
                Pending Claims
            </h2>

            <table className="table table-bordered table-hover">

                <thead className="table-dark">

                    <tr>

                        <th>Claim ID</th>
                        <th>Patient ID</th>
                        <th>Invoice ID</th>
                        <th>Claim Amount</th>
                        <th>Diagnosis</th>
                        <th>Claim Date</th>
                        <th>Status</th>
                        <th>Actions</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        claims.length === 0 ?

                            (

                                <tr>

                                    <td
                                        colSpan="8"
                                        className="text-center"
                                    >
                                        No Pending Claims
                                    </td>

                                </tr>

                            )

                            :

                            (

                                claims.map((claim) => (

                                    <tr key={claim.claimId}>

                                        <td>{claim.claimId}</td>
                                        <td>{claim.patientId}</td>
                                        <td>{claim.invoiceId}</td>
                                        <td>₹{claim.claimAmount}</td>
                                        <td>{claim.diagnosis}</td>
                                        <td>{claim.claimDate}</td>

                                        <td>
                                            <span className="badge bg-warning text-dark">
                                                {claim.status}
                                            </span>
                                        </td>

                                        <td>

                                            <button
                                                className="btn btn-success btn-sm me-2"
                                                onClick={() => handleApprove(claim.claimId)}
                                            >
                                                Approve
                                            </button>

                                            <button
                                                className="btn btn-danger btn-sm"
                                                onClick={() => handleReject(claim.claimId)}
                                            >
                                                Reject
                                            </button>

                                        </td>

                                    </tr>

                                ))

                            )

                    }

                </tbody>

            </table>

        </div>

    );

}

export default PendingClaims;