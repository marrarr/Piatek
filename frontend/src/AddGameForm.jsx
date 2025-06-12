import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AddGameForm() {
  const [title, setTitle] = useState('');
  const [releaseDate, setReleaseDate] = useState('');
  const [developer, setDeveloper] = useState('');
  const [publisher, setPublisher] = useState('');
  const [description, setDescription] = useState('');
  const [genres, setGenres] = useState([]);
  const [platforms, setPlatforms] = useState([]);
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [selectedPlatforms, setSelectedPlatforms] = useState([]);
  const [message, setMessage] = useState('');
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

  useEffect(() => {
    if (!token) {
      setError('Brak tokenu autoryzacji. Zaloguj się ponownie.');
      return;
    }

    axios.get('http://localhost:8080/api/genres', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setGenres(res.data))
      .catch(err => {
        console.error(err);
        setError('Nie udało się pobrać gatunków.');
      });

    axios.get('http://localhost:8080/api/platforms', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setPlatforms(res.data))
      .catch(err => {
        console.error(err);
        setError('Nie udało się pobrać platform.');
      });
  }, [token]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!token) {
      setError('Brak tokenu autoryzacji. Zaloguj się ponownie.');
      return;
    }

    const newGame = {
      title,
      releaseDate: releaseDate ? new Date(releaseDate).toISOString() : null,
      developer,
      publisher,
      description,
      genreIds: selectedGenres,
      platformIds: selectedPlatforms,
    };

    axios.post('http://localhost:8080/api/games', newGame, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(() => {
        setMessage('Gra dodana pomyślnie!');
        setError(null);
        setTitle('');
        setReleaseDate('');
        setDeveloper('');
        setPublisher('');
        setDescription('');
        setSelectedGenres([]);
        setSelectedPlatforms([]);
      })
      .catch(err => {
        console.error(err);
        setError('Wystąpił błąd podczas dodawania gry.');
        setMessage('');
      });
  };

  // Obsługa checkboxów gatunków
  const handleGenreChange = (id) => {
    setSelectedGenres(prev =>
      prev.includes(id) ? prev.filter(g => g !== id) : [...prev, id]
    );
  };

  // Obsługa checkboxów platform
  const handlePlatformChange = (id) => {
    setSelectedPlatforms(prev =>
      prev.includes(id) ? prev.filter(p => p !== id) : [...prev, id]
    );
  };

  if (error) {
    return (
      <div className="max-w-xl mx-auto p-4">
        <h2 className="text-2xl font-bold mb-4 text-red-600">Błąd</h2>
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="max-w-xl mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4">Dodaj nową grę</h2>
      {message && <p className="mb-4 text-green-600">{message}</p>}
      <form onSubmit={handleSubmit}>
        <label className="block mb-2">
          Tytuł:
          <input
            type="text"
            value={title}
            onChange={e => setTitle(e.target.value)}
            required
            maxLength={100}
            className="border p-2 w-full"
          />
        </label>

        <label className="block mb-2">
          Data wydania:
          <input
            type="date"
            value={releaseDate}
            onChange={e => setReleaseDate(e.target.value)}
            className="border p-2 w-full"
          />
        </label>

        <label className="block mb-2">
          Twórca:
          <input
            type="text"
            value={developer}
            onChange={e => setDeveloper(e.target.value)}
            required
            maxLength={100}
            className="border p-2 w-full"
          />
        </label>

        <label className="block mb-2">
          Wydawca:
          <input
            type="text"
            value={publisher}
            onChange={e => setPublisher(e.target.value)}
            required
            maxLength={100}
            className="border p-2 w-full"
          />
        </label>

        <label className="block mb-2">
          Opis:
          <textarea
            value={description}
            onChange={e => setDescription(e.target.value)}
            maxLength={1000}
            className="border p-2 w-full"
          />
        </label>

        <fieldset className="mb-4">
          <legend className="font-semibold mb-2">Gatunki:</legend>
          {genres.map(genre => (
            <label key={genre.id} className="block">
              <input
                type="checkbox"
                checked={selectedGenres.includes(genre.id)}
                onChange={() => handleGenreChange(genre.id)}
              />{' '}
              {genre.name}
            </label>
          ))}
        </fieldset>

        <fieldset className="mb-4">
          <legend className="font-semibold mb-2">Platformy:</legend>
          {platforms.map(platform => (
            <label key={platform.id} className="block">
              <input
                type="checkbox"
                checked={selectedPlatforms.includes(platform.id)}
                onChange={() => handlePlatformChange(platform.id)}
              />{' '}
              {platform.name}
            </label>
          ))}
        </fieldset>

        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Dodaj grę
        </button>
      </form>
    </div>
  );
}

export default AddGameForm;
