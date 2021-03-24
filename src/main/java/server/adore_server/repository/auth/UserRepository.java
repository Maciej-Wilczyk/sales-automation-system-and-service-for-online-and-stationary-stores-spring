package server.adore_server.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.auth.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET email=:email, login=:login,name_surname=:name_surname WHERE user_id=:user_id", nativeQuery = true)
     void updateUser(
            @Param("email") String email,
            @Param("login") String login,
            @Param("name_surname") String name_surname,
            @Param("user_id") long user_id
    );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE user_id=:id",  nativeQuery = true)
    int deleteUser(
            @Param("id") long id
    );
}
