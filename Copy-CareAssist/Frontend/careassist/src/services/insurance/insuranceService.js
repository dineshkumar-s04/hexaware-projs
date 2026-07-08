import api from "../api/api";

// Get pending claims
export const getPendingClaims = async () => {

    const response = await api.get("/api/claims/pending");

    return response.data;

};

// Get processed claims
export const getProcessedClaims = async () => {

    const response = await api.get("/api/claims/processed");

    return response.data;

};

// Approve claim
export const approveClaim = async (claimId) => {

    const response = await api.put(
        `/api/claims/approve/${claimId}`
    );

    return response.data;

};

// Reject claim
export const rejectClaim = async (claimId, reason) => {

    const response = await api.put(
        `/api/claims/reject/${claimId}`,
        null,
        {
            params: {
                reason: reason
            }
        }
    );

    return response.data;

};