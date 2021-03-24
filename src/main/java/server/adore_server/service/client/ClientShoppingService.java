package server.adore_server.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.repository.client.ClientShoppingRepository;
import server.adore_server.response.ClientShoppingHistoryProductsResponse2;
import server.adore_server.response.ClientShoppingHistoryProductsResponse;
import server.adore_server.response.ClientShoppingHistoryResponse;


import java.util.ArrayList;
import java.util.List;

@Service
public class ClientShoppingService {

    @Autowired
    ClientShoppingRepository clientShoppingRepository;



    public List<ClientShoppingHistoryResponse> getHistory(long id){
        return clientShoppingRepository.historyPoints(id);
    }

    public List<ClientShoppingHistoryProductsResponse2> getHistoryProducts(long id){
        List<ClientShoppingHistoryProductsResponse2> listToSend = new ArrayList<>();
        var list = clientShoppingRepository.historyProducts(id);
        for(ClientShoppingHistoryProductsResponse i: list){
            ClientShoppingHistoryProductsResponse2 newOne = new ClientShoppingHistoryProductsResponse2();
            newOne.setDate(i.getDate());
            newOne.setName(i.getProduct_name());
            newOne.setOptions(i.getOptions());
            newOne.setReturned(i.getReturned());
            newOne.setReturnedDate(i.getReturned_date());

            if(i.getAfter_disc() != 0)
                newOne.setPrice(i.getAfter_disc());
            else
                newOne.setPrice(i.getPrice_brutto());

            listToSend.add(newOne);
        }
        return listToSend;
    }



}
