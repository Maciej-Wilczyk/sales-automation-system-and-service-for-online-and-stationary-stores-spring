package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.model.client.TypeOfCard;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeOfCardRepository extends JpaRepository<TypeOfCard, Long> {


    @Query(value = "SELECT *  FROM type_of_card WHERE card_type=:type", nativeQuery = true)
    TypeOfCard findByCardType(
            @Param("type") String type
    );

    @Query(value = "SELECT type_of_card_id, card_type, discount FROM type_of_card", nativeQuery = true)
    List<Object> getAll();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM type_of_card WHERE type_of_card_id=:id", nativeQuery = true)
    int delete(
            @Param("id") long id
    );
}
