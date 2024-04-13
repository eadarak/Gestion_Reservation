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
  }
]);

function App() {
  return (
    <RouterProvider router={router}>
      {/* Inclure ici le composant Layout qui contient Home */}
    </RouterProvider>
  );
}

export default App;
