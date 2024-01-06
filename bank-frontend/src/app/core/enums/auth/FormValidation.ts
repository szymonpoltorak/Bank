export enum FormValidation {
    EMAIL_PATTERN = '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$',
    EMAIL_VALUE = '',

    PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ %&])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ %&]+$",
    PASSWORD_VALUE = ''
}
