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

  useEffect(() => {
    if (selectedRow) {
      setName(selectedRow.name);
      setIsDirectory(selectedRow.isDirectory ? 'Folder' : 'File');
    }
  }, [selectedRow]);

  const handleUpdate = () => {
    const updatedData = {
      newName: name,
      isDirectory: isDirectory === 'Folder',
    };
    alert(`Updating: ${JSON.stringify(updatedData)}`);
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
          <Button variant="contained" color="primary" onClick={handleUpdate} fullWidth>
            Update
          </Button>
        </form>
      </Box>
    </Modal>
  );
};

export default UpdateFileNodeModel;
