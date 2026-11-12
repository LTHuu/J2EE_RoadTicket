package backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.backend.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
