import React from 'react'
import { Box, Button, Container, TextField, Typography } from '@mui/material';
import { useFormContext } from 'react-hook-form'
import { findInputError } from '../utils/findInputError'
import { isFormInvalid } from '../utils/isFormInvalid'
import { AnimatePresence, motion, transform } from 'framer-motion'
import { useSpring, animated } from '@react-spring/web';

export const Input = ({ label, id, placeholder, validation, name, value, type }) => {
    const { register, formState: { errors }, } = useFormContext();

    const inputError = findInputError(errors, name);
    const isInvalid = isFormInvalid(inputError);

    return (
        <div className="flex flex-col w-full gap-2">
            <div className="flex justify-between">
                <AnimatePresence mode="wait" initial={false}>
                    {isInvalid && (
                        <InputError
                            message={inputError.error.message}
                            key={inputError.error.message}
                        />
                    )}
                </AnimatePresence>
            </div>
            <TextField
                type={type}
                id={id}
                label={label}
                name={name}
                value={value}
                fullWidth
                margin='normal'
                placeholder={placeholder}
                {...register(name, validation)}
            />
        </div>
    )
}

const InputError = ({ message }) => {
    const muiStyles = {
        display: 'flex',
        alignItmes: 'center',
        gap: 1,
        padding: 2,
        fontWeight: '600',
        color: '#ff0000',
        broderRadius: '4px',
    };
    const { opacity, y } = useSpring({ opacity: 1, y: 0 });

    return (
        <animated.div style=
            {{
                opacity,
                transform: y.to((value) => 'transalteY(${value}px)')
            }}>
            <Box sx={muiStyles}>
                <Typography variant='body1'>
                    {message}
                </Typography>
            </Box>
        </animated.div>
    );
};