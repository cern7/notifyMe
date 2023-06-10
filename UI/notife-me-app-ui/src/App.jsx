import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import RegisterPage from './RegisterAuthentication/RegisterPage'
import { Routes, Route } from 'react-router-dom'
import ConfirmEmailComponent from './RegisterAuthentication/ConfirmEmailComponent'
import Login from './RegisterAuthentication/Login'

function App() {

  return (
    <>
      <Routes>
        <Route path='/login' element={<Login/>}/>
        <Route path='/register' element={<RegisterPage />} />
        <Route path='/register/confirmEmail/:token' element={<ConfirmEmailComponent />} />
      </Routes>

    </>
  )
}

export default App
