import React, { useState } from 'react';
import axios from 'axios';

function PlatformForm({ onAdded }) {
  const [name, setName] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/platforms', { name });
      setName('');
      setError(null);
      if (onAdded) onAdded(response.data);
    } catch (err) {
      setError('Błąd podczas dodawania platformy');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mb-6">
      <h3 className="text-xl font-semibold mb-2">Dodaj nową platformę</h3>
      <input
        type="text"
        placeholder="Nazwa platformy"
        value={name}
        onChange={e => setName(e.target.value)}
        required
        className="border rounded px-2 py-1 mr-2"
      />
      <button type="submit" className="bg-green-600 text-white px-4 py-1 rounded">
        Dodaj
      </button>
      {error && <p className="text-red-600 mt-2">{error}</p>}
    </form>
  );
}

export default PlatformForm;
