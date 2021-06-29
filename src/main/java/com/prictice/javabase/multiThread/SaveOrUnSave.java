package com.prictice.javabase.multiThread;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: SavaOrUnSave.java
 * @package com.prictice.javabase.multiThread
 * @description:
 * @date 2019/9/12 11:24
 */
public class SaveOrUnSave implements Runnable{

    private int num = 0;

    private AtomicInteger atomicInteger = new AtomicInteger();

    private List list;

    private CopyOnWriteArrayList copyOnWriteArrayList;

    private HashMap hashMap;

    private ConcurrentHashMap concurrentHashMap;

    public void increment(){
        for (int i = 0; i < 10000; i++) {
            num++;
            atomicInteger.addAndGet(1);
        }
    }

    @Override
    public void run() {
        this.increment();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public CopyOnWriteArrayList getCopyOnWriteArrayList() {
        return copyOnWriteArrayList;
    }

    public void setCopyOnWriteArrayList(CopyOnWriteArrayList copyOnWriteArrayList) {
        this.copyOnWriteArrayList = copyOnWriteArrayList;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public ConcurrentHashMap getConcurrentHashMap() {
        return concurrentHashMap;
    }

    public void setConcurrentHashMap(ConcurrentHashMap concurrentHashMap) {
        this.concurrentHashMap = concurrentHashMap;
    }
}
