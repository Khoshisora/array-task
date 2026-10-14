package edu.arrays.specification;

import edu.arrays.entity.IntArrayEntity;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;


public final class ArrayAggregate {

  private final IntArrayEntity entity;
  private final OptionalLong sum;
  private final OptionalDouble average;
  private final OptionalInt min;
  private final OptionalInt max;

  public ArrayAggregate(IntArrayEntity entity, OptionalLong sum, OptionalDouble average,
      OptionalInt min, OptionalInt max) {
    this.entity = entity;
    this.sum = sum;
    this.average = average;
    this.min = min;
    this.max = max;
  }

  public IntArrayEntity getEntity() {
    return entity;
  }

  public OptionalLong getSum() {
    return sum;
  }

  public OptionalDouble getAverage() {
    return average;
  }

  public OptionalInt getMin() {
    return min;
  }

  public OptionalInt getMax() {
    return max;
  }
}
