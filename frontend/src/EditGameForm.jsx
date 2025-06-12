import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function EditGameForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [game, setGame] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/games/${id}`)
      .then((res) => {
        if (!res.ok) throw new Error('Nie udało się pobrać danych gry');
        return res.json();
      })
      .then((data) => setGame(data))
      .catch((err) => setError(err.message));
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setGame((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`http://localhost:8080/api/games/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(game)
      });

      if (!res.ok) throw new Error('Błąd podczas zapisu danych');

      navigate(`/game/${id}`);
    } catch (err) {
      setError(err.message);
    }
  };

  if (error) return <p className="text-red-600 mt-4">{error}</p>;
  if (!game) return <p className="mt-4 text-gray-600">Ładowanie danych gry...</p>;

  return (
    <div className="container mx-auto max-w-xl p-6">
      <h2 className="text-2xl font-bold mb-4">Edytuj grę</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block font-semibold mb-1">Tytuł</label>
          <input
            type="text"
            name="title"
            value={game.title || ''}
            onChange={handleChange}
            className="w-full border p-2 rounded"
            required
          />
        </div>

        <div>
          <label className="block font-semibold mb-1">Opis</label>
          <textarea
            name="description"
            value={game.description || ''}
            onChange={handleChange}
            className="w-full border p-2 rounded"
            rows="5"
          />
        </div>

        <div>
          <label className="block font-semibold mb-1">Twórca</label>
          <input
            type="text"
            name="developer"
            value={game.developer || ''}
            onChange={handleChange}
            className="w-full border p-2 rounded"
          />
        </div>

        <div>
          <label className="block font-semibold mb-1">Wydawca</label>
          <input
            type="text"
            name="publisher"
            value={game.publisher || ''}
            onChange={handleChange}
            className="w-full border p-2 rounded"
          />
        </div>

        <div>
          <label className="block font-semibold mb-1">Data wydania</label>
          <input
            type="date"
            name="releaseDate"
            value={game.releaseDate?.slice(0, 10) || ''}
            onChange={handleChange}
            className="w-full border p-2 rounded"
          />
        </div>

        <button
          type="submit"
          className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded"
        >
          Zapisz zmiany
        </button>
      </form>
    </div>
  );
}

export default EditGameForm;
