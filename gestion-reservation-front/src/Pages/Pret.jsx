import React, { useEffect, useState } from "react";
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
} from "@mui/material";
import { URL_PRETS } from "../constante/Server"; // Assuming you have a constant for the API URL
import CreateRetour from "../Component/Create/CreateRetour";

const Pret = () => {
  const [prets, setPrets] = useState([]);
  const [filteredPrets, setFilteredPrets] = useState([]);
  const [perPage, setPerPage] = useState(5);
  const [searchTerm, setSearchTerm] = useState("");
  const [orderBy, setOrderBy] = useState(null);
  const [order, setOrder] = useState("asc");

  useEffect(() => {
    const fetchPrets = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_PRETS, {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la récupération des prêts");
        }
        const data = await response.json();
        setPrets(data);
        setFilteredPrets(data); // Peut-être que vous voulez filtrer les prets initialement ?
      } catch (error) {
        console.error(error);
      }
    };

    fetchPrets();
  }, []);

  const handleChangePerPage = (event) => {
    setPerPage(event.target.value);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    const filtered = prets.filter((pret) =>
      pret.heureDebutPret
        .toLowerCase()
        .includes(event.target.value.toLowerCase())
    );
    setFilteredPrets(filtered);
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
    <div style={{ marginTop: "20px" }}>
      <TextField
        id="search"
        label="Recherche"
        variant="outlined"
        value={searchTerm}
        onChange={handleSearch}
        sx={{ mt: 1 }}
      />
      <TableContainer component={Paper} sx={{ mt: 1 }}>
        <Table aria-label="pret table">
          <TableHead>
            <TableRow>
              <TableCell onClick={() => handleSort("libelleRessource")}>
                Ressource{" "}
                {orderBy === "libelleRessource" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("datePret")}>
                Date du Pret{" "}
                {orderBy === "datePret" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("heureDebutPret")}>
                Heure début de prêt{" "}
                {orderBy === "heureDebutPret" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("duree")}>
                Durée{" "}
                {orderBy === "duree" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredPrets
              .sort(sortFunction)
              .slice(0, perPage)
              .map((pret) => (
                <TableRow key={pret.idPret}>
                  <TableCell>{pret.ressource.libelleRessource}</TableCell>
                  <TableCell>{pret.datePret}</TableCell>
                  <TableCell>{pret.heureDebutPret} H</TableCell>
                  <TableCell>{pret.duree} h</TableCell>
                  <TableCell>
                    {pret.ressource.etat === "En Pret" && (
                      <CreateRetour
                        ressource={pret.ressource}
                        date={pret.datePret}
                      />
                    )}
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
      <div
        style={{
          display: "flex",
          justifyContent: "flex-end",
          marginTop: "10px",
        }}
      >
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

export default Pret;
