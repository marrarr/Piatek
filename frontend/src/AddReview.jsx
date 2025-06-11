import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AddReviewForm from './AddReviewForm.jsx';

function AddReview({ user }) {
  const { id } = useParams(); // id gry
  const navigate = useNavigate();

  // Funkcja wywoływana po udanym dodaniu recenzji
  const onReviewAdded = () => {
    alert('Recenzja została dodana!');
    navigate(`/game/${id}`); // wracamy do strony gry
  };

  if (!user) {
    return <p>Musisz być zalogowany, aby dodać recenzję.</p>;
  }

  return (
    <div className="container mx-auto p-4">
      <AddReviewForm gameId={id} user={user} onReviewAdded={onReviewAdded} />
    </div>
  );
}

export default AddReview;
