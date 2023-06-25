import React, { useState } from 'react'
import { Link, useLocation } from 'react-router-dom';
// import 'react-calendar/dist/Calendar.css';


const BookingComponent = () => {

  const location = useLocation();
  const bookDate = Number(location.state.startDateTime);
  return (
    <div>
      <h2>Booking successfully created</h2>
      <h4>Booking time: {new Date(bookDate)
        .toLocaleString('en-US',
          {
            month: 'numeric',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            year: 'numeric'
          })}
      </h4>
      <h4>Payment status: {location.state.paymentStatus}</h4>
      <h4>Status: {location.state.status}</h4>
      <h3>Check you bookings: </h3>
      <Link to='/bookings/all'>
        <button>My Bookings</button>
      </Link>
    </div>
  )
}

export default BookingComponent;