//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter the number of people: ");
//        int numPeople = scanner.nextInt();
//
//        ArrayList<Person> rightSide = new ArrayList<>();
//        for (int i = 0; i < numPeople; i++) {
//            System.out.print("Enter the name for person " + (i + 1) + ": ");
//            String name = scanner.next();
//
//            System.out.print("Enter the time required for person " + (i + 1) + ": ");
//            int time = scanner.nextInt();
//
//            rightSide.add(new Person(name, time));
//        }
//        System.out.print("please enter a time limit: ");
//        int TotalTimeLimit = scanner.nextInt();
//
//        Astar algo = new Astar(TotalTimeLimit);
//        ArrayList<Person> leftSide = new ArrayList<>();
//        State initialState = new State(leftSide, rightSide, 0, 0, 0, null, 0);
//        System.out.println("Finding a path... \n");
//        //Start time
//        long begin = System.currentTimeMillis();
//        //Starting the watch
//        new Astar(TotalTimeLimit).AstarBridgeCrossing(initialState);
//        //End time
//        long end = System.currentTimeMillis();
//
//            long time = end - begin;
//            System.out.println();
//            algo.AstarBridgeCrossing(initialState);
//            System.out.println("Time taken to run the algorithm: " + time + " milli seconds");
//
//            if (algo.successfulStates.size() == 1) {
//                System.out.println("No valid path found.");
//            } else {
//                System.out.println("The optimal path was found!");
//                System.out.println("printing steps...");
//                algo.printSuccessfulStates();
//                System.out.println("\nprinting the path...");
//                algo.printCombinations();
//            }
//        }
//    }
//}

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of people with input validation
        int numPeople = 0;
        while (true) {
            try {
                System.out.print("Enter the number of people: ");
                numPeople = scanner.nextInt();
                break; // Exit the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        ArrayList<Person> rightSide = new ArrayList<>();
        for (int i = 0; i < numPeople; i++) {
            // Get name with input validation
            System.out.print("Enter the name for person " + (i + 1) + ": ");
            String name = scanner.next();

            // Get time with input validation
            int time = 0;
            while (true) {
                try {
                    System.out.print("Enter the time required for person " + (i + 1) + ": ");
                    time = scanner.nextInt();
                    break; // Exit the loop if input is valid
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            rightSide.add(new Person(name, time));
        }

        // Get total time limit with input validation
        int totalTimeLimit = 0;
        while (true) {
            try {
                System.out.print("Please enter a time limit: ");
                totalTimeLimit = scanner.nextInt();
                break; // Exit the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        Astar algo = new Astar(totalTimeLimit);
        ArrayList<Person> leftSide = new ArrayList<>();
        State initialState = new State(leftSide, rightSide, 0, 0, 0, null, 0);
        System.out.println("Finding a path... \n");
        //Start time
        long begin = System.currentTimeMillis();
        //Starting the watch
        new Astar(totalTimeLimit).AstarBridgeCrossing(initialState);
        //End time
        long end = System.currentTimeMillis();

        long time = end - begin;
        System.out.println();
        algo.AstarBridgeCrossing(initialState);
        System.out.println("Time taken to run the algorithm: " + time + " milli seconds");

        if (algo.successfulStates.size() == 1) {
            System.out.println("No valid path found.");
        } else {
            System.out.println("The optimal path was found!");
            System.out.println("printing steps...");
            algo.printSuccessfulStates();
            System.out.println("\nprinting the path...");
            algo.printCombinations();
        }
    }
}

