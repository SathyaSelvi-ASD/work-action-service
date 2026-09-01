package com.vbox.workaction.config;


import com.vbox.workaction.api.dto.response.ApiResponse;
import com.vbox.workaction.api.dto.response.ApiMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.util.Map;

@Component
public class DisclosureClient {

    private static final Logger log = LoggerFactory.getLogger(DisclosureClient.class);

    private final RestClient restClient;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${disclosure.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${disclosure.service.search-url:/api/disclosures/v1/search}")
    private String searchUrl;

    public DisclosureClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ApiResponse search(Map<String, Object> request) {
        try {
            ApiResponse resp = restClient.post()
                    .uri(baseUrl + searchUrl)
                    .body(request)
                    .retrieve()
                    .body(ApiResponse.class);

            if (resp == null) {
                var apiMessage = new ApiMessage("EMPTY_RESPONSE", "Empty response from disclosure service");
                return new ApiResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Empty response from disclosure service", List.of(apiMessage), List.of(), null);
            }

            if (resp.statusCode() == HttpStatus.OK.value()) {
                return resp;
            }

            // Remote returned a non-success status inside ApiResponse - propagate as ApiResponse with errors preserved or synthesized
            List<ApiMessage> errors = resp.errors() != null ? resp.errors() : List.of(new ApiMessage("DISCLOSURE_SERVICE_ERROR", resp.message()));
            return new ApiResponse("ERROR", resp.statusCode(), resp.message() != null ? resp.message() : "Disclosure service returned error", errors, resp.warnings(), resp.data());
        } catch (RestClientException ex) {
            // Network / client errors (e.g., I/O, timeouts) - try to extract a concise message from downstream response body if present
            log.error("Disclosure service unavailable: {}", baseUrl + searchUrl, ex);
            String downstreamMsg = ex.getMessage();
            String extractedMessage = null;
            try {
                if (downstreamMsg != null) {
                    int idx = downstreamMsg.indexOf('{');
                    if (idx >= 0) {
                        String jsonPart = downstreamMsg.substring(idx);
                        // attempt to parse the JSON body and extract a user-friendly message or first error message
                        ApiResponse parsed = objectMapper.readValue(jsonPart, ApiResponse.class);
                        if (parsed != null) {
                            if (parsed.message() != null && !parsed.message().isEmpty()) {
                                extractedMessage = parsed.message();
                            } else if (parsed.errors() != null && !parsed.errors().isEmpty()) {
                                extractedMessage = parsed.errors().get(0).message();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("Failed to parse downstream error body: {}", downstreamMsg, e);
            }
            String errMsg = extractedMessage != null ? extractedMessage : (downstreamMsg != null ? downstreamMsg : "Disclosure service unavailable");
            var apiMessage = new ApiMessage("DISCLOSURE_SERVICE_UNAVAILABLE", errMsg);
            // set top-level message to a concise statement including any extracted downstream message
            String topLevelMessage = "Work Action disclosure service is unavailable." + (extractedMessage != null ? " " + extractedMessage : "");
            return new ApiResponse("ERROR", HttpStatus.BAD_GATEWAY.value(), topLevelMessage, List.of(apiMessage), List.of(), null);
        } catch (Exception ex) {
            // Fallback for any other unexpected exception
            log.error("Unexpected error while calling disclosure service", ex);
            var apiMessage = new ApiMessage("DISCLOSURE_SERVICE_ERROR", ex.getMessage() != null ? ex.getMessage() : "Unexpected error");
            return new ApiResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to call disclosure service search endpoint", List.of(apiMessage), List.of(), null);
        }
    }
}