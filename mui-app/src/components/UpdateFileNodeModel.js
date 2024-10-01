// src/components/UpdateUserModal.js

import React, { useEffect, useState } from 'react';
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  MenuItem,
} from '@mui/material';
import axios from 'axios';

const modalStyle = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  boxShadow: 24,
  p: 4,
};

const UpdateFileNodeModel = ({ open, handleClose, selectedRow }) => {
  const [name, setName] = useState('');
  const [isDirectory, setIsDirectory] = useState('');
  const [parentNode, setParentNode] = useState('');

  useEffect(() => {
    if (selectedRow) {
      setName(selectedRow.name);
      setIsDirectory(selectedRow.isDirectory ? 'Folder' : 'File');
      setParentNode(selectedRow.parentNode || '');
    }
  }, [selectedRow]);

  const handleUpdate = async () => {
    const updatedData = {
      newName: name,
      isDirectory: isDirectory === 'Folder',
    };

    if (parentNode) {
      updatedData.parentNode = parentNode;
    }

    try {
      const token = localStorage.getItem('token');
      const response = await axios.put(
        `http://localhost:8080/api/filesystem/update?name=${selectedRow.name}`,
        updatedData,
        {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
            'Accept': 'application/json',
          },
        }
      );
      console.log('Update:', response.data);
      alert(`Node atualizado`);
      window.location.reload()
    } catch (error) {
      if (error.response && error.response.status === 403) {
        alert("Sessão expirada por favor autentique novamente!");
      }
      
      alert(error.response.data.mensagem);
      console.error('Error:', + error.response.data.mensagem);
    }

    handleClose();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={modalStyle}>
        <Typography variant="h6" component="h2" gutterBottom>
          Update {isDirectory === 'Folder' ? 'Folder' : 'File'}
        </Typography>
        <form>
          <TextField
            label="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
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
          <Button variant="contained" color="primary" onClick={handleUpdate} fullWidth>
            Update
          </Button>
        </form>
      </Box>
    </Modal>
  );
};

export default UpdateFileNodeModel;
