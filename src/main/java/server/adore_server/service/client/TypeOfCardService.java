package server.adore_server.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.model.client.TypeOfCard;
import server.adore_server.repository.client.SizeGroupRepository;
import server.adore_server.repository.client.TypeOfCardRepository;

import java.util.List;

@Service
public class TypeOfCardService {

    @Autowired
    TypeOfCardRepository typeOfCardRepository;

    public TypeOfCard add(TypeOfCard typeOfCard){
        return typeOfCardRepository.save(typeOfCard);
    }

    public List<Object> getAll(){
        return typeOfCardRepository.getAll();
    }

    @Transactional
    @Modifying
    public int delete(long id) {
        return typeOfCardRepository.delete(id);
    }

}
