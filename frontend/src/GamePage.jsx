import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

/**
 * GamePage – szczegóły gry + recenzje
 *
 * 🔄 Ładuje dwa endpointy:
 *   1. GET http://localhost:8080/api/games/:id → GameResponseDetailsDTO
 *   2. GET http://localhost:8080/api/reviews/game/:id → [ReviewResponseDTO]
 *
 * ⚙️ Bez żadnych aliasów (@) i bibliotek zewnętrznych – działa w czystym CRA/Vite
 */
function GamePage({ user }) {
  const { id } = useParams();
  const [game, setGame] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [gameRes, reviewsRes] = await Promise.all([
          fetch(`http://localhost:8080/api/games/${id}`),
          fetch(`http://localhost:8080/api/reviews/game/${id}`)
        ]);

        if (!gameRes.ok) throw new Error('Nie udało się pobrać danych gry');
        if (!reviewsRes.ok) throw new Error('Nie udało się pobrać recenzji');

        const gameData = await gameRes.json();
        const reviewsData = await reviewsRes.json();

        setGame(gameData);
        setReviews(reviewsData);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  // Funkcja usuwająca recenzję - dostępna tylko dla admina
  const handleDeleteReview = async (reviewId) => {
    if (!window.confirm('Czy na pewno chcesz usunąć tę recenzję?')) return;

    try {
      const res = await fetch(`http://localhost:8080/api/reviews/${reviewId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          // Możesz dodać nagłówki autoryzacyjne jeśli wymagane, np. token
        },
      });

      if (!res.ok) {
        throw new Error('Nie udało się usunąć recenzji');
      }

      // Usuń recenzję z lokalnego stanu, by odświeżyć listę
      setReviews((prev) => prev.filter((r) => r.id !== reviewId));
    } catch (err) {
      alert(err.message);
    }
  };

  if (loading) {
    return <p className="text-center text-gray-600 mt-8">Ładowanie...</p>;
  }

  if (error) {
    return <p className="text-center text-red-600 mt-8">Błąd: {error}</p>;
  }

  return (
    <div className="container mx-auto p-4">
      {/* Szczegóły gry */}
      <div className="mb-6 flex flex-col items-center">
        <h1 className="text-4xl font-bold mb-2 text-center break-words">{game.title}</h1>

        {/* Wyświetl okładkę tylko jeśli backend ją zwraca */}
        {game.imageUrl && (
          <img
            src={game.imageUrl}
            alt={game.title}
            className="w-full max-w-md rounded-2xl shadow-lg object-cover"
          />
        )}

        {game.description && (
          <p className="mt-4 text-lg text-gray-700 whitespace-pre-wrap text-center">
            {game.description}
          </p>
        )}

        <div className="mt-4 text-xl font-semibold">
          Ocena: {Number(game.averageRating || 0).toFixed(1)}/10
        </div>

        {user && (
          <Link
            to={`/game/${id}/add-review`}
            className="mt-4 inline-block bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-xl transition-colors"
          >
            Dodaj recenzję
          </Link>
        )}
      </div>

      {/* Recenzje */}
      <h2 className="text-2xl font-bold mb-4">Recenzje</h2>

      {reviews.length === 0 ? (
        <p className="text-gray-600">Brak recenzji dla tej gry.</p>
      ) : (
        <div className="space-y-4">
          {reviews.map((review) => (
            <div
              key={review.id}
              className="p-4 border rounded-2xl shadow-sm bg-white/60 backdrop-blur-sm"
            >
              <div className="flex justify-between items-center">
                <span className="font-semibold">Użytkownik #{review.userId}</span>
                <span className="text-sm text-yellow-600 font-medium">
                  {review.rating}/10
                </span>
              </div>
              <p className="mt-2 text-gray-700 whitespace-pre-wrap">
                {review.reviewText}
              </p>
              <p className="mt-2 text-gray-700 whitespace-pre-wrap">{review.reviewText}</p>

              {/* Przycisk Usuń widoczny tylko dla admina */}
              {user?.username === 'admin' && (
                <button
                  onClick={() => handleDeleteReview(review.id)}
                  className="mt-2 text-red-600 hover:text-red-800 font-semibold"
                >
                  Usuń
                </button>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default GamePage;