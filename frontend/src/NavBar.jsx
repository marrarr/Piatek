import { Link } from 'react-router-dom';

function NavBar({ user }) {
  return (
    <nav className="bg-blue-600 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold">Portal Gier</Link>
        <div className="space-x-4">
          <Link to="/" className="hover:underline">Strona Główna</Link>
          {!user && <Link to="/login" className="hover:underline">Logowanie</Link>}
          {user && <span>Witaj, {user.username}</span>}
        </div>
      </div>
    </nav>
  );
}

export default NavBar;
