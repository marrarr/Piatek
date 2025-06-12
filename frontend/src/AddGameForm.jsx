import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AddGameForm() {
  const [title, setTitle] = useState('');
  const [releaseDate, setReleaseDate] = useState('');
  const [developer, setDeveloper] = useState('');
  const [publisher, setPublisher] = useState('');
  const [description, setDescription] = useState('');
  const [genres, setGenres] = useState([]);       // Wszystkie dostępne gatunki do wyboru
  const [platforms, setPlatforms] = useState([]); // Wszystkie platformy do wyboru
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [selectedPlatforms, setSelectedPlatforms] = useState([]);
  const [message, setMessage] = useState('');

  // Pobierz listę gatunków i platform z backendu przy załadowaniu formularza
  useEffect(() => {
    axios.get('http://localhost:8080/api/genres')
      .then(res => setGenres(res.data))
      .catch(err => console.error(err));

    axios.get('http://localhost:8080/api/platforms')
      .then(res => setPlatforms(res.data))
      .catch(err => console.error(err));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    // Przygotuj obiekt do wysłania na backend
    const newGame = {
      title,
      releaseDate: releaseDate ? new Date(releaseDate).toISOString() : null,
      developer,
      publisher,
      description,
      genreIds: selectedGenres,
      platformIds: selectedPlatforms,
    };

    axios.post('http://localhost:8080/api/games', newGame)
      .then(res => {
        setMessage('Gra dodana pomyślnie!');
        // wyczyść formularz lub przekieruj gdzieś
        setTitle('');
        setReleaseDate('');
        setDeveloper('');
        setPublisher('');
        setDescription('');
        setSelectedGenres([]);
        setSelectedPlatforms([]);
      })
      .catch(err => {
        setMessage('Wystąpił błąd podczas dodawania gry.');
        console.error(err);
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

  return (
    <div className="max-w-xl mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4">Dodaj nową grę</h2>
      {message && <p className="mb-4">{message}</p>}
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
