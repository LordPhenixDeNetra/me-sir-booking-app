package sn.sir.booking.me_sir_booking_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.booking.me_sir_booking_app.domain.Customer;
import sn.sir.booking.me_sir_booking_app.domain.Reservation;
import sn.sir.booking.me_sir_booking_app.model.CustomerDTO;
import sn.sir.booking.me_sir_booking_app.repos.CustomerRepository;
import sn.sir.booking.me_sir_booking_app.repos.ReservationRepository;
import sn.sir.booking.me_sir_booking_app.util.NotFoundException;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public CustomerService(final CustomerRepository customerRepository,
            final ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<CustomerDTO> findAll() {
        final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
        return customers.stream()
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .toList();
    }

    public CustomerDTO get(final Long id) {
        return customerRepository.findById(id)
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        mapToEntity(customerDTO, customer);
        return customerRepository.save(customer).getId();
    }

    public void update(final Long id, final CustomerDTO customerDTO) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(customerDTO, customer);
        customerRepository.save(customer);
    }

    public void delete(final Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(final Customer customer, final CustomerDTO customerDTO) {
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setDateCreated(customer.getDateCreated());
        customerDTO.setLastUpdated(customer.getLastUpdated());
        customerDTO.setReservation(customer.getReservation() == null ? null : customer.getReservation().getId());
        return customerDTO;
    }

    private Customer mapToEntity(final CustomerDTO customerDTO, final Customer customer) {
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setDateCreated(customerDTO.getDateCreated());
        customer.setLastUpdated(customerDTO.getLastUpdated());
        final Reservation reservation = customerDTO.getReservation() == null ? null : reservationRepository.findById(customerDTO.getReservation())
                .orElseThrow(() -> new NotFoundException("reservation not found"));
        customer.setReservation(reservation);
        return customer;
    }

}
