package backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.backend.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
