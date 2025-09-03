package chat.support.agent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${server.endpoint}")
    private String endpointServer;

    public String getEndpointServer(){
        return endpointServer;
    }


}
