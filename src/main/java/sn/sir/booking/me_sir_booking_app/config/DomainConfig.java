package sn.sir.booking.me_sir_booking_app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("sn.sir.booking.me_sir_booking_app.domain")
@EnableJpaRepositories("sn.sir.booking.me_sir_booking_app.repos")
@EnableTransactionManagement
public class DomainConfig {
}
