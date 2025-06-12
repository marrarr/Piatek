import React, { useState } from 'react';
import axios from 'axios';

function GenreForm({ onAdded }) {
  const [name, setName] = useState('');
  const [error, setError] = useState(null);

  // Pobierz token JWT z localStorage
  const token = (() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      try {
        return JSON.parse(storedUser).token;
      } catch {
        return null;
      }
    }
    return null;
  })();

  const handleSubmit = async e => {
    e.preventDefault();

    if (!token) {
      setError('Brak tokenu autoryzacji. Zaloguj się ponownie.');
      return;
    }

    try {
      const response = await axios.post(
        'http://localhost:8080/api/genres',
        { name },
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
      setName('');
      setError(null);
      if (onAdded) onAdded(response.data);
    } catch (err) {
      setError('Błąd podczas dodawania gatunku');
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mb-6">
      <h3 className="text-xl font-semibold mb-2">Dodaj nowy gatunek</h3>
      <input
        type="text"
        placeholder="Nazwa gatunku"
        value={name}
        onChange={e => setName(e.target.value)}
        required
        className="border rounded px-2 py-1 mr-2"
      />
      <button type="submit" className="bg-blue-600 text-white px-4 py-1 rounded">
        Dodaj
      </button>
      {error && <p className="text-red-600 mt-2">{error}</p>}
    </form>
  );
}

export default GenreForm;
