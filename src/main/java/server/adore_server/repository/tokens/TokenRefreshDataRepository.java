package server.adore_server.repository.tokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.adore_server.model.tokens.TokenRefreshData;

@Repository
public interface TokenRefreshDataRepository extends JpaRepository<TokenRefreshData,Long> {

    @Query(value = "SELECT * FROM token_refresh_data WHERE id=:n", nativeQuery = true)
    TokenRefreshData getData(
            @Param("n") int n
    );

    @Query(value = "SELECT * FROM token_refresh_data WHERE id=:n", nativeQuery = true)
    TokenRefreshData getLogin(
            @Param("n") int n
    );


}
