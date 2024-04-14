import React, { useState } from 'react';
import { URL_INSCRIPTION } from '../constante/Server';

import "../styles/inscription.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

const Inscription = () => {
  const INITIAL_USER = {
    nom: '',
    prenom: '',
    matricule: '',
    email: '',
    mdp: ''
  }
  const [formData, setFormData] = useState(INITIAL_USER);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      const response = await fetch(URL_INSCRIPTION, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        setErrorMessage('Erreur lors de l\'inscription');
        setSuccessMessage("");
        throw new Error('Erreur lors de l\'inscription');
        
      }

      setSuccessMessage("Inscription réussie !");
      setErrorMessage("");
      setFormData(INITIAL_USER);
      window.location.href = '/authentification';
    } catch (error) {
      console.error("Erreur lors de la connexion:", error);
      setErrorMessage("Erreur lors de la connexion.");
      setSuccessMessage("");
    }
  };

  return (
    <div className="inscription-page">
      <div className='container-inscription'>
        <h2>Inscription</h2>
        {successMessage && (
          <div className="alert alert-success">{successMessage}</div>
        )}
        {errorMessage && (
          <div className="alert alert-danger">{errorMessage}</div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              name="nom"
              value={formData.nom}
              onChange={handleChange}
              placeholder="Nom"
              className="form-control"
            />
            <input
              type="text"
              name="prenom"
              value={formData.prenom}
              onChange={handleChange}
              placeholder="Prénom"
              className="form-control"
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              name="matricule"
              value={formData.matricule}
              onChange={handleChange}
              placeholder="Matricule"
              className="form-control"
            />
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Email"
              className="form-control"
            />
          </div>
          <div className='form-group password-group'>
            <input
              type={showPassword ? "text" : "password"}
              name="mdp"
              value={formData.mdp}
              onChange={handleChange}
              placeholder="Mot de passe"
              className="form-control"
            />
            <span
              onClick={() => setShowPassword(!showPassword)}
              className="password-toggle"
            >
              <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye} />
            </span>
          </div>
          <button type="submit" className="btn">S'inscrire</button>
          <p className="signup-link">
          Vous avez deja un compte ?{" "}
          <Link to="/authentification" className="link">
            se connecter
          </Link>
          .
        </p>
        </form>
      </div>
    </div>
  );
};

export default Inscription;
