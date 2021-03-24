package server.adore_server.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.model.SaleRecord;
import server.adore_server.model.SaleRecordDeleted;
import server.adore_server.repository.SaleRecordDeletedRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleRecordDeletedService {
    @Autowired
    SaleRecordDeletedRepository saleRecordDeletedRepository;

    public List<SaleRecordDeleted> addSD(List<SaleRecord> list, String user) {

        List<SaleRecordDeleted> deletedList = new ArrayList<>();
        for (SaleRecord i : list) {
            Gson gson = new Gson();
            i.setEmployee(user);
            String jsonString = gson.toJson(i);
            SaleRecordDeleted saleRecordDeleted = new SaleRecordDeleted();
            saleRecordDeleted = gson.fromJson(jsonString, SaleRecordDeleted.class);
            deletedList.add(saleRecordDeleted);
        }
        return saleRecordDeletedRepository.saveAll(deletedList);
    }
}
