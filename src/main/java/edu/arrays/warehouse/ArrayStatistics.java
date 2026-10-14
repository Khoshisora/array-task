package edu.arrays.warehouse;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;


public final class ArrayStatistics {

  private static final int PRIME = 31;

  private final OptionalLong sum;
  private final OptionalDouble average;
  private final OptionalInt min;
  private final OptionalInt max;

  public ArrayStatistics(OptionalLong sum, OptionalDouble average, OptionalInt min,
      OptionalInt max) {
    this.sum = sum;
    this.average = average;
    this.min = min;
    this.max = max;
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

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    ArrayStatistics that = (ArrayStatistics) other;
    return sum.equals(that.sum)
        && average.equals(that.average)
        && min.equals(that.min)
        && max.equals(that.max);
  }

  @Override
  public int hashCode() {
    int result = sum.hashCode();
    result = PRIME * result + average.hashCode();
    result = PRIME * result + min.hashCode();
    result = PRIME * result + max.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ArrayStatistics[sum=" + sum + ", average=" + average + ", min=" + min + ", max=" + max
        + "]";
  }
}
