import React, { useEffect, useState } from 'react';
import axios from 'axios';
import GameCard from './GameCard.jsx';

function HomePage() {
  const [games, setGames] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState(''); // stan dla wyszukiwarki

  useEffect(() => {
    const fetchGames = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/games');
        const gamesWithImages = response.data.map(game => {
          let imageUrl = null;
          if (game.image && game.image.data) {
            const base64 = btoa(
              new Uint8Array(game.image.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ''
              )
            );
            imageUrl = `data:${game.image.contentType};base64,${base64}`;
          }

          return {
            ...game,
            imageUrl,
          };
        });
        setGames(gamesWithImages);
      } catch (err) {
        setError('Błąd podczas pobierania gier: ' + err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchGames();
  }, []);

  // filtrujemy gry po tytule wg searchTerm (case insensitive)
  const filteredGames = games.filter(game =>
    game.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <p className="text-center text-gray-600 mt-8">Ładowanie gier...</p>;
  }

  if (error) {
    return <p className="text-center text-red-600 mt-8">Błąd: {error}</p>;
  }

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-6 text-center">Najlepsze Gry</h1>

      <div className="mb-6 flex justify-center">
        <input
          type="text"
          placeholder="Wyszukaj grę..."
          className="border rounded px-4 py-2 w-full max-w-md focus:outline-none focus:ring-2 focus:ring-blue-600"
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
      </div>

      {filteredGames.length === 0 ? (
        <p className="text-center text-gray-500">Brak wyników wyszukiwania.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredGames.map(game => (
            <GameCard
              key={game.id}
              id={game.id}
              title={game.title}
              image={game.imageUrl}
              rating={game.averageRating}
            />
          ))}
        </div>
      )}
    </div>
  );
}

export default HomePage;
