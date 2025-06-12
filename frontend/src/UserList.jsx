import React, { useEffect, useState } from 'react';
import axios from 'axios';

function UserList() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/users')
      .then(res => setUsers(res.data))
      .catch(err => console.error('Błąd podczas pobierania użytkowników', err));
  }, []);

  const handleDelete = (userId) => {
    axios.delete(`http://localhost:8080/api/users/${userId}`)
      .then(() => setUsers(users.filter(user => user.id !== userId)))
      .catch(err => console.error('Błąd podczas usuwania użytkownika', err));
  };

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">Użytkownicy</h2>
      <ul className="space-y-2">
        {users.map(user => (
          <li key={user.id} className="flex justify-between items-center border p-2 rounded">
            <span>{user.username}</span>
            <button onClick={() => handleDelete(user.id)} className="bg-red-500 text-white px-3 py-1 rounded">Usuń</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default UserList;
