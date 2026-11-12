package backend.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.backend.entity.TripInfo;
import backend.backend.enums.TripStatus;

public interface TripInfoRepository extends JpaRepository<TripInfo, Long> {
    List<TripInfo> findAllByRoute_Id(Long routeId);

    List<TripInfo> findAllByVehicle_Id(Long vehicleId);

    @Query("""
            SELECT t FROM TripInfo t
            JOIN t.route r
            WHERE (:routeId IS NULL OR r.id = :routeId)
              AND (:vehicleId IS NULL OR t.vehicle.id = :vehicleId)
              AND (:status IS NULL OR t.status = :status)
              AND (:departureLocation IS NULL
                   OR LOWER(r.departureLocation) LIKE LOWER(CONCAT('%', :departureLocation, '%')))
              AND (:arrivalLocation IS NULL
                   OR LOWER(r.arrivalLocation) LIKE LOWER(CONCAT('%', :arrivalLocation, '%')))
              AND (:departureFrom IS NULL OR t.departureTime >= :departureFrom)
              AND (:departureTo IS NULL OR t.departureTime <= :departureTo)
              AND (:arrivalFrom IS NULL OR t.arrivalTime >= :arrivalFrom)
              AND (:arrivalTo IS NULL OR t.arrivalTime <= :arrivalTo)
            ORDER BY t.departureTime
            """)
    List<TripInfo> searchTrips(
            @Param("routeId") Long routeId,
            @Param("vehicleId") Long vehicleId,
            @Param("status") TripStatus status,
            @Param("departureLocation") String departureLocation,
            @Param("arrivalLocation") String arrivalLocation,
            @Param("departureFrom") LocalDateTime departureFrom,
            @Param("departureTo") LocalDateTime departureTo,
            @Param("arrivalFrom") LocalDateTime arrivalFrom,
            @Param("arrivalTo") LocalDateTime arrivalTo);
}
