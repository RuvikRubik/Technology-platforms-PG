package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static void dodajMaga(EntityManager em, Scanner scanner) {
        em.getTransaction().begin();

        System.out.print("Podaj nazwę maga: ");
        String name = scanner.nextLine();
        System.out.print("Podaj poziom maga: ");
        int level = Integer.parseInt(scanner.nextLine());
        System.out.print("Podaj nazwę wieży: ");
        String towerName = scanner.nextLine();

        Tower tower = em.createQuery("SELECT t FROM Tower t WHERE t.name = :towerName", Tower.class)
                .setParameter("towerName", towerName)
                .getSingleResult();

        if (tower == null) {
            System.out.println("Wieża o podanej nazwie nie istnieje.");
            em.getTransaction().rollback();
            return;
        }

        Mage mage = new Mage(name,level,tower);

        em.persist(mage);
        em.getTransaction().commit();
        em.refresh(tower);
        System.out.println("Dodano nowego maga.");
    }

    private static void dodajWieze(EntityManager em, Scanner scanner) {
        em.getTransaction().begin();

        System.out.print("Podaj nazwę wieży: ");
        String name = scanner.nextLine();
        System.out.print("Podaj wysokość wieży: ");
        int height = Integer.parseInt(scanner.nextLine());

        Tower tower = new Tower(name,height);

        em.persist(tower);
        em.getTransaction().commit();

        System.out.println("Dodano nową wieżę.");
    }

    private static void usunMaga(EntityManager em, Scanner scanner) {
        em.getTransaction().begin();

        System.out.print("Podaj nazwę maga do usunięcia: ");
        String name = scanner.nextLine();

        Mage mage = em.find(Mage.class, name);
        if (mage != null) {
            Tower tower = mage.getTower();
            tower.getMages().remove(mage);
            em.remove(mage);
            em.getTransaction().commit();
            System.out.println("Usunięto maga: " + name);
        } else {
            System.out.println("Nie znaleziono maga o nazwie: " + name);
            em.getTransaction().rollback();
        }
    }

    private static void usunWieze(EntityManager em, Scanner scanner) {
        em.getTransaction().begin();

        System.out.print("Podaj nazwę wieży do usunięcia: ");
        String name = scanner.nextLine();

        Tower tower = em.find(Tower.class, name);
        if (tower != null) {
            em.remove(tower);
            em.getTransaction().commit();
            System.out.println("Usunięto wieżę: " + name);
        } else {
            System.out.println("Nie znaleziono wieży o nazwie: " + name);
            em.getTransaction().rollback();
        }
    }

    private static void wyswietlWszystkie(EntityManager em) {
        List<Mage> mages = em.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
        List<Tower> towers = em.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();

        System.out.println("Lista magów:");
        for (Mage mage : mages) {
            System.out.println("Nazwa: " + mage.getName() + ", Poziom: " + mage.getLevel() + ", Wieża: " + mage.getTower().getName());
        }

        System.out.println("Lista wież:");
        for (Tower tower : towers) {
            System.out.print("Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight() + ", Liczba magów: " + tower.getMages().size());
        }
    }


    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager em = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Dodaj nowego maga");
            System.out.println("2. Dodaj nową wieżę");
            System.out.println("3. Usuń maga");
            System.out.println("4. Usuń wieżę");
            System.out.println("5. Wyświetl Wszystko");
            System.out.println("6. Pobranie wszystkie wież niższych niż");
            System.out.println("7. Wyjdź z aplikacji");
            System.out.print("Wybierz opcję: ");
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    dodajMaga(em, scanner);
                    break;
                case 2:
                    dodajWieze(em, scanner);
                    break;
                case 3:
                    usunMaga(em, scanner);
                    break;
                case 4:
                    usunWieze(em, scanner);
                    break;
                case 5:
                    wyswietlWszystkie(em);
                    break;
                case 6  :
                    System.out.println("podaj wysokosc:");
                    int height = Integer.parseInt(scanner.nextLine());
                    List<Tower> shorterTowers = em.createQuery("SELECT t FROM Tower t WHERE t.height < :height", Tower.class)
                            .setParameter("height", height)
                            .getResultList();
                    System.out.println("Lista wież niższych niż "+height+":");
                    for (Tower tower : shorterTowers) {
                        System.out.println("Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight());
                    }
                    break;
                case 7:
                    System.out.println("Zamykanie aplikacji...");
                    em.close();
                    factory.close();
                    break;
                default:
                    System.out.println("Niepoprawna opcja, spróbuj ponownie.");
            }
        }
    }
}