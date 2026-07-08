import api from "../api/api";

export const submitClaim = async (claimData) => {

    const response = await api.post("/api/claims", claimData);

    return response.data;

};

export const getPatientClaims = async (patientId) => {

    const response = await api.get(`/api/claims/patient/${patientId}`);

    return response.data;

};
