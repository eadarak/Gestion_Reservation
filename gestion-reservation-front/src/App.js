// App.js
import React from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Layout from './Layout/Layout';
import Home from './Pages/Home';
import Reservation from './Pages/Reservation';
import Pret from './Pages/Pret.jsx';
import Retour from './Pages/Retour';
import Ressource from './Pages/Ressource';
import Categorie from './Pages/Categorie';
import Utilisateur from './Pages/Utilisateur';
import Authentification from './Pages/Authentification';
import Documentation from './Pages/Documentation';
import Inscription from './Component/Inscription.jsx';
import FaireReservation from './Pages/FaireReservation.jsx';
import FairePret from './Pages/FairePret.jsx';
import ReservationDuMois from './Pages/ReservationDuMois.jsx';
import ReservationUser from './Pages/ReservationUser.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout><Home /></Layout>
  },
  {
    path: '/reservation',
    element: <Layout><Reservation /></Layout>
  },
  {
    path: '/pret',
    element: <Layout><Pret /></Layout>
  },
  {
    path: '/retour',
    element: <Layout><Retour /></Layout>
  },
  {
    path: '/ressource',
    element: <Layout><Ressource /></Layout>
  },
  {
    path: '/categorie',
    element: <Layout><Categorie /></Layout>
  },
  {
    path: '/utilisateur',
    element: <Layout><Utilisateur /></Layout>
  },
  {
    path: '/authentification',
    element: <Layout><Authentification /></Layout>
  },
  {
    path: '/documentation',
    element: <Layout><Documentation /></Layout>
  },
  {
    path: '/inscription',
    element: <Layout>< Inscription/></Layout>
  },
  {
    path: '/faire-reservation',
    element: <Layout>< FaireReservation/></Layout>
  },
  {
    path: '/faire-pret',
    element: <Layout>< FairePret/></Layout>
  },
  {
    path: '/reservations-du-mois',
    element: <Layout>< ReservationDuMois/></Layout>
  },
  {
    path: '/mes-reservations',
    element: <Layout>< ReservationUser/></Layout>
  }
]);

function App() {
  return (
    <RouterProvider router={router}>
    </RouterProvider>
  );
}

export default App;
