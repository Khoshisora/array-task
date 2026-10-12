package edu.arrays.service;

import edu.arrays.entity.IntArrayEntity;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class ArrayStatisticsServiceImpl implements ArrayStatisticsService {

  @Override
  public OptionalInt findMin(IntArrayEntity entity) {
    int[] elements = entity.getElements();
    OptionalInt result = OptionalInt.empty();
    if (elements.length > 0) {
      int min = elements[0];
      for (int element : elements) {
        if (element < min) {
          min = element;
        }
      }
      result = OptionalInt.of(min);
    }
    return result;
  }

  @Override
  public OptionalInt findMax(IntArrayEntity entity) {
    int[] elements = entity.getElements();
    OptionalInt result = OptionalInt.empty();
    if (elements.length > 0) {
      int max = elements[0];
      for (int element : elements) {
        if (element > max) {
          max = element;
        }
      }
      result = OptionalInt.of(max);
    }
    return result;
  }

  @Override
  public OptionalLong findSum(IntArrayEntity entity) {
    int[] elements = entity.getElements();
    OptionalLong result = OptionalLong.empty();
    if (elements.length > 0) {
      long sum = 0;
      for (int element : elements) {
        sum += element;
      }
      result = OptionalLong.of(sum);
    }
    return result;
  }

  @Override
  public OptionalDouble findAverage(IntArrayEntity entity) {
    OptionalLong sum = findSum(entity);
    OptionalDouble result = OptionalDouble.empty();
    if (sum.isPresent()) {
      long total = sum.getAsLong();
      int size = entity.getSize();
      result = OptionalDouble.of((double) total / size);
    }
    return result;
  }
}
