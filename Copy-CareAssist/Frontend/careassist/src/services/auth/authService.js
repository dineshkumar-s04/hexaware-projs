import api from "../api/api";

export const login = async (loginData) => {
  const response = await api.post("/auth/login", loginData);
  return response.data;
};

export const register = async (userData) => {
  const response = await api.post("/api/users/register", userData);
  return response.data;
};

export const forgotPassword = async (email) => {
    const response = await api.post("/auth/forgot-password", {
        email
    });
    return response.data;
};

export const resetPassword = async (token, newPassword) => {
    const response = await api.post("/auth/reset-password", {
        token,
        newPassword
    });
    return response.data;
};