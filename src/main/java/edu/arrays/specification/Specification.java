package edu.arrays.specification;


public interface Specification<T> {

  boolean isSatisfiedBy(T candidate);
}
