import React, { useState } from "react";
import { Modal, TextField, Button, Typography } from "@mui/material";
import { URL_UTILISATEUR } from "../../constante/Server";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";

export default function CreateUser() {
  const INITIAL = { nom: "", email: "", prenom: "", matricule: "" };
  const [open, setOpen] = useState(false);
  const [userData, setUserData] = useState(INITIAL);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      const sessionToken = sessionStorage.getItem("bearer");
      const response = await fetch(URL_UTILISATEUR, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${sessionToken}`,
        },
        body: JSON.stringify(userData),
      });

      if (!response.ok) {
        throw new Error("Erreur lors de la soumission des données");
      }

      setUserData(INITIAL);
      setOpen(false);
      window.location.reload();
    } catch (error) {
      console.error("Erreur :", error);
    }
  };

  return (
    <div>
      <Button
        onClick={handleOpen}
        variant="contained"
        sx={{
          backgroundColor: "#113f36",
          "&:hover": {
            backgroundColor: "#0D2924",
          },
          padding: "10px 20px",
        }}
      >
        <FontAwesomeIcon icon={faUserPlus} style={{ fontSize: "24px" }} />
      </Button>
      <Modal open={open} onClose={handleClose}>
        <div
          style={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            backgroundColor: "white",
            padding: 20,
          }}
        >
          <Typography variant="h5" gutterBottom>
            Ajouter un nouvel utilisateur
          </Typography>
          <TextField
            name="nom"
            label="Nom"
            value={userData.nom}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="prenom"
            label="Prénom"
            value={userData.prenom}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="email"
            label="Email"
            value={userData.email}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="matricule"
            label="Matricule"
            value={userData.matricule}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <div
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "space-around",
            }}
          >
            <Button
              onClick={handleSubmit}
              variant="contained"
              sx={{
                marginTop: 10,
                marginRight: 10,
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
              Ajouter
            </Button>
            <Button
              onClick={handleClose}
              variant="contained"
              sx={{
                marginTop: 10,
                marginRight: 10,
                fontSize: "16px",
                fontWeight: "bold",
                letterSpacing: ".75px",
                backgroundColor: "#970000",
                "&:hover": {
                  backgroundColor: "#5a0000",
                  color: "white",
                },
              }}
            >
              Annuler
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
