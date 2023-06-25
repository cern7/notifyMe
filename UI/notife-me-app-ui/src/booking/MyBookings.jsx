import React from 'react'
import { useSelector } from 'react-redux';

const MyBookings = () => {
  const id = localStorage.getItem('userId');
  const userType = localStorage.getItem('userType')
  return (
    <div>
      {(userType === 'ADMIN') &&
        <div>will call all bookings in the app for all customers</div>}
      {(userType === 'EMPLOYEE') &&
        <div>will call all bookings for this employee</div>}
      {(userType === 'CUSTOMER') &&
        <div>will call all bookings for this Customer</div>}
    </div>
  )
}

export default MyBookings;
