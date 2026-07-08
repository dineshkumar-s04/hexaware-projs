import { useState } from "react";
import { useSearchParams, useNavigate, Link } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { resetPassword } from "../../services/auth/authService";

const resetPasswordSchema = Yup.object({

    newPassword: Yup.string()
        .min(6, "Password must contain at least 6 characters")
        .required("New Password is required"),

    confirmPassword: Yup.string()
        .oneOf([Yup.ref("newPassword")], "Passwords do not match")
        .required("Confirm Password is required")

});

function ResetPassword() {

    const [searchParams] = useSearchParams();

    const navigate = useNavigate();

    const [alert, setAlert] = useState({
        show: false,
        type: "",
        message: "",
    });

    const [loading, setLoading] = useState(false);

    const token = searchParams.get("token");

    const initialValues = {

        newPassword: "",

        confirmPassword: ""

    };

    const handleSubmit = async (values) => {

        setLoading(true);

        try {

            const response = await resetPassword(
                token,
                values.newPassword
            );

            setAlert({
                show: true,
                type: "success",
                message: response,
            });

            setTimeout(() => {
                navigate("/");
            }, 2000);

        } catch (error) {

            console.error(error);

            setAlert({
                show: true,
                type: "danger",
                message:
                    error.response?.data || "Unable to reset password.",
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

                            Reset Password

                        </h2>

                        <Formik
                            initialValues={initialValues}
                            validationSchema={resetPasswordSchema}
                            onSubmit={handleSubmit}
                        >

                            <Form>

                                <div className="mb-3">

                                    <label className="form-label">

                                        New Password

                                    </label>

                                    <Field
                                        type="password"
                                        name="newPassword"
                                        className="form-control"
                                    />

                                    <div className="text-danger">

                                        <ErrorMessage name="newPassword" />

                                    </div>

                                </div>

                                <div className="mb-3">

                                    <label className="form-label">

                                        Confirm Password

                                    </label>

                                    <Field
                                        type="password"
                                        name="confirmPassword"
                                        className="form-control"
                                    />

                                    <div className="text-danger">

                                        <ErrorMessage name="confirmPassword" />

                                    </div>

                                </div>

                                <button
                                    type="submit"
                                    className="btn btn-success w-100"
                                    disabled={loading}
                                >
                                    {loading ? (
                                        <>
                                            <span
                                                className="spinner-border spinner-border-sm me-2"
                                                role="status"
                                                aria-hidden="true"
                                            ></span>
                                            Resetting Password...
                                        </>
                                    ) : (
                                        "Reset Password"
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

export default ResetPassword;