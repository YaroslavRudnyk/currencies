package com.example.currencies.util;

public final class LogUtil {

  public static int getObjectLogId(Object obj) {
    return System.identityHashCode(obj);
  }
}
