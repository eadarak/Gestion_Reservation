import React, { useState } from "react";
import { Modal, TextField, Button, Typography } from "@mui/material";
import { URL_RESERVATION } from "../../constante/Server";

export default function CreateReservation({ ressource }) {
  const INITIAL = { heureDebut: "", heureFin: "", datePrevue: "", ressource: ressource };
  const [open, setOpen] = useState(false);
  const [reservationData, setReservationData] = useState(INITIAL);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReservationData({ ...reservationData, [name]: value });
  };


    const handleSubmit = async () => {
        try {
        const sessionToken = sessionStorage.getItem("bearer");
        const response = await fetch(URL_RESERVATION, {
            method: "POST",
            headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${sessionToken}`,
            },
            body: JSON.stringify(reservationData),
        });
    
        if (response.status === 201) {
            setReservationData(INITIAL);
            setOpen(false);
            window.location.reload();
            alert("Réservation effectuée avec succès!");
        } else {
            throw new Error("Erreur lors de la soumission des données");
        }
        } catch (error) {
        console.error("Erreur :", error);
        alert("Une erreur est survenue lors de la réservation. Veuillez réessayer.");
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
        Réserver

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
            Faire une réservation pour {ressource.libelleRessource}
          </Typography>
          <input type="hidden" name="ressource" value={ressource} />
          <TextField
            name="heureDebut"
            label="Heure de début"
            value={reservationData.heureDebut}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="heureFin"
            label="Heure de fin"
            value={reservationData.heureFin}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            name="datePrevue"
            label="Date prévue"
            type="date"
            value={reservationData.datePrevue}
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
              Réserver
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
