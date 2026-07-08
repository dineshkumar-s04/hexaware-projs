import { useEffect } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { generateInvoice } from "../../services/invoice/invoiceService";

const invoiceSchema = Yup.object({

    patientId: Yup.number()
        .required("Patient ID is required")
        .positive("Patient ID must be positive"),

    consultationFee: Yup.number()
        .required("Consultation Fee is required")
        .min(0, "Consultation Fee cannot be negative"),

    diagnosticTestFee: Yup.number()
        .required("Diagnostic Test Fee is required")
        .min(0, "Diagnostic Test Fee cannot be negative"),

    scanFee: Yup.number()
        .required("Scan Fee is required")
        .min(0, "Scan Fee cannot be negative"),

    medicineFee: Yup.number()
        .required("Medicine Fee is required")
        .min(0, "Medicine Fee cannot be negative"),

    invoiceDate: Yup.date()
        .required("Invoice Date is required"),

    dueDate: Yup.date()
        .required("Due Date is required")

});

function CalculateTotal({ values, setFieldValue }) {

    useEffect(() => {

        const consultation =
            Number(values.consultationFee) || 0;

        const diagnostic =
            Number(values.diagnosticTestFee) || 0;

        const scan =
            Number(values.scanFee) || 0;

        const medicine =
            Number(values.medicineFee) || 0;

        const billAmount =
            consultation +
            diagnostic +
            scan +
            medicine;

        const tax =
            Number((billAmount * 0.08).toFixed(2));

        const total =
            Number((billAmount + tax).toFixed(2));

        setFieldValue("tax", tax);

        setFieldValue("totalAmount", total);

    }, [
        values.consultationFee,
        values.diagnosticTestFee,
        values.scanFee,
        values.medicineFee,
        setFieldValue
    ]);

    return null;

}

function GenerateInvoice() {

    const initialValues = {

        patientId: "",

        consultationFee: 0,

        diagnosticTestFee: 0,

        scanFee: 0,

        medicineFee: 0,

        invoiceDate: "",

        dueDate: "",

        tax: 0,

        totalAmount: 0

    };

    const handleSubmit = async (values, { resetForm }) => {

        try {

            const invoiceData = {

                patientId: Number(values.patientId),

                providerId: Number(localStorage.getItem("providerId")),

                consultationFee: Number(values.consultationFee),

                diagnosticTestFee: Number(values.diagnosticTestFee),

                scanFee: Number(values.scanFee),

                medicineFee: Number(values.medicineFee),

                invoiceDate: values.invoiceDate,

                dueDate: values.dueDate

            };

            const response = await generateInvoice(invoiceData);

            console.log(response);

            alert("Invoice Generated Successfully");

            resetForm();

        } catch (error) {

            console.error(error);

            alert("Failed to Generate Invoice");

        }

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-4">
                Generate Invoice
            </h2>

            <Formik
                initialValues={initialValues}
                validationSchema={invoiceSchema}
                onSubmit={handleSubmit}
            >

                {({ values, setFieldValue }) => (

                    <Form>

                        <CalculateTotal
                            values={values}
                            setFieldValue={setFieldValue}
                        />

                    <div className="mb-3">

                        <label className="form-label">
                            Patient ID
                        </label>

                        <Field
                            type="number"
                            name="patientId"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="patientId" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Consultation Fee
                        </label>

                        <Field
                            type="number"
                            name="consultationFee"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="consultationFee" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Diagnostic Test Fee
                        </label>

                        <Field
                            type="number"
                            name="diagnosticTestFee"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="diagnosticTestFee" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Scan Fee
                        </label>

                        <Field
                            type="number"
                            name="scanFee"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="scanFee" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Medicine Fee
                        </label>

                        <Field
                            type="number"
                            name="medicineFee"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="medicineFee" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Invoice Date
                        </label>

                        <Field
                            type="date"
                            name="invoiceDate"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="invoiceDate" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Due Date
                        </label>

                        <Field
                            type="date"
                            name="dueDate"
                            className="form-control"
                        />

                        <div className="text-danger">

                            <ErrorMessage name="dueDate" />

                        </div>

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Tax (8%)
                        </label>

                        <Field
                            type="number"
                            name="tax"
                            className="form-control"
                            readOnly
                        />

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            Total Amount
                        </label>

                        <Field
                            type="number"
                            name="totalAmount"
                            className="form-control"
                            readOnly
                        />

                    </div>

                    <button
                        type="submit"
                        className="btn btn-primary"
                    >
                        Generate Invoice
                    </button>

                </Form>

                )}

            </Formik>

        </div>

    );

}

export default GenerateInvoice;