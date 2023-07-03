package io.ktakashi.tsii5m.concurrency.deadlock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        var lock1 = new ReentrantLock();
        var lock2 = new ReentrantLock();
        var channel = new LinkedBlockingQueue<Boolean>();
        var thread1 = new Thread(() -> lockAndWait(lock1, channel, lock2));
        var thread2 = new Thread(() -> lockAndWait(lock2, channel, lock1));
        thread1.start();
        thread2.start();
        channel.put(true);
        thread1.join();
        thread2.join();
    }

    private static void lockAndWait(ReentrantLock first, LinkedBlockingQueue<Boolean> channel, ReentrantLock second) {
        try {
            channel.take();
            first.lock();
            System.out.println(Thread.currentThread().getName() + " ready!");
            channel.put(true);
            System.out.println(Thread.currentThread().getName() + " 2nd lock!");
            second.lock();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            first.unlock();
            second.unlock();
        }
    }
}
