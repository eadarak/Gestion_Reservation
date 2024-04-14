import React from 'react';
import Button from '@mui/material/Button'; // Import du composant Button depuis Material-UI
import {URL_DOCUMENTATTION } from '../constante/Server';

export default function Documentation() {
  // Fonction de redirection vers la documentation
  const redirectToDocumentation = () => {
    // Ajoutez ici le lien vers votre documentation
    window.location.href = URL_DOCUMENTATTION;
  };

  return (
    <div style={{
      padding:"20px 30px"
    }}>
      <div>Documentation du Backend</div>
      <Button variant="contained" onClick={redirectToDocumentation}>
        Voir la documentation
      </Button>
    </div>
  );
}
