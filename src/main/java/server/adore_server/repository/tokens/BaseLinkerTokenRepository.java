package server.adore_server.repository.tokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.adore_server.model.tokens.BaseLinkerToken;

@Repository
public interface BaseLinkerTokenRepository extends JpaRepository<BaseLinkerToken,Long> {

    @Query(value = "SELECT * FROM base_linker_token WHERE id=:n", nativeQuery = true)
    BaseLinkerToken getData(
            @Param("n") int n
    );

}
