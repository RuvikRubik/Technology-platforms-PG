package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Użycie: java Main <liczba wątków>");
            System.exit(1);
        }
        int numThreads = Integer.parseInt(args[0]);
        TaskQueue taskQueue = new TaskQueue();
        ResultCollector resultCollector = new ResultCollector();
        List<Thread> threads = new ArrayList<>();
        File file = new File("D:\\193113\\untitled\\src\\main\\java\\org\\example\\test2.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            long numberToCheck = Long.parseLong(scanner.nextLine());
            taskQueue.addTask(new Task(numberToCheck));
        }
        // Uruchamianie wątków realizujących złożone obliczenia
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new TaskWorker(taskQueue,resultCollector));
            thread.start();
            threads.add(thread);
        }

        // Wątek do zgłaszania nowych zadań przez użytkownika
        Thread userInputThread = new Thread(new UserInputHandler(taskQueue,resultCollector,threads,numThreads));
        userInputThread.start();

    }
}
