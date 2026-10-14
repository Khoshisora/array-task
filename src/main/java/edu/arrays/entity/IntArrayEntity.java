package edu.arrays.entity;

import edu.arrays.exception.ArrayException;
import edu.arrays.observer.AbstractArraySubject;
import java.util.Arrays;
import java.util.UUID;


public class IntArrayEntity extends AbstractArraySubject {

  private UUID id;
  private int[] elements;

  public IntArrayEntity(int[] elements) {
    this.elements = elements.clone();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int[] getElements() {
    return elements.clone();
  }

  public void setElements(int[] elements) {
    boolean changed = !Arrays.equals(this.elements, elements);
    this.elements = elements.clone();
    if (changed) {
      notifyArrayChanged(this);
    }
  }

  public void setElement(int index, int value) throws ArrayException {
    if (index < 0 || index >= elements.length) {
      throw new ArrayException("Index " + index + " is out of bounds for size " + elements.length);
    }
    if (elements[index] != value) {
      elements[index] = value;
      notifyArrayChanged(this);
    }
  }

  public int getSize() {
    return elements.length;
  }

  public boolean containsElement(int element) {
    boolean contains = false;
    for (int current : elements) {
      if (current == element) {
        contains = true;
        break;
      }
    }
    return contains;
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
