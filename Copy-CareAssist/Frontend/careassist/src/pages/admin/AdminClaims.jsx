import { useEffect, useState } from "react";
import { getAllClaims } from "../../services/admin/adminService";

function AdminClaims() {

    const [claims, setClaims] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadClaims();
    }, []);

    const loadClaims = async () => {
        try {
            const data = await getAllClaims();
            setClaims(data);
        } catch (err) {
            console.error(err);
            setError("Unable to load claims.");
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="container mt-4">
                <h3>Loading Claims...</h3>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container mt-4">
                <div className="alert alert-danger">
                    {error}
                </div>
            </div>
        );
    }

    return (
        <div className="container mt-4">

            <h2 className="mb-4">
                All Claims
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
                        <th>Claim Date</th>
                        <th>Approved Date</th>
                    </tr>
                </thead>

                <tbody>

                    {claims.length === 0 ? (
                        <tr>
                            <td colSpan="8" className="text-center">
                                No Claims Found
                            </td>
                        </tr>
                    ) : (
                        claims.map((claim) => (
                            <tr key={claim.claimId}>
                                <td>{claim.claimId}</td>
                                <td>{claim.patientId}</td>
                                <td>{claim.invoiceId}</td>
                                <td>₹{claim.claimAmount}</td>
                                <td>{claim.diagnosis}</td>
                                <td>{claim.status}</td>
                                <td>{claim.claimDate}</td>
                                <td>{claim.approvedDate || "-"}</td>
                            </tr>
                        ))
                    )}

                </tbody>

            </table>

        </div>
    );
}

export default AdminClaims;