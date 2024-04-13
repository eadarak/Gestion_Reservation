import React from "react";
import { NavLink } from "react-router-dom";
import Logo from "../assets/logos/Logo_uasz-bg-transparent.png";
import "../styles/Navbar.css"; // Assurez-vous d'importer votre fichier CSS

export default function Navbar() {

  return (
    <header className="sidebar">
      <div className="entete">
        <img src={Logo} alt="" />
        <span>Universite Assane Seck</span>
      </div>

      <hr className="separator" />

      <nav className="nav flex-column">
        <NavLink
          to="/"
          className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-house"></i>
          Accueil
        </NavLink>
        <NavLink
          to="/reservation"
          className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-calendar-plus"></i> 
          Reservation
        </NavLink>
        <NavLink
          to="/pret"
          className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-arrows-rotate"></i>
          Pret
        </NavLink>
        <NavLink
          to="/retour"
          className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-arrow-right-arrow-left"></i>
          Retour
        </NavLink>
        <NavLink
          to="/ressource"
          className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-microchip"></i>
          Ressource
        </NavLink>
        <NavLink
          to="/categorie"
          className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-sitemap"></i>
          Categorie
        </NavLink>
      </nav>

      <hr className="separator" />

      <nav className="nav flex-column">
        <NavLink to="/authentification"
        className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-fingerprint"></i>
          Authentification
        </NavLink>
        <NavLink 
        to="/documentation"
        className={({ isActive }) => (isActive ? "active-link" : "link")}
        >
          <i class="fa-solid fa-file-code"></i>
          Documentation
        </NavLink>
        <p> &copy; ead & SD & BMD & MT </p>
      </nav>
    </header>
  );
}
