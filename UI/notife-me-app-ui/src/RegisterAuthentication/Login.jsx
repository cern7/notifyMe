import React, { useState } from 'react'
import { loginRequest } from '../api/LoginApi';
import { Box, Button, Container, TextField, Typography } from '@mui/material';

const Login = () => {
  const [username, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userDTO = {
        username,
        password,
      }
      const response = await loginRequest(userDTO);

      console.log('User logged in: ', response.data);
      setEmail('');
      setPassword('');
    } catch (error) {
      console.error('Login failed: ', error);
    }


  }
  return (
    <Container maxWidth="sm">
      <Box mt={5} textAlign="center">
        <Typography variant="h4" component="h1" gutterBottom>
          Login
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Email"
            type="email"
            value={username}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
            margin="normal"
            required
          />
          <TextField
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            fullWidth
            margin="normal"
            required
          />
          <Button variant="contained" color="primary" type="submit">
            Login
          </Button>
        </form>
      </Box>
    </Container>
  );
};

export default Login;