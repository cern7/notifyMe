import axios from "axios";
const BOOKING_API = "http://localhost:8080/api/booking/";

// import Cookies from 'js-cookie';

const config = {
  headers: {
    'Content-type': 'application/json; charset=UTF-8',
    'Authorization': localStorage.getItem('token')
  },
};


export const getAllCustomerBookings = () => axios.get(`${BOOKING_API}customer/all`, config);
export const getAllBookings = () => axios.get(`${BOOKING_API}all`, config);