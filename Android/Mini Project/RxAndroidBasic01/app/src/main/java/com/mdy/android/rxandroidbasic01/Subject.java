package com.mdy.android.rxandroidbasic01;

import java.util.ArrayList;
import java.util.List;

/**
 * 옵저버 패턴 이해하기
 * 1초에 한번씩 등록된 옵저버들에게 "Hello" 메시지를 날린다.
 */

public class Subject extends Thread{

    // Subject는 옵저버를 담아둘 수 있는(저장할) 공간이 필요
    List<Observer> observers = new ArrayList<>();   // 옵저버를 등록하는 저장소
    boolean run_flag = true;

    public Subject() {

    }

    @Override
    public void run() {
        while(run_flag) {
            try {
                Thread.sleep(1000);
                for (Observer observer : observers) {
                    observer.notification("Hello");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 옵저버를 등록하는 함수
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    // 옵저버를 삭제하는 함수
    public void deleteObserver(int count){
        observers.remove(count);
    }

    // 옵저버에 공지하는 함수
    public interface Observer {
        public void notification(String msg);
    }
}
