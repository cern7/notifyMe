import React from 'react'
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { removeUserData } from './actions/userToken';

const HomeComponent = () => {
  const { token, id, userType } = useSelector((state) => state.user);
  const dispatch = useDispatch();

  const handleLogout = () => {
    if (token) {
      dispatch(removeUserData())
    }
  }
  return (
    <div>
      {token ? (
        <div>
          <h2>UserData</h2>
          <h4>Token: {token}</h4>
          <h4>id: {id}</h4>
          <h4>UserType: {userType}</h4>
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