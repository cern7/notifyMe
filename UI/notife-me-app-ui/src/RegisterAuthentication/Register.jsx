import React, { useState } from 'react'
import { FormProvider, useForm } from 'react-hook-form'
import { Input } from "./Input"
import { useNavigate } from 'react-router-dom';
import {
    fname_validation,
    lname_validation,
    geoAdd_validation,
    password_validation,
    confirmPassword_validation,
    email_validation,
    confirmEmail_validation,
    phoneNumber_validation
} from '../utils/inputValidation'
import { Box, Typography, Button, ButtonGroup } from '@mui/material';
import { registerNewCustomer } from '../api/RegisterApi';

const Register = () => {
    const { getValues, setValue } = useForm({
        defaultValues: {
            firstName: '',
            lastName: '',
            geographicAddress: '',
            password: '',
            confirmPassword: '',
            email: '',
            confirmEmail: '',
            phoneNumber: '',
            type: "CUSTOMER",
        }
    });
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();
    const methods = useForm();

    const submitForm = async (e) => {
        try {
            const response = await registerNewCustomer(getValues());
        } catch (error) {
            console.error('Registration failed: ', error);
        }
    };

    const onSubmit = methods.handleSubmit(formData => {
        Object.keys(formData).forEach((field) => {
            setValue(field, formData[field]);
        });
        submitForm();
        methods.reset();
        setSuccess(true);
    });

    return (
        <FormProvider {...methods}>
            <form
                onSubmit={e => e.preventDefault()}
                noValidate
                className='container'
            >
                <div className="grid gap-10 md:grid-cols-2">
                    <Box sx={{ maxWidth: 400, mx: 'auto', mt: 4, px: 2 }}
                        style={{ backgroundColor: '#e4dac8', borderRadius: '35px' }}>
                        <Typography
                            variant="h4"
                            align="center"
                            gutterBottom color='#505749'>
                            Register
                        </Typography>
                        <Input {...fname_validation} />
                        <Input {...lname_validation} />
                        <Input {...geoAdd_validation} />
                        <Input {...password_validation} />
                        <Input {...confirmPassword_validation} />
                        <Input {...email_validation} />
                        <Input {...confirmEmail_validation} />
                        <Input {...phoneNumber_validation} />
                    </Box>
                </div>
                <div className='mt-5'>
                    {success && (
                        <ButtonGroup variant='text' aria-label='text button group'>
                            <Button color="success">Successfull Register</Button>
                            <Button onClick={() => navigate('/login')}>Login</Button>
                        </ButtonGroup>
                    )}
                </div>
                <div className='mt-5'>
                    {!success && (<Button color='primary' variant="contained" onClick={onSubmit}>
                        Register
                    </Button>)}
                </div>
            </form>
        </FormProvider>
    )
}

export default Register;