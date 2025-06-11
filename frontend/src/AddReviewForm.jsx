import React, { useState } from 'react';

function AddReviewForm({ gameId, user, onReviewAdded }) {
  const [rating, setRating] = useState(1);
  const [reviewText, setReviewText] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Prosta walidacja
    if (rating < 1 || rating > 10) {
      setError('Ocena musi być od 1 do 10.');
      return;
    }
    if (reviewText.trim() === '') {
      setError('Treść recenzji nie może być pusta.');
      return;
    }
    if (reviewText.length > 2000) {
      setError('Recenzja może mieć maksymalnie 2000 znaków.');
      return;
    }

    setError(null);
    setLoading(true);

    try {
      // Przykład wysyłki do API (dopasuj URL i dane do swojego backendu)
      const response = await fetch('http://localhost:8080/api/reviews', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // jeśli używasz tokena autoryzacji to dodaj go w nagłówku
          //'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({
          gameId,
          userId: user.id,
          rating,
          reviewText,
        }),
      });

      if (!response.ok) {
        throw new Error('Błąd przy dodawaniu recenzji');
      }

      const newReview = await response.json();

      // Reset formularza
      setRating(1);
      setReviewText('');

      // Wywołanie callbacka, np. do odświeżenia listy recenzji
      if (onReviewAdded) onReviewAdded(newReview);

    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-md mx-auto bg-white p-6 rounded shadow">
      <h3 className="text-xl font-bold mb-4">Dodaj recenzję</h3>

      {error && <p className="text-red-600 mb-4">{error}</p>}

      <label className="block mb-2 font-semibold">
        Ocena (1-10):
        <input
          type="number"
          min="1"
          max="10"
          value={rating}
          onChange={(e) => setRating(parseInt(e.target.value))}
          className="w-full border border-gray-300 rounded px-3 py-2 mt-1"
          required
        />
      </label>

      <label className="block mb-4 font-semibold">
        Treść recenzji:
        <textarea
          value={reviewText}
          onChange={(e) => setReviewText(e.target.value)}
          maxLength={2000}
          rows={5}
          className="w-full border border-gray-300 rounded px-3 py-2 mt-1"
          required
        />
      </label>

      <button
        type="submit"
        disabled={loading}
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
      >
        {loading ? 'Dodawanie...' : 'Dodaj recenzję'}
      </button>
    </form>
  );
}

export default AddReviewForm;
