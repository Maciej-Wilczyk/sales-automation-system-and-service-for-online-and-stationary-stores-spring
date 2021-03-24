package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.MinimumAmount;
import server.adore_server.model.client.Prize;
@Repository
public interface MinimumAmountRepository extends JpaRepository<MinimumAmount,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE minimum_amount SET amount=:amount WHERE amount=:amountOld", nativeQuery = true)
    int edit(
            @Param("amount") double amount,
            @Param("amountOld") double amountOld
    );

    @Query(value = "SELECT amount FROM minimum_amount", nativeQuery = true)
    double getA();

}
