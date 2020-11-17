package com.hw.githubclient.rxtest;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Creation {
    private static final String TAG = Creation.class.getSimpleName();

    private static final class Producer {

        Observable<String> just() {
            return Observable.just("1", "2", "3");
        }

        Observable<String> fromIterable() {
            return Observable.fromIterable(Arrays.asList("1", "2", "3"));
        }

        Observable interval() {
            return Observable.interval(5, TimeUnit.SECONDS);
        }

        Observable timer() {
            return Observable.timer(5, TimeUnit.SECONDS);
        }

        Observable range() {
            return Observable.range(1, 10);
        }

        Observable fromCallable() {
            return Observable.fromCallable(() -> Producer.this.randomResult());
        }

        boolean randomResult() {
            return Arrays.asList(true, false, true).get(new Random().nextInt(2));
        }

        Observable create() {
            return Observable.create((new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                    try {
                        for (int i = 0; i < 10; i++) {
                            if (Producer.this.randomResult()) {
                                emitter.onNext("Success");
                                emitter.onComplete();
                                break;
                            } else {
                                emitter.onError(new RuntimeException("Error!"));
                                break;

                            }
                        }
                    } catch (Throwable t) {
                        emitter.onError(new RuntimeException("Error!"));
                    }
                }
            }));
        }
    }

    private static final class Consumer {
        Producer producer;

        Consumer(Producer producer) {
            this.producer = producer;
        }

        final Observer<String> stringObserver = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        };

        private void execJust() {
            producer.just().subscribe(stringObserver);
        }

        private void execLambda() {
            Disposable disposable = producer.just().subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete "));
        }

        private void execFromIterable() {
            producer.fromIterable().subscribe(stringObserver);
        }

        private void execInterval() {
            producer.interval().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }

        private void execTimer() {
            producer.timer().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }

        private void execRanger() {
            producer.range().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }

        private void execFromCallable() {
            producer.fromCallable().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }

        private void execCreate() {
            Disposable disposable = producer.create().subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError"),
                    () -> Log.i(TAG, "onComplete "));

        }

        public void exec() {
            //execJust();
            //execLambda();
            //execFromIterable();
            //execInterval();
            //execTimer();
            //execRanger();
            //execFromCallable();

            execCreate();
        }


    }

    public static void exec() {
        new Consumer(new Producer()).exec();
    }


}
