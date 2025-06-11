import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Rejestracja() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordRepeat, setPasswordRepeat] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== passwordRepeat) {
      setError('Hasła muszą być takie same.');
      return;
    }

    setError(null);
    setLoading(true);

    try {
      await axios.post('http://localhost:8080/api/auth/register', {
        username,
        email,
        password,
      });

      // Jeśli rejestracja się powiodła:
      alert('Rejestracja zakończona sukcesem! Możesz się teraz zalogować.');
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Coś poszło nie tak podczas rejestracji.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-bold mb-6 text-center">Rejestracja</h2>

      {error && <p className="text-red-600 mb-4">{error}</p>}

      <form onSubmit={handleSubmit}>

        <label className="block mb-2 font-semibold" htmlFor="username">Nazwa użytkownika</label>
        <input
          id="username"
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          className="w-full p-2 border rounded mb-4"
          placeholder="Wpisz nazwę użytkownika"
        />

        <label className="block mb-2 font-semibold" htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          className="w-full p-2 border rounded mb-4"
          placeholder="Wpisz adres email"
        />

        <label className="block mb-2 font-semibold" htmlFor="password">Hasło</label>
        <input
          id="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="w-full p-2 border rounded mb-4"
          placeholder="Wpisz hasło"
          minLength={6}
        />

        <label className="block mb-2 font-semibold" htmlFor="passwordRepeat">Powtórz hasło</label>
        <input
          id="passwordRepeat"
          type="password"
          value={passwordRepeat}
          onChange={(e) => setPasswordRepeat(e.target.value)}
          required
          className="w-full p-2 border rounded mb-4"
          placeholder="Powtórz hasło"
          minLength={6}
        />

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-green-600 text-white p-2 rounded hover:bg-green-700 disabled:opacity-50"
        >
          {loading ? 'Rejestracja...' : 'Zarejestruj się'}
        </button>
      </form>
    </div>
  );
}

export default Rejestracja;
