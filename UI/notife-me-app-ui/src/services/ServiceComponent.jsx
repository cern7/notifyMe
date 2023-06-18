import React, { useEffect, useState } from 'react'
import { getAllAvailServices } from '../api/ServicesApi';
import { useDispatch, useSelector } from 'react-redux';
import Cookies from 'js-cookie';
import { setAvailableServices } from '../actions/availableServices';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

const ServiceComponent = () => {

  const dispatch = useDispatch();
  const userToken = Cookies.get('jwtToken');
  const storeServices = useSelector((state) => state.services);
  const [data, setData] = useState([]);

  const columns = [
    { field: "serviceName", headerName: "Name" },
    { field: "description", headerName: "Description" },
    { field: "price", headerName: "Price" },
    { field: "duration", headerName: "Duration" },
    { field: "category", headerName: "Category" },
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getAllAvailServices(userToken);
        setData(response.data);
        // console.log("fetching data....", JSON.stringify(data));
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    if (data.length > 0) {
      console.log("starting loading data....");
      loadServices();
    }
  }, [data])

  const loadServices = () => {
    data.map((service) => {
      dispatch(setAvailableServices(
        service.serviceName,
        service.description,
        service.price,
        service.duration,
        service.availability,
        service.category,
        service.imageUrl,
        service.iid,
      ));
      console.log(service)
    })
    console.log("data loaded into store!!!");
  }
  
  return (
    <div>
      <h2>Services</h2>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Description</TableCell>
              <TableCell>Price</TableCell>
              <TableCell>Duration</TableCell>
              <TableCell>Category</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {storeServices.length > 0 && storeServices.map((service) => {
              console.log('service:', service); // Log each service object
              return (
                <TableRow key={service.iid}>
                  <TableCell>{service.serviceName}</TableCell>
                  <TableCell>{service.description}</TableCell>
                  <TableCell>{service.price}</TableCell>
                  <TableCell>{service.duration}</TableCell>
                  <TableCell>{service.category}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );


}


export default ServiceComponent;