export const fname_validation = {
    name: 'firstName',
    label: 'First Name',
    type: 'text',
    id: 'firstName',
    placeholder: 'type your First Name...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        maxLength: {
            value: 30,
            message: 'max 30 characters'
        },
    },
}
export const lname_validation = {
    name: 'lastName',
    label: 'Last Name',
    type: 'text',
    id: 'lastName',
    placeholder: 'type your Last Name...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        maxLength: {
            value: 30,
            message: 'max 30 characters'
        },
    },
}

export const geoAdd_validation = {
    name: 'geographicAddress',
    label: 'Address',
    type: 'text',
    id: 'geographicAddress',
    placeholder: 'type your Geographic Address...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        maxLength: {
            value: 30,
            message: 'max 30 characters'
        },
    },
}

export const password_validation = {
    name: 'password',
    label: 'Password',
    type: 'password',
    id: 'password',
    placeholder: 'type your password...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        minLength: {
            value: 8,
            message: 'min 8 characters'
        },
        pattern: {
            value: /^(?=.*[!#$%&*_])(?=.*[a-z)])(?=.*[A-Z)])(?=.*\d)/,
            message: 'at least one special char, one lowercase&uppercase char and one digit'
        },
    },
}

export const confirmPassword_validation = {
    name: 'confirmPassword',
    label: 'Confirm Password',
    type: 'password',
    id: 'confirmPassword',
    placeholder: 'confirm your password...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        validate: (value, values) => {
            return value === values.password || 'Password do not match';
        }
    }
}

export const email_validation = {
    name: 'email',
    label: 'Email',
    type: 'email',
    id: 'email',
    placeholder: 'type your email...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        pattern: {
            value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            message: 'inncorect email format'
        }
    },
}

export const confirmEmail_validation = {
    name: 'confirmEmail',
    label: 'Confirm Email',
    type: 'email',
    id: 'confirmEmail',
    placeholder: 'confirm your email...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        validate: (value, values) => {
            return value === values.email || 'email address does not match';
        }
    }
}

export const phoneNumber_validation = {
    name: 'phoneNumber',
    label: 'Phone Number',
    type: 'text',
    id: 'phoneNumber',
    placeholder: 'type your phone umber...',
    validation: {
        required: {
            value: true,
            message: 'required',
        },
        maxLength: {
            value: 15,
            message: 'Incorrect format'
        },
        pattern: {
            value: /^\+40-7\d{2}-\d{3}-\d{3}$/,
            message: 'Format +40-7XX-XXX-XXX'
        }
    },
}