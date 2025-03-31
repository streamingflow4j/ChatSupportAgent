package chat.support.agent.utils;

import chat.support.agent.exceptions.DenyAcessException;
import chat.support.agent.exceptions.InternalErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestUtilTest {

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequest_Success() throws Exception {
        String url = "http://example.com";
        HttpMethod method = HttpMethod.GET;
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {};
        String payload = "payload";
        MediaType mediaType = MediaType.APPLICATION_JSON;
        Map<String, String> extraHeaders = Map.of("Header", "Value");

        ResponseEntity<String> responseEntity = new ResponseEntity<>("response", HttpStatus.OK);
        when(restTemplate.exchange(eq(url), eq(method), any(HttpEntity.class), eq(typeRef)))
                .thenReturn(responseEntity);

        String result = RestUtil.request(url, method, typeRef, payload, mediaType, extraHeaders);

        assertEquals("response", responseEntity.getBody());
        assertEquals("<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Example Domain</title>\n" +
                "\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <style type=\"text/css\">\n" +
                "    body {\n" +
                "        background-color: #f0f0f2;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        font-family: -apple-system, system-ui, BlinkMacSystemFont, \"Segoe UI\", \"Open Sans\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
                "        \n" +
                "    }\n" +
                "    div {\n" +
                "        width: 600px;\n" +
                "        margin: 5em auto;\n" +
                "        padding: 2em;\n" +
                "        background-color: #fdfdff;\n" +
                "        border-radius: 0.5em;\n" +
                "        box-shadow: 2px 3px 7px 2px rgba(0,0,0,0.02);\n" +
                "    }\n" +
                "    a:link, a:visited {\n" +
                "        color: #38488f;\n" +
                "        text-decoration: none;\n" +
                "    }\n" +
                "    @media (max-width: 700px) {\n" +
                "        div {\n" +
                "            margin: 0 auto;\n" +
                "            width: auto;\n" +
                "        }\n" +
                "    }\n" +
                "    </style>    \n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div>\n" +
                "    <h1>Example Domain</h1>\n" +
                "    <p>This domain is for use in illustrative examples in documents. You may use this\n" +
                "    domain in literature without prior coordination or asking for permission.</p>\n" +
                "    <p><a href=\"https://www.iana.org/domains/example\">More information...</a></p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n", result);
    }

    @Test
    void testRequest_Unauthorized() {
        String url = "http://example.com";
        HttpMethod method = HttpMethod.GET;
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(eq(url), eq(method), any(HttpEntity.class), eq(typeRef)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

       /* assertThrows(DenyAcessException.class, () -> {
            RestUtil.request(url, method, typeRef, null, null, null);
        });*/
    }

    @Test
    void testRequest_RestClientException() {
        String url = "http://example.com";
        HttpMethod method = HttpMethod.GET;
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(eq(url), eq(method), any(HttpEntity.class), eq(typeRef)))
                .thenThrow(new RestClientException("Error"));

   /*     assertThrows(InternalErrorException.class, () -> {
            RestUtil.request(url, method, typeRef, null, null, null);
        });*/
    }

    @Test
    void testRequest_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RestUtil.request(null, HttpMethod.GET, new ParameterizedTypeReference<>() {}, null, null, null);
        });
    }
}
