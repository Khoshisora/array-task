package edu.arrays.specification;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;


public enum Comparison {

  GREATER,
  LESS,
  EQUAL;

  public boolean isSatisfied(OptionalLong actual, long expected) {
    boolean result = false;
    if (actual.isPresent()) {
      result = isSatisfied(actual.getAsLong(), expected);
    }
    return result;
  }

  public boolean isSatisfied(OptionalDouble actual, double expected) {
    boolean result = false;
    if (actual.isPresent()) {
      result = isSatisfied(actual.getAsDouble(), expected);
    }
    return result;
  }

  public boolean isSatisfied(OptionalInt actual, int expected) {
    boolean result = false;
    if (actual.isPresent()) {
      result = isSatisfied(actual.getAsInt(), expected);
    }
    return result;
  }

  private boolean isSatisfied(long actual, long expected) {
    return compare(Double.compare(actual, expected));
  }

  private boolean isSatisfied(double actual, double expected) {
    return compare(Double.compare(actual, expected));
  }

  private boolean compare(int comparisonResult) {
    boolean result = false;
    if (this == GREATER) {
      result = comparisonResult > 0;
    } else if (this == LESS) {
      result = comparisonResult < 0;
    } else {
      result = comparisonResult == 0;
    }
    return result;
  }
}
