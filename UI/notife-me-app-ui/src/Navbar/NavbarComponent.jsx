import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import Drawer from '@mui/material/Drawer';
import Button from '@mui/material/Button';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';

const pages = ['Home', 'Services', 'Bookins']
const settings = ['Profile', 'Account', 'Logout']

const NavbarComponent = ({ children }) => {

  const linkStyle = {
    border: "1px solid #aaaaaa",
    padding: "4px",
    borderRadius: "4px",
    marginLeft: "4px",
    marginRight: "4px",
  };

  const navigate = useNavigate();
  const location = useLocation();

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate(`/github/${username}`);
    setUserFound(true);
  };

  const [openDrawer, setOpenDrawer] = useState(false);

  const toggleDrawer = () => {
    setOpenDrawer(!openDrawer);
  };

  return (
    <div style={{ padding: "30px" }}>
      <Button onClick={toggleDrawer}>MENU</Button>
      <Drawer
        anchor="top"
        open={openDrawer}
        onClose={toggleDrawer}
      >
        <List>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemText primary={
                <Link to="/services" style={linkStyle}>
                  Services
                </Link>
              } />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemText primary={
                <Link to="/about" style={linkStyle}>
                  About
                </Link>
              } />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemText primary={
                <Link to="/contact" style={linkStyle}>
                  Contact
                </Link>
              } />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemText primary={
                <Link to="/github" style={linkStyle}>
                  Github details
                </Link>
              } />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemText primary={
                <Link to="/posts" style={linkStyle}>
                  Posts(c30)
                </Link>
              } />
            </ListItemButton>
          </ListItem>
        </List>
        {(location.pathname.startsWith('/github')) && (
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              value={username}
              onChange={(e) => {
                setUserName(e.target.value);
              }}
            />
            <button type="submit">Find</button>
          </form>
        )}
      </Drawer>
    </div>
  );
};

export default NavbarComponent;