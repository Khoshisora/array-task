package edu.arrays.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

class ArrayStatisticsTest {

  private static final OptionalLong SUM = OptionalLong.of(6);
  private static final OptionalDouble AVERAGE = OptionalDouble.of(2.0);
  private static final OptionalInt MIN = OptionalInt.of(1);
  private static final OptionalInt MAX = OptionalInt.of(3);
  private static final OptionalLong OTHER_SUM = OptionalLong.of(60);
  private static final String EXPECTED_STRING =
      "ArrayStatistics[sum=OptionalLong[6], average=OptionalDouble[2.0], min=OptionalInt[1], "
          + "max=OptionalInt[3]]";

  @Test
  void equalsShouldBeTrueForTheSameAggregates() {
    // given
    ArrayStatistics first = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    ArrayStatistics second = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    // when
    boolean actual = first.equals(second);
    // then
    assertTrue(actual);
  }

  @Test
  void equalsShouldBeFalseForDifferentSums() {
    // given
    ArrayStatistics first = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    ArrayStatistics second = new ArrayStatistics(OTHER_SUM, AVERAGE, MIN, MAX);
    // then
    assertNotEquals(first, second);
  }

  @Test
  void hashCodeShouldBeEqualForTheSameAggregates() {
    // given
    ArrayStatistics first = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    ArrayStatistics second = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    // when
    int actual = first.hashCode();
    // then
    assertEquals(second.hashCode(), actual);
  }

  @Test
  void getSumShouldReturnStoredValue() {
    // given
    ArrayStatistics statistics = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    // when
    OptionalLong actual = statistics.getSum();
    // then
    assertEquals(SUM, actual);
  }

  @Test
  void toStringShouldContainAllAggregates() {
    // given
    ArrayStatistics statistics = new ArrayStatistics(SUM, AVERAGE, MIN, MAX);
    // when
    String actual = statistics.toString();
    // then
    assertEquals(EXPECTED_STRING, actual);
  }
}
