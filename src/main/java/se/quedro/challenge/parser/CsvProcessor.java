package se.quedro.challenge.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.quedro.challenge.dto.CsvSale;
import se.quedro.challenge.dto.Sale;
import se.quedro.challenge.processors.SalesProcessor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is responsible for parce the source (CSV) file content to java object.
 * it includes method for objects verification, value updation and metadata filtering
 * which all related to process complete list of SaleObjects.
 */
public class CsvProcessor extends SalesProcessor implements SalesObjectParser {

    private final static Logger logger = LoggerFactory.getLogger(CsvProcessor.class);

    public CsvProcessor() {
    }

    /**
     * Called once per Sale to parse file to Java object.
     *
     * @param filePath The file path should be valid path.
     * @throws IOException
     */
    @Override
    public List<Sale> readAndProcessObject(String filePath) throws IOException {
        File objectFile = loadExternalObjectFile(filePath);
        List<CsvSale> beans = filteringSaleList(new CsvToBeanBuilder(new FileReader(objectFile)).withSeparator(';')
                .withType(CsvSale.class)
                .build()
                .parse());
        logger.info("Sale Objects parsing process is successfull..");
        return createCommonSaleList(beans);
    }

    /**
     * Create common Sale list from parse objects for send API
     *
     * @param cvsSaleList Objects parse from resource file.
     * @return new Sale Object list
     */
    protected List<Sale> createCommonSaleList(List<CsvSale> cvsSaleList) {
        return cvsSaleList.stream()
                .map(p -> new Sale(p.getType(), p.getSizeSqm(), p.getStartingPrice(), p.getCity(),
                        p.getStreet(), p.getFloor()))
                .collect(Collectors.toList());
    }

    /**
     * Called once per Sale to parse file to Java object.
     *
     * @param objectList The file path should be valid path.
     * @throws IOException
     */
    private List<CsvSale> filteringSaleList(List<CsvSale> objectList) {
        int recordCount = Integer.parseInt(objectList.get(objectList.size() - 1).getSizeSqm());
        List<CsvSale> filterdBeans = objectList.stream()
                .filter(c -> (!c.getType().equalsIgnoreCase("START")))
                .filter(c -> (!c.getType().equalsIgnoreCase("END")))
                .collect(Collectors.toList());

        recordCountVarification(recordCount, filterdBeans.size());
        return filterdBeans;
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
