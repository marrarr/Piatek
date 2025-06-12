import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';


function GamePage({ user }) {
  const { id } = useParams();
  const [game, setGame] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();


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

        // Konwersja Set na tablicę, jeśli to konieczne
        if (gameData.genres && !Array.isArray(gameData.genres)) {
          gameData.genres = Array.from(gameData.genres);
        }
        if (gameData.platforms && !Array.isArray(gameData.platforms)) {
          gameData.platforms = Array.from(gameData.platforms);
        }

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

  const handleDeleteReview = async (reviewId) => {
    if (!window.confirm('Czy na pewno chcesz usunąć tę recenzję?')) return;

    try {
      const res = await fetch(`http://localhost:8080/api/reviews/${reviewId}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
      });

      if (!res.ok) throw new Error('Nie udało się usunąć recenzji');

      setReviews((prev) => prev.filter((r) => r.id !== reviewId));
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteGame = async () => {
  if (!window.confirm('Czy na pewno chcesz usunąć tę grę?')) return;

  try {
    const res = await fetch(`http://localhost:8080/api/games/${id}`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
    });

    if (!res.ok) throw new Error('Nie udało się usunąć gry');

    alert('Gra została usunięta.');
    navigate('/'); // lub np. navigate('/games');
  } catch (err) {
    alert(err.message);
  }
};


  if (loading) return <p className="text-center text-gray-600 mt-8">Ładowanie...</p>;
  if (error) return <p className="text-center text-red-600 mt-8">Błąd: {error}</p>;
  if (!game) return <p className="text-center text-gray-600 mt-8">Gra nie znaleziona</p>;

  return (
    <div className="container mx-auto p-4">
      <div className="mb-6 flex flex-col items-center text-center">
        <h1 className="text-4xl font-bold mb-2 break-words">{game.title}</h1>

        {game.description && (
          <p className="mt-4 text-lg text-gray-700 whitespace-pre-wrap">
            {game.description}
          </p>
        )}

        <div className="mt-4 text-gray-800 space-y-1 text-base">
          {game.developer && <div><strong>Twórca:</strong> {game.developer}</div>}
          {game.publisher && <div><strong>Wydawca:</strong> {game.publisher}</div>}
          {game.releaseDate && (
            <div><strong>Data wydania:</strong> {new Date(game.releaseDate).toLocaleDateString()}</div>
          )}

          {game.genres && game.genres.length > 0 && (
            <div className="mt-2">
              <strong>Gatunki:</strong>
              <div className="flex flex-wrap gap-2 mt-1 justify-center">
                {game.genres.map((genre) => (
                  <span
                    key={genre.id}
                    className="inline-block bg-blue-100 text-blue-800 text-sm font-medium px-3 py-1 rounded-full"
                  >
                    {genre.name}
                  </span>
                ))}
              </div>
            </div>
          )}

          {game.platforms && game.platforms.length > 0 && (
            <div className="mt-2">
              <strong>Platformy:</strong>
              <div className="flex flex-wrap gap-2 mt-1 justify-center">
                {game.platforms.map((platform) => (
                  <span
                    key={platform.id}
                    className="inline-block bg-green-100 text-green-800 text-sm font-medium px-3 py-1 rounded-full"
                  >
                    {platform.name}
                  </span>
                ))}
              </div>
            </div>
          )}
        </div>

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

        {user?.username === 'admin' && (
  <>
    <Link
      to={`/game/${id}/edit`}
      className="mt-2 inline-block bg-yellow-500 hover:bg-yellow-600 text-white font-semibold py-2 px-4 rounded-xl transition-colors"
    >
      Edytuj grę
    </Link>
    <button
      onClick={handleDeleteGame}
      className="mt-2 ml-2 inline-block bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-xl transition-colors"
    >
      Usuń grę
    </button>
  </>
)}


      </div>

      <h2 className="text-2xl font-bold mb-4">Recenzje</h2>

      {reviews.length === 0 ? (
        <p className="text-gray-600">Brak recenzji dla tej gry.</p>
      ) : (
        <div className="space-y-4">
          {reviews.map((review) => (
            <div key={review.id} className="p-4 border rounded-2xl shadow-sm bg-white/60 backdrop-blur-sm">
              <div className="flex justify-between items-center">
                <span className="font-semibold">Użytkownik #{review.userId}</span>
                <span className="text-sm text-yellow-600 font-medium">
                  {review.rating}/10
                </span>
              </div>
              <p className="mt-2 text-gray-700 whitespace-pre-wrap">
                {review.reviewText}
              </p>

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