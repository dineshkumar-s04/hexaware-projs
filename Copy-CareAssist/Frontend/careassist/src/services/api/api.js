import axios from "axios";

const api = axios.create({
    baseURL: "http://16.113.43.9:8080",
    headers: {
        "Content-Type": "application/json"
    }
});

// Add JWT automatically to every request
api.interceptors.request.use(

    (config) => {

        const token = localStorage.getItem("token");

        if (token) {

            config.headers.Authorization = `Bearer ${token}`;

        }

        return config;

    },

    (error) => Promise.reject(error)

);

api.interceptors.response.use(

    (response) => response,

    (error) => {

        if (!error.response) {
            return Promise.reject(error);
        }

        if (error.response.status === 401) {

            localStorage.removeItem("token");
            localStorage.removeItem("email");
            localStorage.removeItem("role");
            localStorage.removeItem("patientId");
            localStorage.removeItem("providerId");

            window.location.replace("/");
        }

        return Promise.reject(error);
    }

);

export default api;