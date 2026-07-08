import api from "../api/api";

export const getAllUsers = async () => {
    const response = await api.get("/api/users");
    return response.data;
};

export const getAllClaims = async () => {
    const response = await api.get("/api/claims");
    return response.data;
};

export const getAllPayments = async () => {
    const response = await api.get("/api/payments");
    return response.data;
};