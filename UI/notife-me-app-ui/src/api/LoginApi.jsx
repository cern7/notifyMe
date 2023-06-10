import axios from "axios";
const BASE_API = "http://localhost:8080/api/auth/login";

const postMethodConfig = {
  header: {
    'Content-type': 'application/json; charset=UTF-8',
  },
};

export const loginRequest = (loginDTO) => axios.post(`${BASE_API}`, loginDTO, postMethodConfig);