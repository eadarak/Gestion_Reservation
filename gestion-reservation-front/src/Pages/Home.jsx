import React, { useEffect, useRef } from 'react';
import gif from "../assets/svg/Landing page.gif";
import Typewriter from 'typewriter-effect/dist/core';
import { Button } from '@mui/material';
import { Link } from 'react-router-dom';

export default function Home() {
  const typewriterRef = useRef(null);

  useEffect(() => {
    typewriterRef.current = new Typewriter('#typewriter', {
      strings: ['UASZ,', 'Réservez...', 'Gérez..., ', 'Etudiez...'],
      autoStart: true,
      loop: true, // Added for continuous typing
    });

    typewriterRef.current.start();
  }, []);

  return (
    <div className="home">
      <div className='landing' style={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '10px 15px',
      }}>
        <div>
          <h4 className="Titre" style={{ fontFamily: '\'Poppins\', sans-serif', fontSize: '30px', fontWeight: '600', fontStyle: "italic",textTransform: 'uppercase', margin: '2px' }}>
            Votre API de Gestion Universitaire : <br/>
            <span id="typewriter" style={{ textTransform: "uppercase", color:"#11634c" }}>
            </span>
          </h4>

          <div className='button' style={{
            display:"flex",
            justifyContent:"space-between"
          }}>
          <Button
          component={Link}
          to="/inscription"
          variant="contained"
          sx={{
            fontSize: "16px",
            fontWeight: "bold",
            letterSpacing: ".75px",
            backgroundColor: "white",
            color:"#11634c",
            marginTop:"20px",
            padding: "10px 15px",
            "&:hover": {
              backgroundColor: "#0D2924",
              color: "white",
            },
            borderRadius:"30px"

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
            marginTop:"20px",
            padding: "10px 15px",
            "&:hover": {
              backgroundColor: "white",
              color: "#11634c",
            },
            borderRadius:"30px"
          }}
        >
          je suis un responsable
        </Button>
          </div>
        </div>
        <img src={gif} alt="GIF animé" />
      </div>
    </div>
  );
}

