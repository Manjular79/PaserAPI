package se.quedro.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * class is used for Json object mapping.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonSaleObjets {
    int numberOfSaleObjects;
    private List<JsonSale> saleObjects;
}
