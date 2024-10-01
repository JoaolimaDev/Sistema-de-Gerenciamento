import React, { useState } from 'react';
import axios from 'axios';
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
  const [parentNode, setParentNode] = useState('');

  const handleRegister = async () => {
    const token = localStorage.getItem('token');
    const newEntry = {
      name: newName,
      isDirectory: isDirectory === 'Folder',
    };

    
    if (parentNode) {
      newEntry.parentNode = parentNode;
    }

    try {
      const response = await axios.post(
        'http://localhost:8080/api/filesystem/create',
        newEntry,
        {
          headers: {
            Accept: '*/*',
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      console.log(response);
      
      alert("Registrado com sucesso: " + newName);
      handleClose(); 
      window.location.reload();
    } catch (error) {
      if (error.response && error.response.status === 403) {
        alert("Sessão expirada por favor autentique novamente!");
      }
      
      alert(error.response.data.mensagem);
      console.error('Error:', + error.response.data.mensagem);
      
    }
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Typography variant="h6" component="h2">
          Registro de uma nova entidade!
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
          <TextField
            label="Parent Node (optional)"
            value={parentNode}
            onChange={(e) => setParentNode(e.target.value)}
            fullWidth
            margin="normal"
          />
          <Button variant="contained" color="primary" onClick={handleRegister}>
            Register
          </Button>
        </form>
      </Box>
    </Modal>
  );
};

export default RegisterModal;
