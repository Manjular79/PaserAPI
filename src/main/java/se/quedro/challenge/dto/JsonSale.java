package se.quedro.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class is used for Json object mapping.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonSale {
    String type;
    String id;
    String sizeSqm;
    String startingPrice;
    PostalAddress postalAddress;
}
