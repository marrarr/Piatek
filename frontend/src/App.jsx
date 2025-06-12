import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './NavBar.jsx';
import HomePage from './HomePage.jsx';
import GamePage from './GamePage.jsx';
import Logowanie from './Logowanie.jsx';
import AddReview from './AddReview.jsx';
import Rejestracja from './Rejestracja.jsx';
import AdminPanel from './AdminPanel.jsx';
import UserList from './UserList.jsx';
import AddGameForm from './AddGameForm.jsx';
import GenreForm from './GenreForm.jsx';
import PlatformForm from './PlatformForm.jsx';
import GenreList from './GenreList.jsx';
import PlatformList from './PlatformList.jsx';

// Komponent zabezpieczający dostęp tylko dla admina
const RequireAdmin = ({ user, children }) => {
  if (!user || user.username !== 'admin') {
    return <Navigate to="/" replace />;
  }
  return children;
};

function App() {
  const [user, setUser] = useState(null);

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
        <Route path="/" element={<HomePage user={user} />} />
        <Route path="/game/:id" element={<GamePage user={user} />} />
        <Route
          path="/game/:id/add-review"
          element={user ? <AddReview user={user} /> : <Navigate to="/login" replace />}
        />
        <Route
          path="/login"
          element={user ? <Navigate to="/" replace /> : <Logowanie onLoginSuccess={handleLoginSuccess} />}
        />
        <Route path="/register" element={<Rejestracja />} />

        {/* Panel administratora i podstrony (tylko dla admina) */}
        <Route
          path="/admin"
          element={
            <RequireAdmin user={user}>
              <AdminPanel user={user} />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/users"
          element={
            <RequireAdmin user={user}>
              <UserList />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/add-game"
          element={
            <RequireAdmin user={user}>
              <AddGameForm />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/add-genre"
          element={
            <RequireAdmin user={user}>
              <GenreForm />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/add-platform"
          element={
            <RequireAdmin user={user}>
              <PlatformForm />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/genres"
          element={
            <RequireAdmin user={user}>
              <GenreList />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/platforms"
          element={
            <RequireAdmin user={user}>
              <PlatformList />
            </RequireAdmin>
          }
        />

        {/* Catch-all */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
