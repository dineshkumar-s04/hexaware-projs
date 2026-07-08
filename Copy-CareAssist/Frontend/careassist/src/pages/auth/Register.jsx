import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { register } from "../../services/auth/authService";

const registerSchema = Yup.object({

  name: Yup.string()
    .min(3, "Name must contain at least 3 characters")
    .required("Name is required"),

  email: Yup.string()
    .email("Invalid email")
    .required("Email is required"),

  phone: Yup.string()
    .matches(/^[0-9]{10}$/, "Phone number must contain 10 digits")
    .required("Phone number is required"),

  password: Yup.string()
    .min(6, "Password must contain at least 6 characters")
    .required("Password is required"),

  confirmPassword: Yup.string()
    .oneOf([Yup.ref("password")], "Passwords do not match")
    .required("Confirm Password is required"),

  role: Yup.string()
    .required("Role is required")

});

function Register() {

  const navigate = useNavigate();

  const [alert, setAlert] = useState({
    show: false,
    type: "",
    message: "",
  });

  const [loading, setLoading] = useState(false);

  const initialValues = {
    name: "",
    email: "",
    phone: "",
    password: "",
    confirmPassword: "",
    role: ""
  };

  const handleSubmit = async (values) => {

    setLoading(true);

    try {

      const userData = {
        name: values.name,
        email: values.email,
        phone: values.phone,
        password: values.password,
        role: values.role
      };

      await register(userData);

      setAlert({
        show: true,
        type: "success",
        message: "Registration Successful! Redirecting to Login...",
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
          error.response?.data?.message || "Registration Failed",
      });

    } finally {
        setLoading(false);
    }

  };

  return (

    <div className="row justify-content-center mt-5">

      <div className="col-md-6">

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
              CareAssist Registration
            </h2>

            <Formik
              initialValues={initialValues}
              validationSchema={registerSchema}
              onSubmit={handleSubmit}
            >

              <Form>

                <div className="mb-3">

                  <label className="form-label">
                    Full Name
                  </label>

                  <Field
                    type="text"
                    name="name"
                    className="form-control"
                    placeholder="Enter Full Name"
                  />

                  <div className="text-danger">
                    <ErrorMessage name="name" />
                  </div>

                </div>

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
                    Phone Number
                  </label>

                  <Field
                    type="text"
                    name="phone"
                    className="form-control"
                    placeholder="Enter Phone Number"
                  />

                  <div className="text-danger">
                    <ErrorMessage name="phone" />
                  </div>

                </div>

                <div className="mb-3">

                  <label className="form-label">
                    Role
                  </label>

                  <Field
                    as="select"
                    name="role"
                    className="form-select"
                  >

                    <option value="">
                      Select Role
                    </option>

                    <option value="PATIENT">
                      Patient
                    </option>

                    <option value="PROVIDER">
                      Healthcare Provider
                    </option>

                    <option value="INSURANCE">
                      Insurance Company
                    </option>

                  </Field>

                  <div className="text-danger">
                    <ErrorMessage name="role" />
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

                <div className="mb-3">

                  <label className="form-label">
                    Confirm Password
                  </label>

                  <Field
                    type="password"
                    name="confirmPassword"
                    className="form-control"
                    placeholder="Confirm Password"
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
                      Registering...
                    </>
                  ) : (
                    "Register"
                  )}
                </button>

              </Form>

            </Formik>

            <div className="mt-3 text-center">

              Already have an account?{" "}

              <Link to="/">
                Login
              </Link>

            </div>

          </div>

        </div>

      </div>

    </div>

  );

}

export default Register;