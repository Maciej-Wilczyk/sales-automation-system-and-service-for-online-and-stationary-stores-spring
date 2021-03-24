package server.adore_server.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.MinimumAmount;
import server.adore_server.repository.client.MinimumAmountRepository;

import java.util.List;

@Service
public class MinimumAmountService {

    @Autowired
    MinimumAmountRepository minimumAmountRepository;
    public double get(){
        List<MinimumAmount> list = minimumAmountRepository.findAll();
        return list.get(0).getAmount();
    }
    @Transactional
    @Modifying
    public int edit(double newA){
        List<MinimumAmount> list = minimumAmountRepository.findAll();
        double old = list.get(0).getAmount();
        return minimumAmountRepository.edit(newA,old);

    }
}
