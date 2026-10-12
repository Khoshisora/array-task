package edu.arrays.exception;


public class ArrayException extends Exception {

  public ArrayException(String message) {
    super(message);
  }

  public ArrayException(String message, Throwable cause) {
    super(message, cause);
  }
}
