package chat.support.agent.langchain4j;

import chat.support.agent.utils.RestUtil;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.logging.Logger;


public class Lang4jTools {
    /*
    static Logger logger = Logger.getLogger(String.valueOf(Lang4jTools.class));

    private static final String BASE_URI = "http://localhost:8080/rabbitmq";

    public Lang4jTools(){}

    @Tool("Deletion for a defined Rule with API structure body")
    public void deleteRule(@P("Structure for Deletion body") String body) throws Exception {
        logger.warning("Tool executed: ===> "+body);
        chamarRestEndPoint(BASE_URI+"/rule/delete", HttpMethod.POST,
                new ParameterizedTypeReference<String>() {}, body);
    }

    @Tool("Update for a defined Rule with API structure body")
    public void updateRule(@P("Structure for Update body defined") String body) throws Exception {
        logger.warning("Tool executed: ===> "+body);
        chamarRestEndPoint(BASE_URI+"/rule/update", HttpMethod.POST,
                new ParameterizedTypeReference<String>() {}, body);
    }

    @Tool("Create a new Rule with API structure body")
    public void createRule(@P("Structure for new Rule body") String body) throws Exception {
        logger.warning("Tool executed: ===> "+body);
        chamarRestEndPoint(BASE_URI+"/rule/create", HttpMethod.POST,
                new ParameterizedTypeReference<String>() {}, body);
    }

    @Tool("Create event with API structure body")
    public void createEvent(@P("Structure for Create event body")String body) throws Exception {
        logger.warning("Tool executed: ===> "+body);
        chamarRestEndPoint(BASE_URI+"/event/create", HttpMethod.POST,
                new ParameterizedTypeReference<String>() {}, body);
    }

    @Tool("Create data input for defined event with API structure body")
    public void createData(@P("Structure for Create data input body") String body) throws Exception {
        logger.warning("Tool executed: ===> "+body);
        chamarRestEndPoint(BASE_URI+"/data/create", HttpMethod.POST,
                new ParameterizedTypeReference<String>() {}, body);
    }

    private <T, P> T chamarRestEndPoint(String endpoint,
                                        HttpMethod verbo,
                                        ParameterizedTypeReference<?> typeRef,
                                        P payload) throws Exception {
        return RestUtil.request(endpoint,verbo,typeRef,payload);
    }
*/
}
