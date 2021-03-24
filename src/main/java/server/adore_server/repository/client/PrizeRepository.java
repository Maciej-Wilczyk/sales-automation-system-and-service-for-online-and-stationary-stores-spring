package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.Prize;

@Repository
public interface PrizeRepository extends JpaRepository<Prize,Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM prize WHERE prize_id=:id", nativeQuery = true)
    int delete(
            @Param("id") long id
    );

}
