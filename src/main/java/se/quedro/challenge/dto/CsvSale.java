package se.quedro.challenge.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class is created for map the reource values to the java objetcs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CsvSale {
    @CsvBindByPosition(position = 0)
    String type;
    @CsvBindByPosition(position = 1)
    String sizeSqm;
    @CsvBindByPosition(position = 2)
    String startingPrice;
    @CsvBindByPosition(position = 3)
    String city;
    @CsvBindByPosition(position = 4)
    String street;
    @CsvBindByPosition(position = 5)
    String floor;
    String pricePerSquareMeter;

}
