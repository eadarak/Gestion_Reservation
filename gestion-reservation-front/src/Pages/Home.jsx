import React, { useEffect, useRef, useState } from "react";
import gif from "../assets/svg/Landing page.gif";
import welcome from "../assets/svg/Welcome.gif";
import CountUp from "react-countup";
import Typewriter from "typewriter-effect/dist/core";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import { LineChart } from "@mui/x-charts/LineChart";

import "../styles/home.css";
import {
  URL_API,
  URL_PRETS,
  URL_RESERVATION,
  URL_RESSOURCE,
} from "../constante/Server";

export default function Home() {
  const typewriterRef = useRef(null);
  const [user, setUser] = useState(null);
  const [users, setUsers] = useState(0);
  const [ressources, setRessources] = useState(0);
  const [reservations, setReservations] = useState(0);
  const [prets, setPrets] = useState(0);

  useEffect(() => {
    const token = sessionStorage.getItem("bearer");
    if (token) {
      const decodedPayload = JSON.parse(atob(token.split(".")[1]));
      const email = decodedPayload.sub;
      const userRole = decodedPayload.role;
      setUser({ email, role: userRole });
    }
  }, []);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_API + "/total-user", {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la recuperation des utilisateurs");
        }
        const data = await response.json();
        setUsers(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchUsers();
  }, []);

  useEffect(() => {
    const fetchRessources = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_RESSOURCE + "/total-ressource", {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la recuperation des ressources");
        }
        const data = await response.json();
        setRessources(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchRessources();
  }, []);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_RESERVATION + "/total-reservation", {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la recuperation des reservations");
        }
        const data = await response.json();
        setReservations(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchReservations();
  }, []);

  useEffect(() => {
    const fetchPrets = async () => {
      try {
        const sessionToken = sessionStorage.getItem("bearer");

        const response = await fetch(URL_PRETS + "/total-pret", {
          headers: {
            Authorization: `Bearer ${sessionToken}`,
          },
        });
        if (!response.ok) {
          throw new Error("Erreur lors de la recuperation des Prets");
        }
        const data = await response.json();
        setPrets(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchPrets();
  }, []);

  useEffect(() => {
    typewriterRef.current = new Typewriter("#typewriter", {
      strings: ["UASZ", "Réservez...", "Gérez...", "Etudiez..."],
      autoStart: true,
      loop: true, // Added for continuous typing
    });

    typewriterRef.current.start();
  }, []);

  return (
    <div className="home">
      {user === null && (
        <div
          className="landing"
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            padding: "10px 15px",
          }}
        >
          <div>
            <h4
              className="Titre"
              style={{
                fontFamily: "'Poppins', sans-serif",
                fontSize: "30px",
                fontWeight: "600",
                fontStyle: "italic",
                textTransform: "uppercase",
                margin: "2px",
              }}
            >
              Votre API de Gestion Universitaire : <br />
              <span
                id="typewriter"
                style={{ textTransform: "uppercase", color: "#11634c" }}
              ></span>
            </h4>

            <div
              className="button"
              style={{
                display: "flex",
                justifyContent: "space-between",
              }}
            >
              <Button
                component={Link}
                to="/inscription"
                variant="contained"
                sx={{
                  fontSize: "16px",
                  fontWeight: "bold",
                  letterSpacing: ".75px",
                  backgroundColor: "white",
                  color: "#11634c",
                  marginTop: "20px",
                  padding: "10px 15px",
                  "&:hover": {
                    backgroundColor: "#0D2924",
                    color: "white",
                  },
                  borderRadius: "30px",
                }}
              >
                je suis un client
              </Button>

              <Button
                component={Link}
                to="/authentification"
                variant="contained"
                sx={{
                  fontSize: "16px",
                  fontWeight: "bold",
                  letterSpacing: ".75px",
                  backgroundColor: "#113f36",
                  marginTop: "20px",
                  padding: "10px 15px",
                  "&:hover": {
                    backgroundColor: "white",
                    color: "#11634c",
                  },
                  borderRadius: "30px",
                }}
              >
                je suis un responsable
              </Button>
            </div>
          </div>
          <img src={gif} alt="GIF animé" />
        </div>
      )}

      {/* Home page for user */}
      {user && user.role === "UTILISATEUR" && (
        <div
          className="landing"
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            padding: "10px 5px",
          }}
        >
          <div>
            <h4
              className="Titre"
              style={{
                fontFamily: "'Poppins', sans-serif",
                fontSize: "30px",
                fontWeight: "600",
                fontStyle: "italic",
                textTransform: "uppercase",
                margin: "2px",
                letterSpacing: "1.9px",
              }}
            >
              Bienvenue dans l'Espace Etudiant
            </h4>

            <div
              className="button"
              style={{
                display: "flex",
                flexDirection: "column",
                width: "500px",
                margin: "0 auto",
              }}
            >
              <Button
                component={Link}
                to="/faire-reservation"
                variant="contained"
                sx={{
                  fontSize: "16px",
                  fontWeight: "bold",
                  letterSpacing: ".75px",
                  backgroundColor: "white",
                  color: "#11634c",
                  marginTop: "20px",
                  padding: "10px 15px",
                  "&:hover": {
                    backgroundColor: "#0D2924",
                    color: "white",
                  },
                  borderRadius: "30px",
                }}
              >
                Catalogue des ressources
              </Button>

              <Button
                component={Link}
                to="/mes-reservations"
                variant="contained"
                sx={{
                  fontSize: "16px",
                  fontWeight: "bold",
                  letterSpacing: ".75px",
                  backgroundColor: "white",
                  color: "#11634c",
                  marginTop: "20px",
                  padding: "10px 15px",
                  "&:hover": {
                    backgroundColor: "#0D2924",
                    color: "white",
                  },
                  borderRadius: "30px",
                }}
              >
                Mes Reservations
              </Button>
            </div>
          </div>
          <img src={welcome} alt="GIF animé" />
        </div>
      )}

      {/* admin general */}
      {user && user.role === "ADMINISTRATEUR" && (
        <div className="landing admin-space">
          <h4
            className="Titre"
            style={{
              fontFamily: "'Poppins', sans-serif",
              fontSize: "30px",
              fontWeight: "600",
              fontStyle: "italic",
              textTransform: "uppercase",
              margin: "2px",
              letterSpacing: "1.9px",
              paddingTop: "15px",
            }}
          >
            Espace ADMIN
          </h4>
          <div className="container">
            <div className="row mt-3">
              <div className="card col-lg-3 col-md-6 col-sm-6 m-2">
                <button className="attach">
                  <i class="fa-solid fa-users"></i>
                </button>
                <div className="card-body">
                  <div>Utilisateur</div>
                  <div>
                    <CountUp end={users} duration={3} />
                  </div>
                </div>
              </div>
              <div className="card col-lg-3 col-md-6 col-sm-6 m-2">
                <button className="attach">
                  <i class="fa-solid fa-computer"></i>
                </button>
                <div className="card-body">
                  <div>Ressource</div>
                  <div>
                    <CountUp end={ressources} duration={3} />
                  </div>
                </div>
              </div>
              <div className="card col-lg-3 col-md-6 col-sm-6 m-2">
                <button className="attach">
                  <i class="fa-solid fa-handshake-angle"></i>
                </button>
                <div className="card-body">
                  <div>Reservation</div>
                  <div>
                    <CountUp end={reservations} duration={3} />
                  </div>
                </div>
              </div>
              <div className="card col-lg-3 col-md-6 col-sm-6 m-2">
                <button className="attach">
                  <i class="fa-solid fa-code-compare"></i>
                </button>
                <div className="card-body">
                  <div>Pret</div>
                  <div>
                    <CountUp end={prets} duration={3} />
                  </div>
                </div>
              </div>
            </div>
            <div className="row  row-chart mt-2">
              <LineChart
                xAxis={[{ data: [1, 2, 3, 5, 8, 10] }]}
                series={[
                  {
                    data: [2, 5.5, 2, 8.5, 1.5, 5],
                  },
                ]}
                width={500}
                height={300}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
