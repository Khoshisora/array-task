package edu.arrays.specification;

import java.util.UUID;


public final class ArraySpecifications {

  private ArraySpecifications() {
  }

  public static Specification<ArrayAggregate> idEquals(UUID id) {
    return aggregate -> id.equals(aggregate.getEntity().getId());
  }

  public static Specification<ArrayAggregate> sizeEquals(int size) {
    return aggregate -> aggregate.getEntity().getSize() == size;
  }

  public static Specification<ArrayAggregate> containsElement(int element) {
    return aggregate -> aggregate.getEntity().containsElement(element);
  }

  public static Specification<ArrayAggregate> sum(Comparison comparison, long value) {
    return aggregate -> comparison.isSatisfied(aggregate.getSum(), value);
  }

  public static Specification<ArrayAggregate> average(Comparison comparison, double value) {
    return aggregate -> comparison.isSatisfied(aggregate.getAverage(), value);
  }

  public static Specification<ArrayAggregate> min(Comparison comparison, int value) {
    return aggregate -> comparison.isSatisfied(aggregate.getMin(), value);
  }

  public static Specification<ArrayAggregate> max(Comparison comparison, int value) {
    return aggregate -> comparison.isSatisfied(aggregate.getMax(), value);
  }

}
