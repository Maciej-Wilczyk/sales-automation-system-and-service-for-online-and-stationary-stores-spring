package server.adore_server.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.auth.UserRoles;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id=:id",  nativeQuery = true)
    int deleteRole(
            @Param("id") long id
    );
}
