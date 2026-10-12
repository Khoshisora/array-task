package edu.arrays.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IntArrayEntityTest {

  private static final int[] ELEMENTS = {3, 1, 2};
  private static final int[] OTHER_ELEMENTS = {7, 8};
  private static final String EXPECTED_STRING = "IntArrayEntity[3, 1, 2]";

  @Test
  void getElementsShouldReturnCopyOfElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    int[] expected = {3, 1, 2};
    int[] returned = entity.getElements();
    returned[0] = 100;
    // when
    int[] actual = entity.getElements();
    // then
    assertArrayEquals(expected, actual);
  }

  @Test
  void setElementsShouldReplaceElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    entity.setElements(OTHER_ELEMENTS);
    // when
    int[] actual = entity.getElements();
    // then
    assertArrayEquals(OTHER_ELEMENTS, actual);
  }

  @Test
  void getSizeShouldReturnNumberOfElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    int actual = entity.getSize();
    // then
    assertEquals(3, actual);
  }

  @Test
  void equalsShouldBeTrueForSameElements() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(ELEMENTS);
    // when
    boolean actual = first.equals(second);
    // then
    assertTrue(actual);
  }

  @Test
  void equalsShouldBeFalseForDifferentElements() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(OTHER_ELEMENTS);
    // then
    assertNotEquals(first, second);
  }

  @Test
  void hashCodeShouldBeEqualForSameElements() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(ELEMENTS);
    // when
    int actual = first.hashCode();
    // then
    assertEquals(second.hashCode(), actual);
  }

  @Test
  void toStringShouldContainElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    String actual = entity.toString();
    // then
    assertEquals(EXPECTED_STRING, actual);
  }
}
