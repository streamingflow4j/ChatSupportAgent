package chat.support.agent.utils;

import java.util.Map;


import chat.support.agent.exceptions.DenyAcessException;
import chat.support.agent.exceptions.InternalErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;


public class RestUtil {

	private static final Logger logger = LogManager.getLogger(RestUtil.class);
	
	private RestUtil() {
	}

	public static <T, P> T request(String url, HttpMethod verbo, ParameterizedTypeReference<?> typeRef, P payload, MediaType mediaType, Map<String, String> extraHeaders) throws Exception {
		if (url==null) {
			throw new IllegalArgumentException("url cant't be null");
		}
		try {
			logger.info("Calling url {}", url);
			ResponseEntity<T> response = (ResponseEntity<T>) new RestTemplate().exchange(
					url, 
					verbo,
					getHeaders(payload, mediaType, extraHeaders), 
					typeRef);

			return response.getBody();
		}
		catch (HttpClientErrorException e) {
			logger.error("Error in actuate of enpoint Status=[" + e.getStatusCode() + "]", e);
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED) || e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
				throw new DenyAcessException("Acess denied on connection with '"+shortUrl(url)+"': "+e.getMessage(), e);
			} else if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new Exception(e.getMessage());
			}
			throw e;
		}
		catch (RestClientException e) {
			logger.error("RestClientException no acionamento de enpoint", e);
			throw new InternalErrorException("Connection declined '"+shortUrl(url)+"'. Error: "+e.getMessage(), e);
		}
	}

	public static <T, P> T chamar(String url, HttpMethod verbo, ParameterizedTypeReference<?> typeRef, P payload, MediaType mediaType) throws Exception {
		return request(url, verbo, typeRef, payload, mediaType, null);
	}

	public static <T, P> T chamar(String url, HttpMethod verbo, ParameterizedTypeReference<?> typeRef, P payload, Map<String, String> extraHeaders) throws Exception {
		return request(url, verbo, typeRef, payload, null, extraHeaders);
	}

	public static <T> T request(String url, HttpMethod verbo, ParameterizedTypeReference<?> typeRef, Map<String, String> extraHeaders) throws Exception {
		return request(url, verbo, typeRef, null, null, extraHeaders);
	}

	public static <T, P> T request(String url, HttpMethod verbo, ParameterizedTypeReference<?> typeRef, P payload) throws Exception {
		return request(url, verbo, typeRef, payload, null, null);
	}

	public static <T> T chamar(String url, HttpMethod verbo, ParameterizedTypeReference<?> typeRef) throws Exception {
		return request(url, verbo, typeRef, null);
	}
	
	private static String shortUrl(String url) {
		if (StringUtils.isEmpty(url)) return "";
		int endIndex = url.length()>100 ? 100 : url.length();
		return url.substring(0, endIndex) + (endIndex+1 < url.length() ? "..." : "");
	}
	
	private static <T> HttpEntity<T> getHeaders(T body, MediaType mediaType, Map<String, String> headersMap) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType == null ? MediaType.APPLICATION_JSON : mediaType);
		if (headersMap!=null) {
			for (var entry : headersMap.entrySet()) {
			    headers.set(entry.getKey(), entry.getValue());
			}
		}
		return new HttpEntity<>(body, headers);
	}


}
