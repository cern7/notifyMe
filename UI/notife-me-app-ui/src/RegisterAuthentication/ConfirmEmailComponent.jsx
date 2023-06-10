import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { confirmEmail } from '../api/ConfirmEmailApi';


const ConfirmEmailComponent = () => {

  const { token } = useParams();
  const [confirmResult, setConfirmResult] = useState(null);

  useEffect(() => {
    const confirmEmailToken = async () => {
      try {
        const response = await confirmEmail(token);
        setConfirmResult(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    confirmEmailToken();
  }, [token]);

  return (
    <div>
      {confirmResult ? (
        <div>
          <h2>Email confirmed!</h2>
          <p>{confirmResult}</p>
          <Link to='/login'>
            <button>Go to Login</button>
          </Link>
        </div>
      ) : (
        <div>
          <h2>Confirming email...</h2>
        </div>
      )}
    </div>
  );
}

export default ConfirmEmailComponent;