package com.notifyme.application.assembler;

import com.notifyme.application.controller.UserController;
import com.notifyme.application.entity.Customer;
import com.notifyme.application.model.CustomerModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler
        extends RepresentationModelAssemblerSupport<Customer, CustomerModel> {

    public CustomerModelAssembler() {
        super(UserController.class, CustomerModel.class);
    }

    @Override
    public CollectionModel<CustomerModel> toCollectionModel(Iterable<? extends Customer> entities) {
        CollectionModel<CustomerModel> customerModels = super.toCollectionModel(entities);
        customerModels.add(linkTo(methodOn(UserController.class).getAllCustomers()).withSelfRel());
        return customerModels;
    }

    @Override
    public CustomerModel toModel(Customer entity) {
        CustomerModel customerModel = instantiateModel(entity);
        customerModel.add(linkTo(methodOn(UserController.class)
                .getCustomerById(entity.getIID()))
                .withSelfRel());
        customerModel.setIID(entity.getIID());
        customerModel.setPaymentID(entity.getPaymentID());
        customerModel.setInvoice(entity.getInvoice());
//        customerModel.setBookings(toBookingModel(entity.getBookings()));
        return customerModel;

    }

//    private Set<BookingModel> toBookingModel(Set<Booking> bookings) {
//        if (bookings.isEmpty()) {
//            return Collections.emptySet();
//        }
//
//        return bookings.stream()
//                .map(booking -> BookingModel.builder()
//                        .IID(booking.getIID())
//                        .startDateTime(booking.getStartDateTime())
//                        .build()
//                        .add(linkTo(
//                                methodOn(BookingController.class)
//                                        .getBookingById(booking.getIID()))
//                                .withSelfRel()))
//                .collect(Collectors.toSet());
//    }
}
