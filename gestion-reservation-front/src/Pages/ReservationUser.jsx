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
  IconButton,
  Button,
} from "@mui/material";
import CancelIcon from "@mui/icons-material/Cancel";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { URL_RESERVATION, URL_UTILISATEUR } from "../constante/Server";
import { Link } from 'react-router-dom';

export default function ReservationUser() {
  const [reservations, setReservations] = useState([]);
  const [filteredReservations, setFilteredReservations] = useState([]);
  const [perPage, setPerPage] = useState(5);
  const [searchTerm, setSearchTerm] = useState("");
  const [orderBy, setOrderBy] = useState(null);
  const [order, setOrder] = useState("asc");
  const [user, setUser] = useState(null);
  const [users, setUsers] = useState([null]);
  const [token, setToken] = useState(null);
  const [email, setEmail] = useState("");



  useEffect(() => {
    const bearer = sessionStorage.getItem("bearer");
    if (bearer) {
      const decodedPayload = JSON.parse(atob(bearer.split(".")[1]));
      const mail = decodedPayload.sub;
      setEmail(mail);
    }
  }, []);

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

        // Recherche de l'utilisateur avec le même e-mail
        const utilisateur = data.find((u) => u.email === email);
        setUser(utilisateur);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUsers();
  }, [email]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_RESERVATION + `/list/${user.id}`, {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la récupération des réservations");
        }
        const data = await response.json();
        setReservations(data);
        setFilteredReservations(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchReservations();
  }, [user]);

  const handleChangePerPage = (event) => {
    setPerPage(event.target.value);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    const filtered = reservations.filter((reservation) =>
      reservation.libelleRessource
        .toLowerCase()
        .includes(event.target.value.toLowerCase())
    );
    setFilteredReservations(filtered);
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

  const handleCancelReservation = async (id) => {
    try {
      const confirmed = window.confirm(
        `Voulez-vous vraiment annuler la réservation ${id} ?`
      );
      if (!confirmed) return;

      const sessionToken = sessionStorage.getItem("bearer");

      const response = await fetch(
        `${URL_RESERVATION}/annuler-reservation/${id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        }
      );
      if (!response.ok) {
        throw new Error("Failed to cancel reservation");
      }
      setReservations(
        reservations.filter((reservation) => reservation.idReservation !== id)
      );
      setFilteredReservations(
        filteredReservations.filter(
          (reservation) => reservation.idReservation !== id
        )
      );
    } catch (error) {
      console.error(error);
    }
  };


  return (
    <div style={{ marginTop: "20px" }}>
      <Link to="/">
      <IconButton
        sx={{
          backgroundColor: "#11634c",
          "&:hover": {
            backgroundColor: "#113f36",
            color: "white",
          },
        }}
      >
        <ArrowBackIcon
          sx={{
            color: "white",
          }}
        />
      </IconButton>
      </Link>
        <br />
      <TextField
        id="search"
        label="Recherche"
        variant="outlined"
        value={searchTerm}
        onChange={handleSearch}
        sx={{ mt: 1 }}
      />
      <TableContainer component={Paper} sx={{ mt: 1 }}>
        <Table aria-label="reservation table">
          <TableHead>
            <TableRow>
              <TableCell onClick={() => handleSort("libelleRessource")}>
                Libellé{" "}
                {orderBy === "libelleRessource" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("datePrevue")}>
                Date prévue{" "}
                {orderBy === "datePrevue" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("heureDebut")}>
                Heure de début{" "}
                {orderBy === "heureDebut" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("heureFin")}>
                Heure de fin{" "}
                {orderBy === "heureFin" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("dateReservation")}>
                Date de réservation{" "}
                {orderBy === "dateReservation" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("email")}>
                Effectué par{" "}
                {orderBy === "email" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredReservations
              .sort(sortFunction)
              .slice(0, perPage)
              .map((reservation) => (
                <TableRow key={reservation.idReservation}>
                  <TableCell>
                    {reservation.ressource.libelleRessource}
                  </TableCell>
                  <TableCell>{reservation.datePrevue}</TableCell>
                  <TableCell>{reservation.heureDebut}</TableCell>
                  <TableCell>{reservation.heureFin}</TableCell>
                  <TableCell>{reservation.dateReservation}</TableCell>
                  <TableCell>{reservation.utilisateur.email}</TableCell>
                  <TableCell>
                    <IconButton
                      onClick={() =>
                        handleCancelReservation(reservation.idReservation)
                      }
                      sx={{
                        backgroundColor: "#ff0000",
                        "&:hover": {
                          backgroundColor: "#720000",
                          color: "white",
                        },
                      }}
                    >
                      <CancelIcon
                        sx={{
                          color: "white",
                        }}
                      />
                    </IconButton>
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
}
