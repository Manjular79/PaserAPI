package se.quedro.challenge.parser;

import se.quedro.challenge.dto.FileType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Main factory has been used to create objects for each file types.
 */
public class ParserFactory {

    final static Map<String, Supplier<SalesObjectParser>> map = new HashMap<>();

    static {
        map.put(FileType.CSV.toString(), CsvProcessor::new);
        map.put(FileType.JSON.toString(), JsonParser::new);
    }

    public SalesObjectParser getParser(String saleType) {
        Supplier<SalesObjectParser> parserSupplier = map.get(saleType.toUpperCase());
        if (parserSupplier != null) {
            return parserSupplier.get();
        }
        throw new IllegalArgumentException("No such Parser " + saleType.toUpperCase());
    }
}
