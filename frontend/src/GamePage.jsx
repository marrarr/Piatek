import { useParams, useNavigate } from 'react-router-dom';
import React from 'react';

function GamePage({ user }) {
  const { id } = useParams();
  const navigate = useNavigate();

  const gameData = {
    1: {
      title: "Wiedźmin 3: Dziki Gon",
      image: "https://via.placeholder.com/600x400?text=Wiedźmin+3",
      description: "Epicka gra RPG osadzona w otwartym świecie fantasy, oparta na książkach Andrzeja Sapkowskiego.",
      rating: 9.5,
      reviews: [
        { user: "Jan K.", text: "Niesamowita fabuła i świat!", rating: 10 },
        { user: "Anna P.", text: "Świetna gra, ale wymaga mocnego PC.", rating: 9 },
      ],
    },
    2: {
      title: "Cyberpunk 2077",
      image: "https://via.placeholder.com/600x400?text=Cyberpunk+2077",
      description: "Futurystyczna gra RPG z otwartym światem w Night City.",
      rating: 8.0,
      reviews: [
        { user: "Marek Z.", text: "Wciągająca, ale ma bugi.", rating: 7 },
      ],
    },
    3: {
      title: "Elden Ring",
      image: "https://via.placeholder.com/600x400?text=Elden+Ring",
      description: "Gra action RPG od FromSoftware z otwartym światem fantasy.",
      rating: 9.2,
      reviews: [
        { user: "Kasia L.", text: "Wyzwanie na każdym kroku!", rating: 9 },
      ],
    },
  };

  const game = gameData[id] || { title: "Gra nie znaleziona", description: "Brak danych", rating: 0, reviews: [] };

  // Przekierowanie do formularza dodawania recenzji
  const handleAddReview = () => {
    navigate(`/game/${id}/add-review`);
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">{game.title}</h1>
      <img src={game.image} alt={game.title} className="w-full max-w-2xl mx-auto rounded mb-4" />
      <p className="text-gray-700 mb-4">{game.description}</p>
      <p className="text-lg font-semibold mb-4">Ocena: {game.rating}/10</p>

      {/* Przycisk widoczny tylko dla zalogowanego użytkownika */}
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
        {game.reviews.length > 0 ? (
          game.reviews.map((review, index) => (
            <div key={index} className="bg-white p-4 rounded shadow">
              <p className="font-semibold">{review.user}</p>
              <p>{review.text}</p>
              <p className="text-sm text-gray-600">Ocena: {review.rating}/10</p>
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
