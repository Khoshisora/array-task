package edu.arrays.factory;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import edu.arrays.entity.IntArrayEntity;
import org.junit.jupiter.api.Test;

class IntArrayFactoryTest {

  private static final int[] ELEMENTS = {4, 5, 6};

  @Test
  void createShouldWrapGivenElements() {
    // given
    ArrayFactory factory = new IntArrayFactory();
    // when
    IntArrayEntity entity = factory.create(ELEMENTS);
    // then
    assertArrayEquals(ELEMENTS, entity.getElements());
  }

  @Test
  void createShouldReturnEmptyEntityForEmptyArray() {
    // given
    ArrayFactory factory = new IntArrayFactory();
    int[] empty = {};
    // when
    IntArrayEntity entity = factory.create(empty);
    // then
    assertArrayEquals(empty, entity.getElements());
  }
}
