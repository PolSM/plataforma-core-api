package api.application.controllers;

import api.application.dtos.PriceDTO;
import api.application.exceptions.PriceNotFoundException;
import api.application.services.PriceApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/price")
@Tag(name = "Price", description = "Price Managment API")
public class PriceController {
    private final PriceApplicationService priceApplicationService;

    public PriceController(PriceApplicationService priceApplicationService) {
        this.priceApplicationService = priceApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get price by product ID, brand ID, and date", description = "Returns the price details for the specified product taking priority into account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved price", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Price not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<PriceDTO> getPrice(
            @RequestParam("product_id") Integer productId,
            @RequestParam("brand_id") Integer brandId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        PriceDTO price = priceApplicationService.getPrice(productId, brandId, date)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for product ID: " + productId + " and brand ID: " + brandId));
        return ResponseEntity.ok(price);
    }
}