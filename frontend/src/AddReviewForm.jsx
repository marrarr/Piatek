// src/AddReviewForm.jsx
import React, { useState } from 'react';
import axios from 'axios';

function AddReviewForm({ gameId, user, onReviewAdded }) {
  // --- lokalny stan formularza ---
  const [rating, setRating] = useState(1);
  const [reviewText, setReviewText] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // --- walidacja pomocnicza ---
  const validate = () => {
    //if (!user?.id) return 'Musisz być zalogowany, aby dodać recenzję.';
    if (rating < 1 || rating > 10) return 'Ocena musi być od 1 do 10.';
    if (!reviewText.trim()) return 'Treść recenzji nie może być pusta.';
    if (reviewText.length > 2000) return 'Recenzja może mieć maksymalnie 2000 znaków.';
    return null;
  };

  // --- wysyłka formularza ---
  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationError = validate();
    if (validationError) {
      setError(validationError);
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const { data: newReview } = await axios.post(
        'http://localhost:8080/api/reviews',
        {
          //userId: user.id,
          userId: "2",
          gameId,
          rating,
          reviewText,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            // Jeżeli używasz JWT/cookie, podaj auth header
            // Authorization: `Bearer ${token}`,
          },
        },
      );

      // wyczyść formularz
      setRating(1);
      setReviewText('');

      // odśwież listę recenzji w GamePage (lub gdziekolwiek wywołasz)
      onReviewAdded?.(newReview);
    } catch (err) {
      if (err.response?.data?.message) {
        setError(err.response.data.message);
      } else {
        setError(err.message || 'Nie udało się dodać recenzji.');
      }
    } finally {
      setLoading(false);
    }
  };

  // --- render ---
  return (
    <form onSubmit={handleSubmit} className="max-w-md mx-auto bg-white p-6 rounded shadow">
      <h2 className="text-xl font-bold mb-4">Dodaj recenzję</h2>

      {error && <p className="text-red-600 mb-4 break-words">{error}</p>}

      <label className="block mb-3 font-semibold">
        Ocena (1–10):
        <input
          type="number"
          min="1"
          max="10"
          step="1"
          value={rating}
          onChange={(e) => setRating(Number(e.target.value))}
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
        className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded disabled:opacity-50"
      >
        {loading ? 'Dodawanie…' : 'Dodaj recenzję'}
      </button>
    </form>
  );
}

export default AddReviewForm;
