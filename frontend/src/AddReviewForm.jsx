import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AddReviewForm({ gameId, onReviewAdded }) {
  const [rating, setRating] = useState(1);
  const [reviewText, setReviewText] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const getUserData = () => {
    try {
      const storedUser = localStorage.getItem('user');
      if (!storedUser) return null;
      return JSON.parse(storedUser);
    } catch (error) {
      console.error('Error parsing user data:', error);
      return null;
    }
  };

  const validate = () => {
    const user = getUserData();
    if (!user || !user.id) return 'Musisz być zalogowany, aby dodać recenzję.';
    if (rating < 1 || rating > 10) return 'Ocena musi być od 1 do 10.';
    if (!reviewText.trim()) return 'Treść recenzji nie może być pusta.';
    if (reviewText.length > 2000) return 'Recenzja może mieć maksymalnie 2000 znaków.';
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const validationError = validate();
    if (validationError) {
      setError(validationError);
      return;
    }

    const user = getUserData();
    if (!user || !user.id || !user.token) {
      setError('Błąd autoryzacji. Zaloguj się ponownie.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await axios.post(
        `http://localhost:8080/api/reviews`,
        {
          userId: user.id,  // Wysyłamy userId zgodnie z DTO
          gameId,
          rating,
          reviewText,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.token}`,
          },
        }
      );

      setRating(1);
      setReviewText('');
      
      if (onReviewAdded) {
        onReviewAdded(response.data);
      }
      
      navigate(`/game/${gameId}`, { replace: true });
    } catch (err) {
      console.error('Error submitting review:', err);
      setError(
        err.response?.data?.message || 
        err.message || 
        'Nie udało się dodać recenzji. Spróbuj ponownie.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-gray-800">Dodaj recenzję</h2>
      
      {error && (
        <div className="mb-4 p-3 bg-red-100 text-red-700 rounded-md">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="rating" className="block text-sm font-medium text-gray-700 mb-1">
            Ocena (1-10)
          </label>
          <input
            id="rating"
            type="number"
            min="1"
            max="10"
            value={rating}
            onChange={(e) => setRating(parseInt(e.target.value) || 1)}
            className="w-20 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label htmlFor="reviewText" className="block text-sm font-medium text-gray-700 mb-1">
            Treść recenzji
          </label>
          <textarea
            id="reviewText"
            value={reviewText}
            onChange={(e) => setReviewText(e.target.value)}
            maxLength={2000}
            rows={6}
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            required
          />
          <p className="mt-1 text-xs text-gray-500">
            {reviewText.length}/2000 znaków
          </p>
        </div>

        <div className="flex items-center space-x-4">
          <button
            type="submit"
            disabled={loading}
            className="px-4 py-2 bg-blue-600 text-white font-medium rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {loading ? 'Wysyłanie...' : 'Dodaj recenzję'}
          </button>
          
          <button
            type="button"
            onClick={() => navigate(`/game/${gameId}`)}
            className="px-4 py-2 border border-gray-300 text-gray-700 font-medium rounded-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
          >
            Anuluj
          </button>
        </div>
      </form>
    </div>
  );
}

export default AddReviewForm;