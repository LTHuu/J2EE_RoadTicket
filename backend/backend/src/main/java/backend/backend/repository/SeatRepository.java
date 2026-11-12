package backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.backend.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
