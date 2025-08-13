package api.acceptance;

import api.application.dtos.PriceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceAcceptanceTest {

    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    @Autowired
    private TestRestTemplate restTemplate;

    static Stream<Arguments> acceptanceCriteriaOK() {
        return Stream.of(
                // Test 1: Petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA).
                Arguments.of("2020-06-14T10:00:00", new BigDecimal("35.50"), LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59")),
                // Test 2: Petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA).
                Arguments.of("2020-06-14T16:00:00", new BigDecimal("25.45"), LocalDateTime.parse("2020-06-14T15:00:00"), LocalDateTime.parse("2020-06-14T18:30:00")),
                // Test 3: Petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA).
                Arguments.of("2020-06-14T21:00:00", new BigDecimal("35.50"), LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59")),
                // Test 4: Petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA).
                Arguments.of("2020-06-15T10:00:00", new BigDecimal("30.50"), LocalDateTime.parse("2020-06-15T00:00:00"), LocalDateTime.parse("2020-06-15T11:00:00")),
                // Test 5: Petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA).
                Arguments.of("2020-06-16T21:00:00", new BigDecimal("38.95"), LocalDateTime.parse("2020-06-15T16:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"))
        );
    }

    static Stream<Arguments> acceptanceCriteriaNotFound() {
        return Stream.of(
                Arguments.of("2019-12-31T23:59:59"),
                Arguments.of("2025-01-01T00:00:00")
        );
    }

    @ParameterizedTest
    @MethodSource("acceptanceCriteriaOK")
    @DisplayName("Should return 200 OK with the correct price for valid requests")
    void should_return_correct_price_e2e(String dateTime, BigDecimal expectedPrice, LocalDateTime expectedStartDate, LocalDateTime expectedEndDate) {
        String url = UriComponentsBuilder.fromPath("/price")
                .queryParam("product_id", PRODUCT_ID)
                .queryParam("brand_id", BRAND_ID)
                .queryParam("date", dateTime)
                .toUriString();

        ResponseEntity<PriceDTO> response = restTemplate.getForEntity(url, PriceDTO.class);

        PriceDTO responseBody = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(responseBody.getBrandId()).isEqualTo(BRAND_ID);
        assertThat(responseBody.getStartDate()).isEqualTo(expectedStartDate);
        assertThat(responseBody.getEndDate()).isEqualTo(expectedEndDate);
        assertThat(responseBody.getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @ParameterizedTest
    @MethodSource("acceptanceCriteriaNotFound")
    @DisplayName("Should return 404 Not Found for dates with no applicable price")
    void should_return_404_not_found(String dateTime) {
        String url = UriComponentsBuilder.fromPath("/price")
                .queryParam("product_id", PRODUCT_ID)
                .queryParam("brand_id", BRAND_ID)
                .queryParam("date", dateTime)
                .toUriString();

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("message")).asString().contains("Price not found");
    }

}