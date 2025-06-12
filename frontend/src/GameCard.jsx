import { Link } from 'react-router-dom';


function GameCard({ id, title, image, rating }) {
  return (
    <Link to={`/game/${id}`} className="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition">
      <h3 className="text-xl font-semibold mt-2">{title}</h3>
      <p className="text-gray-600">Ocena: {rating}/10</p>
    </Link>
  );
}

export default GameCard;