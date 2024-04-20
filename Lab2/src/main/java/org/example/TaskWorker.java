package org.example;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class TaskWorker implements Runnable {
    private final TaskQueue taskQueue;
    private final ResultCollector resultCollector;

    public TaskWorker(TaskQueue taskQueue, ResultCollector resultCollector) {
        this.taskQueue = taskQueue;
        this.resultCollector = resultCollector;
    }

    @Override
    public void run() {
        System.out.println("powołano taskworkera.");
        while (true) {
            try {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Koniec pracy wątku.");
                    System.out.println(Thread.currentThread().getId());
                    resultCollector.zakonczono++;
                    return;
                }
                Task task = taskQueue.getTask();
                List<Long> dzielnik = new ArrayList<>();
                System.out.println("Wątek " + Thread.currentThread().getId() + " : Pobrano zadanie: " + task.getNumberToCheck());
                for(long i =1;i<=task.getNumberToCheck();i++){
                    if(task.getNumberToCheck() % i == 0){
                        dzielnik.add(i);
                    }
                }
                Wynik temp =new Wynik(task.getNumberToCheck(),dzielnik);
                resultCollector.addResult(temp);
                System.out.println("zakonczono działanie");
            } catch (InterruptedException e) {
                System.out.println("Koniec pracy wątku.");
                System.out.println(Thread.currentThread().getId());
                resultCollector.zakonczono++;
                return;
                //e.printStackTrace();
            }
    }
    }
}
