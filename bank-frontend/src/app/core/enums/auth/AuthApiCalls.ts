export const enum AuthApiCalls {
    REGISTER_URL = "/api/v1/auth/register",
    LOGIN_URL = "/api/v1/auth/login",
    REFRESH_URL = "/api/v1/auth/refreshToken",

    ERROR_FOUND = '{"authToken": "", "refreshToken": ""}',
    LOGOUT_URL = "/api/v1/auth/logout",
    FORGOT_PASSWORD_URL = "/api/v1/auth/forgotPassword",
    RESET_PASSWORD_URL = "/api/v1/auth/resetPassword",
    GET_PASSWORD_COMBINATION_URL = "/api/v1/auth/getCombination"
}
