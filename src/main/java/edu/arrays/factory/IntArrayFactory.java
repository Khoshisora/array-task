package edu.arrays.factory;

import edu.arrays.entity.IntArrayEntity;

public class IntArrayFactory implements ArrayFactory {

  @Override
  public IntArrayEntity create(int[] elements) {
    return new IntArrayEntity(elements);
  }
}
