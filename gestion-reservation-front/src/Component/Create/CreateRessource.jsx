import React, { useState } from "react";
import { Modal, TextField, Button, Typography } from "@mui/material";
import { URL_RESSOURCE } from "../../constante/Server";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

export default function CreateUser() {
  const INITIAL = { libelleRessource: "", description: ""};
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
      const response = await fetch(URL_RESSOURCE, {
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
        <FontAwesomeIcon icon={faPlus} style={{ fontSize: "24px" }} />
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
            Ajouter une nouvelle Ressource
          </Typography>
          <TextField
            name="libelleRessource"
            label="Libelle"
            value={userData.libelleRessource}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="description"
            label="Descrition"
            value={userData.description}
            onChange={handleChange}
            fullWidth
            margin="normal"
            multiline
            rows={4}
  
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
