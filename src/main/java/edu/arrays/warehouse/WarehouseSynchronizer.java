package edu.arrays.warehouse;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.observer.ArrayObserver;

/**
 * The observer that keeps the warehouse in sync with the repository: it recalculates the aggregates
 * when an array appears or changes and forgets them when the array is removed.
 */
public class WarehouseSynchronizer implements ArrayObserver {

  private final Warehouse warehouse;

  public WarehouseSynchronizer(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  @Override
  public void arrayAdded(IntArrayEntity entity) {
    warehouse.recalculate(entity);
  }

  @Override
  public void arrayChanged(IntArrayEntity entity) {
    warehouse.recalculate(entity);
  }

  @Override
  public void arrayRemoved(IntArrayEntity entity) {
    warehouse.forget(entity);
  }
}
