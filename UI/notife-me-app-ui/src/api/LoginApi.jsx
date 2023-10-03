import axios from "axios";
const BASE_API = `${import.meta.env.VITE_BACKEND_API_URL}/api/auth/login`;
console.log(BASE_API);


const postMethodConfig = {

  headers: {
    'Content-type': 'application/json; charset=UTF-8',
    'withCredentials': true,
  },
};

export const loginRequest = (loginDTO) => axios.post(`${BASE_API}`, loginDTO, postMethodConfig);