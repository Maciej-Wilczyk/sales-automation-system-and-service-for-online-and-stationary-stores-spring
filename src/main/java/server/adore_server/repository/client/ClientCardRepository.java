package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.ClientCard;

import java.util.Optional;

@Repository
public interface ClientCardRepository extends JpaRepository<ClientCard,Long> {
    int countByCardNr(long cardNr);
    int countByPhoneNr(int phoneNr);

    @Query(value = "SELECT * FROM client_card where phone_nr =:phone_nr", nativeQuery = true)
    ClientCard findByPhoneNr(
            @Param("phone_nr") int phone_nr
    );

    @Query(value = "SELECT * FROM client_card where card_nr =:card_nr", nativeQuery = true)
    ClientCard findByCardNr(
            @Param("card_nr") long card_nr
    );

    @Query(value = "SELECT * FROM client_card where name =:name AND surname=:surname", nativeQuery = true)
    ClientCard findByNameAndSurname(
            @Param("name") String name,
            @Param("surname") String surname
    );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM client_card WHERE card_id=:id", nativeQuery = true)
    int delete(
            @Param("id") long id
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE client_card SET phone_nr=:phone, card_nr=:card_nr, name=:name, surname=:surname, comment=:comment, client_type_id=:client_type_id, type_of_card_id=:type_of_card_id,size_group_id=:size_group_id WHERE card_id=:id", nativeQuery = true)
    void cardUpdate(
            @Param("id") long id,
            @Param("phone") int phone,
            @Param("card_nr") long card_nr,
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("comment") String comment,
            @Param("client_type_id") long client_type_id,
            @Param("type_of_card_id") long type_of_card_id,
            @Param("size_group_id") long size_group_id
    );

    @Query(value = "SELECT COUNT(recommending_card_nr) FROM client_card WHERE recommending_card_nr=:card_nr", nativeQuery = true)
    int referralsSum(
            @Param("card_nr") long card_nr
    );




}
