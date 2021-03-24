package server.adore_server.service.tokens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.repository.tokens.TokenRefreshDataRepository;

@Service
public class TokenRefreshDataService {
    @Autowired
    TokenRefreshDataRepository tokenRefreshDataRepository;
    public String getData(){
        return  tokenRefreshDataRepository.getData(1).getDataToAdore();
    }

    public String getLogin(){
        return  tokenRefreshDataRepository.getLogin(1).getLogin();
        //return  tokenRefreshDataRepository.find(1).getLogin();
    }
}
