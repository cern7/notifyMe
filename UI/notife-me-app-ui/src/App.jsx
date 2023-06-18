import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import RegisterPage from './RegisterAuthentication/RegisterPage'
import { Routes, Route } from 'react-router-dom'
import ConfirmEmailComponent from './RegisterAuthentication/ConfirmEmailComponent'
import Login from './RegisterAuthentication/Login'
import HomeComponent from './HomeComponent'
import ServiceComponent from './services/ServiceComponent'
import NavbarComponent from './Navbar/NavbarComponent'

function App() {

  return (
    <>
      {/* <NavbarComponent /> */}
      <Routes>
        <Route path='/login' element={<Login />} />
        <Route path='/register' element={<RegisterPage />} />
        <Route path='/register/confirmEmail/:token' element={<ConfirmEmailComponent />} />
        <Route path='/home' element={<HomeComponent />} />
        <Route path='/service' element={<ServiceComponent />} />
      </Routes>

    </>
  )
}

export default App
