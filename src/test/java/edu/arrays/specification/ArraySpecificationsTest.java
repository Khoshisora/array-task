package edu.arrays.specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.arrays.entity.IntArrayEntity;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ArraySpecificationsTest {

  private static final UUID ASSIGNED_ID = UUID.fromString("00000000-0000-0000-0000-000000000007");
  private static final UUID OTHER_ID = UUID.fromString("00000000-0000-0000-0000-000000000008");
  private static final int[] ELEMENTS = {2, 4, 6};
  private static final int[] OTHER_ELEMENTS = {1, 5};
  private static final int[] NO_ELEMENTS = {};
  private static final int SIZE = 3;
  private static final int STORED_ELEMENT = 4;
  private static final int MISSING_ELEMENT = 9;
  private static final OptionalLong SUM = OptionalLong.of(12);
  private static final OptionalLong OTHER_SUM = OptionalLong.of(6);
  private static final OptionalDouble AVERAGE = OptionalDouble.of(4.0);
  private static final OptionalInt MIN = OptionalInt.of(2);
  private static final OptionalInt MAX = OptionalInt.of(6);
  private static final long SMALLER_SUM_BOUND = 10;
  private static final double AVERAGE_BOUND = 4.0;
  private static final int SMALLER_MIN_BOUND = 1;
  private static final int EMPTY_SUM_BOUND = 0;
  private static final IntArrayEntity ENTITY = createEntity(ASSIGNED_ID, ELEMENTS);
  private static final IntArrayEntity OTHER_ENTITY = createEntity(OTHER_ID, OTHER_ELEMENTS);
  private static final IntArrayEntity EMPTY_ENTITY = createEntity(OTHER_ID, NO_ELEMENTS);
  private static final ArrayAggregate AGGREGATE = new ArrayAggregate(ENTITY, SUM, AVERAGE, MIN,
      MAX);
  private static final ArrayAggregate OTHER_AGGREGATE = new ArrayAggregate(OTHER_ENTITY, OTHER_SUM,
      AVERAGE, MIN, MAX);
  private static final ArrayAggregate EMPTY_AGGREGATE = new ArrayAggregate(EMPTY_ENTITY,
      OptionalLong.empty(), OptionalDouble.empty(), OptionalInt.empty(), OptionalInt.empty());

  @Test
  void idEqualsShouldMatchStoredIdentifier() {
    // given
    Specification<ArrayAggregate> specification = ArraySpecifications.idEquals(ASSIGNED_ID);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void idEqualsShouldNotMatchAnotherIdentifier() {
    // given
    Specification<ArrayAggregate> specification = ArraySpecifications.idEquals(ASSIGNED_ID);
    // when
    boolean actual = specification.isSatisfiedBy(OTHER_AGGREGATE);
    // then
    assertFalse(actual);
  }

  @Test
  void sizeEqualsShouldMatchNumberOfElements() {
    // given
    Specification<ArrayAggregate> specification = ArraySpecifications.sizeEquals(SIZE);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void containsElementShouldBeTrueForStoredElement() {
    // given
    Specification<ArrayAggregate> specification =
        ArraySpecifications.containsElement(STORED_ELEMENT);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void containsElementShouldBeFalseForMissingElement() {
    // given
    Specification<ArrayAggregate> specification =
        ArraySpecifications.containsElement(MISSING_ELEMENT);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertFalse(actual);
  }

  @Test
  void sumShouldMatchGreaterSum() {
    // given
    Comparison comparison = Comparison.GREATER;
    Specification<ArrayAggregate> specification =
        ArraySpecifications.sum(comparison, SMALLER_SUM_BOUND);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void sumShouldNotMatchSmallerSum() {
    // given
    Comparison comparison = Comparison.GREATER;
    Specification<ArrayAggregate> specification =
        ArraySpecifications.sum(comparison, SMALLER_SUM_BOUND);
    // when
    boolean actual = specification.isSatisfiedBy(OTHER_AGGREGATE);
    // then
    assertFalse(actual);
  }

  @Test
  void averageShouldMatchEqualAverage() {
    // given
    Comparison comparison = Comparison.EQUAL;
    Specification<ArrayAggregate> specification =
        ArraySpecifications.average(comparison, AVERAGE_BOUND);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void minShouldMatchGreaterMinimum() {
    // given
    Comparison comparison = Comparison.GREATER;
    Specification<ArrayAggregate> specification =
        ArraySpecifications.min(comparison, SMALLER_MIN_BOUND);
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void maxShouldMatchEqualToMaximum() {
    // given
    Comparison comparison = Comparison.EQUAL;
    Specification<ArrayAggregate> specification = ArraySpecifications.max(comparison, MAX.getAsInt());
    // when
    boolean actual = specification.isSatisfiedBy(AGGREGATE);
    // then
    assertTrue(actual);
  }

  @Test
  void sumShouldNotMatchEmptyAggregate() {
    // given
    Comparison comparison = Comparison.GREATER;
    Specification<ArrayAggregate> specification =
        ArraySpecifications.sum(comparison, EMPTY_SUM_BOUND);
    // when
    boolean actual = specification.isSatisfiedBy(EMPTY_AGGREGATE);
    // then
    assertFalse(actual);
  }

  private static IntArrayEntity createEntity(UUID id, int[] elements) {
    IntArrayEntity entity = new IntArrayEntity(elements);
    entity.setId(id);
    return entity;
  }
}
