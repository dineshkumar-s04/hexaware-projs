import { Form, Button } from "react-bootstrap";

function LoginForm() {
    return (
        <>
            <div className="text-center mb-4">
                <h2 className="fw-bold text-primary">
                    CareAssist
                </h2>

                <p className="text-muted">
                    Medical Billing & Claims Management System
                </p>
            </div>

            <Form>

                <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>

                    <Form.Control
                        type="email"
                        placeholder="Enter your email"
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Password</Form.Label>

                    <Form.Control
                        type="password"
                        placeholder="Enter your password"
                    />
                </Form.Group>

                <div className="d-flex justify-content-end mb-3">
                    <a href="/" className="text-decoration-none">
                        Forgot Password?
                    </a>
                </div>

                <div className="d-grid">
                    <Button variant="primary" size="lg">
                        Login
                    </Button>
                </div>

                <div className="text-center mt-4">
                    Don't have an account?{" "}
                    <a href="/" className="text-decoration-none">
                        Register
                    </a>
                </div>

            </Form>
        </>
    );
}

export default LoginForm;