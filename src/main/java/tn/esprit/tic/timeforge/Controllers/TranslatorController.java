package tn.esprit.tic.timeforge.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/translate")
public class TranslatorController {

    private static final Logger logger = LoggerFactory.getLogger(TranslatorController.class);

    @Value("${rapidapi.url}")
    private String apiUrl;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<String> translateText(@RequestBody Map<String, String> request) {
        try {
            String text = request.get("text");
            String sep = request.getOrDefault("sep", "|");

            // Langues par défaut
            String fromLang = request.getOrDefault("from", "en");
            String toLang = request.getOrDefault("to", "fr");

            logger.info("🔍 Requête reçue: text={}, sep={}, from={}, to={}", text, sep, fromLang, toLang);

            // Remplacement dynamique de la langue dans l'URL
            String fullApiUrl = apiUrl
                    .replace("from=en", "from=" + fromLang)
                    .replace("to=fr", "to=" + toLang);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-RapidAPI-Key", rapidApiKey);
            headers.set("X-RapidAPI-Host", rapidApiHost);

            // Construction du JSON
            String requestBody = String.format("{\"sep\":\"%s\", \"text\":\"%s\"}", sep, text);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(fullApiUrl, requestEntity, String.class);

            logger.info("✅ Réponse reçue: {}", responseEntity.getBody());

            return ResponseEntity.ok(responseEntity.getBody());

        } catch (Exception e) {
            logger.error("❌ ERREUR API: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la traduction.");
        }
    }
}
