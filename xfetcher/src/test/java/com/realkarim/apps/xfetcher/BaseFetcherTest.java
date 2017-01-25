package com.realkarim.apps.xfetcher;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

public class BaseFetcherTest {

    Integer responseTimeLimit = 5; // In Seconds

    @Test
    public void dataFetching_success(){
        final CountDownLatch signal = new CountDownLatch(1);

        BaseFetcher<String> baseFetcher = new BaseFetcher<String>() {
            @Override
            protected void onRawResponse(Byte[] raw) {
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onResponse(String response) {
                Assert.fail("onResponse fired from BaseFetcher instead of its derived class!");
            }

            @Override
            public void onError(String error) {
                Assert.fail(error);
            }
        };

        baseFetcher.fetchFromURL("");

        try {

            Boolean callbackReceived = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            if(!callbackReceived){
                Assert.fail("Callback not received because onRawResponse has not been fired!");
            }

        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void dataFetching_fail(){
        final CountDownLatch signal = new CountDownLatch(1);

        BaseFetcher<String> baseFetcher = new BaseFetcher<String>() {
            @Override
            protected void onRawResponse(Byte[] raw) {
                Assert.fail("onRawResponse is fired given a wrong URL!");
            }

            @Override
            public void onResponse(String response) {
                Assert.fail("onResponse fired from BaseFetcher instead of its derived class and given a wrong URL!");
            }

            @Override
            public void onError(String error) {
                signal.countDown(); // notify the count down latch

            }
        };

        baseFetcher.fetchFromURL("WRONG URL");

        try {

            Boolean callbackReceived = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            if(!callbackReceived){
                Assert.fail("Callback not received because onError has not been fired!");
            }

        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }
}
