import axios from "axios";
const SERVICE_API = "http://localhost:8080/api/service/all";
const BOOKING_API = "http://localhost:8080/api/service/booking";
const GET_EMPLOYEES_OF_SERVICE = "http://localhost:8080/api/service/allEmployeesToService/"

import Cookies from 'js-cookie';

const config = {
  headers: {
    'Content-type': 'application/json; charset=UTF-8',
    'Authorization': Cookies.get('jwtToken')
  },
};

export const getAllAvailServices = () => axios.get(`${SERVICE_API}`, config);


export const getAllEmployeesByServiceId = (serviceId) => axios.get(`${GET_EMPLOYEES_OF_SERVICE}${serviceId}`, config);


export const bookService = (bookingRequestDTO) => 
axios.post(`${BOOKING_API}`, bookingRequestDTO, config);

