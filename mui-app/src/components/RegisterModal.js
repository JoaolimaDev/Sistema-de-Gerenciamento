import React, { useState } from 'react';
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  MenuItem,
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

const RegisterModal = ({ open, handleClose }) => {
  const [newName, setNewName] = useState('');
  const [isDirectory, setIsDirectory] = useState('File'); 

  const handleRegister = () => {
    const newEntry = {
      name: newName,
      isDirectory: isDirectory === 'Folder',
    };
    alert(`Registering new: ${JSON.stringify(newEntry)}`);
    handleClose();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Typography variant="h6" component="h2">
          Register New Entry
        </Typography>
        <form>
          <TextField
            label="Name"
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            select
            label="Type"
            value={isDirectory}
            onChange={(e) => setIsDirectory(e.target.value)}
            fullWidth
            margin="normal"
          >
            <MenuItem value="Folder">Folder</MenuItem>
            <MenuItem value="File">File</MenuItem>
          </TextField>
          <Button variant="contained" color="primary" onClick={handleRegister}>
            Register
          </Button>
        </form>
      </Box>
    </Modal>
  );
};

export default RegisterModal;
