import React, { useState } from "react";
import { Modal, TextField, Button, Typography } from "@mui/material";
import { URL_PRETS } from "../../constante/Server";

export default function CreatePret({ ressource }) {
  const INITIAL = { datePret: "", heureDebutPret: 0, ressource: ressource };
  const [open, setOpen] = useState(false);
  const [pretData, setPretData] = useState(INITIAL);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPretData({ ...pretData, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      const sessionToken = sessionStorage.getItem("bearer");
      const response = await fetch(URL_PRETS, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${sessionToken}`,
        },
        body: JSON.stringify(pretData), // Envoyer pretData directement
      });

      if (response.status === 201) {
        setPretData(INITIAL);
        setOpen(false);
        window.location.reload();
        alert("Prêt effectué avec succès!");
      } else {
        throw new Error("Erreur lors de la soumission des données");
      }
    } catch (error) {
      console.error("Erreur :", error);
      alert(
        "Une erreur est survenue lors de la création du prêt. Veuillez réessayer."
      );
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
        Prêter
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
            Créer un prêt pour {ressource.libelleRessource}
          <input type="hidden" name="ressource" value={ressource} />

          </Typography>
          <TextField
            name="datePret"
            label="Date du Pret"
            type="date"
            value={pretData.datePret}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="heureDebutPret"
            label="Heure de début"
            value={pretData.heureDebutPret}
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
              Prêter
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
