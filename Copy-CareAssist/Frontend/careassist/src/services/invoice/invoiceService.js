import api from "../api/api";

export const generateInvoice = async (invoiceData) => {
    const response = await api.post("/api/invoices", invoiceData);
    return response.data;
};

export const getAllInvoices = async () => {
    const response = await api.get("/api/invoices");
    return response.data;
};

export const getInvoiceById = async (invoiceId) => {
    const response = await api.get(`/api/invoices/${invoiceId}`);
    return response.data;
};

export const getInvoicesByProvider = async (providerId) => {

    const response = await api.get(
        `/api/invoices/provider/${providerId}`
    );

    return response.data;

};