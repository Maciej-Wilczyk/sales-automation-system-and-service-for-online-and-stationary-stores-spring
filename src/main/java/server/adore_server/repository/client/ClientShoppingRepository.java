package server.adore_server.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.adore_server.model.client.ClientShopping;
import server.adore_server.response.ClientShoppingHistoryProductsResponse;
import server.adore_server.response.ClientShoppingHistoryResponse;


import java.util.List;


@Repository
public interface ClientShoppingRepository extends JpaRepository<ClientShopping, Long> {

    @Query(value = "SELECT SUM(points) FROM client_shopping WHERE card_id=:id", nativeQuery = true)
    double sum(
            @Param("id") long id
    );


    @Query(value = "SELECT  sale_record.date, sale_record.product_name, sale_record.after_disc, sale_record.returned, sale_record.returned_date, sale_record.price_brutto, sale_record.options  FROM client_shopping INNER JOIN sale_record ON client_shopping.card_id = sale_record.card_id WHERE client_shopping.card_id=:id", nativeQuery = true)
    List<ClientShoppingHistoryProductsResponse> historyProducts(
            @Param("id") long id
    );

    @Query(value = "SELECT transaction_id, date, points FROM client_shopping  WHERE client_shopping.card_id=:id", nativeQuery = true)
    List<ClientShoppingHistoryResponse> historyPoints(
            @Param("id") long id
    );

}
