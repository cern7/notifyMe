export const LOAD_BOOKINGS = 'LOAD_BOOKINGS';

export const loadBookings = (bookingResponseDTO) => {
  return {
    type: LOAD_BOOKINGS,
    payload: {
      iid: bookingResponseDTO.iid,
      startDateTime: bookingResponseDTO.startDateTime,
      endDateTime: bookingResponseDTO.endDateTime,
      bookingStatus: bookingResponseDTO.status,
      paymentStatus: bookingResponseDTO.paymentStatus,
      notes: bookingResponseDTO.notes,
      employee: bookingResponseDTO.employee,
      service: {
        serviceName: bookingResponseDTO.service.serviceName,
        description: bookingResponseDTO.service.description,
        price: bookingResponseDTO.service.price,
        duration: bookingResponseDTO.service.duration,
        availability: bookingResponseDTO.service.availability,
        category: bookingResponseDTO.service.category,
        imageUrl: bookingResponseDTO.service.imageUrl,
        iid: bookingResponseDTO.service.iid,
      }
    }
  }
}