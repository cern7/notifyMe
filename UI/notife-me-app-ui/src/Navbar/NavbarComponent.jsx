import React, { useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import CircleNotificationsIcon from '@mui/icons-material/CircleNotifications';
import { useDispatch } from 'react-redux';
import MenuIcon from '@mui/icons-material/Menu';
import Menu from '@mui/material/Menu';
import { MenuItem } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { useSelector } from 'react-redux';


const NavbarComponent = () => {
  const [anchorEl, setAnchorEl] = useState(null);
  // TODO: add this property to store
  const [auth, setAuth] = useState(false);

  const id = localStorage.getItem('userId')
  const userType = localStorage.getItem('userType');

  // const cookieToken = Cookies.get('jwtToken');
  const cookieToken = localStorage.getItem('token');
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleServiceClick = () => {
    navigate('/service')
  }

  const handleHomeClick = () => {
    navigate('/home')
  }

  const handleMyBookingsClick = () => {
    navigate('/bookings/all')
  }

  const handleLogout = () => {
    if (cookieToken) {
      // Cookies.remove('jwtToken', { sameSite: 'strict' });
      localStorage.removeItem('token');
      localStorage.removeItem('userId')
      localStorage.removeItem('userType')
      navigate('/home')
    }
  }

  const handleAdminAllBookings = () => {
    navigate('/bookings')
  }

  const handleEmployeeBookings = () => {
    navigate('/bookings')
  }

  const handleLogin = () => {
    navigate('/login')
  }

  const handleRegister = () => {
    navigate('/register')
  }

  return (
    <Box sx={{ position: "fixed", top: 0, left: 0, right: 0, zIndex: 999, flexGrow: 1 }}>
      <AppBar position="static" sx={{ backgroundColor: '#444654' }}>
        <Toolbar>
          <Typography variant="h6" component="div"
            sx={{ flexGrow: 1, display: 'flex', alignItems: 'center' }}>
            <IconButton aria-label='notif' size='medium' onClick={handleHomeClick}>
              <CircleNotificationsIcon />
            </IconButton>
            <span style={{ fontFamily: 'Arial', fontWeight: 'bold' }}>NotifyMe</span>
          </Typography>
          <Box sx={{ typography: 'h6', fontFamily: 'Times New Roman' }}>
            {(userType === 'ADMIN') 
            && (<IconButton aria-label='button' color='inherit' onClick={handleAdminAllBookings}>
              <span>All Bookings(admin)</span>
            </IconButton>)}
            {(userType === 'EMPLOYEE') && (<IconButton aria-label='button' color='inherit' onClick={handleEmployeeBookings}>
              <span>My jobs(bookings)</span>
            </IconButton>)}
            {cookieToken
              && (userType === 'CUSTOMER' || userType === 'ADMIN')
              && (<IconButton aria-label='button' color='inherit' onClick={handleServiceClick}>
                <span>Services</span>
              </IconButton>)}
            {cookieToken &&
              (userType === 'CUSTOMER')
              && (<IconButton aria-label='button' color='inherit' onClick={handleMyBookingsClick}>
                <span>My Bookings</span>
              </IconButton>)}
            {cookieToken && (<IconButton aria-label='button' color='inherit' onClick={handleHomeClick}>
              <span>Home</span>
            </IconButton>)}
            {!cookieToken ?
              (<>
                <IconButton aria-label='button' color='inherit' onClick={handleLogin}>
                  <span>Login</span>
                </IconButton>
                <IconButton aria-label='button' color='inherit' onClick={handleRegister}>
                  <span>Register</span>
                </IconButton>
              </>) : (<IconButton aria-label='button' color='inherit' onClick={handleLogout}>
                <span>Logout</span>
              </IconButton>
              )}
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  )
}

export default NavbarComponent;