package server.adore_server.service.client;

import javafx.css.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.client.SizeGroup;
import server.adore_server.repository.client.SizeGroupRepository;

import java.util.List;

@Service
public class SizeGroupService {

    @Autowired
    SizeGroupRepository sizeGroupRepository;

    public SizeGroup add(SizeGroup sizeGroup){
        return sizeGroupRepository.save(sizeGroup);
    }

    public List<Object> getSizeGroups(){
        return sizeGroupRepository.getAll();
    }

    @Transactional
    @Modifying
    public int delete(long id) {
        return sizeGroupRepository.delete(id);
    }

}
