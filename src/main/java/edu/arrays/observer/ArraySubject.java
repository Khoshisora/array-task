package edu.arrays.observer;


public interface ArraySubject {

  void addObserver(ArrayObserver observer);

  void removeObserver(ArrayObserver observer);
}
