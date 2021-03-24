package server.adore_server.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.ClientType;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.repository.client.ClientTypeRepository;
import server.adore_server.repository.client.SizeGroupRepository;

import java.util.List;

@Service
public class ClientTypeService {

    @Autowired
    ClientTypeRepository clientTypeRepository;

    public ClientType add(ClientType clientType){

        return clientTypeRepository.save(clientType);
    }

    public List<Object> getClientType(){
        return clientTypeRepository.getAll();
    }
    @Transactional
    @Modifying
    public int delete(long id) {
        return clientTypeRepository.delete(id);
    }


}
