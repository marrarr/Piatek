import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './NavBar.jsx';
import HomePage from './HomePage.jsx';
import GamePage from './GamePage.jsx';
import Logowanie from './Logowanie.jsx';
import AddReview from './AddReview.jsx';
import Rejestracja from './Rejestracja.jsx';

/**
 * Główna aplikacja z trwałym logowaniem.
 *  - Użytkownik przechowywany w localStorage, więc odświeżenie strony nie wyloguje.
 *  - NavBar dostaje onLogout, by móc wyczyścić stan i localStorage.
 */
function App() {
  const [user, setUser] = useState(null);

  // Przy starcie aplikacji sprawdź localStorage
  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (e) {
        localStorage.removeItem('user');
      }
    }
  }, []);

  const handleLoginSuccess = (userData) => {
    localStorage.setItem('user', JSON.stringify(userData));
    setUser(userData);
  };

  const handleLogout = () => {
    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <BrowserRouter>
      <NavBar user={user} onLogout={handleLogout} />

      <Routes>
        {/* Strona główna */}
        <Route path="/" element={<HomePage user={user} />} />

        {/* Szczegóły gry */}
        <Route path="/game/:id" element={<GamePage user={user} />} />

        {/* Dodawanie recenzji – tylko po zalogowaniu */}
        <Route
          path="/game/:id/add-review"
          element={
            user ? <AddReview user={user} /> : <Navigate to="/login" replace />
          }
        />

        {/* Logowanie */}
        <Route
          path="/login"
          element={
            user ? (
              <Navigate to="/" replace />
            ) : (
              <Logowanie onLoginSuccess={handleLoginSuccess} />
            )
          }
        />

        {/* Catch-all – nieznane ścieżki */}
        <Route path="*" element={<Navigate to="/" replace />} />
        <Route path="/register" element={<Rejestracja />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
