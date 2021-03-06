package se.quedro.challenge.parser;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.quedro.challenge.dto.JsonSale;
import se.quedro.challenge.dto.JsonSaleObjets;
import se.quedro.challenge.dto.Sale;
import se.quedro.challenge.processors.SalesProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is responsible for parce the source (JSON) file content to java object.
 * it includes method for objects verificationn and metadata filtering
 * which all related to process complete list of SaleObjects.
 */
public class JsonParser extends SalesProcessor implements SalesObjectParser {

    private final static Logger logger = LoggerFactory.getLogger(JsonParser.class);

    /**
     * Called once per JSON File to parse file to Java object.
     *
     * @param filePath The file path should be valid path.
     * @throws IOException
     */
    @Override
    public List<Sale> readAndProcessObject(String filePath) throws FileNotFoundException {
        File objectFile = loadExternalObjectFile(filePath);
        Gson gson = new Gson();
        JsonSaleObjets jsonFileObjets = gson.fromJson(new FileReader(objectFile), JsonSaleObjets.class);
        int numberOfRecords = jsonFileObjets.getNumberOfSaleObjects();
        List<Sale> saleList = createCommonSaleList(jsonFileObjets.getSaleObjects());
        recordCountVarification(numberOfRecords, saleList.size());
        logger.info("Sale Objects parsing process is successfull..");
        return saleList;
    }

    /**
     * Create common Sale list from parse objects for send API
     *
     * @param saleList Objects parse from resource file.
     * @return new Sale Object list
     */
    protected List<Sale> createCommonSaleList(List<JsonSale> saleList) {
        return saleList.stream()
                .map(p -> new Sale(p.getType(), p.getSizeSqm(), p.getStartingPrice(), p.getPostalAddress().getCity(),
                        p.getPostalAddress().getStreet(), p.getPostalAddress().getFloor(), p.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Method has been created to verify individual object content verification before process
     *
     * @param filePath
     * @return if the file verification success method return true
     */
    @Override
    public boolean verifyFileContent(String filePath) {
        return true;
    }

    /**
     * Method will make standerd values for common content.
     * ex : APT as A values as "Apartment"
     *
     * @param saleList
     * @return Method will return the sale object list with update velues
     */
    @Override
    protected List<Sale> updateSaleContent(List<Sale> saleList) {

        return saleList;
    }

}
