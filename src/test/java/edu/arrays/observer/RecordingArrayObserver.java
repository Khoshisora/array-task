package edu.arrays.observer;

import edu.arrays.entity.IntArrayEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Test double: records the events it receives instead of reacting to them.
 */
public class RecordingArrayObserver implements ArrayObserver {

  private final List<IntArrayEntity> addedEntities = new ArrayList<>();
  private final List<IntArrayEntity> changedEntities = new ArrayList<>();
  private final List<IntArrayEntity> removedEntities = new ArrayList<>();

  @Override
  public void arrayAdded(IntArrayEntity entity) {
    addedEntities.add(entity);
  }

  @Override
  public void arrayChanged(IntArrayEntity entity) {
    changedEntities.add(entity);
  }

  @Override
  public void arrayRemoved(IntArrayEntity entity) {
    removedEntities.add(entity);
  }

  public List<IntArrayEntity> getAddedEntities() {
    return List.copyOf(addedEntities);
  }

  public List<IntArrayEntity> getChangedEntities() {
    return List.copyOf(changedEntities);
  }

  public List<IntArrayEntity> getRemovedEntities() {
    return List.copyOf(removedEntities);
  }
}
