import ProtectedRoute from "./ProtectedRoute";

import { Routes, Route } from "react-router-dom";
import Layout from "../components/layout/Layout";

import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import ForgotPassword from "../pages/auth/ForgotPassword";

import AdminDashboard from "../pages/admin/AdminDashboard";
import AdminUsers from "../pages/admin/AdminUsers";
import AdminClaims from "../pages/admin/AdminClaims";
import AdminPayments from "../pages/admin/AdminPayments";

import PatientDashboard from "../pages/patient/PatientDashboard";
import ProviderDashboard from "../pages/provider/ProviderDashboard";
import InsuranceDashboard from "../pages/insurance/InsuranceDashboard";

import InsurancePlans from "../pages/patient/InsurancePlans";
import MyInsurance from "../pages/patient/MyInsurance";
import Invoice from "../pages/patient/Invoice";
import SubmitClaim from "../pages/patient/SubmitClaim";
import ClaimHistory from "../pages/patient/ClaimHistory";

import GenerateInvoice from "../pages/provider/GenerateInvoice";
import ProviderInvoices from "../pages/provider/ProviderInvoices";

import PendingClaims from "../pages/insurance/PendingClaims";
import ProcessedClaims from "../pages/insurance/ProcessedClaims";

import ResetPassword from "../pages/auth/ResetPassword";

function AppRoutes() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <Layout>
            <Login />
          </Layout>
        }
      />

      <Route
        path="/register"
        element={
          <Layout>
            <Register />
          </Layout>
        }
      />

      <Route
        path="/forgot-password"
        element={
          <Layout>
            <ForgotPassword />
          </Layout>
        }
      />

      <Route
        path="/admin"
        element={
          <ProtectedRoute allowedRoles={["ADMIN"]}>
            <Layout>
              <AdminDashboard />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
          path="/admin/users"
          element={
          <ProtectedRoute allowedRoles={["ADMIN"]}>
            <Layout>
              <AdminUsers />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
          path="/admin/claims"
          element={
          <ProtectedRoute allowedRoles={["ADMIN"]}>
            <Layout>
              <AdminClaims />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
          path="/admin/payments"
          element={
          <ProtectedRoute allowedRoles={["ADMIN"]}>
            <Layout>
              <AdminPayments />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/patient"
        element={
          <ProtectedRoute allowedRoles={["PATIENT"]}>
            <Layout>
              <PatientDashboard />
            </Layout>
          </ProtectedRoute>
        }
      />
      
      <Route
        path="/provider"
        element={
          <ProtectedRoute allowedRoles={["PROVIDER"]}>
            <Layout>
            <ProviderDashboard />
          </Layout>
          </ProtectedRoute>          
        }
      />

      <Route
        path="/insurance"
        element={
          <ProtectedRoute allowedRoles={["INSURANCE"]}>
            <Layout>
              <InsuranceDashboard />
            </Layout>
          </ProtectedRoute>
          
        }
      />

      <Route
          path="/insurance/pending-claims"
          element={
          <ProtectedRoute allowedRoles={["INSURANCE"]}>
            <Layout>
              <PendingClaims />
            </Layout>
          </ProtectedRoute>
          
        }
      />

      <Route
          path="/insurance/processed-claims"
          element={
          <ProtectedRoute allowedRoles={["INSURANCE"]}>
            <Layout>
              <ProcessedClaims />
            </Layout>
          </ProtectedRoute>
          
        }
      />

      <Route
        path="/patient/plans"
        element={
          <ProtectedRoute allowedRoles={["PATIENT"]}>
            <Layout>
              <InsurancePlans />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/patient/my-insurance"
        element={
          <ProtectedRoute allowedRoles={["PATIENT"]}>
            <Layout>
              <MyInsurance />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/patient/invoice"
        element={
          <ProtectedRoute allowedRoles={["PATIENT"]}>
            <Layout>
              <Invoice />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/patient/submit-claim"
        element={
          <ProtectedRoute allowedRoles={["PATIENT"]}>
            <Layout>
              <SubmitClaim />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/patient/claim-history"
        element={
          <ProtectedRoute allowedRoles={["PATIENT"]}>
            <Layout>
              <ClaimHistory />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
          path="/provider/generate-invoice"
          element={
          <ProtectedRoute allowedRoles={["PROVIDER"]}>
              <Layout>
                <GenerateInvoice />
              </Layout>
          </ProtectedRoute>          
        }
      />

      <Route
          path="/provider/invoices"
          element={
          <ProtectedRoute allowedRoles={["PROVIDER"]}>
              <Layout>
                <ProviderInvoices />
              </Layout>
          </ProtectedRoute>          
        }
      />

      <Route
          path="/reset-password"
          element={
              <Layout>
                  <ResetPassword />
              </Layout>
          }
      />
      
    </Routes>
  );
}

export default AppRoutes;
