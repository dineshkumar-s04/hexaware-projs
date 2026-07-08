import api from "../api/api";

export const getInsurancePlans = async () => {

    const response = await api.get("/api/plans");

    return response.data;

};

export const enrollInsurancePlan = async (patientId, planId) => {

    const response = await api.post("/api/enrollments", {
        patientId,
        planId
    });

    return response.data;

};

export const getPatientEnrollments = async (patientId) => {

    const response = await api.get(`/api/enrollments/patient/${patientId}`);

    return response.data;

};

export const getPatientInvoices = async (patientId) => {

    const response = await api.get(`/api/invoices/patient/${patientId}`);

    return response.data;

};

export const getPatientByEmail = async (email) => {

    const response = await api.get(`/api/patients/email/${email}`);

    return response.data;

};
