import React from 'react'
import { useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { removeUserData } from './actions/userToken';
import Cookies from 'js-cookie';

const HomeComponent = () => {
  const { id, userType } = useSelector((state) => state.user);
  const cookieToken = Cookies.get('jwtToken');
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    if (cookieToken) {
      Cookies.remove('jwtToken', { sameSite: 'strict' });
      dispatch(removeUserData())
    }
  }

  const handleRedirectToServices = () => {
    navigate('/service');
  }
  return (
    <div>
      {cookieToken ? (
        <div>
          <h2>UserData</h2>
          <button onClick={handleRedirectToServices}>List Available Services</button>
          <button onClick={handleLogout}>Logout</button>
        </div>
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