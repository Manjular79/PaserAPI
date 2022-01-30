package se.quedro.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class used to map the  saleObject related values to the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    String type;
    String sizeSqm;
    String startingPrice;
    String city;
    String street;
    String floor;
    String pricePerSquareMeter;
    String id;

    public Sale(String type, String sizeSqm, String startingPrice, String city, String street, String floor, String id) {
        this.type = type;
        this.sizeSqm = sizeSqm;
        this.startingPrice = startingPrice;
        this.city = city;
        this.street = street;
        this.floor = (floor != null && !floor.isEmpty()) ? floor : "1";
        this.id = id;
        this.pricePerSquareMeter = Integer.toString(Integer.parseInt(this.startingPrice) / Integer.parseInt(this.sizeSqm));
    }

    public Sale(String type, String sizeSqm, String startingPrice, String city, String street, String floor) {
        this.type = type;
        this.sizeSqm = sizeSqm;
        this.startingPrice = startingPrice;
        this.city = city;
        this.street = street;
        this.floor = (floor != null && !floor.isEmpty()) ? floor : "1";
        this.pricePerSquareMeter = Integer.toString(Integer.parseInt(this.startingPrice) / Integer.parseInt(this.sizeSqm));
    }
}
