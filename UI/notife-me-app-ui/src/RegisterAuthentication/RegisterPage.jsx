import React, { useState } from 'react';
import { registerNewCustomer } from '../api/RegisterApi';
import { useNavigate } from 'react-router-dom';
import { Box, Button, Container, TextField, Typography } from '@mui/material';


const RegisterPage = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [geographicAddress, setGeographicAddress] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [confirmEmail, setConfirmEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [error, setError] = useState('');
  const navigate=useNavigate();

  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
  };
  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };
  const handleGeographicAddressChange = (e) => {
    setGeographicAddress(e.target.value);
  };
  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };
  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };
  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  }
  const handleConfirmEmailChange = (e) => {
    setConfirmEmail(e.target.value);
  };
  const handlePhoneNumberChange = (e) => {
    setPhoneNumber(e.target.value);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (email !== confirmEmail) {
      setError('Email and Confirm Email do not match');
      return;
    }

    if (password !== confirmPassword) {
      setError('Password and Confirm Password do not match');
      return;
    }

    if (!isValidEmail(email)) {
      setError('Invalid Email');
      return;
    }

    if (!isValidPassword(password)) {
      setError('Invalid Password. Password must be at least 8 characters long');
      return;
    }
    // Perform regitration logic, make API request
    try {
      const userDTO = {
        firstName,
        lastName,
        geographicAddress,
        password,
        confirmPassword,
        email,
        confirmEmail,
        phoneNumber,
        type: "CUSTOMER",
      };
      const response = await registerNewCustomer(userDTO);

      console.log('Registration response:', response.data);
      setFirstName('');
      setLastName('');
      setGeographicAddress('');
      setPassword('');
      setConfirmPassword('');
      setEmail('');
      setConfirmEmail('');
      setPhoneNumber('');
      navigate('/login');
    } catch (error) {
      // Handle any API errors
      console.error('Registration failed:', error);
      setError('Registration failed. Please try again.');
    }
  };

  const isValidEmail = (email) => {
    // Basic email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };
  const isValidPassword = (password) => {
    // Password should be at least 8 characters long
    return password.length >= 8;
  };

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto', mt: 4, px: 2 }}>
      <Typography variant="h4" align="center" gutterBottom>
        Register
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="First Name"
          value={firstName}
          onChange={handleFirstNameChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Last Name"
          value={lastName}
          onChange={handleLastNameChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Geographic Address"
          value={geographicAddress}
          onChange={handleGeographicAddressChange}
          fullWidth
          margin="normal"
        />
        <TextField
          type="password"
          label="Password"
          value={password}
          onChange={handlePasswordChange}
          fullWidth
          margin="normal"
        />
        <TextField
          type="password"
          label="Confirm Password"
          value={confirmPassword}
          onChange={handleConfirmPasswordChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Email"
          value={email}
          onChange={handleEmailChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Confirm Email"
          value={confirmEmail}
          onChange={handleConfirmEmailChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Phone Number"
          value={phoneNumber}
          onChange={handlePhoneNumberChange}
          fullWidth
          margin="normal"
        />
        <Button type="submit" variant="contained" fullWidth>
          Register
        </Button>
      </form>
    </Box>
  );
}

export default RegisterPage;