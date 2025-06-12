import { Link, useNavigate } from 'react-router-dom';

function NavBar({ user, onLogout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('user');
    onLogout();
    navigate('/');
  };

  return (
    <nav className="bg-blue-600 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold">Portal Gier</Link>
        <div className="space-x-4 flex items-center">
          <Link to="/" className="hover:underline">Strona Główna</Link>

          {/* Panel Admina widoczny tylko dla użytkownika 'admin' */}
          {user?.username === 'admin' && (
            <Link
              to="/admin"
              className="ml-4 bg-yellow-500 text-black px-3 py-1 rounded hover:bg-yellow-600"
            >
              Panel Admina
            </Link>
          )}

          {!user && (
            <>
              <Link to="/login" className="hover:underline ml-4">Logowanie</Link>
              <Link to="/register" className="hover:underline ml-4">Rejestracja</Link>
            </>
          )}

          {user && (
            <>
              <span className="ml-4">Witaj, {user.username}</span>
              <button
                onClick={handleLogout}
                className="ml-4 bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
              >
                Wyloguj
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

export default NavBar;
