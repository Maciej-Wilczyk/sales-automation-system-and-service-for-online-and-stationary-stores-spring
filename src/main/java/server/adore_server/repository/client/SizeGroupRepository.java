package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.SizeGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeGroupRepository extends JpaRepository<SizeGroup,Long> {
    Optional<SizeGroup> findBySize(String size);

    @Query(value = "SELECT size_group_id, size_ FROM size_group", nativeQuery = true)
    List<Object> getAll();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM size_group WHERE size_group_id=:id", nativeQuery = true)
    int delete(
            @Param("id") long id
    );
}
