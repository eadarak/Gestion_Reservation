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
  Button,
} from "@mui/material";
import { URL_UTILISATEUR } from "../constante/Server";
import CreateUser from "../Component/Create/CreateUser";
import { Link } from "react-router-dom";

const Utilisateur = () => {
  const [users, setUsers] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [perPage, setPerPage] = useState(5);
  const [searchTerm, setSearchTerm] = useState("");
  const [token, setToken] = useState("");
  const [orderBy, setOrderBy] = useState(null);
  const [order, setOrder] = useState("asc");

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");
        setToken(sessionToken);

        const response = await fetch(URL_UTILISATEUR, {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la récupération des utilisateurs");
        }
        const data = await response.json();
        setUsers(data);
        setFilteredUsers(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUsers();
  }, []);

  const handleChangePerPage = (event) => {
    setPerPage(event.target.value);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    const filtered = users.filter((user) =>
      user.nom.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setFilteredUsers(filtered);
  };

  const handleSort = (column) => {
    if (orderBy === column) {
      setOrder(order === "asc" ? "desc" : "asc");
    } else {
      setOrderBy(column);
      setOrder("asc");
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
    <div style={{ marginTop: "20px"}}>
      <div style={{ display: "flex", justifyContent: "center"}}>
        <CreateUser/>
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
              <TableCell onClick={() => handleSort("nom")}>
                Nom{" "}
                {orderBy === "nom" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("prenom")}>
              Nom{" "}
                {orderBy === "nom" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("email")}>
                Email{" "}
                {orderBy === "email" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("materiel")}>
                Materiel{" "}
                {orderBy === "materiel" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("libelle")}>
                Role{" "}
                {orderBy === "libelle" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredUsers
              .sort(sortFunction)
              .slice(0, perPage)
              .map((user) => (
                <TableRow key={user.id}>
                  <TableCell>{user.nom}</TableCell>
                  <TableCell>{user.prenom}</TableCell>
                  <TableCell>{user.email}</TableCell>
                  <TableCell>{user.matricule}</TableCell>
                  <TableCell>{user.role.libelle}</TableCell>
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
    </div>
  );
};

export default Utilisateur;
