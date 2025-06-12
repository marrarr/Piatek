import React from 'react';
import { Link, Navigate } from 'react-router-dom';

const AdminPanel = ({ user }) => {
  if (!user || user.username !== 'admin') {
    return <Navigate to="/" replace />;
  }

  return (
    <div className="container mx-auto p-6">
      <h1 className="text-3xl font-bold mb-6">Panel administratora</h1>
      <div className="space-y-4">
        <Link to="/" className="block text-blue-600 hover:underline">Strona główna</Link>
        <Link to="/admin/users" className="block text-blue-600 hover:underline">Lista użytkowników</Link>
        <Link to="/admin/add-game" className="block text-blue-600 hover:underline">Dodaj nową grę</Link>
        <Link to="/admin/genres" className="block text-blue-600 hover:underline">Lista gatunków</Link>
        <Link to="/admin/platforms" className="block text-blue-600 hover:underline">Lista platform</Link>
        <Link to="/admin/add-genre" className="block text-blue-600 hover:underline">Dodaj gatunek</Link>
        <Link to="/admin/add-platform" className="block text-blue-600 hover:underline">Dodaj platformę</Link>
      </div>
    </div>
  );
};

export default AdminPanel;
