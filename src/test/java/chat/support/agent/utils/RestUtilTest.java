package chat.support.agent.utils;

import org.jetbrains.annotations.NotNull;
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
        assertEquals(getString() +"""
                <!doctype html>
                <html>
                <head>
                    <title>Example Domain</title>
                
                    <meta charset="utf-8" />
                    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1" />
                    <style type="text/css">
                    body {
                        background-color: #f0f0f2;
                        margin: 0;
                        padding: 0;
                        font-family: -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
                       \s
                    }
                    div {
                        width: 600px;
                        margin: 5em auto;
                        padding: 2em;
                        background-color: #fdfdff;
                        border-radius: 0.5em;
                        box-shadow: 2px 3px 7px 2px rgba(0,0,0,0.02);
                    }
                    a:link, a:visited {
                        color: #38488f;
                        text-decoration: none;
                    }
                    @media (max-width: 700px) {
                        div {
                            margin: 0 auto;
                            width: auto;
                        }
                    }
                    </style>   \s
                </head>
                
                <body>
                <div>
                    <h1>Example Domain</h1>
                    <p>This domain is for use in illustrative examples in documents. You may use this
                    domain in literature without prior coordination or asking for permission.</p>
                    <p><a href="https://www.iana.org/domains/example">More information...</a></p>
                </div>
                </body>
                </html>
                """, result);
    }

    private static @NotNull String getString() {
        return "";
    }

    @Test
    void testRequest_Unauthorized() {
        String url = "http://example.com";
        HttpMethod method = HttpMethod.GET;
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(eq(url), eq(method), any(HttpEntity.class), eq(typeRef)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

    }

    @Test
    void testRequest_RestClientException() {
        String url = "http://example.com";
        HttpMethod method = HttpMethod.GET;
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(eq(url), eq(method), any(HttpEntity.class), eq(typeRef)))
                .thenThrow(new RestClientException("Error"));
    }

    @Test
    void testRequest_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RestUtil.request(null, HttpMethod.GET, new ParameterizedTypeReference<>() {}, null, null, null);
        });
    }
}
