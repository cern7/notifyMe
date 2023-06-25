
import { LOAD_BOOKINGS } from "../actions/myBookings";

const initialState = {
  bookings: [],
};

function bookingReducer(state = initialState, action) {
  switch (action.type) {
    case LOAD_BOOKINGS:
      const existingIndex = state.bookings.find((booking) => booking.iid === action.payload.iid);
      if (!existingIndex) {
        return {
          ...state,
          bookings: [
            ...state.bookings,
            {
              iid: action.payload.iid,
              startDateTime: action.payload.startDateTime,
              endDateTime: action.payload.endDateTime,
              bookingStatus: action.payload.bookingStatus,
              paymentStatus: action.payload.paymentStatus,
              notes: action.payload.notes,
              employee: action.payload.employee,
              service: {
                serviceName: action.payload.service.serviceName,
                description: action.payload.service.description,
                price: action.payload.service.price,
                duration: action.payload.service.duration,
                availability: action.payload.service.availability,
                category: action.payload.service.category,
                imageUrl: action.payload.service.imageUrl,
                iid: action.payload.service.iid,
              }
            }
          ]
        }
      } else {
        return state;
      }
    default:
      return state;
  }
}
export default bookingReducer;