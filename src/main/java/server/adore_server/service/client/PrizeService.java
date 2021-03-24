package server.adore_server.service.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.Prize;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.repository.client.PrizeRepository;

import java.util.List;

@Service
public class PrizeService {

    @Autowired
    PrizeRepository prizeRepository;

    public Prize add(JSONObject jsonObject){

        int neededPoints = jsonObject.getInt("neededPoints");
        String description = jsonObject.getString("description");
        Prize prize = new Prize();
        try {
            long prizeId = jsonObject.getLong("prizeId");
            prize.setPrizeId(prizeId);
        }catch (Exception e){}


        prize.setDescription(description);
        prize.setNeededPoints(neededPoints);
        return prizeRepository.save(prize);
    }

    public List<Prize> getPrizes(){
        return prizeRepository.findAll();
    }

    @Transactional
    @Modifying
    public int delete(long id) {
        return prizeRepository.delete(id);
    }
}
