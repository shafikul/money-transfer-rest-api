package com.shafikul.money.transfer.utility;

public interface Transformer<S, T> {

    T transform(S source);

    S transformBack(T target);
}