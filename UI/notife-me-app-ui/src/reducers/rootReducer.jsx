import { combineReducers } from "@reduxjs/toolkit";
import userReducer from "./userReducer";
import serviceReducer from "./serviceReducer";

const rootReducer = combineReducers({
  user: userReducer,
  services: serviceReducer,
});

export default rootReducer;