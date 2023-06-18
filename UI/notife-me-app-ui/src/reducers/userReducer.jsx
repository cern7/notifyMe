import { SET_USER_DATA, REMOVE_USER_DATA } from "../actions/userToken";

const initialState = {
  id: null,
  userType: null,
};

function userReducer(state = initialState, action) {
  switch (action.type) {
    case SET_USER_DATA:
      return {
        ...state,
        id: action.payload.id,
        userType: action.payload.userType,
      };
    case REMOVE_USER_DATA:
      return initialState;
    default:
      return state
  }
}

export default userReducer;