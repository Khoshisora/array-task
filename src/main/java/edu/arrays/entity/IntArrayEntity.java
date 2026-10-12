package edu.arrays.entity;

import java.util.Arrays;


public class IntArrayEntity {

  private int[] elements;

  public IntArrayEntity(int[] elements) {
    this.elements = elements.clone();
  }

  public int[] getElements() {
    return elements.clone();
  }

  public void setElements(int[] elements) {
    this.elements = elements.clone();
  }

  public int getSize() {
    return elements.length;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    IntArrayEntity that = (IntArrayEntity) other;
    return Arrays.equals(elements, that.elements);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(elements);
  }

  @Override
  public String toString() {
    return "IntArrayEntity" + Arrays.toString(elements);
  }
}
