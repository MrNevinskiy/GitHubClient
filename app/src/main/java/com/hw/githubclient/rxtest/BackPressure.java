package com.hw.githubclient.rxtest;


import android.util.Log;

import java.util.Arrays;
import java.util.Random;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackPressure {
    private static final String TAG = BackPressure.class.getSimpleName();

    private final static class Producer {
        Observable<String> just() {
            return Observable.just("1", "2", "3", "3");
        }

        Observable<String> just2() {
            return Observable.just("4", "5", "6");
        }

        boolean randomResult() {
            return Arrays.asList(true, false, true).get(new Random().nextInt(2));
        }

        private Observable noBackPressure() {
            return Observable.range(1, 10000000).subscribeOn(Schedulers.io());
        }

        private Flowable backPressure() {
            return Flowable.range(1, 10000000).subscribeOn(Schedulers.io()).onBackpressureLatest();
        }
    }

    private static final class Consumer {
        BackPressure.Producer producer;

        Consumer(BackPressure.Producer producer) {
            this.producer = producer;
        }

        public void consumeNoBackPressure() {
            producer.noBackPressure().observeOn(Schedulers.computation()).subscribe((s) -> {
                Thread.sleep(1000);
                Log.i(TAG, s.toString());
            });
        }

        public void consumeBackPressure() {
            producer.backPressure().observeOn(Schedulers.computation(), false, 1).subscribe((s) -> {
                Thread.sleep(1000);
                Log.i(TAG, s.toString());
            });
        }

        public void execComposite() {
            CompositeDisposable compositeDisposable = new CompositeDisposable();

            Disposable disposable1 = producer.just().subscribe((s)->{Log.i(TAG, "onNext 1 " + s);});
            Disposable disposable2 = producer.just2().subscribe((s)->{Log.i(TAG, "onNext 2 " + s);});

            compositeDisposable.addAll(disposable1);
            compositeDisposable.addAll(disposable2);

            compositeDisposable.dispose();
        }


        public void exec() {
            //consumeNoBackPressure();
            //consumeBackPressure();
            execComposite();
        }
    }

    public static void exec() {
        new Consumer(new Producer()).exec();
    }
}
