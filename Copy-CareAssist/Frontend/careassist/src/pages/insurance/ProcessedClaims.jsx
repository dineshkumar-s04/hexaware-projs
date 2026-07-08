import { useEffect, useState } from "react";
import { getProcessedClaims } from "../../services/insurance/insuranceService";

function ProcessedClaims() {

    const [claims, setClaims] = useState([]);

    const loadClaims = async () => {

        try {

            const data = await getProcessedClaims();

            setClaims(data);

        } catch (error) {

            console.error("Error loading processed claims", error);

        }

    };

    useEffect(() => {

        loadClaims();

    }, []);

    return (

        <div className="container mt-4">

            <h2 className="mb-4">
                Processed Claims
            </h2>

            <table className="table table-bordered table-hover">

                <thead className="table-dark">

                    <tr>

                        <th>Claim ID</th>
                        <th>Patient ID</th>
                        <th>Invoice ID</th>
                        <th>Claim Amount</th>
                        <th>Diagnosis</th>
                        <th>Status</th>
                        <th>Approved Date</th>
                        <th>Rejection Reason</th>

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
                                        No Processed Claims
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

                                        <td>

                                            {
                                                claim.status === "APPROVED" ?

                                                    <span className="badge bg-success">
                                                        APPROVED
                                                    </span>

                                                    :

                                                    <span className="badge bg-danger">
                                                        REJECTED
                                                    </span>
                                            }

                                        </td>

                                        <td>
                                            {claim.approvedDate ?? "-"}
                                        </td>

                                        <td>
                                            {claim.rejectionReason ?? "-"}
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

export default ProcessedClaims;