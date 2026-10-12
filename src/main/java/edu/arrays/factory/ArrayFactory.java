package edu.arrays.factory;

import edu.arrays.entity.IntArrayEntity;


public interface ArrayFactory {

  IntArrayEntity create(int[] elements);
}
