import React, { useEffect, useState } from 'react';
import axios from 'axios';

function PlatformList() {
  const [platforms, setPlatforms] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/platforms')
      .then(res => setPlatforms(res.data))
      .catch(err => console.error('Błąd podczas pobierania platform', err));
  }, []);

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">Lista platform</h2>
      <ul className="space-y-2">
        {platforms.map(platform => (
          <li key={platform.id} className="border p-2 rounded">
            {platform.name}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default PlatformList;
