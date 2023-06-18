import React, { useState } from 'react'
import { loginRequest } from '../api/LoginApi';
import { Box, Button, Container, TextField, Typography } from '@mui/material';
import { useDispatch } from 'react-redux';
import { setUserData } from '../actions/userToken'
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userDTO = {
        username,
        password,
      }
      const response = await loginRequest(userDTO);
      // save userToken in cookies
      const token = response.data.token;
      Cookies.set('jwtToken', token, { sameSite: 'strict' });
      // save userData in store
      dispatch(setUserData(
        response.data.userDetails.iid,
        response.data.userDetails.type
      ));
      setUsername('');
      setPassword('');
      navigate('/home');
    } catch (error) {
      console.error('Login failed: ', error);
    }


  }
  return (
    <Container maxWidth="sm" style={{ backgroundColor: '' }}>
      <Box mt={5} textAlign="center" style={{ backgroundColor: '#a79d52', borderRadius: '50px' }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Login
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Email"
            type="email"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
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