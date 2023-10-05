package memotyou;

import org.springframework.stereotype.Component;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@Component
public class ChatGptRequestDAO {
        private static final String apiKey = "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";
        private static final String chatGptEndpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
        private final RestTemplate restTemplate;

        public ChatGptRequestDAO(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public ResponseEntity<String> sendChatGptRequest(String prompt) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + apiKey);
                headers.set("Content-Type", "application/json");

                String requestBody = "{\"prompt\": \"" + prompt + "\"}";

                HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

                return restTemplate.postForEntity(chatGptEndpoint, request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }
    }


