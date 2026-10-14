package edu.arrays.warehouse;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.service.ArrayStatisticsService;
import edu.arrays.service.ArrayStatisticsServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Keeps the sum, average, min and max of every stored array and recalculates them as soon as an
 * array changes.
 *
 * <p>Singleton, so the whole application reads the same cached values. Deliberately not thread
 * safe, the task forbids the concurrent variant.</p>
 */
public final class Warehouse {

  private static final Logger LOGGER = LogManager.getLogger();

  private static Warehouse instance;

  private final ArrayStatisticsService statisticsService;
  private final Map<UUID, ArrayStatistics> statistics = new HashMap<>();

  private Warehouse(ArrayStatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  /**
   * Returns the single warehouse of the application, creating it on the first call.
   */
  public static Warehouse getInstance() {
    if (instance == null) {
      instance = new Warehouse(new ArrayStatisticsServiceImpl());
      LOGGER.debug("Warehouse instance created");
    }
    return instance;
  }

  /**
   * Recalculates and stores the aggregates of the array.
   */
  public void recalculate(IntArrayEntity entity) {
    ArrayStatistics recalculated = calculate(entity);
    statistics.put(entity.getId(), recalculated);
    LOGGER.info("Warehouse recalculated for array {}: {}", entity.getId(), recalculated);
  }

  /**
   * Drops the aggregates of an array that is no longer stored.
   */
  public void forget(IntArrayEntity entity) {
    statistics.remove(entity.getId());
    LOGGER.info("Warehouse forgot array {}", entity.getId());
  }

  public Optional<ArrayStatistics> findById(UUID id) {
    return Optional.ofNullable(statistics.get(id));
  }

  public OptionalLong findSum(IntArrayEntity entity) {
    return statisticsOf(entity).getSum();
  }

  public OptionalDouble findAverage(IntArrayEntity entity) {
    return statisticsOf(entity).getAverage();
  }

  public OptionalInt findMin(IntArrayEntity entity) {
    return statisticsOf(entity).getMin();
  }

  public OptionalInt findMax(IntArrayEntity entity) {
    return statisticsOf(entity).getMax();
  }

  public int getSize() {
    return statistics.size();
  }

  public boolean containsId(UUID id) {
    return statistics.containsKey(id);
  }

  private ArrayStatistics calculate(IntArrayEntity entity) {
    OptionalLong sum = statisticsService.findSum(entity);
    OptionalDouble average = statisticsService.findAverage(entity);
    OptionalInt min = statisticsService.findMin(entity);
    OptionalInt max = statisticsService.findMax(entity);
    return new ArrayStatistics(sum, average, min, max);
  }

  /**
   * Returns the cached aggregates, calculating them without caching when the array was never
   * registered in the warehouse.
   */
  private ArrayStatistics statisticsOf(IntArrayEntity entity) {
    ArrayStatistics cached = statistics.get(entity.getId());
    ArrayStatistics result = cached;
    if (result == null) {
      result = calculate(entity);
    }
    return result;
  }
}
