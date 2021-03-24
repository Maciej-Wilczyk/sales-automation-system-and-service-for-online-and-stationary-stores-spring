package server.adore_server.repository.rarelyUsed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.adore_server.model.clcker.Tags;
@Repository
public interface TagsRepository extends JpaRepository<Tags,Long> {

}
