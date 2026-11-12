package backend.backend.repository;

import backend.backend.entity.User_BusCom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.backend.enums.StaffPosition;

import java.util.List;

public interface UBComRepository extends JpaRepository<User_BusCom, Long> {

    boolean existsByUser_IdAndBusCompany_Id(Long userId, Long busCompanyId);

    boolean existsByUser_IdAndBusCompany_IdAndIdNot(Long userId, Long busCompanyId, Long id);

    @Query("""
            SELECT ub FROM User_BusCom ub
            JOIN ub.user u
            JOIN ub.busCompany bc
            WHERE (:searchText IS NULL
                OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchText, '%'))
                OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchText, '%'))
                OR LOWER(bc.name) LIKE LOWER(CONCAT('%', :searchText, '%')))
              AND (:userId IS NULL OR u.id = :userId)
              AND (:busCompanyId IS NULL OR bc.id = :busCompanyId)
              AND (:position IS NULL OR ub.position = :position)
              AND (:active IS NULL OR ub.active = :active)
            """)
    List<User_BusCom> search(
            @Param("searchText") String searchText,
            @Param("userId") Long userId,
            @Param("busCompanyId") Long busCompanyId,
            @Param("position") StaffPosition position,
            @Param("active") Boolean active);
}
