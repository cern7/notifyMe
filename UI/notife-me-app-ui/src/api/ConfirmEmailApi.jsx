import axios from "axios";
const BASE_API = `${import.meta.env.VITE_BACKEND_API_URL}/api/auth/registrationConfirm`;

const postMethodConfig = {
  header: {
    'Content-type': 'application/json; charset=UTF-8',
  },
};

export const confirmEmail = (token) => axios.post(`${BASE_API}?token=${token}`, postMethodConfig);