export const SET_USER_DATA = 'SET_USER_DATA';
export const REMOVE_USER_DATA = 'REMOVE_USER_DATA';

export const setUserData = (token, id, userType) => {
  return {
    type: SET_USER_DATA,
    payload: {
      token,
      id,
      userType,
    },
  };
};

export const removeUserData = () => {
  return {
    type: REMOVE_USER_DATA
  };
};