package server.adore_server.service.tokens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.repository.tokens.BaseLinkerTokenRepository;

@Service
public class BaseLinkerTokenService {
    @Autowired
    BaseLinkerTokenRepository baseLinkerTokenRepository;


    public String getData(){
        return  baseLinkerTokenRepository.getData(1).getToken();
    }
}
