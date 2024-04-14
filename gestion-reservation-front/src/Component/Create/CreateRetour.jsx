import React, { useState } from "react";
import { Modal, TextField, Button, Typography } from "@mui/material";
import { URL_RETOURS } from "../../constante/Server";

export default function CreateRetour({ ressource, date }) {
  const INITIAL = { dateRetour: date || "", heureRetour: "", ressource: ressource }; // Utiliser uniquement l'ID de la ressource
  const [open, setOpen] = useState(false);
  const [retourData, setRetourData] = useState(INITIAL);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRetourData({ ...retourData, [name]: value });
  };

  const handleReturn = async () => {
    try {
      const sessionToken = sessionStorage.getItem("bearer");
      const response = await fetch(URL_RETOURS, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${sessionToken}`,
        },
        body: JSON.stringify(retourData), // Envoyer seulement l'ID de la ressource
      });
      console.log("les donnees envoyes ", retourData)


      if (response.status === 201) {
        console.log("les donnees envoyes ", retourData)
        setRetourData(INITIAL);
        setOpen(false);
        window.location.reload();
        alert("Retour enregistré avec succès!");
      } else {
        throw new Error("Erreur lors de la soumission des données");
      }
    } catch (error) {
      console.error("Erreur :", error);
      alert(
        "Une erreur est survenue lors de l'enregistrement du retour. Veuillez réessayer."
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
        Effectuer Retour
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
            Créer un retour de prêt pour {ressource.libelleRessource}
          </Typography>
          <input type="hidden" name="ressource" value={ressource} />

          {/* Pas besoin d'un champ caché pour l'ID de la ressource */}
          <TextField
            name="dateRetour"
            label="Date du Retour"
            type="date"
            value={retourData.dateRetour}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="heureRetour"
            label="Heure de Retour"
            value={retourData.heureRetour}
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
              onClick={handleReturn}
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
              Retourner
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
