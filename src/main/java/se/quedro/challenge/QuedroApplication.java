package se.quedro.challenge;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import se.quedro.challenge.processors.QuedroRunner;

@SpringBootApplication
@ComponentScan({"se.quedro.challenge.service"})
@Configuration
public class QuedroApplication {

    private final static Logger logger = LoggerFactory.getLogger(QuedroApplication.class);

    @Value("${quedro.accepted.file.types}")
    private String acceptedFileTypes = "json|csv|xml";

    public static void main(String[] args) {
        SpringApplication.run(QuedroApplication.class, args);
        logger.info("Starting application...!");
        QuedroApplication quedroApplication = new QuedroApplication();
        if (quedroApplication.validateArguments(args[0])) {
            try {
                logger.info("Application started with file neme {} ", args[0]);
                QuedroRunner runner = new QuedroRunner();
                runner.runReporter(args[0]);
            } catch (Exception e) {
                logger.error("Processing is unsuccessful", e);
            }
        } else {
            logger.info("File name is incorrect please try again with correct format ex: FileName.extension {} ", args[0]);
        }
    }

    private boolean validateArguments(String args) {
        if (args.isEmpty())
            return false;
        String fileRegexPattern = "^[a-zA-Z0-9._ -]+\\.(" + acceptedFileTypes + ")$";
        System.out.println(acceptedFileTypes);
        return args.matches(fileRegexPattern);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}


