package api.application.services;

import api.application.dtos.PriceDTO;
import api.builders.PriceBuilder;
import api.domain.model.Price;
import api.domain.ports.PriceRepository;
import api.infrastructure.persistence.PriceRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PriceApplicationServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceApplicationService priceApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    private static final LocalDateTime DATE = LocalDateTime.now();

    @Test
    void should_retrieve_a_price_and_serialize_it() {
        Price price = PriceBuilder.aPrice().build();
        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, DATE)).thenReturn(Optional.ofNullable(price));

        Optional<PriceDTO> priceDTO = priceApplicationService.getPrice(PRODUCT_ID, BRAND_ID, DATE);

        PriceDTO expectedDto = new PriceDTO(
                PRODUCT_ID,
                BRAND_ID,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new BigDecimal("35.50"),
                "EUR"
        );
        assertEquals(expectedDto, priceDTO.get());
        verify(priceRepository, times(1)).findApplicablePrice(PRODUCT_ID, BRAND_ID, DATE);
    }

    @Test
    void should_return_null_if_there_is_no_price() {
        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, DATE)).thenReturn(Optional.empty());

        Optional<PriceDTO> priceDTO = priceApplicationService.getPrice(PRODUCT_ID, BRAND_ID, DATE);

        assertTrue(priceDTO.isEmpty());
    }
}