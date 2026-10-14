package edu.arrays.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.arrays.comparator.IntArrayComparators;
import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import edu.arrays.observer.RecordingArrayObserver;
import edu.arrays.specification.ArrayAggregate;
import edu.arrays.specification.ArraySpecifications;
import edu.arrays.specification.Comparison;
import edu.arrays.specification.Specification;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class InMemoryArrayRepositoryTest {

  private static final int[] ELEMENTS = {1, 2, 3};
  private static final int[] OTHER_ELEMENTS = {10, 20};
  private static final UUID UNKNOWN_ID = UUID.fromString("00000000-0000-0000-0000-000000000099");
  private static final int FIRST_INDEX = 0;
  private static final int SECOND_INDEX = 1;
  private static final int NEW_VALUE = 50;
  private static final int SEARCHED_SIZE = 2;
  private static final int SEARCHED_ELEMENT = 20;
  private static final long SUM_BOUND = 15;
  private static final long TOO_LARGE_SUM_BOUND = 1000;

  @Test
  void addShouldStoreTheArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    // when
    List<IntArrayEntity> actual = repository.findAll();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void addShouldReturnTrueForNewArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    boolean actual = repository.add(entity);
    // then
    assertTrue(actual);
  }

  @Test
  void addShouldStoreEqualArraysSeparately() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(ELEMENTS);
    repository.add(first);
    repository.add(second);
    // when
    List<IntArrayEntity> actual = repository.findAll();
    // then
    assertEquals(List.of(first, second), actual);
  }

  @Test
  void addShouldReturnFalseForNullArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    // when
    boolean actual = repository.add(null);
    // then
    assertFalse(actual);
  }

  @Test
  void addShouldAssignIdentifier() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    // when
    UUID actual = entity.getId();
    // then
    assertNotNull(actual);
  }

  @Test
  void addShouldAssignDifferentIdentifiers() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(first);
    repository.add(second);
    // when
    UUID actual = second.getId();
    // then
    assertNotEquals(first.getId(), actual);
  }

  @Test
  void removeShouldDropStoredArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    // when
    boolean actual = repository.remove(entity);
    // then
    assertTrue(actual);
    assertEquals(List.of(), repository.findAll());
  }

  @Test
  void removeShouldDropOnlyTheRequestedArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(first);
    repository.add(second);
    repository.remove(first);
    // when
    List<IntArrayEntity> actual = repository.findAll();
    // then
    assertEquals(List.of(second), actual);
  }

  @Test
  void removeShouldReturnFalseForUnknownArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity stored = new IntArrayEntity(ELEMENTS);
    repository.add(stored);
    IntArrayEntity unknown = new IntArrayEntity(OTHER_ELEMENTS);
    // when
    boolean actual = repository.remove(unknown);
    // then
    assertFalse(actual);
  }

  @Test
  void findAllShouldReturnArraysInStoringOrder() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(first);
    repository.add(second);
    // when
    List<IntArrayEntity> actual = repository.findAll();
    // then
    assertEquals(List.of(first, second), actual);
  }

  @Test
  void findByIdShouldReturnStoredArray() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    UUID assignedId = entity.getId();
    // when
    Optional<IntArrayEntity> actual = repository.findById(assignedId);
    // then
    assertEquals(Optional.of(entity), actual);
  }

  @Test
  void findByIdShouldReturnEmptyForUnknownIdentifier() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    // when
    Optional<IntArrayEntity> actual = repository.findById(UNKNOWN_ID);
    // then
    assertEquals(Optional.empty(), actual);
  }

  @Test
  void findBySpecificationShouldMatchByIdentifier() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    UUID assignedId = entity.getId();
    Specification<ArrayAggregate> specification = ArraySpecifications.idEquals(assignedId);
    // when
    List<IntArrayEntity> actual = repository.findBySpecification(specification);
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void findBySpecificationShouldMatchBySize() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    repository.add(new IntArrayEntity(ELEMENTS));
    IntArrayEntity smaller = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(smaller);
    Specification<ArrayAggregate> specification = ArraySpecifications.sizeEquals(SEARCHED_SIZE);
    // when
    List<IntArrayEntity> actual = repository.findBySpecification(specification);
    // then
    assertEquals(List.of(smaller), actual);
  }

  @Test
  void findBySpecificationShouldMatchByElement() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    repository.add(new IntArrayEntity(ELEMENTS));
    IntArrayEntity withElement = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(withElement);
    Specification<ArrayAggregate> specification =
        ArraySpecifications.containsElement(SEARCHED_ELEMENT);
    // when
    List<IntArrayEntity> actual = repository.findBySpecification(specification);
    // then
    assertEquals(List.of(withElement), actual);
  }

  @Test
  void findBySpecificationShouldMatchBySum() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    repository.add(new IntArrayEntity(ELEMENTS));
    IntArrayEntity bigSum = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(bigSum);
    Comparison comparison = Comparison.GREATER;
    Specification<ArrayAggregate> specification = ArraySpecifications.sum(comparison, SUM_BOUND);
    // when
    List<IntArrayEntity> actual = repository.findBySpecification(specification);
    // then
    assertEquals(List.of(bigSum), actual);
  }

  @Test
  void findBySpecificationShouldReturnEmptyListWhenNothingMatches() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    repository.add(new IntArrayEntity(ELEMENTS));
    Comparison comparison = Comparison.GREATER;
    Specification<ArrayAggregate> specification =
        ArraySpecifications.sum(comparison, TOO_LARGE_SUM_BOUND);
    // when
    List<IntArrayEntity> actual = repository.findBySpecification(specification);
    // then
    assertEquals(List.of(), actual);
  }

  @Test
  void sortByShouldOrderBySize() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity bigger = new IntArrayEntity(ELEMENTS);
    IntArrayEntity smaller = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(bigger);
    repository.add(smaller);
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_SIZE;
    // when
    List<IntArrayEntity> actual = repository.sortBy(comparator);
    // then
    assertEquals(List.of(smaller, bigger), actual);
  }

  @Test
  void sortByShouldNotChangeStoringOrder() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity bigger = new IntArrayEntity(ELEMENTS);
    IntArrayEntity smaller = new IntArrayEntity(OTHER_ELEMENTS);
    repository.add(bigger);
    repository.add(smaller);
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_SIZE;
    repository.sortBy(comparator);
    // when
    List<IntArrayEntity> actual = repository.findAll();
    // then
    assertEquals(List.of(bigger, smaller), actual);
  }

  @Test
  void addObserverShouldReceiveAddedEvent() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    RecordingArrayObserver observer = new RecordingArrayObserver();
    repository.addObserver(observer);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    // when
    List<IntArrayEntity> actual = observer.getAddedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void addObserverShouldReceiveRemovedEvent() {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    RecordingArrayObserver observer = new RecordingArrayObserver();
    repository.addObserver(observer);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    repository.remove(entity);
    // when
    List<IntArrayEntity> actual = observer.getRemovedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void addObserverShouldReceiveChangeOfStoredArray() throws ArrayException {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    RecordingArrayObserver observer = new RecordingArrayObserver();
    repository.addObserver(observer);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void addObserverShouldReceiveChangeOfArrayStoredBeforeSubscription() throws ArrayException {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    repository.addObserver(observer);
    entity.setElement(SECOND_INDEX, NEW_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void removeObserverShouldStopEvents() throws ArrayException {
    // given
    ArrayRepository repository = new InMemoryArrayRepository();
    RecordingArrayObserver observer = new RecordingArrayObserver();
    repository.addObserver(observer);
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    repository.add(entity);
    repository.removeObserver(observer);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(), actual);
  }
}
