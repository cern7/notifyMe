import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getAllCustomerBookings } from '../api/BookingApi';
import Cookies from 'js-cookie';
import { loadBookings } from '../actions/myBookings';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

const MyBookings = () => {
  const id = localStorage.getItem('userId');
  const userToken = Cookies.get('jwtToken');
  const userType = localStorage.getItem('userType')
  const dispatch = useDispatch();
  const [data, setData] = useState([]);
  const storeBookings = useSelector((state) => state.bookings.bookings);
  const navigate = useNavigate();

  useEffect(() => {
    if (storeBookings.length === 0) {
      const fetchData = async () => {
        try {
          const response = await getAllCustomerBookings();
          console.log(response.data);
          setData(response.data);
        } catch (error) {
          console.error("Couldn't call backend api loading bookings", error)
        }
      }
      fetchData();
    }
  }, [storeBookings, userToken]);

  useEffect(() => {
    if (data.length > 0) {
      loadBookingsToStore();
      console.log("loading data into store")
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
      {(userType === 'ADMIN') &&
        <div>will call all bookings in the app for all customers</div>}
      {(userType === 'EMPLOYEE') &&
        <div>will call all bookings for this employee</div>}
      {(userType === 'CUSTOMER') &&
        <div>
          <h5>will call all bookings for this Customer</h5>
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
    </div>
  )
}

export default MyBookings;
