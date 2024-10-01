// src/components/Navbar.js
import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import RegisterModal from '../components/RegisterModal'; // Import the modal

const Navbar = ({ token, onLogout }) => {
  const [openRegisterModal, setOpenRegisterModal] = useState(false);

  const handleOpenRegister = () => setOpenRegisterModal(true);
  const handleCloseRegister = () => setOpenRegisterModal(false);

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" style={{ flexGrow: 1 }}>
            Desafio sistema de arquivos
          </Typography>
          {token ? (
            <>
              <Button color="inherit" component={Link} to="/dashboard">
                Dashboard
              </Button>
              <Button color="inherit" onClick={handleOpenRegister}>
                Register
              </Button>
              <Button color="inherit" onClick={onLogout}>
                Logout
              </Button>
            </>
          ) : (
            <Button color="inherit" component={Link} to="/">
              Login
            </Button>
          )}
        </Toolbar>
      </AppBar>
      
      <RegisterModal open={openRegisterModal} handleClose={handleCloseRegister} />
    </>
  );
};

export default Navbar;
