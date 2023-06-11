import { SET_USER_DATA, REMOVE_USER_DATA } from "../actions/userToken";

const intialState = {
  token: null,
  id: null,
  userType: null,
};

function userReducer(state = intialState, action) {
  switch (action.type) {
    case SET_USER_DATA:
      return {
        ...state,
        token: action.payload.token,
        id: action.payload.id,
        userType: action.payload.userType,
      };
    case REMOVE_USER_DATA:
      return intialState;
    default:
      return state
  }
}

export default userReducer;