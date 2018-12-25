/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.experimental;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.collection.Mapped;
import org.cactoos.iterable.IterableEnvelope;

/**
 * The envelope object which allows to execute the tasks concurrently.
 *
 * @param <T> The type of task result item.
 * @since 1.0.0
 */
class ThreadsEnvelope<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param fnc The function to map each task into {@link Future}.
     * @param tasks The tasks to be executed concurrently.
     * @checkstyle IllegalCatchCheck (20 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    ThreadsEnvelope(
        final Func<Collection<Callable<T>>, Collection<Future<T>>> fnc,
        final Iterable<Scalar<T>> tasks
    ) {
        super(() -> {
            try {
                return new Mapped<>(
                    Future::get,
                    fnc.apply(new Mapped<>(task -> task::value, tasks))
                );
            } catch (final Exception exp) {
                throw new CompletionException(exp);
            }
        });
    }
}
