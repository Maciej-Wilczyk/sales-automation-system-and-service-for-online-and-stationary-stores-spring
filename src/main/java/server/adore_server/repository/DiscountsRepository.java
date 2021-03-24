package server.adore_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import server.adore_server.model.Discounts;
import server.adore_server.response.DiscountsResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, Long> {

    @Query(value = "SELECT discount_name FROM discounts", nativeQuery = true)
    List<String> findAllDiscountsName();

    Optional<Discounts> findByDiscountName(String s);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM discounts WHERE discount_id=:id", nativeQuery = true)
    int deleteDisc(
            @Param("id") long id
    );

}
