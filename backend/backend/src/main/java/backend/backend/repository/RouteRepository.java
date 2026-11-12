package backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.backend.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
