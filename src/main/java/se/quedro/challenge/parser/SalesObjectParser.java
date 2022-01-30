package se.quedro.challenge.parser;

import org.springframework.stereotype.Service;
import se.quedro.challenge.dto.Sale;

import java.io.IOException;
import java.util.List;

@Service
public interface SalesObjectParser {
    List<Sale> readAndProcessObject(String filePath) throws IOException;
}
