import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './NavBar.jsx';
import HomePage from './HomePage.jsx';
import GamePage from './GamePage.jsx';
import Logowanie from './Logowanie.jsx';
import AddReview from './AddReview.jsx'; // import komponentu formularza dodawania recenzji

function App() {
  const [user, setUser] = useState(null); // null = nie zalogowany

  return (
    <BrowserRouter>
      <NavBar user={user} />
      <Routes>
        <Route path="/" element={<HomePage user={user} />} />
        <Route path="/game/:id" element={<GamePage user={user} />} />
        <Route path="/game/:id/add-review" element={
          user ? (
            <AddReview user={user} />
          ) : (
            <Navigate to="/login" />
          )
        } />
        <Route path="/login" element={
          user ? <Navigate to="/" /> : <Logowanie onLoginSuccess={setUser} />
        } />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
