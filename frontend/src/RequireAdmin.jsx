import React from 'react';
import { Navigate } from 'react-router-dom';

const RequireAdmin = ({ user, children }) => {
  if (!user || user.username !== 'admin') {
    return <Navigate to="/" replace />;
  }
  return children;
};

export default RequireAdmin;
