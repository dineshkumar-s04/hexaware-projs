import { useEffect, useState } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";

import { getPatientInvoices } from "../../services/patient/patientService";
import { submitClaim } from "../../services/claim/claimService";

function SubmitClaim() {

    const [invoices, setInvoices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {

        loadInvoices();

    }, []);

    const loadInvoices = async () => {

        try {

            setLoading(true);

            const patientId = localStorage.getItem("patientId");

            const data = await getPatientInvoices(patientId);

            setInvoices(data);

        }
        catch (err) {

            console.error(err);

            setError("Unable to load invoices.");

        }
        finally {

            setLoading(false);

        }

    };

    const validationSchema = Yup.object({

        invoiceId: Yup.number()
            .typeError("Please select an invoice")
            .required("Please select an invoice"),

        claimAmount: Yup.number()
            .required("Claim amount is required")
            .positive("Claim amount must be greater than 0"),

        diagnosis: Yup.string()
            .required("Diagnosis is required"),

        treatmentDetails: Yup.string()
            .required("Treatment details are required")

    });

    return (

        <div className="container mt-4">

            <div className="mb-4">

                <h2 className="fw-bold">
                    Submit Insurance Claim
                </h2>

                <p className="text-muted mb-0">
                    Submit your medical claim for insurance processing.
                </p>

            </div>

            {
                success &&
                <div className="alert alert-success">
                    {success}
                </div>
            }

            {
                error &&
                <div className="alert alert-danger">
                    {error}
                </div>
            }

            {
                loading ?

                    <div className="text-center my-5">

                        <div
                            className="spinner-border text-primary"
                            role="status"
                        >
                            <span className="visually-hidden">
                                Loading...
                            </span>
                        </div>

                        <p className="mt-3">
                            Loading invoices...
                        </p>

                    </div>

                    :

                    <div className="card shadow-sm">

                        <div className="card-body">

                            <Formik

                                initialValues={{
                                    invoiceId: "",
                                    claimAmount: "",
                                    diagnosis: "",
                                    treatmentDetails: ""
                                }}

                                validationSchema={validationSchema}

                                onSubmit={async (
                                    values,
                                    {
                                        resetForm,
                                        setSubmitting
                                    }
                                ) => {

                                    try {

                                        setSuccess("");
                                        setError("");

                                        const patientId =
                                            localStorage.getItem("patientId");

                                        const claimData = {

                                            patientId: Number(patientId),

                                            invoiceId: Number(values.invoiceId),

                                            companyId: 1,

                                            claimAmount: values.claimAmount,

                                            diagnosis: values.diagnosis,

                                            treatmentDetails:
                                                values.treatmentDetails,

                                            claimDate:
                                                new Date()
                                                    .toISOString()
                                                    .split("T")[0],

                                            status: "PENDING"

                                        };

                                        await submitClaim(claimData);

                                        setSuccess(
                                            "Claim submitted successfully."
                                        );

                                        resetForm();

                                    }
                                    catch (err) {

                                        console.error(err);

                                        setError(
                                            "Failed to submit claim."
                                        );

                                    }
                                    finally {

                                        setSubmitting(false);

                                    }

                                }}

                            >

                                {
                                    ({ isSubmitting }) => (

                                        <Form>

                                            <div className="mb-3">

                                                <label className="form-label fw-semibold">
                                                    Invoice
                                                </label>

                                                <Field
                                                    as="select"
                                                    name="invoiceId"
                                                    className="form-select"
                                                >

                                                    <option value="">
                                                        Select Invoice
                                                    </option>

                                                    {

                                                        invoices.map((invoice) => (

                                                            <option
                                                                key={invoice.invoiceId}
                                                                value={invoice.invoiceId}
                                                            >

                                                                {invoice.invoiceNumber}
                                                                {" - "}
                                                                ₹
                                                                {Number(
                                                                    invoice.totalAmount
                                                                ).toLocaleString()}

                                                            </option>

                                                        ))

                                                    }

                                                </Field>

                                                <div className="text-danger small">

                                                    <ErrorMessage
                                                        name="invoiceId"
                                                    />

                                                </div>

                                            </div>

                                            <div className="mb-3">

                                                <label className="form-label fw-semibold">
                                                    Claim Amount
                                                </label>

                                                <Field
                                                    type="number"
                                                    name="claimAmount"
                                                    className="form-control"
                                                    placeholder="Enter claim amount"
                                                />

                                                <div className="text-danger small">

                                                    <ErrorMessage
                                                        name="claimAmount"
                                                    />

                                                </div>

                                            </div>

                                            <div className="mb-3">

                                                <label className="form-label fw-semibold">
                                                    Diagnosis
                                                </label>

                                                <Field
                                                    type="text"
                                                    name="diagnosis"
                                                    className="form-control"
                                                    placeholder="Enter diagnosis"
                                                />

                                                <div className="text-danger small">

                                                    <ErrorMessage
                                                        name="diagnosis"
                                                    />

                                                </div>

                                            </div>

                                            <div className="mb-4">

                                                <label className="form-label fw-semibold">
                                                    Treatment Details
                                                </label>

                                                <Field
                                                    as="textarea"
                                                    name="treatmentDetails"
                                                    className="form-control"
                                                    rows="4"
                                                    placeholder="Describe treatment provided"
                                                />

                                                <div className="text-danger small">

                                                    <ErrorMessage
                                                        name="treatmentDetails"
                                                    />

                                                </div>

                                            </div>

                                            <button
                                                type="submit"
                                                className="btn btn-primary px-4"
                                                disabled={isSubmitting}
                                            >

                                                {
                                                    isSubmitting
                                                        ? "Submitting..."
                                                        : "Submit Claim"
                                                }

                                            </button>

                                        </Form>

                                    )
                                }

                            </Formik>

                        </div>

                    </div>

            }

        </div>

    );

}

export default SubmitClaim;