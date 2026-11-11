package backend.backend.repository;

import backend.backend.entity.BusCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusCompanyRepository extends JpaRepository<BusCompany, Long> {
}
