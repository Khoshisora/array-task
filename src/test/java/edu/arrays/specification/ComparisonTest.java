package edu.arrays.specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

class ComparisonTest {

  private static final OptionalLong SUM = OptionalLong.of(120);
  private static final OptionalLong SMALL_SUM = OptionalLong.of(20);
  private static final OptionalLong EMPTY_SUM = OptionalLong.empty();
  private static final OptionalDouble AVERAGE = OptionalDouble.of(12.5);
  private static final OptionalDouble EMPTY_AVERAGE = OptionalDouble.empty();
  private static final OptionalInt MIN = OptionalInt.of(3);
  private static final OptionalInt EMPTY_MIN = OptionalInt.empty();
  private static final long GREATER_BOUND = 100;
  private static final long EQUAL_BOUND = 120;
  private static final long SMALLER_BOUND = 30;
  private static final long EQUAL_SMALL_SUM_BOUND = 20;
  private static final double AVERAGE_BOUND = 10.0;
  private static final int MIN_BOUND = 5;

  @Test
  void greaterShouldBeTrueWhenActualValueIsGreater() {
    // given
    Comparison comparison = Comparison.GREATER;
    // when
    boolean actual = comparison.isSatisfied(SUM, GREATER_BOUND);
    // then
    assertTrue(actual);
  }

  @Test
  void greaterShouldBeFalseWhenactualValueIsEqual() {
    // given
    Comparison comparison = Comparison.GREATER;
    // when
    boolean actual = comparison.isSatisfied(SUM, EQUAL_BOUND);
    // then
    assertFalse(actual);
  }

  @Test
  void lessShouldBeTrueWhenActualValueIsLess() {
    // given
    Comparison comparison = Comparison.LESS;
    // when
    boolean actual = comparison.isSatisfied(SMALL_SUM, SMALLER_BOUND);
    // then
    assertTrue(actual);
  }

  @Test
  void lessShouldBeFalseWhenActualValueIsEqual() {
    // given
    Comparison comparison = Comparison.LESS;
    // when
    boolean actual = comparison.isSatisfied(SMALL_SUM, EQUAL_SMALL_SUM_BOUND);
    // then
    assertFalse(actual);
  }

  @Test
  void equalShouldBeTrueWhenActualValueIsEqual() {
    // given
    Comparison comparison = Comparison.EQUAL;
    // when
    boolean actual = comparison.isSatisfied(SUM, EQUAL_BOUND);
    // then
    assertTrue(actual);
  }

  @Test
  void equalShouldBeFalseWhenActualValueIsGreater() {
    // given
    Comparison comparison = Comparison.EQUAL;
    // when
    boolean actual = comparison.isSatisfied(SUM, GREATER_BOUND);
    // then
    assertFalse(actual);
  }

  @Test
  void greaterShouldBeTrueForAverageOfGreaterValue() {
    // given
    Comparison comparison = Comparison.GREATER;
    // when
    boolean actual = comparison.isSatisfied(AVERAGE, AVERAGE_BOUND);
    // then
    assertTrue(actual);
  }

  @Test
  void lessShouldBeTrueForSmallerMinimum() {
    // given
    Comparison comparison = Comparison.LESS;
    // when
    boolean actual = comparison.isSatisfied(MIN, MIN_BOUND);
    // then
    assertTrue(actual);
  }

  @Test
  void emptySumShouldSatisfyNothing() {
    // given
    Comparison comparison = Comparison.GREATER;
    // when
    boolean actual = comparison.isSatisfied(EMPTY_SUM, GREATER_BOUND);
    // then
    assertFalse(actual);
  }

  @Test
  void emptyAverageShouldSatisfyNothing() {
    // given
    Comparison comparison = Comparison.LESS;
    // when
    boolean actual = comparison.isSatisfied(EMPTY_AVERAGE, AVERAGE_BOUND);
    // then
    assertFalse(actual);
  }

  @Test
  void emptyMinShouldSatisfyNothing() {
    // given
    Comparison comparison = Comparison.EQUAL;
    // when
    boolean actual = comparison.isSatisfied(EMPTY_MIN, MIN_BOUND);
    // then
    assertFalse(actual);
  }
}
