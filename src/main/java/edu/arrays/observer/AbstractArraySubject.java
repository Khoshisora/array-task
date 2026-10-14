package edu.arrays.observer;

import edu.arrays.entity.IntArrayEntity;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractArraySubject implements ArraySubject {

  private final List<ArrayObserver> observers = new ArrayList<>();

  @Override
  public final void addObserver(ArrayObserver observer) {
    if (observer != null && !observers.contains(observer)) {
      observers.add(observer);
      onObserverAdded(observer);
    }
  }

  @Override
  public final void removeObserver(ArrayObserver observer) {
    boolean removed = observers.remove(observer);
    if (removed) {
      onObserverRemoved(observer);
    }
  }

  protected void onObserverAdded(ArrayObserver observer) {
  }

  protected void onObserverRemoved(ArrayObserver observer) {
  }

  protected final void notifyArrayAdded(IntArrayEntity entity) {
    for (ArrayObserver observer : snapshotObservers()) {
      observer.arrayAdded(entity);
    }
  }

  protected final void notifyArrayChanged(IntArrayEntity entity) {
    for (ArrayObserver observer : snapshotObservers()) {
      observer.arrayChanged(entity);
    }
  }

  protected final void notifyArrayRemoved(IntArrayEntity entity) {
    for (ArrayObserver observer : snapshotObservers()) {
      observer.arrayRemoved(entity);
    }
  }

  private List<ArrayObserver> snapshotObservers() {
    return new ArrayList<>(observers);
  }

  protected final List<ArrayObserver> subscribedObservers() {
    return new ArrayList<>(observers);
  }
}
