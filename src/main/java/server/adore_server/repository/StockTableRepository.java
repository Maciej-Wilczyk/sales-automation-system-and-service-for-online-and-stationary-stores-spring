package server.adore_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.SaleRecord;
import server.adore_server.model.StockTable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockTableRepository extends JpaRepository<StockTable,Long> {
    Optional<StockTable> findByEan(long ean);

    Optional<StockTable> findByName(String name);

    Optional<StockTable> findByNameAndOptions(String name, String options );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM stock_table WHERE productID=:p", nativeQuery = true)
    int deleteProduct(
            @Param("p") long p
    );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM stock_table WHERE stock_id=:p", nativeQuery = true)
    int deleteOptions(
            @Param("p") long p
    );

    @Query(value = "SELECT * FROM stock_table WHERE extended= 0", nativeQuery = true)
    List<StockTable> getAllExtended0();


    @Query(value = "SELECT * FROM stock_table WHERE name=:n AND extended=0", nativeQuery = true)
    StockTable getAllExtended0Name(
            @Param("n") String n
    );

    @Query(value = "SELECT * FROM stock_table WHERE ean=:n", nativeQuery = true)
    StockTable getByEan(
            @Param("n") long n
    );


    @Query(value = "SELECT * FROM stock_table WHERE name=:n AND extended=1", nativeQuery = true)
    List<StockTable> getAllExtended1(
            @Param("n") String n

    );

    @Query(value = "SELECT * FROM stock_table WHERE name=:n AND extended=1 AND options=:o", nativeQuery = true)
    StockTable getAllExtended1NameOptions(
            @Param("n") String n,
            @Param("o") String o
    );

    @Query(value = "SELECT * FROM stock_table WHERE extended=1 AND productID=:id", nativeQuery = true)
    List<StockTable> getAllExtended1ProductId(
            @Param("id") int id
    );

    @Query(value = "SELECT * FROM stock_table WHERE  productID=:id", nativeQuery = true)
    List<StockTable> getAllByProductId(
            @Param("id") int id
    );

}
