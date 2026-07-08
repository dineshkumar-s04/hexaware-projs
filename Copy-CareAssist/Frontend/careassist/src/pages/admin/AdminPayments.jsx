import { useEffect, useState } from "react";
import { getAllPayments } from "../../services/admin/adminService";

function AdminPayments() {

    const [payments, setPayments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadPayments();
    }, []);

    const loadPayments = async () => {
        try {
            const data = await getAllPayments();
            setPayments(data);
        } catch (err) {
            console.error(err);
            setError("Unable to load payments.");
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="container mt-4">
                <h3>Loading Payments...</h3>
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
                Payment History
            </h2>

            <table className="table table-bordered table-hover">

                <thead className="table-dark">
                    <tr>
                        <th>Payment ID</th>
                        <th>Claim ID</th>
                        <th>Amount</th>
                        <th>Payment Date</th>
                        <th>Status</th>
                        <th>Transaction Reference</th>
                    </tr>
                </thead>

                <tbody>

                    {payments.length === 0 ? (
                        <tr>
                            <td colSpan="6" className="text-center">
                                No Payments Found
                            </td>
                        </tr>
                    ) : (
                        payments.map((payment) => (
                            <tr key={payment.paymentId}>
                                <td>{payment.paymentId}</td>
                                <td>{payment.claimId}</td>
                                <td>₹{payment.amount}</td>
                                <td>{payment.paymentDate || "-"}</td>
                                <td>{payment.status}</td>
                                <td>{payment.transactionRef || "-"}</td>
                            </tr>
                        ))
                    )}

                </tbody>

            </table>

        </div>
    );
}

export default AdminPayments;