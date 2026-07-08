import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { login } from "../../services/auth/authService";
import { getPatientByEmail } from "../../services/patient/patientService";
import { getProviderByEmail } from "../../services/provider/providerService";

const loginSchema = Yup.object({

  email: Yup.string()
    .email("Invalid email")
    .required("Email is required"),

  password: Yup.string()
    .required("Password is required")

});

function Login() {

    const navigate = useNavigate();

    const [alert, setAlert] = useState({
      show: false,
      type: "",
      message: "",
    });

    const [loading, setLoading] = useState(false);

    const initialValues = {
      email: "",
      password: ""
    };

  const handleSubmit = async (values) => {

    setLoading(true);

    try {

        const response = await login(values);

        localStorage.setItem("token", response.token);
        localStorage.setItem("email", response.email);
        localStorage.setItem("role", response.role);

        // Get patient details only for patient users
        if (response.role === "PATIENT") {

            const patient = await getPatientByEmail(response.email);

            localStorage.setItem("patientId", patient.patientId);

        }

        if (response.role === "PROVIDER") {

            const provider = await getProviderByEmail(response.email);

            localStorage.setItem("providerId", provider.providerId);

        }

        switch (response.role) {

            case "ADMIN":
                navigate("/admin");
                break;

            case "PATIENT":
                navigate("/patient");
                break;

            case "PROVIDER":
                navigate("/provider");
                break;

            case "INSURANCE":
                navigate("/insurance");
                break;

            default:
                navigate("/");
        }

    } catch (error) {

        console.error(error);

        setAlert({
          show: true,
          type: "danger",
          message: "Invalid Email or Password",
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
              CareAssist Login
            </h2>

            <Formik
              initialValues={initialValues}
              validationSchema={loginSchema}
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
                    placeholder="Enter Email"
                  />

                  <div className="text-danger">

                    <ErrorMessage name="email" />

                  </div>

                </div>

                <div className="mb-3">

                  <label className="form-label">
                    Password
                  </label>

                  <Field
                    type="password"
                    name="password"
                    className="form-control"
                    placeholder="Enter Password"
                  />

                  <div className="text-danger">

                    <ErrorMessage name="password" />

                  </div>

                </div>

                <button
                  type="submit"
                  className="btn btn-primary w-100"
                  disabled={loading}
                >
                  {loading ? (
                    <>
                      <span
                        className="spinner-border spinner-border-sm me-2"
                        role="status"
                        aria-hidden="true"
                      ></span>
                      Logging in...
                    </>
                  ) : (
                    "Login"
                  )}
                </button>

              </Form>

            </Formik>

            <div className="mt-3 text-center">

              <Link to="/forgot-password">

                Forgot Password?

              </Link>

            </div>

            <div className="mt-2 text-center">

              Don't have an account?

              {" "}

              <Link to="/register">

                Register

              </Link>

            </div>

          </div>

        </div>

      </div>

    </div>

  );
}

export default Login;