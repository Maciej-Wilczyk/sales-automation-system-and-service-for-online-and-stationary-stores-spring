package server.adore_server.service.rarelyUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.model.clcker.Suppliers;
import server.adore_server.repository.rarelyUsed.SuppliersRepository;
import java.util.List;

@Service
public class SuppliersService {

    @Autowired
    SuppliersRepository suppliersRepository;

    public Suppliers add(Suppliers suppliers) {
        return suppliersRepository.save(suppliers);
    }

    public List<Suppliers> getSList() {
        return suppliersRepository.findAll();
    }
}
