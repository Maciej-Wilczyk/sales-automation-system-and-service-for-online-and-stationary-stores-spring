package server.adore_server.service.rarelyUsed;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.clcker.Delivered;
import server.adore_server.repository.rarelyUsed.DeliveredRepository;

import java.util.List;

@Service
public class DeliveredService {
    @Autowired
    DeliveredRepository deliveredRepository;

    public List<Delivered> add(List<Delivered> list) {
        return deliveredRepository.saveAll(list);
    }

    public long getMaxDelId() {
        return deliveredRepository.getMaxDelTr();
    }

    public Object[][] getByInvoiceCounted(String s) {
        var list = deliveredRepository.getByInvoice(s);
        Delivered delivered;
        boolean flag = false;
        Object[][] sortedList = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            delivered = list.get(i);
            if (i == 0) {

                sortedList[0][0] = delivered;
                sortedList[0][1] = 1;
            } else {
                for (int j = 0; j < i; j++) {
                    try {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(sortedList[j][0]);
                        Delivered delivered1 = gson.fromJson(jsonString, Delivered.class);

                        if ((delivered1.getStock_id() == delivered.getStock_id()) && ((double) delivered1.getPurchase_price() == (double) delivered.getPurchase_price())) {
                            flag = true;
                            int a = (int) sortedList[j][1];
                            a++;
                            sortedList[j][1] = a;
                            break;
                        }
                    }
                    catch (Exception e){
                    }
                }
                if (flag == false) {
                    sortedList[i][0] = delivered;
                    sortedList[i][1] = 1;
                }
                flag = false;
            }
        }
        return sortedList;
    }

    public List<Delivered> getBy3(String s, long stock_id, double purchase_price){
        return deliveredRepository.getByInvoice3(s,stock_id,purchase_price);
    }

    @Modifying
    @Transactional
    public boolean addC(List<Delivered> list){
        try {

            deliveredRepository.saveAll(list);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
