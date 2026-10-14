package edu.arrays.main;

import edu.arrays.comparator.IntArrayComparators;
import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import edu.arrays.factory.IntArrayFactory;
import edu.arrays.observer.ArrayObserver;
import edu.arrays.parser.RegexArrayLineParser;
import edu.arrays.reader.TextFileArrayDataReader;
import edu.arrays.repository.ArrayRepository;
import edu.arrays.repository.ArrayRepositoryProvider;
import edu.arrays.service.ArrayImportService;
import edu.arrays.service.ArrayImportServiceImpl;
import edu.arrays.service.ArraySortService;
import edu.arrays.service.ArraySortServiceImpl;
import edu.arrays.service.ArrayStatisticsService;
import edu.arrays.service.ArrayStatisticsServiceImpl;
import edu.arrays.specification.ArrayAggregate;
import edu.arrays.specification.ArraySpecifications;
import edu.arrays.specification.Comparison;
import edu.arrays.specification.Specification;
import edu.arrays.validator.RegexArrayLineValidator;
import edu.arrays.warehouse.Warehouse;
import edu.arrays.warehouse.WarehouseSynchronizer;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ArrayApplication {

  private static final Logger LOGGER = LogManager.getLogger();
  private static final String DATA_FILE_PATH = "data/arrays.txt";
  private static final int FIRST_INDEX = 0;
  private static final int SEARCHED_SIZE = 4;
  private static final int SEARCHED_ELEMENT = 7;
  private static final long SMALL_SUM_BOUND = 50;
  private static final double AVERAGE_BOUND = 5.0;
  private static final int MAX_BOUND = 40;
  private static final int CHANGED_VALUE = 999;

  public static void main(String[] args) {
    ArrayApplication application = new ArrayApplication();
    application.run();
  }

  private void run() {
    ArrayRepository repository = ArrayRepositoryProvider.getRepository();
    Warehouse warehouse = Warehouse.getInstance();
    ArrayObserver synchronizer = new WarehouseSynchronizer(warehouse);
    repository.addObserver(synchronizer);

    ArrayImportService importService = createImportService();
    ArrayStatisticsService statisticsService = new ArrayStatisticsServiceImpl();
    ArraySortService sortService = new ArraySortServiceImpl();
    try {
      List<IntArrayEntity> imported = importService.importArrays(DATA_FILE_PATH);
      store(repository, imported);
      processEach(repository, statisticsService, sortService);
      logSortedCollections(repository);
      logSearches(repository);
      logChange(repository, warehouse);
      logRemoval(repository, warehouse);
    } catch (ArrayException e) {
      LOGGER.error("Application failed", e);
    }
  }

  private ArrayImportService createImportService() {
    return new ArrayImportServiceImpl(new TextFileArrayDataReader(), new RegexArrayLineValidator(),
        new RegexArrayLineParser(), new IntArrayFactory());
  }

  private void store(ArrayRepository repository, List<IntArrayEntity> entities) {
    for (IntArrayEntity entity : entities) {
      boolean added = repository.add(entity);
      UUID id = entity.getId();
      LOGGER.info("Stored array {} with id {}, added: {}", entity, id, added);
    }
  }

  private void processEach(ArrayRepository repository, ArrayStatisticsService statisticsService,
      ArraySortService sortService) {
    List<IntArrayEntity> entities = repository.findAll();
    for (IntArrayEntity entity : entities) {
      process(entity, statisticsService, sortService);
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

  private void logSortedCollections(ArrayRepository repository) {
    Comparator<IntArrayEntity> byId = IntArrayComparators.BY_ID;
    Comparator<IntArrayEntity> bySize = IntArrayComparators.BY_SIZE;
    Comparator<IntArrayEntity> byFirstElement = IntArrayComparators.BY_FIRST_ELEMENT;
    logSorted(repository, "by id", byId);
    logSorted(repository, "by size", bySize);
    logSorted(repository, "by first element", byFirstElement);
  }

  private void logSorted(ArrayRepository repository, String description,
      Comparator<IntArrayEntity> comparator) {
    List<IntArrayEntity> sorted = repository.sortBy(comparator);
    LOGGER.info("Sorted {}: {}", description, sorted);
  }

  private void logSearches(ArrayRepository repository) {
    Comparison greater = Comparison.GREATER;
    Comparison less = Comparison.LESS;
    Comparison equal = Comparison.EQUAL;
    Specification<ArrayAggregate> bySize = ArraySpecifications.sizeEquals(SEARCHED_SIZE);
    Specification<ArrayAggregate> byElement = ArraySpecifications.containsElement(SEARCHED_ELEMENT);
    Specification<ArrayAggregate> bySum = ArraySpecifications.sum(greater, SMALL_SUM_BOUND);
    Specification<ArrayAggregate> byAverage = ArraySpecifications.average(less, AVERAGE_BOUND);
    Specification<ArrayAggregate> byMax = ArraySpecifications.max(equal, MAX_BOUND);
    logSearchById(repository);
    logSearch(repository, "size " + SEARCHED_SIZE, bySize);
    logSearch(repository, "element " + SEARCHED_ELEMENT, byElement);
    logSearch(repository, "sum greater than " + SMALL_SUM_BOUND, bySum);
    logSearch(repository, "average less than " + AVERAGE_BOUND, byAverage);
    logSearch(repository, "max equal to " + MAX_BOUND, byMax);
  }

  private void logSearchById(ArrayRepository repository) {
    List<IntArrayEntity> entities = repository.findAll();
    boolean hasEntities = !entities.isEmpty();
    if (hasEntities) {
      IntArrayEntity first = entities.get(FIRST_INDEX);
      UUID searchedId = first.getId();
      Specification<ArrayAggregate> specification = ArraySpecifications.idEquals(searchedId);
      logSearch(repository, "id " + searchedId, specification);
    }
  }

  private void logSearch(ArrayRepository repository, String description,
      Specification<ArrayAggregate> specification) {
    List<IntArrayEntity> found = repository.findBySpecification(specification);
    LOGGER.info("Found by {}: {} arrays, {}", description, found.size(), found);
  }

  private void logChange(ArrayRepository repository, Warehouse warehouse) throws ArrayException {
    List<IntArrayEntity> entities = repository.findAll();
    boolean hasEntities = !entities.isEmpty();
    if (hasEntities) {
      IntArrayEntity entity = entities.get(FIRST_INDEX);
      UUID id = entity.getId();
      OptionalLong sumBefore = warehouse.findSum(entity);
      entity.setElement(FIRST_INDEX, CHANGED_VALUE);
      OptionalLong sumAfter = warehouse.findSum(entity);
      LOGGER.info("Sum of array {} was {}, after the element change is {}", id, sumBefore, sumAfter);
    }
  }

  private void logRemoval(ArrayRepository repository, Warehouse warehouse) {
    List<IntArrayEntity> entities = repository.findAll();
    boolean hasEntities = !entities.isEmpty();
    if (hasEntities) {
      IntArrayEntity entity = entities.get(FIRST_INDEX);
      UUID id = entity.getId();
      boolean removed = repository.remove(entity);
      boolean keptInWarehouse = warehouse.containsId(id);
      LOGGER.info("Removed array {}: removed {}, warehouse keeps it {}", id, removed,
          keptInWarehouse);
    }
  }
}
