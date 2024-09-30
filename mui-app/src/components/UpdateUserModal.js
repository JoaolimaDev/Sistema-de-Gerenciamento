// src/components/UpdateUserModal.js

import React from 'react';
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
} from '@mui/material';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  boxShadow: 24,
  p: 4,
};

const UpdateUserModal = ({ open, handleClose, selectedRow }) => {
  const handleUpdate = () => {
    // Add update logic here
    alert(`Updating user: ${selectedRow.name}`);
    handleClose();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Typography variant="h6" component="h2">
          Update User
        </Typography>
        {selectedRow && (
          <form>
            <TextField
              label="Name"
              defaultValue={selectedRow.name}
              fullWidth
              margin="normal"
            />
            <TextField
              label="Email"
              defaultValue={selectedRow.email}
              fullWidth
              margin="normal"
            />
            <TextField
              label="Role"
              defaultValue={selectedRow.role}
              fullWidth
              margin="normal"
            />
            <Button variant="contained" color="primary" onClick={handleUpdate}>
              Update
            </Button>
          </form>
        )}
      </Box>
    </Modal>
  );
};

export default UpdateUserModal;
