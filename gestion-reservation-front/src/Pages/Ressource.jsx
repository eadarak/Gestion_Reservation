import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  IconButton,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";

import { URL_RESSOURCE } from "../constante/Server";
import CreateRessource from "../Component/Create/CreateRessource";
import { Link } from "react-router-dom";

const Ressource = () => {
  const [ressource, setRessources] = useState([]);
  const [filteredRessources, setFilteredRessources] = useState([]);
  const [perPage, setPerPage] = useState(5);
  const [searchTerm, setSearchTerm] = useState("");
  const [token, setToken] = useState("");
  const [orderBy, setOrderBy] = useState(null);
  const [order, setOrder] = useState("asc");
  const [editFormData, setEditFormData] = useState({});
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [editResourceId, setEditResourceId] = useState(null);

  useEffect(() => {
    const fetchRessources = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");
        setToken(sessionToken);

        const response = await fetch(URL_RESSOURCE, {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la récupération des ressources");
        }
        const data = await response.json();
        setRessources(data);
        setFilteredRessources(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchRessources();
  }, []);

  const handleDelete = async (id) => {
    try {
      const confirmed = window.confirm(
        `Voulez-vous vraiment supprimer la ressource ${id} ?`
      );
      if (!confirmed) return;

      const response = await fetch(`${URL_RESSOURCE}/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Failed to delete resource");
      }
      setRessources(ressource.filter((resource) => resource.idRessource !== id));
      setFilteredRessources(filteredRessources.filter((resource) => resource.idRessource !== id));
    } catch (error) {
      console.error(error);
    }
  };

  const handleChangePerPage = (event) => {
    setPerPage(event.target.value);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    const filtered = ressource.filter((ressource) =>
      ressource.libelleRessource.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setFilteredRessources(filtered);
  };

  const handleSort = (column) => {
    if (orderBy === column) {
      setOrder(order === "asc" ? "desc" : "asc");
    } else {
      setOrderBy(column);
      setOrder("asc");
    }
  };

  const handleOpenEditModal = (id) => {
    const resourceToEdit = ressource.find((resource) => resource.idRessource === id);
    setEditFormData(resourceToEdit);
    setEditResourceId(id);
    setEditModalOpen(true);
  };

  const handleEditChange = (e) => {
    setEditFormData({
      ...editFormData,
      [e.target.name]: e.target.value,
    });
  };

  const handleEditSubmit = async () => {
    try {
      const response = await fetch(`${URL_RESSOURCE}/${editResourceId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(editFormData),
      });
      if (!response.ok) {
        throw new Error("Echec lors de la modification de la ressource");
      }
      const updatedResources = ressource.map((resource) =>
        resource.idRessource === editResourceId ? editFormData : resource
      );
      setRessources(updatedResources);
      setFilteredRessources(updatedResources);
      setEditModalOpen(false);
    } catch (error) {
      console.error(error);
    }
  };

  const sortFunction = (a, b) => {
    const valueA = a[orderBy];
    const valueB = b[orderBy];

    if (typeof valueA === "undefined" || typeof valueB === "undefined") {
      return 0;
    }

    if (order === "asc") {
      return valueA.localeCompare(valueB);
    } else {
      return valueB.localeCompare(valueA);
    }
  };

  return (
    <div style={{ marginTop: "20px" }}>
      <div style={{ display: "flex", justifyContent: "space-around", alignItems:"center" }}>
        <CreateRessource />

        <Button
        component={Link} 
        to="/faire-reservation"
        variant="contained"
        sx={{
          fontSize: "16px",
          fontWeight: "bold",
          letterSpacing: ".75px",
          backgroundColor: "#113f36",
          "&:hover": {
            backgroundColor: "#0D2924",
            color: "white",
          },
        }}
        
      >
        Faire Réservation
      </Button>


      <Button
        component={Link} 
        to="/faire-pret"
        variant="contained"
        sx={{
          fontSize: "16px",
          fontWeight: "bold",
          letterSpacing: ".75px",
          backgroundColor: "#113f36",
          "&:hover": {
            backgroundColor: "#0D2924",
            color: "white",
          },
        }}
        
      >
        Faire Pret
      </Button>
      </div>

      <TextField
        id="search"
        label="Recherche"
        variant="outlined"
        value={searchTerm}
        onChange={handleSearch}
        sx={{ mt: 1 }}
      />
      <TableContainer component={Paper} sx={{ mt: 1 }}>
        <Table aria-label="utilisateur table">
          <TableHead>
            <TableRow>
              <TableCell onClick={() => handleSort("libelleRessource")}>
                Libellé{" "}
                {orderBy === "libelleRessource" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("etat")}>
                État{" "}
                {orderBy === "etat" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("utilisateur.email")}>
                Enregistré par{" "}
                {orderBy === "utilisateur.email" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredRessources
              .sort(sortFunction)
              .slice(0, perPage)
              .map((ressource) => (
                <TableRow key={ressource.idRessource}>
                  <TableCell>{ressource.libelleRessource}</TableCell>
                  <TableCell>{ressource.etat}</TableCell>
                  <TableCell>{ressource.utilisateur.email}</TableCell>
                  <TableCell>
                    <IconButton onClick={() => handleOpenEditModal(ressource.idRessource)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton onClick={() => handleDelete(ressource.idRessource)}>
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
      <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "10px" }}>
        <FormControl sx={{ minWidth: 120, mr: 1 }}>
          <InputLabel id="per-page-label">Nombre par page</InputLabel>
          <Select
            labelId="per-page-label"
            id="per-page-select"
            value={perPage}
            label="Nombre par page"
            onChange={handleChangePerPage}
          >
            <MenuItem value={5}>5</MenuItem>
            <MenuItem value={10}>10</MenuItem>
            <MenuItem value={25}>25</MenuItem>
          </Select>
        </FormControl>
      </div>
      <Dialog open={editModalOpen} onClose={() => setEditModalOpen(false)}>
        <DialogTitle>Modifier la ressource</DialogTitle>
        <DialogContent>
          <TextField
            fullWidth
            label="Libellé"
            name="libelleRessource"
            value={editFormData.libelleRessource || ""}
            onChange={handleEditChange}
            variant="outlined"
            margin="normal"
          />
          <TextField
            fullWidth
            label="État"
            name="etat"
            value={editFormData.etat || ""}
            onChange={handleEditChange}
            variant="outlined"
            margin="normal"
          />
           <TextField
            fullWidth
            label="Description"
            name="description"
            value={editFormData.description || ""}
            onChange={handleEditChange}
            variant="outlined"
            margin="normal"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setEditModalOpen(false)}>Annuler</Button>
          <Button onClick={handleEditSubmit}>Enregistrer</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Ressource;
