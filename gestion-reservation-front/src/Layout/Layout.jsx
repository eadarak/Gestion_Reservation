import React from 'react';
import Navbar from './Navbar';
import '../styles/layout.css'


function Layout({ children }) {

  return (
    <div className="layout">
      <Navbar/>
      <div className="container">
        <div className="row header">
          <h2> Gestion des reservation </h2>
        </div>
      <div className="row">
        {children}
        </div>
      </div>
    </div>
  );
}

export default Layout;