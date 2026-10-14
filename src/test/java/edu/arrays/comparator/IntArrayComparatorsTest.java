package edu.arrays.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.arrays.entity.IntArrayEntity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class IntArrayComparatorsTest {

  private static final UUID SMALL_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
  private static final UUID LARGE_ID = UUID.fromString("00000000-0000-0000-0000-000000000005");
  private static final int[] SINGLE_ELEMENT = {9};
  private static final int[] TWO_ELEMENTS = {4, 8};
  private static final int[] FIRST_BIGGER = {5, 1};
  private static final int[] FIRST_SMALLER = {2, 7};
  private static final int[] NO_ELEMENTS = {};

  @Test
  void byIdShouldOrderSmallerIdentifierFirst() {
    // given
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_ID;
    IntArrayEntity smallerId = new IntArrayEntity(SINGLE_ELEMENT);
    smallerId.setId(SMALL_ID);
    IntArrayEntity biggerId = new IntArrayEntity(TWO_ELEMENTS);
    biggerId.setId(LARGE_ID);
    // when
    int actual = comparator.compare(smallerId, biggerId);
    // then
    assertTrue(actual < 0);
  }

  @Test
  void byIdShouldOrderArrayWithoutIdentifierFirst() {
    // given
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_ID;
    IntArrayEntity withoutId = new IntArrayEntity(SINGLE_ELEMENT);
    IntArrayEntity withId = new IntArrayEntity(TWO_ELEMENTS);
    withId.setId(SMALL_ID);
    // when
    int actual = comparator.compare(withoutId, withId);
    // then
    assertTrue(actual < 0);
  }

  @Test
  void bySizeShouldOrderFewerElementsFirst() {
    // given
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_SIZE;
    IntArrayEntity fewer = new IntArrayEntity(SINGLE_ELEMENT);
    IntArrayEntity more = new IntArrayEntity(TWO_ELEMENTS);
    // when
    int actual = comparator.compare(fewer, more);
    // then
    assertTrue(actual < 0);
  }

  @Test
  void byFirstElementShouldOrderSmallerFirstElementFirst() {
    // given
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_FIRST_ELEMENT;
    IntArrayEntity smaller = new IntArrayEntity(FIRST_SMALLER);
    IntArrayEntity bigger = new IntArrayEntity(FIRST_BIGGER);
    // when
    int actual = comparator.compare(smaller, bigger);
    // then
    assertTrue(actual < 0);
  }

  @Test
  void byFirstElementShouldOrderEmptyArrayFirst() {
    // given
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_FIRST_ELEMENT;
    IntArrayEntity empty = new IntArrayEntity(NO_ELEMENTS);
    IntArrayEntity notEmpty = new IntArrayEntity(FIRST_SMALLER);
    // when
    int actual = comparator.compare(empty, notEmpty);
    // then
    assertTrue(actual < 0);
  }

  @Test
  void bySizeShouldSortAWholeList() {
    // given
    Comparator<IntArrayEntity> comparator = IntArrayComparators.BY_SIZE;
    IntArrayEntity two = new IntArrayEntity(TWO_ELEMENTS);
    IntArrayEntity one = new IntArrayEntity(SINGLE_ELEMENT);
    IntArrayEntity none = new IntArrayEntity(NO_ELEMENTS);
    List<IntArrayEntity> actual = new ArrayList<>(List.of(two, one, none));
    // when
    actual.sort(comparator);
    // then
    assertEquals(List.of(none, one, two), actual);
  }
}
