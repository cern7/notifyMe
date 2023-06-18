import axios from "axios";
const SERVICE_API = "http://localhost:8080/api/service/all";



export const getAllAvailServices = (token) => {
  const config = {
    headers: {
      'Content-type': 'application/json; charset=UTF-8',
      'Authorization': `${token}`
    },
  };

  return axios.get(`${SERVICE_API}`, config);
}