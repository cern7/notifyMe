import { combineReducers } from "@reduxjs/toolkit";
import serviceReducer from "./serviceReducer";
import bookingReducer from "./bookingReducer"

const rootReducer = combineReducers({
  services: serviceReducer,
  bookings: bookingReducer,
});

export default rootReducer;