import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function GamePage({ user }) {
  const { id } = useParams();
  const navigate = useNavigate();

  const [game, setGame]   = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState(null);

  // pobranie danych gry
  useEffect(() => {
    const fetchGame = async () => {
      setLoading(true);
      setError(null);
      try {
        const { data } = await axios.get(`http://localhost:8080/api/games/${id}`);
        setGame(data);
      } catch (err) {
        setError(err.response?.data?.message || 'Nie udało się pobrać danych gry');
      } finally {
        setLoading(false);
      }
    };

    fetchGame();
  }, [id]);

  const handleAddReview = () => navigate(`/game/${id}/add-review`);

  if (loading)   return <p className="text-center mt-8">Ładowanie…</p>;
  if (error)     return <p className="text-center text-red-600 mt-8">Błąd: {error}</p>;
  if (!game)     return <p className="text-center mt-8">Gra nie znaleziona</p>;

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">{game.title}</h1>

      <img
        src={game.image}
        alt={game.title}
        className="w-full max-w-2xl mx-auto rounded mb-4"
      />

      <p className="text-gray-700 mb-4">{game.description}</p>
      <p className="text-lg font-semibold mb-4">
        Ocena: {game.averageRating ?? 'Brak ocen'}/10
      </p>

      {user && (
        <button
          onClick={handleAddReview}
          className="mb-6 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Dodaj recenzję
        </button>
      )}

      <h2 className="text-2xl font-bold mb-2">Recenzje</h2>
      <div className="space-y-4">
        {game.reviews && game.reviews.length > 0 ? (
          game.reviews.map((review, idx) => (
            <div key={idx} className="bg-white p-4 rounded shadow">
              <p className="font-semibold">{review.user}</p>
              <p>{review.text}</p>
              <p className="text-sm text-gray-600">
                Ocena: {review.rating}/10
              </p>
            </div>
          ))
        ) : (
          <p>Brak recenzji dla tej gry.</p>
        )}
      </div>
    </div>
  );
}

export default GamePage;
