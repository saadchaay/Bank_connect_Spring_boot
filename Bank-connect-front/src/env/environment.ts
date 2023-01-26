export const environment = {
  production: false,
  apiUrl: {
    loginCustomer: "http://localhost:8080/auth/customer",
    registerCustomer: "http://localhost:8080/register",
    loginAgent: "http://localhost:8080/auth/agent",
    verifyEmail: "http://localhost:8080/code-verification",
    requestsAccount: "http://localhost:8080/agent/requests-all",
    pendingReqAccount: "http://localhost:8080/agent/requests",
    activateAccount: "http://localhost:8080/agent/activate/",
    disableAccount: "http://localhost:8080/agent/disable/",
    deleteAccount: "http://localhost:8080/agent/delete/",
  }
};
