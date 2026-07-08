import api from "../api/api";

export const getProviderByEmail = async (email) => {

    const response = await api.get(`/api/providers/email/${email}`);

    return response.data;

};