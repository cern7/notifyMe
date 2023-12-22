import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
// import RegisterPage from './RegisterAuthentication/RegisterPage'
import Register from './RegisterAuthentication/Register'
import { Routes, Route } from 'react-router-dom'
import ConfirmEmailComponent from './RegisterAuthentication/ConfirmEmailComponent'
import Login from './RegisterAuthentication/Login'
import HomeComponent from './HomeComponent'
import ServiceComponent from './services/ServiceComponent'
import NavbarComponent from './Navbar/NavbarComponent'
import { createTheme, ThemeProvider } from '@mui/material';
import MyBookings from './booking/MyBookings'
import BookingComponent from './booking/BookingComponent'
const theme = createTheme({
  pallette: {
    background: {
      paper: '#fff',
    },
    text: {
      primary: '#173A5E',
      secondary: '#173A5E',
    },
    action: {
      active: '#001E3C',
    },
    success: {
      dark: '#002d28',
    },
  },
});


function App() {


  return (
    <ThemeProvider theme={theme}>
      <NavbarComponent />
      <Routes>
        <Route path='/login' element={<Login />} />
        {/* <Route path='/register' element={<RegisterPage />} /> */}
        <Route path='/register' element={<Register />} />
        <Route path='/register/confirmEmail/:token' element={<ConfirmEmailComponent />} />
        <Route path='/home' element={<HomeComponent />} />
        <Route path='/service' element={<ServiceComponent />} />
        <Route path='/bookings/all' element={<MyBookings />} />
        <Route path='/bookings' element={<MyBookings />} />
        <Route path='/booking' element={<BookingComponent />} />
      </Routes>

    </ThemeProvider>
  )
}

export default App
