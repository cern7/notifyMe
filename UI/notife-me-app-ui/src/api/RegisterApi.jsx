import axios from "axios";
const BASE_API = "http://localhost:8080/api/auth/register";

const postMethodConfig = {
  header: {
    'Content-type': 'application/json; charset=UTF-8',
  },
};

export const registerNewCustomer = (userDTO) => axios.post(`${BASE_API}`, userDTO, postMethodConfig);