package api.application.controllers;

import api.application.dtos.PriceDTO;
import api.application.exceptions.PriceNotFoundException;
import api.application.services.PriceApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    public static final String CURRENCY = "EUR";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceApplicationService priceApplicationService;

    @ParameterizedTest
    @MethodSource("priceProvider")
    @DisplayName("Should return 200 OK with correct price data for valid inputs")
    public void should_return_OK_given_prices(String dateTime, PriceDTO expectedDto) throws Exception {
        LocalDateTime date = LocalDateTime.parse(dateTime);

        when(priceApplicationService.getPrice(PRODUCT_ID, BRAND_ID, date))
                .thenReturn(Optional.of(expectedDto));

        mockMvc.perform(get("/price")
                        .param("product_id", String.valueOf(PRODUCT_ID))
                        .param("brand_id", String.valueOf(BRAND_ID))
                        .param("date", date.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(expectedDto.getProductId()))
                .andExpect(jsonPath("$.brandId").value(expectedDto.getBrandId()))
                .andExpect(jsonPath("$.price").value(expectedDto.getPrice()))
                .andExpect(jsonPath("$.currency").value(expectedDto.getCurrency()))
                .andExpect(jsonPath("$.startDate").value(expectedDto.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(expectedDto.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @ParameterizedTest
    @MethodSource("priceNotFoundProvider")
    @DisplayName("Should return 404 Not Found for dates with no applicable price")
    public void should_return_KO_when_not_found(String dateTime) throws Exception {
        LocalDateTime date = LocalDateTime.parse(dateTime);
        String expectedMessage = "Price not found for product ID: " + PRODUCT_ID + " and brand ID: " + BRAND_ID;

        when(priceApplicationService.getPrice(PRODUCT_ID, BRAND_ID, date))
                .thenThrow(new PriceNotFoundException(expectedMessage));

        mockMvc.perform(get("/price")
                        .param("product_id", String.valueOf(PRODUCT_ID))
                        .param("brand_id", String.valueOf(BRAND_ID))
                        .param("date", date.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    static Stream<Arguments> priceProvider() {
        return Stream.of(
                Arguments.of("2020-06-14T10:00:00", new PriceDTO(PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), BigDecimal.valueOf(35.50), CURRENCY)),
                Arguments.of("2020-06-14T16:00:00", new PriceDTO(PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-14T15:00:00"), LocalDateTime.parse("2020-06-14T18:30:00"), BigDecimal.valueOf(25.45), CURRENCY)),
                Arguments.of("2020-06-14T21:00:00", new PriceDTO(PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), BigDecimal.valueOf(35.50), CURRENCY)),
                Arguments.of("2020-06-15T10:00:00", new PriceDTO(PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-15T00:00:00"), LocalDateTime.parse("2020-06-15T11:00:00"), BigDecimal.valueOf(30.50), CURRENCY)),
                Arguments.of("2020-06-16T21:00:00", new PriceDTO(PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-15T16:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), BigDecimal.valueOf(38.95), CURRENCY))
        );
    }

    static Stream<Arguments> priceNotFoundProvider() {
        return Stream.of(
                Arguments.of("2019-12-31T23:59:59"),
                Arguments.of("2025-01-01T00:00:00")
        );
    }
}