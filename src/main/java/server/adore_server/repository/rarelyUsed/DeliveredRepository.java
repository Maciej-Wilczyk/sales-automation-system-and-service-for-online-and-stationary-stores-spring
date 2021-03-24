package server.adore_server.repository.rarelyUsed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.clcker.Delivered;

import java.util.List;

@Repository
public interface DeliveredRepository extends JpaRepository<Delivered,Long> {

    @Query(value = "SELECT MAX(delivered_id) FROM delivered", nativeQuery = true)
    Long getMaxDelTr();

    @Query(value = "SELECT * FROM delivered WHERE invoice=:s", nativeQuery = true)
    List<Delivered> getByInvoice(
            @Param("s") String s
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE delivered SET is_corrected=:is_corrected, correct_comment=:correct_comment,correct_date=:correct_date WHERE invoice=:invoice AND stock_id=:stock_id", nativeQuery = true)
    void saveC(
            @Param("is_corrected") int is_corrected,
            @Param("correct_comment") String correct_comment,
            @Param("correct_date") String correct_date,
            @Param("invoice") String invoice,
            @Param("stock_id") long stock_id
    );

    @Query(value = "SELECT * FROM delivered WHERE invoice=:s AND stock_id=:stock_id AND purchase_price=:purchase_price", nativeQuery = true)
    List<Delivered> getByInvoice3(
            @Param("s") String s,
            @Param("stock_id") long stock_id,
            @Param("purchase_price") double purchase_price
    );

}
