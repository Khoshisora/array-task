package edu.arrays.service;

import edu.arrays.entity.IntArrayEntity;

public class ArraySortServiceImpl implements ArraySortService {

  @Override
  public void bubbleSort(IntArrayEntity entity) {
    int[] elements = entity.getElements();
    int length = elements.length;
    boolean swapped = true;
    for (int i = 0; swapped && i < length - 1; i++) {
      swapped = false;
      for (int j = 0; j < length - 1 - i; j++) {
        if (elements[j] > elements[j + 1]) {
          int buffer = elements[j];
          elements[j] = elements[j + 1];
          elements[j + 1] = buffer;
          swapped = true;
        }
      }
    }
    entity.setElements(elements);
  }

  @Override
  public void insertionSort(IntArrayEntity entity) {
    int[] elements = entity.getElements();
    for (int i = 1; i < elements.length; i++) {
      int current = elements[i];
      int j = i - 1;
      while (j >= 0 && elements[j] > current) {
        elements[j + 1] = elements[j];
        j--;
      }
      elements[j + 1] = current;
    }
    entity.setElements(elements);
  }
}
