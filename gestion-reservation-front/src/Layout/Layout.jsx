import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import '../styles/layout.css';

function Layout({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = sessionStorage.getItem('bearer');
    console.log("token", token);
  
    if (token) {
      const decodedPayload = JSON.parse(atob(token.split(".")[1]));
      const email = decodedPayload.sub;
      const userRole = decodedPayload.role;
  
      setUser({ email, role: userRole });
      console.log("Informations sur l'utilisateur", { email, role: userRole });
    }
  }, []); // Assurez-vous de passer un tableau vide comme deuxième argument pour exécuter cet effet une seule fois après le premier rendu
  
  


  return (
    <div className="layout">
      <Navbar />
      <div className="container">
        <div className="row header">
          <div className="header-info"> {/* Ajoutez la classe header-info ici */}
            <h2>Gestion des réservations</h2>
            {user && (
              <div className="user-info">
                <span className="email">{user.email}</span>
                <FontAwesomeIcon icon={faUserCircle} className="avatar" />
              </div>
            )}
          </div>
        </div>
        <div className="row">{children}</div>
      </div>
    </div>
  );
  
  
}

export default Layout;
