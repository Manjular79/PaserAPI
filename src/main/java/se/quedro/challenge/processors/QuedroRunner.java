package se.quedro.challenge.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.quedro.challenge.dto.Sale;
import se.quedro.challenge.parser.ParserFactory;
import se.quedro.challenge.parser.SalesObjectParser;
import se.quedro.challenge.service.SaleObjectConsumer;
import se.quedro.challenge.service.SaleObjectConsumerImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class QuedroRunner {

    private final static Logger logger = LoggerFactory.getLogger(QuedroRunner.class);

    @Autowired
    private SaleObjectConsumer saleObjectConsumer;

    /**
     * Method is used to call main functionality of the sale object process.
     *
     * @param fileName source file name and location with complete has to be given
     * @throws Exception
     */
    public void runReporter(String fileName) throws Exception {

        ParserFactory parserFactory = new ParserFactory();
        SalesObjectParser parser = parserFactory.getParser(getFileTypeByFileName(fileName));
        List<Sale> saleObjectsList = parser.readAndProcessObject(fileName);
        saleObjectConsumer = new SaleObjectConsumerImpl();
        String priorityOrderAttribute = saleObjectConsumer.getPriorityOrderAttribute().toString();

        logger.info("RecordCount,priorityOrderAttribute {} ", saleObjectsList.size() + " " + priorityOrderAttribute);

        /**
         * Code segment has been used to order the sale elements according to the given priority parameter.
         */
        switch (priorityOrderAttribute.toUpperCase()) {
            case "CITY":
                Collections.sort(saleObjectsList, Comparator.comparing(s -> s.getCity()));
                break;
            case "SQUAREMETERS":
                Collections.sort(saleObjectsList, Comparator.comparing(s -> s.getSizeSqm()));
                break;
            case "PRICEPERSQUAREMETER":
                Collections.sort(saleObjectsList, new Comparator<Sale>() {
                    @Override
                    public int compare(Sale o1, Sale o2) {
                        return Integer.compare(Integer.parseInt(o1.getPricePerSquareMeter())
                                , Integer.parseInt(o2.getPricePerSquareMeter()));
                    }
                });
        }

        long recCount = callToAPI(saleObjectsList);
        logger.info("Process is successfully completed. Final record count {} ", recCount);
    }

    /**
     * Method used to Report Sale via API to the external systems.
     *
     * @param saleObjectsList
     * @return completed process count has been returnd.
     */
    private long callToAPI(List<Sale> saleObjectsList) {
        long recCount = 0;
        for (Sale saleObject : saleObjectsList) {
            logger.debug("Completed Record Count {} ", saleObject.toString());
            saleObjectConsumer.startSaleObjectTransaction();
            saleObjectConsumer.reportSaleObject(Integer.parseInt(saleObject.getSizeSqm()), saleObject.getPricePerSquareMeter(), saleObject.getCity(), saleObject.getStreet(), Integer.parseInt(saleObject.getFloor()));
            /* The method which was highlighted in document was not in the API.*/
            //saleObjectConsumer.endsaletransaction();
            recCount++;
        }
        saleObjectConsumer.commitSaleObjectTransaction();
        return recCount;
    }

    /**
     * Method has been used to extract the file type from the file name.
     *
     * @param fileName complete path name file name with extension.
     * @return
     */
    private String getFileTypeByFileName(String fileName) {
        StringBuilder builder = new StringBuilder(fileName);
        return builder.substring(builder.indexOf(".") + 1);
    }
}