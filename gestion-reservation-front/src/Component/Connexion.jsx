import React, { useState } from "react";
import { Link } from "react-router-dom";
import { URL_CONNEXION } from "../constante/Server";
import "../styles/connexion.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

const Connexion = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = {
      username,
      password,
    };

    setUsername("");
    setPassword("");

    try {
      const response = await fetch("http://localhost:8080/api/connexion", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        const responseData = await response.json();
        const authToken = responseData.bearer;
        setToken(authToken);
        sessionStorage.setItem("bearer", authToken);
        console.log("Connexion réussie ! Token généré:", authToken);
        const decodedPayload = JSON.parse(atob(authToken.split(".")[1]));
        const userId = decodedPayload.sub;
        console.log("ID de l'utilisateur connecté:", userId);
        const userRole = decodedPayload.role;
        console.log("Rôle de l'utilisateur connecté:", userRole);
        setSuccessMessage("Connexion réussie !");
        setErrorMessage("");
      } else {
        const errorData = await response.json();
        setErrorMessage(errorData.message);
        setSuccessMessage("");
      }
    } catch (error) {
      console.error("Erreur lors de la connexion:", error);
      setErrorMessage("Erreur lors de la connexion.");
      setSuccessMessage("");
    }
  };

  return (
    <div className="connexion-container">
      <div className="form-container">
        <h2 className="title">Connexion</h2>
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
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              className="form-input"
              placeholder="Nom d'utilisateur"
            />
          </div>
          <div className="form-group password-group">
            <input
              type={showPassword ? "text" : "password"}
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="form-input"
              placeholder="Mot de passe"
            />
            <span
              onClick={() => setShowPassword(!showPassword)}
              className="password-toggle"
            >
              <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye} />
            </span>
          </div>
          <div className="form-group">
            <button type="submit" className="btn">
              Se connecter
            </button>
          </div>
        </form>
        <p className="signup-link">
          Vous n'avez pas de compte ?{" "}
          <Link to="/inscription" className="link">
            Inscrivez-vous ici
          </Link>
          .
        </p>
      </div>
    </div>
  );
};

export default Connexion;
