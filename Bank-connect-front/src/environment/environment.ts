const baseUrlApi = 'http://localhost:8080';
export const environment = {
  production: false,
  apiUrl: {
    loginCustomer: baseUrlApi+'/auth/customer',
    registerCustomer: baseUrlApi+'/register',
    loginAgent: baseUrlApi+'/auth/agent',
    verifyEmail: baseUrlApi+'/code-verification',
    transfer: baseUrlApi+'/customer/transfer',
    deposit: baseUrlApi+'/customer/deposit',
    withdraw: baseUrlApi+'/customer/withdraw',
    requestsAccount: baseUrlApi+'/agent/requests-all',
    pendingReqAccount: baseUrlApi+'/agent/requests',
    activateAccount: baseUrlApi+'/agent/activate/',
    disableAccount: baseUrlApi+'/agent/disable/',
    deleteAccount: baseUrlApi+'/agent/delete/',
    getAccount: baseUrlApi+'/customer/account',
  }
};
