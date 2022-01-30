package se.quedro.challenge.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.quedro.challenge.dto.Sale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public abstract class SalesProcessor {
    private final static Logger logger = LoggerFactory.getLogger(SalesProcessor.class);


    protected abstract boolean verifyFileContent(String filePath);

    protected abstract List<Sale> updateSaleContent(List<Sale> beans);

    /**
     * Method is used to verify the source file record count with extracted sale record count
     *
     * @param recordCount
     * @param fileCount
     */
    protected void recordCountVarification(int recordCount, int fileCount) {
        logger.info("Record count verification started");
        if (recordCount != fileCount)
            throw new RuntimeException("Recornd count not maching !");
    }

    /**
     * Method contain verification for loading external file to the system
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    protected File loadExternalObjectFile(String filePath) throws FileNotFoundException {
        logger.info("File verification started");
        if (!verifyFileContent(filePath))
            throw new FileNotFoundException("Given file path not valid ! : " + filePath);

        if (!new File(filePath).exists())
            throw new FileNotFoundException("Path file give has been not found ! : " + filePath);

        return new File(filePath);
    }

    /**
     * Method is created for print the list of sale objects and (developer testing purpose)
     *
     * @param beans
     */
    protected void printSaleObjectList(List<Sale> beans) {
        beans.forEach(System.out::println);
    }
}
