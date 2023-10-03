import axios from "axios";
const SERVICE_API = `${import.meta.env.VITE_BACKEND_API_URL}/api/service/all`;
const BOOKING_API = `${import.meta.env.VITE_BACKEND_API_URL}/api/service/booking`;
const GET_EMPLOYEES_OF_SERVICE = `${import.meta.env.VITE_BACKEND_API_URL}/api/service/allEmployeesToService/`

import Cookies from 'js-cookie';

const config = {
  headers: {
    'Content-type': 'application/json; charset=UTF-8',
    'Authorization': localStorage.getItem('token')
  },
};

export const getAllAvailServices = () => axios.get(`${SERVICE_API}`, config);


export const getAllEmployeesByServiceId = (serviceId) => axios.get(`${GET_EMPLOYEES_OF_SERVICE}${serviceId}`, config);


export const bookService = (bookingRequestDTO) =>
  axios.post(`${BOOKING_API}`, bookingRequestDTO, config);

