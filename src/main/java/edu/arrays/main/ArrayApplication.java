package edu.arrays.main;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import edu.arrays.factory.IntArrayFactory;
import edu.arrays.parser.RegexArrayLineParser;
import edu.arrays.reader.TextFileArrayDataReader;
import edu.arrays.service.ArrayImportService;
import edu.arrays.service.ArrayImportServiceImpl;
import edu.arrays.service.ArraySortService;
import edu.arrays.service.ArraySortServiceImpl;
import edu.arrays.service.ArrayStatisticsService;
import edu.arrays.service.ArrayStatisticsServiceImpl;
import edu.arrays.validator.RegexArrayLineValidator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayApplication {

  private static final Logger LOGGER = LogManager.getLogger();
  private static final String DATA_FILE_PATH = "data/arrays.txt";

  public static void main(String[] args) {
    ArrayApplication application = new ArrayApplication();
    application.run();
  }

  private void run() {
    ArrayImportService importService = new ArrayImportServiceImpl(
        new TextFileArrayDataReader(),
        new RegexArrayLineValidator(),
        new RegexArrayLineParser(),
        new IntArrayFactory());
    ArrayStatisticsService statisticsService = new ArrayStatisticsServiceImpl();
    ArraySortService sortService = new ArraySortServiceImpl();
    try {
      List<IntArrayEntity> entities = importService.importArrays(DATA_FILE_PATH);
      for (IntArrayEntity entity : entities) {
        process(entity, statisticsService, sortService);
      }
    } catch (ArrayException e) {
      LOGGER.error("Application failed", e);
    }
  }

  private void process(IntArrayEntity entity, ArrayStatisticsService statisticsService,
      ArraySortService sortService) {
    OptionalInt min = statisticsService.findMin(entity);
    OptionalInt max = statisticsService.findMax(entity);
    OptionalLong sum = statisticsService.findSum(entity);
    OptionalDouble average = statisticsService.findAverage(entity);
    LOGGER.info("{} min={} max={} sum={} average={}", entity, min, max, sum, average);

    IntArrayEntity bubbleSorted = new IntArrayEntity(entity.getElements());
    sortService.bubbleSort(bubbleSorted);
    LOGGER.info("Bubble sort: {}", bubbleSorted);

    IntArrayEntity insertionSorted = new IntArrayEntity(entity.getElements());
    sortService.insertionSort(insertionSorted);
    LOGGER.info("Insertion sort: {}", insertionSorted);
  }
}
