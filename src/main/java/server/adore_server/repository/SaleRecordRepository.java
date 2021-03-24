package server.adore_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.SaleRecord;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public interface SaleRecordRepository extends JpaRepository<SaleRecord, Long> {

       @Query(value = "SELECT MAX(transaction_id) FROM sale_record", nativeQuery = true)
    Long getMaxTr();

       Optional<List<SaleRecord>> findAllByNameAndOptions(String name, String options);
       Optional<List<SaleRecord>> findAllByName(String name);


    @Query(value = "SELECT * FROM sale_record WHERE transaction_id=(SELECT MAX(transaction_id) FROM sale_record WHERE where_=:string)", nativeQuery = true)
    List<SaleRecord> getLastSaleRecord(
            @Param("string") String string
    );

    @Query(value = "SELECT MAX(transaction_id) FROM sale_record WHERE where_=:s", nativeQuery = true)
    int getMaxTrStacjonarna(
            @Param("s") String s
    );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sale_record WHERE transaction_id=:id",  nativeQuery = true)
    int deleteTr(
            @Param("id") int id
    );

    @Query(value="SELECT * FROM sale_record WHERE baselinker_id=:id",  nativeQuery = true)
    List<SaleRecord> existsByBaselinker_id(
     @Param("id") long id
    );

    @Query(value="SELECT * FROM sale_record WHERE ean=:ean",  nativeQuery = true)
    List<SaleRecord> getAllByEan(
            @Param("ean") long ean
    );

    @Query(value="SELECT * FROM sale_record WHERE transaction_id=:transaction_id",  nativeQuery = true)
    List<SaleRecord> getAllByTransaction_id(
            @Param("transaction_id") int transaction_id
    );

}
//SELECT * FROM sale_record WHERE transaction_id=(SELECT MAX(transaction_id) FROM sale_record WHERE where_="stacjonarna")
