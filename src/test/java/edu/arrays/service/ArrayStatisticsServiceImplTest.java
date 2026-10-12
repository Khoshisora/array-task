package edu.arrays.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import edu.arrays.entity.IntArrayEntity;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

class ArrayStatisticsServiceImplTest {

  private static final int[] ELEMENTS = {4, -2, 9, 1};
  private static final int[] BIG_ELEMENTS = {Integer.MAX_VALUE, Integer.MAX_VALUE};
  private static final int[] NO_ELEMENTS = {};
  private static final double DELTA = 0.0001;

  @Test
  void findMinShouldReturnSmallestElement() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalInt actual = service.findMin(entity);
    // then
    assertEquals(-2, actual.getAsInt());
  }

  @Test
  void findMinShouldReturnEmptyForEmptyArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(NO_ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalInt actual = service.findMin(entity);
    // then
    assertFalse(actual.isPresent());
  }

  @Test
  void findMaxShouldReturnLargestElement() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalInt actual = service.findMax(entity);
    // then
    assertEquals(9, actual.getAsInt());
  }

  @Test
  void findMaxShouldReturnEmptyForEmptyArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(NO_ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalInt actual = service.findMax(entity);
    // then
    assertFalse(actual.isPresent());
  }

  @Test
  void findSumShouldReturnSumOfElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalLong actual = service.findSum(entity);
    // then
    assertEquals(12L, actual.getAsLong());
  }

  @Test
  void findSumShouldNotOverflowInt() {
    // given
    IntArrayEntity entity = new IntArrayEntity(BIG_ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalLong actual = service.findSum(entity);
    // then
    assertEquals(4294967294L, actual.getAsLong());
  }

  @Test
  void findSumShouldReturnEmptyForEmptyArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(NO_ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalLong actual = service.findSum(entity);
    // then
    assertFalse(actual.isPresent());
  }

  @Test
  void findAverageShouldReturnMeanOfElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalDouble actual = service.findAverage(entity);
    // then
    assertEquals(3.0, actual.getAsDouble(), DELTA);
  }

  @Test
  void findAverageShouldReturnEmptyForEmptyArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(NO_ELEMENTS);
    ArrayStatisticsService service = new ArrayStatisticsServiceImpl();
    // when
    OptionalDouble actual = service.findAverage(entity);
    // then
    assertFalse(actual.isPresent());
  }
}
