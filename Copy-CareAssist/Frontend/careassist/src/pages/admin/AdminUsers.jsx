import { useEffect, useState } from "react";
import { getAllUsers } from "../../services/admin/adminService";

function AdminUsers() {

    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {
        try {
            const data = await getAllUsers();
            setUsers(data);
        } catch (err) {
            console.error(err);
            setError("Unable to load users.");
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="container mt-4">
                <h3>Loading Users...</h3>
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
                Registered Users
            </h2>

            <table className="table table-bordered table-hover">

                <thead className="table-dark">
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Account Status</th>
                    </tr>
                </thead>

                <tbody>

                    {users.length === 0 ? (
                        <tr>
                            <td colSpan="6" className="text-center">
                                No Users Found
                            </td>
                        </tr>
                    ) : (
                        users.map((user) => (
                            <tr key={user.userId}>
                                <td>{user.userId}</td>
                                <td>{user.name}</td>
                                <td>{user.email}</td>
                                <td>{user.phone}</td>
                                <td>{user.role}</td>
                                <td>{user.accountStatus}</td>
                            </tr>
                        ))
                    )}

                </tbody>

            </table>

        </div>
    );
}

export default AdminUsers;