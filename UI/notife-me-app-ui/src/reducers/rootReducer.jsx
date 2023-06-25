import { combineReducers } from "@reduxjs/toolkit";
import serviceReducer from "./serviceReducer";

const rootReducer = combineReducers({
  services: serviceReducer,
});

export default rootReducer;