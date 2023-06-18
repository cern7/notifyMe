import axios from "axios";
const BASE_API = "http://localhost:8080/api/auth/login";

const postMethodConfig = {
  headers: {
    'Content-type': 'application/json; charset=UTF-8',
    'withCredentials': true,
  },
};

export const loginRequest = (loginDTO) => axios.post(`${BASE_API}`, loginDTO, postMethodConfig);