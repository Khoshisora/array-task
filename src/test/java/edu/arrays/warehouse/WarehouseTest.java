package edu.arrays.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import edu.arrays.observer.ArrayObserver;
import edu.arrays.repository.ArrayRepository;
import edu.arrays.repository.InMemoryArrayRepository;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class WarehouseTest {

  private static final int[] ELEMENTS = {1, 2, 3};
  private static final int[] NO_ELEMENTS = {};
  private static final UUID REGISTERED_ID = UUID.fromString("00000000-0000-0000-0000-000000000100");
  private static final UUID UNREGISTERED_ID = UUID.fromString("00000000-0000-0000-0000-000000000777");
  private static final int FIRST_INDEX = 0;
  private static final int NEW_VALUE = 5;
  private static final long SUM = 6L;
  private static final long SUM_AFTER_CHANGE = 10L;
  private static final double AVERAGE = 2.0;
  private static final int MIN = 1;
  private static final int MAX = 3;

  @Test
  void getInstanceShouldReturnTheSameInstance() {
    // given
    Warehouse first = Warehouse.getInstance();
    // when
    Warehouse actual = Warehouse.getInstance();
    // then
    assertSame(first, actual);
  }

  @Test
  void recalculateShouldStoreSum() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, REGISTERED_ID);
    warehouse.recalculate(entity);
    // when
    OptionalLong actual = warehouse.findSum(entity);
    // then
    assertEquals(OptionalLong.of(SUM), actual);
  }

  @Test
  void recalculateShouldStoreAverage() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, REGISTERED_ID);
    warehouse.recalculate(entity);
    // when
    OptionalDouble actual = warehouse.findAverage(entity);
    // then
    assertEquals(OptionalDouble.of(AVERAGE), actual);
  }

  @Test
  void recalculateShouldStoreMinAndMax() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, REGISTERED_ID);
    warehouse.recalculate(entity);
    // when
    OptionalInt actualMin = warehouse.findMin(entity);
    OptionalInt actualMax = warehouse.findMax(entity);
    // then
    assertEquals(OptionalInt.of(MIN), actualMin);
    assertEquals(OptionalInt.of(MAX), actualMax);
  }

  @Test
  void findByIdShouldReturnRecalculatedStatistics() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, REGISTERED_ID);
    warehouse.recalculate(entity);
    // when
    Optional<ArrayStatistics> actual = warehouse.findById(REGISTERED_ID);
    // then
    assertTrue(actual.isPresent());
  }

  @Test
  void containsIdShouldBeTrueAfterRecalculation() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, REGISTERED_ID);
    warehouse.recalculate(entity);
    // when
    boolean actual = warehouse.containsId(REGISTERED_ID);
    // then
    assertTrue(actual);
  }

  @Test
  void forgetShouldDropStatistics() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, REGISTERED_ID);
    warehouse.recalculate(entity);
    warehouse.forget(entity);
    // when
    boolean actual = warehouse.containsId(REGISTERED_ID);
    // then
    assertFalse(actual);
  }

  @Test
  void findSumShouldBeEmptyForEmptyArray() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(NO_ELEMENTS, UNREGISTERED_ID);
    // when
    OptionalLong actual = warehouse.findSum(entity);
    // then
    assertEquals(OptionalLong.empty(), actual);
  }

  @Test
  void findSumShouldCalculateStatisticsOfUnregisteredArray() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    IntArrayEntity entity = createStoredEntity(ELEMENTS, UNREGISTERED_ID);
    // when
    OptionalLong actual = warehouse.findSum(entity);
    // then
    assertEquals(OptionalLong.of(SUM), actual);
  }

  @Test
  void synchronizerShouldRecalculateOnStoring() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    ArrayRepository repository = createSynchronizedRepository(warehouse);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    // when
    OptionalLong actual = warehouse.findSum(entity);
    // then
    assertEquals(OptionalLong.of(SUM), actual);
  }

  @Test
  void synchronizerShouldRecalculateAfterElementChange() throws ArrayException {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    ArrayRepository repository = createSynchronizedRepository(warehouse);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    OptionalLong actual = warehouse.findSum(entity);
    // then
    assertEquals(OptionalLong.of(SUM_AFTER_CHANGE), actual);
  }

  @Test
  void synchronizerShouldForgetAfterRemoval() {
    // given
    Warehouse warehouse = Warehouse.getInstance();
    ArrayRepository repository = createSynchronizedRepository(warehouse);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    UUID id = entity.getId();
    repository.remove(entity);
    // when
    boolean actual = warehouse.containsId(id);
    // then
    assertFalse(actual);
  }

  private static ArrayRepository createSynchronizedRepository(Warehouse warehouse) {
    ArrayRepository repository = new InMemoryArrayRepository();
    ArrayObserver synchronizer = new WarehouseSynchronizer(warehouse);
    repository.addObserver(synchronizer);
    return repository;
  }

  private static IntArrayEntity createStoredEntity(int[] elements, UUID id) {
    IntArrayEntity entity = new IntArrayEntity(elements);
    entity.setId(id);
    return entity;
  }
}
