import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { URL_RETOURS } from "../constante/Server"; // Assuming you have a constant for the API URL

const Retour = () => {
  const [retours, setRetours] = useState([]);
  const [filteredRetours, setFilteredRetours] = useState([]);
  const [perPage, setPerPage] = useState(5);
  const [orderBy, setOrderBy] = useState(null);
  const [order, setOrder] = useState("asc");

  useEffect(() => {
    const fetchRetours = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_RETOURS, {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la récupération des prêts");
        }
        const data = await response.json();
        setRetours(data);
        setFilteredRetours(data); // Peut-être que vous voulez filtrer les retours initialement ?
      } catch (error) {
        console.error(error);
      }
    };

    fetchRetours();
  }, []);

  const handleChangePerPage = (event) => {
    setPerPage(event.target.value);
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

      <TableContainer component={Paper} sx={{ mt: 1 }}>
        <Table aria-label="retour table">
          <TableHead>
            <TableRow>
              <TableCell onClick={() => handleSort("libelleRessource")}>
                Ressource{" "}
                {orderBy === "libelleRessource" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("dateRetour")}>
                Date de Retour{" "}
                {orderBy === "dateRetour" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
              <TableCell onClick={() => handleSort("heureRetour")}>
                Heure de retour de prêt{" "}
                {orderBy === "heureRetour" && (
                  <span>{order === "asc" ? "▲" : "▼"}</span>
                )}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredRetours
              .sort(sortFunction)
              .slice(0, perPage)
              .map((retour) => (
                <TableRow key={retour.idRetour}>
                  <TableCell>{retour.ressource.libelleRessource}</TableCell>
                  <TableCell>{retour.dateRetour}</TableCell>
                  <TableCell>{retour.heureRetour} H</TableCell>
                  {/* <TableCell>
                    {retour.ressource.etat === "En Retour" && (
                      <CreateRetour
                        ressource={retour.ressource}
                        date={retour.dateRetour}
                      />
                    )}
                  </TableCell> */}
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

export default Retour;
