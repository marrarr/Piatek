import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Logowanie({ onLoginSuccess }) {
  const [formUsername, setFormUsername] = useState('');
  const [formPassword, setFormPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const zaloguj = () => {
    setLoading(true);
    axios.get('http://localhost:8080/api/auth/login', {
      auth: {
        username: formUsername,
        password: formPassword,
      },
    })
    .then((response) => {
      setLoading(false);
      if (onLoginSuccess) {
        onLoginSuccess({ username: formUsername, ...response.data });
      }
      navigate('/'); // przekierowanie na stronę główną
    })
    .catch(() => {
      setLoading(false);
      alert('Niepoprawny login lub hasło');
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    zaloguj();
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-bold mb-6 text-center">Logowanie</h2>
      <form onSubmit={handleSubmit}>
        <label className="block mb-2 font-semibold" htmlFor="username">Login</label>
        <input
          id="username"
          type="text"
          value={formUsername}
          onChange={(e) => setFormUsername(e.target.value)}
          required
          className="w-full p-2 border rounded mb-4"
          placeholder="Wpisz login"
        />

        <label className="block mb-2 font-semibold" htmlFor="password">Hasło</label>
        <input
          id="password"
          type="password"
          value={formPassword}
          onChange={(e) => setFormPassword(e.target.value)}
          required
          className="w-full p-2 border rounded mb-4"
          placeholder="Wpisz hasło"
        />

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white p-2 rounded hover:bg-blue-700 disabled:opacity-50"
        >
          {loading ? 'Logowanie...' : 'Zaloguj się'}
        </button>
      </form>
    </div>
  );
}

export default Logowanie;
