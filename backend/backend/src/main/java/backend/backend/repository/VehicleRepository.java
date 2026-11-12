package backend.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.backend.entity.Vehicle;
import backend.backend.enums.VehicleType;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> findAllByBusCompany_Id(Long busCompanyId);

    @Query("""
                SELECT v FROM Vehicle v
                WHERE (
                    :text IS NULL
                    OR LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :text, '%'))
                    OR LOWER(v.name) LIKE LOWER(CONCAT('%', :text, '%'))
                    OR LOWER(v.busCompany.name) LIKE LOWER(CONCAT('%', :text, '%'))
                )
                AND (:type IS NULL OR v.type = :type)
                AND (:active IS NULL OR v.active = :active)
            """)
    List<Vehicle> searchVehicles(
            @Param("text") String text,
            @Param("type") VehicleType type,
            @Param("active") Boolean active);
}
