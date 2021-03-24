package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.ClientType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientType,Long> {

    Optional<ClientType> findByClientType(String type);

    @Query(value = "SELECT client_type_id, client_type FROM client_type", nativeQuery = true)
    List<Object> getAll();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM client_type WHERE client_type_id=:id", nativeQuery = true)
    int delete(
            @Param("id") long id
    );

}
