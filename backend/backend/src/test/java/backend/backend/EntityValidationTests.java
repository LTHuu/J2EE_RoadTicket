package backend.backend;

import backend.backend.entity.TripInfo;
import backend.backend.entity.Vehicle;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityValidationTests {
    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = FACTORY.getValidator();

    @AfterAll
    static void closeValidatorFactory() {
        FACTORY.close();
    }

    @Test
    void overnightTripMayArriveOnTheNextDay() {
        TripInfo trip = new TripInfo();
        trip.setDepartureTime(LocalDateTime.of(2026, 10, 10, 22, 0));
        trip.setArrivalTime(LocalDateTime.of(2026, 10, 11, 6, 0));
        trip.setTicketPrice(new BigDecimal("250000.00"));

        assertTrue(VALIDATOR.validate(trip).isEmpty());
    }

    @Test
    void arrivalMustBeStrictlyAfterDeparture() {
        TripInfo trip = new TripInfo();
        trip.setDepartureTime(LocalDateTime.of(2026, 10, 10, 22, 0));
        trip.setArrivalTime(trip.getDepartureTime());
        trip.setTicketPrice(new BigDecimal("250000.00"));

        assertFalse(VALIDATOR.validateProperty(trip, "scheduleValid").isEmpty());
        trip.setArrivalTime(trip.getDepartureTime().minusMinutes(1));
        assertFalse(VALIDATOR.validateProperty(trip, "scheduleValid").isEmpty());
    }

    @Test
    void ticketPriceCannotBeNegativeOrMissing() {
        TripInfo trip = new TripInfo();
        assertFalse(VALIDATOR.validateProperty(trip, "ticketPrice").isEmpty());
        trip.setTicketPrice(new BigDecimal("-0.01"));
        assertFalse(VALIDATOR.validateProperty(trip, "ticketPrice").isEmpty());
    }

    @Test
    void vehicleMustHaveAtLeastOneSeat() {
        Vehicle vehicle = new Vehicle();
        assertFalse(VALIDATOR.validateProperty(vehicle, "seatCapacity").isEmpty());
        vehicle.setSeatCapacity(0);
        assertFalse(VALIDATOR.validateProperty(vehicle, "seatCapacity").isEmpty());
        vehicle.setSeatCapacity(40);
        assertTrue(VALIDATOR.validateProperty(vehicle, "seatCapacity").isEmpty());
    }
}
