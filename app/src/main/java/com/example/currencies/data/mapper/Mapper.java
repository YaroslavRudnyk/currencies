package com.example.currencies.data.mapper;

public interface Mapper<F, T> {
  T map(F from) throws RuntimeException;
}
