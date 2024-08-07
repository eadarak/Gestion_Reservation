import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';
import '../styles/layout.css';
import { Avatar } from '@mui/material';


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
  }, []);

  return (
    <div className="layout">
      {(user && user.role !== 'UTILISATEUR') && (
        <div className='main'>
          <Navbar />
          <div className="container">
            <div className="row header">
              <div className="header-info">
                <h2>Gestion des réservations</h2>
                  <div className="user-info">
                    <Avatar />
                  </div>
              </div>
            </div>
            <div className="row">{children}</div>
          </div>
        </div>
      )}

      {(user && user.role === 'UTILISATEUR')&& (
              <div className="contain">
                <div className="row header">
                  <div className="header-info">
                    <h2>Gestion des réservations</h2>
                    <div className="user-info">
                    <Avatar>
                      
                    </Avatar>
                  </div>
                  </div>
                </div>
                <div className="container">{children}</div>
              </div>
            )}

      {user === null && (
        <div className="contain">
          <div className="row header">
            <div className="header-info">
              <h2>Gestion des réservations</h2>
            </div>
          </div>
          <div className="container">{children}</div>
        </div>
      )}
    </div>
  );
}

export default Layout;
