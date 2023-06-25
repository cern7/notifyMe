export const SET_AVAILABLE_SERVICES = 'SET_AVAILABLE_SERVICES';
export const ADD_BOOKING = 'ADD_BOOKING';

export const setAvailableServices = (serviceName, description, price, duration, availability, category, imageUrl, iid) => {
  return {
    type: SET_AVAILABLE_SERVICES,
    payload: {
      serviceName: serviceName,
      description: description,
      price: price,
      duration: duration,
      availability: availability,
      category: category,
      imageUrl: imageUrl,
      iid: iid,
    }
  };
};
