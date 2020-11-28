package com.hw.githubclient.rxtest;

import android.os.Handler;
import android.util.Log;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Sources {
    private static final String TAG = Sources.class.getSimpleName();

    private static final class Producer {
        Observable<String> just() {
            return Observable.just("1", "2", "3", "3");
        }

        Observable<String> just2() {
            return Observable.just("4", "5", "6");
        }

        boolean randomResult() {
            return Arrays.asList(true, false, true).get(new Random().nextInt(2));
        }

        Observable create() {
            return Observable.create((emitter -> {
                try {
                    for (int i = 0; i< 10; i++) {
                        Thread.sleep(5000);
                        if (randomResult()) {
                            emitter.onNext("Success");
                            //emitter.onComplete();
                        } else {
                            emitter.onError(new RuntimeException("Error!"));
                            //emitter.onNext("UnSuccess");
                            break;
                        }
                    }
                } catch (Throwable t) {
                    emitter.onError(new RuntimeException("Error!"));
                }
            })).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

        Completable completable() {
            return Completable.create((emitter -> {
                try {
                    if (randomResult()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(new RuntimeException("Error 1!"));
                    }

                } catch (Throwable t) {
                    emitter.onError(new RuntimeException("Error 2!"));
                }
            }));
        }

        Maybe maybe() {
            return Maybe.create((emitter -> {
                try {
                    final boolean result = randomResult();

                    if (result) {
                        emitter.onSuccess(result);
                    } else {
                        emitter.onComplete();
                    }
                } catch (Throwable t) {
                    emitter.onError(new RuntimeException("Error 2!"));
                }
            }));
        }

        Single single() {
            return Single.fromCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "Some string lalue";
                }
            });
        }

        ConnectableObservable hotObservable() {
            return Observable.interval(1, TimeUnit.SECONDS).publish();
            //return Observable.interval(1, TimeUnit.SECONDS).publish().replay();
            //return Observable.interval(1, TimeUnit.SECONDS).replay();
            //return Observable.interval(1, TimeUnit.SECONDS).cache();
        }

        Observable hotToColdObservable() {
            //return Observable.interval(1, TimeUnit.SECONDS).publish().refCount();

            return Observable.interval(1, TimeUnit.SECONDS).cache();
        }

        PublishSubject<String> publishSubject() {
            return PublishSubject.create();
        }

        PublishSubject<Long> publishSubjectLong() {
            Observable observable = Observable.interval(1, TimeUnit.SECONDS);

            PublishSubject subject = PublishSubject.create();

            observable.subscribe(subject);

            return subject;
        }

    }

    private static final class Consumer {
        Producer producer;

        Consumer(Producer producer) {
            this.producer = producer;
        }

        public void execCompletable() {
            producer.completable().subscribe(
                    () -> {
                        Log.i(TAG, "onComplete ");},
                    (e) -> Log.i(TAG, "onError " + e.getMessage()));

        }

        public void execSingle() {
            producer.single().subscribe(s -> Log.i(TAG, "onSuccess  " + s), (e) -> {
                Log.i(TAG, "onError ");
            });
        }

        public void execMayBe() {
            producer.maybe().subscribe((s) -> {
                Log.i(TAG, "onSuccess  " + s);
            }, (e) -> {
                Log.i(TAG, "onError ");
            }, () -> {
                Log.i(TAG, "onComplete  ");
            });
        }

        public void execHotObservable() {
            ConnectableObservable hotObservarable = producer.hotObservable();

            hotObservarable.retry(5).subscribe((s) -> { Log.i(TAG, "Observer 1:  " + s); });

            hotObservarable.connect();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hotObservarable.subscribe((s) -> { Log.i(TAG, "Delayed:  " + s); });
                }
            }, 2000);
        }

        public void execHotToColdObservable() {
            Observable hotObservarable = producer.hotToColdObservable();
            hotObservarable.subscribe((s) -> { Log.i(TAG, "Observer 1:  " + s); });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hotObservarable.subscribe((s) -> { Log.i(TAG, "Delayed:  " + s); });
                }
            }, 2000);
        }

        public void execPublishSubject() {
            final PublishSubject subject = producer.publishSubject();

            subject.subscribe((s) -> {
                Log.i(TAG, "onNext  " + s);
            }, (e) -> {
                Log.i(TAG, "onError ");
            });

            // Откуда-то вызов
            subject.onNext("Hello!");
        }

        public void execPublishSubjectLong() {
            final PublishSubject subject = producer.publishSubjectLong();

            subject.subscribe((s) -> {
                Log.i(TAG, "onNext  " + s);
            }, (e) -> {
                Log.i(TAG, "onError ");
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Откуда-то вызов
                    subject.onNext(23);
                }
            }, 2000);
        }

        public void execCreateOnThread() {
            producer.create().retry(5).map((s)-> {return Log.i(TAG, "Thread: " + Thread.currentThread().getName())
                    ;})
//            .onErrorReturn((t) -> {
//                return Log.i(TAG, "on Error message ");
//            })
                    .onErrorResumeNext((t) -> {
                        return Observable.just("2");
                        //return Log.i(TAG, "on Error message ");
                    })
                    .subscribe(
                            (s) -> Log.i(TAG, "onNext " + s),
                            (e) -> Log.i(TAG, "onError"),
                            () -> Log.i(TAG, "onComplete "));
        }

        public void exec() {
            //execCompletable();
            //execSingle();
            //execMayBe();
            //execHotObservable();
            //execHotToColdObservable();
            //execPublishSubject();
            //execPublishSubjectLong();
            execCreateOnThread();
        }
    }

    public static void exec() {
        new Consumer(new Producer()).exec();
    }

}
