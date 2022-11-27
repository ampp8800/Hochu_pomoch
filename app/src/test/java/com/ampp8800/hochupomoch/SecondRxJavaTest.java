package com.ampp8800.hochupomoch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Combine numbersSource and lettersSource in one rx chain to receive proper result in each test.
 * Write your chain by replacing resultObservable value with your solution
 *
 * Beware - this two chains emit values with different time delay.
 */
public class SecondRxJavaTest {

    private final Observable<Integer> numbersSource = Observable.just(1, 2, 3, 4).zipWith(Observable.interval(200, TimeUnit.MILLISECONDS), (item, interval) -> item);
    private final Observable<String> lettersSource = Observable.just("a", "b", "c", "d").zipWith(Observable.interval(350, TimeUnit.MILLISECONDS), (item, interval) -> item);

    @Test
    public void getLatestFromEachSource() {
        // TODO Replace with your solution
        Observable<String> resultObservable = Observable.error(new RuntimeException("Implement your solution"));

        List<String> resultList = new ArrayList<>();
        Disposable ignored = resultObservable.subscribe(resultList::add);

        // Expected result
        Assert.assertEquals(resultList.size(), 6);
        Assertions.assertIterableEquals(Arrays.asList("1a", "2a", "3a", "3b", "4b", "4c"), resultList);
    }

    @Test
    public void summarizeInStrictOrder() {
        // TODO Replace with your solution
        Observable<String> resultObservable = Observable.error(new RuntimeException("Implement your solution"));

        List<String> resultList = new ArrayList<>();
        Disposable ignored = resultObservable.subscribe(resultList::add);

        // Expected result
        Assert.assertEquals(resultList.size(), 16);
        Assertions.assertIterableEquals(Arrays.asList("a1", "b1", "c1", "d1", "a2", "b2", "c2", "d2", "a3", "b3", "c3", "d3", "a4", "b4", "c4", "d4"), resultList);
    }
}
