package edu.arrays.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import edu.arrays.entity.IntArrayEntity;
import org.junit.jupiter.api.Test;

class ArraySortServiceImplTest {

  private static final int[] UNSORTED = {5, 3, 9, 1, 3, 0};
  private static final int[] SORTED = {0, 1, 3, 3, 5, 9};
  private static final int[] ALREADY_SORTED = {1, 2, 3};
  private static final int[] NO_ELEMENTS = {};

  @Test
  void bubbleSortShouldSortAscending() {
    // given
    IntArrayEntity entity = new IntArrayEntity(UNSORTED);
    ArraySortService service = new ArraySortServiceImpl();
    // when
    service.bubbleSort(entity);
    // then
    assertArrayEquals(SORTED, entity.getElements());
  }

  @Test
  void bubbleSortShouldKeepSortedArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ALREADY_SORTED);
    ArraySortService service = new ArraySortServiceImpl();
    // when
    service.bubbleSort(entity);
    // then
    assertArrayEquals(ALREADY_SORTED, entity.getElements());
  }

  @Test
  void bubbleSortShouldHandleEmptyArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(NO_ELEMENTS);
    ArraySortService service = new ArraySortServiceImpl();
    // when
    service.bubbleSort(entity);
    // then
    assertArrayEquals(NO_ELEMENTS, entity.getElements());
  }

  @Test
  void insertionSortShouldSortAscending() {
    // given
    IntArrayEntity entity = new IntArrayEntity(UNSORTED);
    ArraySortService service = new ArraySortServiceImpl();
    // when
    service.insertionSort(entity);
    // then
    assertArrayEquals(SORTED, entity.getElements());
  }

  @Test
  void insertionSortShouldKeepSortedArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ALREADY_SORTED);
    ArraySortService service = new ArraySortServiceImpl();
    // when
    service.insertionSort(entity);
    // then
    assertArrayEquals(ALREADY_SORTED, entity.getElements());
  }

  @Test
  void insertionSortShouldHandleEmptyArray() {
    // given
    IntArrayEntity entity = new IntArrayEntity(NO_ELEMENTS);
    ArraySortService service = new ArraySortServiceImpl();
    // when
    service.insertionSort(entity);
    // then
    assertArrayEquals(NO_ELEMENTS, entity.getElements());
  }
}
