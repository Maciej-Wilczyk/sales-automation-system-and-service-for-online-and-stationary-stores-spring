package server.adore_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.adore_server.model.SaleRecordDeleted;

import java.util.Optional;

@Repository
public interface SaleRecordDeletedRepository extends JpaRepository<SaleRecordDeleted, Long> {

}
