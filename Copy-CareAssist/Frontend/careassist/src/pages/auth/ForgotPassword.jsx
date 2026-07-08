import { useState } from "react";
import { Link } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { forgotPassword } from "../../services/auth/authService";

const forgotPasswordSchema = Yup.object({

    email: Yup.string()
        .email("Invalid email")
        .required("Email is required")

});

function ForgotPassword() {

    const [alert, setAlert] = useState({
        show: false,
        type: "",
        message: "",
    });

    const [loading, setLoading] = useState(false);

    const initialValues = {
        email: ""
    };

    const handleSubmit = async (values, { resetForm }) => {

        setLoading(true);

        try {

            const response = await forgotPassword(values.email);

            setAlert({
                show: true,
                type: "success",
                message: response,
            });

            resetForm();

        } catch (error) {

            console.error(error);

            setAlert({
                show: true,
                type: "danger",
                message:
                    error.response?.data || "Unable to process your request.",
            });

        } finally {
            setLoading(false);
        }

    };

    return (

        <div className="row justify-content-center mt-5">

            <div className="col-md-5">

                <div className="card shadow">

                    <div className="card-body">

                        {alert.show && (
                            <div
                                className={`alert alert-${alert.type} alert-dismissible fade show`}
                                role="alert"
                            >
                                {alert.message}

                                <button
                                    type="button"
                                    className="btn-close"
                                    onClick={() =>
                                        setAlert({
                                            show: false,
                                            type: "",
                                            message: "",
                                        })
                                    }
                                ></button>
                            </div>
                        )}

                        <h2 className="text-center mb-4">
                            Forgot Password
                        </h2>

                        <Formik
                            initialValues={initialValues}
                            validationSchema={forgotPasswordSchema}
                            onSubmit={handleSubmit}
                        >

                            <Form>

                                <div className="mb-3">

                                    <label className="form-label">
                                        Email
                                    </label>

                                    <Field
                                        type="email"
                                        name="email"
                                        className="form-control"
                                        placeholder="Enter your registered email"
                                    />

                                    <div className="text-danger">
                                        <ErrorMessage name="email" />
                                    </div>

                                </div>

                                <button
                                    type="submit"
                                    className="btn btn-warning w-100"
                                    disabled={loading}
                                >
                                    {loading ? (
                                        <>
                                            <span
                                                className="spinner-border spinner-border-sm me-2"
                                                role="status"
                                                aria-hidden="true"
                                            ></span>
                                            Sending Reset Link...
                                        </>
                                    ) : (
                                        "Send Reset Link"
                                    )}
                                </button>

                            </Form>

                        </Formik>

                        <div className="mt-3 text-center">

                            <Link to="/">
                                Back to Login
                            </Link>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default ForgotPassword;