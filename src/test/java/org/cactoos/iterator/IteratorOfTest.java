/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IteratorOf}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IteratorOfTest {

    @Test
    public void emptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "Must create empty iterator",
            new IteratorOf<>().hasNext(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    public void emptyIteratorThrowsException() {
        new Assertion<>(
            "Must throw an exception if empty",
            () -> new IteratorOf<>().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    public void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOf<Integer> iterator = this.iteratorWithFetchedElements();
        new Assertion<>(
            "Must create non empty iterator",
            iterator.hasNext(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    public void nonEmptyIteratorThrowsException() {
        new Assertion<>(
            "Must throw an exception if consumed",
            () -> this.iteratorWithFetchedElements().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    public void convertStringsToIterator() {
        new Assertion<>(
            "Must create an iterator of strings",
            new IterableOf<>(
                new IteratorOf<>(
                    "a", "b", "c"
                )
            ),
            new HasValues<>(
                "a", "b", "c"
            )
        ).affirm();
    }

    // @todo #1166:30min According to this Yegor's post
    //  https://www.yegor256.com/2016/05/03/test-methods-must-share-nothing.html
    //  test methods must share nothing. Refactor this class by inlining the
    //  the code below and remove the method below.
    private IteratorOf<Integer> iteratorWithFetchedElements() {
        final IteratorOf<Integer> iterator = new IteratorOf<>(
            1, 2, 3
        );
        iterator.next();
        iterator.next();
        iterator.next();
        return iterator;
    }
}
