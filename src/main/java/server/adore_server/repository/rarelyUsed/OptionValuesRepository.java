package server.adore_server.repository.rarelyUsed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.adore_server.model.clcker.OptionValues;

@Repository
public interface OptionValuesRepository extends JpaRepository<OptionValues,Long> {
}
