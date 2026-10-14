package edu.arrays.observer;

import edu.arrays.entity.IntArrayEntity;


public interface ArrayObserver {

  void arrayAdded(IntArrayEntity entity);

  void arrayChanged(IntArrayEntity entity);

  void arrayRemoved(IntArrayEntity entity);
}
