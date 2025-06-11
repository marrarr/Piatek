import React, { useEffect, useState } from 'react';
import GameCard from './GameCard.jsx';

function HomePage() {
  const [games, setGames] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchGames = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/games');
        if (!response.ok) {
          throw new Error('Nie udało się pobrać listy gier');
        }
        const data = await response.json();
        setGames(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchGames();
  }, []);

  if (loading) {
    return <p className="text-center text-gray-600 mt-8">Ładowanie gier...</p>;
  }

  if (error) {
    return <p className="text-center text-red-600 mt-8">Błąd: {error}</p>;
  }

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-6 text-center">Najlepsze Gry</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {games.map(game => (
          <GameCard
            key={game.id}
            id={game.id}
            title={game.title}
            image={game.image}
            rating={game.averageRating}
          />
        ))}
      </div>
    </div>
  );
}

export default HomePage;
