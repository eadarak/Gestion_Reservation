import React, { useState } from 'react';
import "../styles/connexion.css"

const Connexion = () => {
  // États pour stocker les valeurs des champs username et password
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [token, setToken] = useState(null);
  const [header, payload, signature] = token.split('.');

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const data = {
      username,
      password
    };

    setUsername('');
    setPassword('');

    try {
      const response = await fetch('http://localhost:8080/api/connexion', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      if (response.ok) {
        const responseData = await response.json();
        console.log('Connexion réussie ! Token généré:', responseData);
        const authToken = responseData.bearer;
        localStorage.setItem('authToken', authToken);
        setToken(authToken);
        console.log('Connexion réussie ! Token généré:', authToken);
        const decodedPayload = JSON.parse(atob(payload));
        const userId = decodedPayload.sub;
        console.log("ID de l'utilisateur connecté:", userId);
        const userRole = decodedPayload.role;
        console.log("Rôle de l'utilisateur connecté:", userRole);
      } else {
        // Traitement de la réponse en cas d'erreur
        console.error('Échec de la connexion');
      }
    } catch (error) {
      console.error('Erreur lors de la connexion:', error);
    }
  };

  return (
    <div className="form-container">
      <h2>Connexion</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Nom d'utilisateur :</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Mot de passe :</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <input type="submit" value="Se connecter" />
        </div>
      </form>
    </div>
  );
};

export default Connexion;
