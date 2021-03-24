package server.adore_server.service.rarelyUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.model.clcker.Deleted;
import server.adore_server.repository.rarelyUsed.DeletedRepository;

@Service
public class DeletedService {
@Autowired
    DeletedRepository deletedRepository;
    public Deleted addToDeleted(Deleted deleted) throws Exception {
        return deletedRepository.save(deleted);
    }


}
