package server.adore_server.repository.rarelyUsed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.adore_server.model.clcker.Options;

@Repository
public interface OptionsRepository extends JpaRepository<Options,Long> {
}
