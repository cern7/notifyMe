import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getAllCustomerBookings, getAllBookings } from '../api/BookingApi';
import Cookies from 'js-cookie';
import { loadBookings } from '../actions/myBookings';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Tab } from '@mui/material';

const MyBookings = () => {
  const id = localStorage.getItem('userId');
  // const userToken = Cookies.get('jwtToken');
  const userToken = localStorage.getItem('token');
  const userType = localStorage.getItem('userType')
  const dispatch = useDispatch();
  const [data, setData] = useState([]);
  const storeBookings = useSelector((state) => state.bookings.bookings);
  const navigate = useNavigate();

  useEffect(() => {
    if (storeBookings.length === 0) {
      const fetchData = async () => {
        try {
          let response
          if (userType === 'CUSTOMER') {
            response = await getAllCustomerBookings();
          } else if (userType === 'ADMIN') {
            // load all bookings
            response = await getAllBookings();
          }
          else if (userType === 'EMPLOYEE') {
            // load all bookings of the employee
            // TO DO rename this function as in backend it is calling the same method
            response = await getAllCustomerBookings();
          }

          console.log(response.data);
          setData(response.data);
        } catch (error) {
          // if (error.response.status === 401 || error.response.status === 400) {
          //   window.location.reload(true);
          //   localStorage.removeItem('token');
          //   localStorage.removeItem('userType');
          //   localStorage.removeItem('userId');
          //   navigate('/login')
          // }
          console.error(error);
          console.error("Couldn't call backend api loading bookings", error)
        }
      }
      fetchData();
    }
  }, [storeBookings, userToken]);

  useEffect(() => {
    if (data.length > 0) {
      loadBookingsToStore();
    }
  }, [data]);

  const loadBookingsToStore = () => {
    data.map((bookingDto) => {
      dispatch(loadBookings(bookingDto));
    })
    setData([]);
  }

  return (
    <div>
      {/* {(userType === 'ADMIN') &&
        <div>will call all bookings in the app for all customers</div>}
      {(userType === 'EMPLOYEE') &&
        <div>will call all bookings for this employee</div>} */}
      {userToken &&
        <div>
          <div>
            {userToken && <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>No</TableCell>
                    <TableCell>Booking Date</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell>Payment Status</TableCell>
                    <TableCell>Notes</TableCell>
                    <TableCell>Service Name</TableCell>
                    {(userType === 'ADMIN' || userType === 'EMPLOYEE')
                      && < TableCell >Customer ID</TableCell>}
                    {(userType === 'ADMIN') &&
                      <TableCell>Employee ID</TableCell>}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {storeBookings.length > 0 && storeBookings.map((booking, index) => {

                    return (<TableRow key={index}>
                      <TableCell>{index + 1}</TableCell>
                      <TableCell>{new Date(Number(booking.startDateTime)).toLocaleString('en-US',
                        {
                          weekday: 'short',
                          month: 'short',
                          day: 'numeric',
                          hour: 'numeric',
                          minute: 'numeric',
                          second: 'numeric',
                          year: 'numeric'
                        })}</TableCell>
                      <TableCell>{booking.bookingStatus}</TableCell>
                      <TableCell>{booking.paymentStatus}</TableCell>
                      <TableCell>{booking.notes}</TableCell>
                      <TableCell>{booking.service.serviceName}</TableCell>
                      {(userType === 'ADMIN' || userType === 'EMPLOYEE')
                        && <TableCell>{booking.customerId}</TableCell>}
                      {userType === 'ADMIN' &&
                        <TableCell>{booking.employeeId}</TableCell>}
                    </TableRow>
                    )
                  })}
                </TableBody>
              </Table>
            </TableContainer>
            }
          </div>
        </div>
      }
    </div >
  )
}

export default MyBookings;
