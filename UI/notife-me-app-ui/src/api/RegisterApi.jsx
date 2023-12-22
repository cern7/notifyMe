import axios from "axios";
const BASE_API = `${import.meta.env.VITE_BACKEND_API_URL}/api/auth/register`;

const postMethodConfig = {
  header: {
    'Content-type': 'application/json; charset=UTF-8',
    },
};

export const registerNewCustomer = (userDTO) => axios.post(`${BASE_API}`, userDTO, postMethodConfig);