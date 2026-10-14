package edu.arrays.service;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import edu.arrays.factory.ArrayFactory;
import edu.arrays.parser.ArrayLineParser;
import edu.arrays.reader.ArrayDataReader;
import edu.arrays.validator.ArrayLineValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayImportServiceImpl implements ArrayImportService {

  private static final Logger LOGGER = LogManager.getLogger();

  private final ArrayDataReader reader;
  private final ArrayLineValidator validator;
  private final ArrayLineParser parser;
  private final ArrayFactory factory;

  public ArrayImportServiceImpl(ArrayDataReader reader, ArrayLineValidator validator,
      ArrayLineParser parser, ArrayFactory factory) {
    this.reader = reader;
    this.validator = validator;
    this.parser = parser;
    this.factory = factory;
  }

  @Override
  public List<IntArrayEntity> importArrays(String path) throws ArrayException {
    List<String> lines = reader.readLines(path);
    List<IntArrayEntity> entities = new ArrayList<>();
    int lineNumber = 0;
    for (String line : lines) {
      lineNumber++;
      if (validator.isValid(line)) {
        int[] elements = parser.parse(line);
        IntArrayEntity entity = factory.create(elements);
        entities.add(entity);
      } else {
        LOGGER.warn("Line {} skipped as invalid: '{}'", lineNumber, line);
      }
    }
    LOGGER.info("Created {} arrays from {} lines", entities.size(), lines.size());
    return entities;
  }
}
