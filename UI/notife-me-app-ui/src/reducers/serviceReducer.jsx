import { SET_AVAILABLE_SERVICES } from "../actions/availableServices";

const initialState = {
  services: [],
};

function serviceReducer(state = initialState, action) {

  switch (action.type) {
    case SET_AVAILABLE_SERVICES:
      const existingIndex = state.services.find((service) => service.iid === action.payload.iid);
      if (!existingIndex) {
        return {
          ...state,
          services: [
            ...state.services,
            {
              serviceName: action.payload.serviceName,
              description: action.payload.description,
              price: action.payload.price,
              duration: action.payload.duration,
              availability: action.payload.availability,
              category: action.payload.category,
              imageUrl: action.payload.imageUrl,
              iid: action.payload.iid,
            },
          ],
        };
      } else {
        return state
      }
    default:
      return state;
  }
}

export default serviceReducer;


