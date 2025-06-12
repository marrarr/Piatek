import React, { useEffect, useState } from 'react';
import axios from 'axios';

function GenreList() {
  const [genres, setGenres] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/genres')
      .then(res => setGenres(res.data))
      .catch(err => console.error('Błąd podczas pobierania gatunków', err));
  }, []);

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">Lista gatunków</h2>
      <ul className="space-y-2">
        {genres.map(genre => (
          <li key={genre.id} className="border p-2 rounded">
            {genre.name}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GenreList;
