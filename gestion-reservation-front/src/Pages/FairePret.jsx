import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography, // Importer le composant Typography
} from "@mui/material";

import { URL_RESSOURCE } from "../constante/Server";
import CreatePret from "../Component/Create/CreatePret";

const FairePret = () => {
  const [ressources, setRessources] = useState([]);

  useEffect(() => {
    const fetchRessources = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_RESSOURCE + "/pretables", {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la récupération des ressources");
        }
        const data = await response.json();
        setRessources(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchRessources();
  }, []);

  return (
    <div style={{ marginTop: "20px" }}>
      <Typography variant="h4" gutterBottom>
    
        Liste de ressources pretables
      </Typography>
      
      <TableContainer component={Paper} sx={{ mt: 1 }}>
        <Table aria-label="utilisateur table">
          <TableHead>
            <TableRow>
              <TableCell>Libellé</TableCell>
              <TableCell>État</TableCell>
              <TableCell>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {ressources.map((ressource) => (
              <TableRow key={ressource.idRessource}>
                <TableCell>{ressource.libelleRessource}</TableCell>
                <TableCell>{ressource.etat}</TableCell>
                <TableCell>
                  <CreatePret ressource={ressource} />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default FairePret;
