import React, { useEffect, useState } from 'react';
import axios from 'axios';

function UserList() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null);

  const token = (() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      try {
        return JSON.parse(storedUser).token;
      } catch {
        return null;
      }
    }
    return null;
  })();

  useEffect(() => {
    if (!token) {
      setError('Brak tokenu autoryzacji. Zaloguj się ponownie.');
      return;
    }

    axios.get('http://localhost:8080/api/admin/users', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(res => {
        setUsers(res.data);
        setError(null);
      })
      .catch(err => {
        console.error('Błąd podczas pobierania użytkowników', err);
        setError('Nie udało się pobrać użytkowników. Sprawdź uprawnienia.');
      });
  }, [token]);

  const handleDelete = (userId) => {
    if (!token) {
      setError('Brak tokenu autoryzacji. Zaloguj się ponownie.');
      return;
    }

    axios.delete(`http://localhost:8080/api/admin/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(() => setUsers(users.filter(user => user.id !== userId)))
      .catch(err => {
        console.error('Błąd podczas usuwania użytkownika', err);
        setError('Nie udało się usunąć użytkownika.');
      });
  };

  if (error) {
    return (
      <div className="container mx-auto p-6">
        <h2 className="text-2xl font-bold mb-4 text-red-600">Błąd</h2>
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">Użytkownicy</h2>
      <ul className="space-y-2">
        {users.map(user => (
          <li key={user.id} className="flex justify-between items-center border p-2 rounded">
            <span>{user.username}</span>
            <div className="space-x-2">
              {user.username !== 'admin' && (
                <button
                  onClick={() => handleDelete(user.id)}
                  className="bg-red-500 text-white px-3 py-1 rounded"
                >
                  Usuń
                </button>
              )}
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default UserList;
