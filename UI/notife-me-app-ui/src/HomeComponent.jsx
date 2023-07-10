import React from 'react'
import { useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import Cookies from 'js-cookie';

const HomeComponent = () => {
  const id = localStorage.getItem('userId')
  const userType = localStorage.getItem('userType');

  const cookieToken = Cookies.get('jwtToken');
  const cookieLocalStorage = localStorage.getItem('token');
  const dispatch = useDispatch();
  const navigate = useNavigate();

  return (
    <div>
      {cookieLocalStorage ? (
        <h1>Welcome to home page</h1>
      ) : (
        <div>
          <h2>Go to login page</h2>
          <Link to='/login'>Login</Link>
        </div>
      )}
    </div>
  )
}

export default HomeComponent;