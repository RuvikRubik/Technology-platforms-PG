package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private Queue<Task> queue = new LinkedList<>();

    public synchronized void addTask(Task task) {
        queue.offer(task);
        notify(); // Powiadomienie o dodaniu nowego zadania
    }

    public synchronized Task getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Czekanie na dostępne zadanie

        }
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}