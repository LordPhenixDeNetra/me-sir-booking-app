package sn.sir.booking.me_sir_booking_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.sir.booking.me_sir_booking_app.domain.Customer;
import sn.sir.booking.me_sir_booking_app.domain.Reservation;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findFirstByReservation(Reservation reservation);

}
