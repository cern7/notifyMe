import React, { useEffect, useState } from 'react'
import { bookService, getAllAvailServices, getAllEmployeesByServiceId } from '../api/ServicesApi';
import { useDispatch, useSelector } from 'react-redux';
import Cookies from 'js-cookie';
import { setAvailableServices } from '../actions/availableServices';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import Calendar from 'react-calendar';
import DateTimePicker from 'react-datetime-picker';
import './Calendar.css'
import './DateTimePicker.css'
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import IconButton from '@mui/material/IconButton';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-clock/dist/Clock.css';


const ServiceComponent = () => {
  const dispatch = useDispatch();
  // const userToken = Cookies.get('jwtToken');
  const userToken = localStorage.getItem('token');
  const [data, setData] = useState([]);
  const storeServices = useSelector((state) => state.services.services);
  const [showCalendar, setShowCalendar] = useState(false);
  const [serviceToBook, setServiceToBook] = useState(null);
  const [serviceDuration, setServiceDuration] = useState();
  const [employeeIdToBook, setEmployeeIdToBook] = useState(null);
  const [bookingDate, setBookingDate] = useState(new Date());
  const [employeesByService, setEmployeeByService] = useState();

  const minDate = new Date();
  const maxDate = new Date(minDate.getFullYear() + 1, minDate.getMonth(), minDate.getDate());
  const navigate = useNavigate();


  useEffect(() => {
    if (storeServices.length === 0) {
      const fetchData = async () => {
        try {
          const response = await getAllAvailServices();
          setData(response.data);
        } catch (error) {
          console.log("LOCALSTORAGE:")
          console.log(localStorage.getItem('token'));
          // localStorage.removeItem('token');
          // localStorage.removeItem('userId')
          // localStorage.removeItem('userType')
          // navigate('/login');
          console.error(error);
        }
      };
      fetchData();
    }
  }, [storeServices, userToken]);

  useEffect(() => {
    const getEmployeeIds = async () => {
      if (serviceToBook !== null) {
        try {
          const response = await getAllEmployeesByServiceId(serviceToBook.iid);
          setEmployeeByService(response.data);
        } catch (error) {
          console.error(error);
        }
      }
    }
    getEmployeeIds();

  }, [serviceToBook])

  useEffect(() => {
    if (data.length > 0) {
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
    })
    setData([]);
  }


  const handleBookNow = (service) => {
    setServiceToBook(service);
    setServiceDuration(service.duration)
    setShowCalendar(true);
    setEmployeeIdToBook(null);
  };


  const handleSubmitBooking = async (e) => {
    e.preventDefault();
    try {
      const bookServiceDTO = {
        employeeId: employeeIdToBook,
        serviceId: serviceToBook.iid,
        customerId: Number(localStorage.getItem('userId')),
        startDateTime: bookingDate.getTime(),
        endDateTime: (Number(bookingDate) + serviceDuration * 1000 * 60),
        status: "PENDING",
        paymentStatus: "PENDING",
        notes: "no notes"
      }
      const response = await bookService(bookServiceDTO);
      navigate('/booking', { state: response.data });
    } catch (error) {
      console.error("Couldn't book service: ", error);
    }
  }

  return (
    <div>

      <h1>Services</h1>
      <div>
        {userToken && <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>Description</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Duration</TableCell>
                <TableCell>Category</TableCell>
                <TableCell>Availability</TableCell>
                {employeesByService && serviceToBook && <TableCell>Employee</TableCell>}
              </TableRow>
            </TableHead>
            <TableBody>
              {storeServices.length > 0 && storeServices.map((service, index) => {
                return (
                  <TableRow key={index}>
                    <TableCell>{service.serviceName}</TableCell>
                    <TableCell>{service.description}</TableCell>
                    <TableCell>{service.price}</TableCell>
                    <TableCell>{service.duration}</TableCell>
                    <TableCell>{service.category}</TableCell>
                    <TableCell>
                      <IconButton onClick={() => handleBookNow(service)}>
                        <CalendarMonthIcon />
                      </IconButton>
                    </TableCell>
                    {serviceToBook !== null && serviceToBook.iid === service.iid
                      && employeesByService &&
                      <TableCell>
                        <select onChange={(e) => setEmployeeIdToBook(Number(e.target.value))}>
                          <option value="">Select an employee</option>
                          {employeesByService.map((employee, index) => {
                            return (
                              <option key={index} value={employee.employee_id}>
                                {employee.firstname}
                              </option>
                            )
                          })}
                        </select>
                      </TableCell>
                    }
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </TableContainer>
        }
      </div>
      <div className='CalendarWrapper'>
        {showCalendar && (
          <div>
            {/* <span className='calendarIcon'>
              <CalendarMonthIcon />
              <h3>Date</h3>
            </span> */}
            <DateTimePicker
              onChange={setBookingDate}
              value={bookingDate}
              minDate={minDate}
              maxDate={maxDate}
              minDetail='year' // set the granularity
              next2Label={null}
              prev2Label={null}
              calendarIcon={<CalendarMonthIcon />}
            />
          </div>
        )}

        {bookingDate && serviceToBook && (
          <div>
            <h2>Booking Details</h2>
            <h3>Booking Date:{bookingDate.toLocaleString('en-US',
              {
                weekday: 'short',
                month: 'short',
                day: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
                second: 'numeric',
                year: 'numeric'
              })}</h3>
            <h3>Service selected : {serviceToBook.serviceName}</h3>
            <button onClick={handleSubmitBooking}>Submit Booking</button>
          </div>
        )}
      </div>
    </div >
  );
}

export default ServiceComponent;