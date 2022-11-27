package com.ampp8800.hochupomoch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Combine numbersSource and lettersSource in one rx chain to receive proper result in each test.
 * Write your chain by replacing resultObservable value with your solution
 */
public class FirstRxJavaTest {

    private final Observable<Integer> numbersSource = Observable.just(1, 2, 3, 4, 5, 6, 7);
    private final Observable<String> lettersSource = Observable.just("a", "b", "c", "d", "e", "f", "g");

    @Test
    public void combining1() {
        // TODO Replace with your solution
        Observable<String> resultObservable = Observable.error(new RuntimeException("Implement your solution"));

        List<String> resultList = new ArrayList<>();
        Disposable ignored = resultObservable.subscribe(resultList::add);
        
        // Expected result
        Assert.assertEquals(7, resultList.size());
        Assertions.assertIterableEquals(Arrays.asList("1a", "2b", "3c", "4d", "5e", "6f", "7g"), resultList);
    }

    @Test
    public void combining2() {
        // TODO Replace with your solution
        Observable<String> resultObservable = Observable.error(new RuntimeException("Implement your solution"));

        List<String> resultList = new ArrayList<>();
        Disposable ignored = resultObservable.subscribe(resultList::add);

        // Expected result
        Assert.assertEquals(4, resultList.size());
        Assertions.assertIterableEquals(Arrays.asList("1a", "3b", "5c", "7d"), resultList);
    }

    @Test
    public void combining3() {
        // TODO Replace with your solution
        Observable<String> resultObservable = Observable.error(new RuntimeException("Implement your solution"));

        List<String> resultList = new ArrayList<>();
        Disposable ignored = resultObservable.subscribe(resultList::add);

        // Expected result
        Assert.assertEquals(4, resultList.size());
        Assertions.assertIterableEquals(Arrays.asList("1A", "3C", "5E", "7G"), resultList);
    }
}
