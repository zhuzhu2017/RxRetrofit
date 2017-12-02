package com.allen.rxretrofitlib.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * retry处理
 * Created by allen on 2017/12/2.
 */

public class RetryWhenNetworkException implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private int retryCount; //重试次数
    private int retryDelay; //延时重试
    private int retryDelayIncrease; //重试一次延时重试增加的时间

    public RetryWhenNetworkException() {
        this(0, 0, 0);
    }

    public RetryWhenNetworkException(int retryCount, int retryDelay) {
        this(retryCount, retryDelay, 0);
    }

    public RetryWhenNetworkException(int retryCount, int retryDelay, int retryDelayIncrease) {
        this.retryCount = retryCount;
        this.retryDelay = retryDelay;
        this.retryDelayIncrease = retryDelayIncrease;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable
                .zipWith(Observable.range(1, retryCount + 1), new Func2<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper call(Throwable throwable, Integer integer) {
                        return new Wrapper(throwable, integer);
                    }
                })
                .flatMap(new Func1<Wrapper, Observable<?>>() {
                    @Override
                    public Observable<?> call(Wrapper wrapper) {
                        if ((wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException)
                                || wrapper.throwable instanceof HttpException
                                && wrapper.index < retryCount + 1) {    //如果超出重试次数也抛出异常，否则默认是进入onComplete
                            return Observable.timer(retryDelay + (wrapper.index - 1) + retryDelayIncrease, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(new Throwable("：Token校验失败"));
                    }
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}
