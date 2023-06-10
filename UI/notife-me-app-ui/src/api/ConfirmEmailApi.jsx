import axios from "axios";
const BASE_API = "http://localhost:8080/api/auth/registrationConfirm";

const postMethodConfig = {
  header: {
    'Content-type': 'application/json; charset=UTF-8',
  },
};

export const confirmEmail = (token) => axios.post(`${BASE_API}?token=${token}`, postMethodConfig);