package server.adore_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.Discounts;
import server.adore_server.repository.DiscountsRepository;
import server.adore_server.request.DiscountsRequest;
import server.adore_server.response.DiscountsResponse;


import java.util.List;

@Service
public class DiscountsService {

    @Autowired
    DiscountsRepository discountsRepository;

    public List<String> getDiscName() {

        return discountsRepository.findAllDiscountsName();
    }

    public DiscountsResponse getDiscInfo(DiscountsRequest discountsRequest) {
        DiscountsResponse discountsResponse = new DiscountsResponse();
        discountsResponse.setDiscountId(discountsRepository.findByDiscountName(discountsRequest.getDiscountName()).orElseThrow().getDiscountId());
        discountsResponse.setDiscountType(discountsRepository.findByDiscountName(discountsRequest.getDiscountName()).orElseThrow().getDiscountType());
        discountsResponse.setValue(discountsRepository.findByDiscountName(discountsRequest.getDiscountName()).orElseThrow().getValue());
        discountsResponse.setMinimumValue(discountsRepository.findByDiscountName(discountsRequest.getDiscountName()).orElseThrow().getMinimumValue());
        return discountsResponse;
    }

    public List<Discounts> getDiscList(){
        return discountsRepository.findAll();
    }

    public Discounts addDisc(Discounts discounts){
        return discountsRepository.save(discounts);
    }

    @Transactional
    @Modifying
    public int delDisc(long id) {
        return discountsRepository.deleteDisc(id);
    }

}
