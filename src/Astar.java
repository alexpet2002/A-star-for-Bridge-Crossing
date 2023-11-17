import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.max;

public class Astar {
    ArrayList<State> successfulStates = new ArrayList<>();
    // use max priority queue when moving from right
    // use min priority queue when moving from left
    int totalTime = 30;

//    public Astar(int totalTime) {
//        this.totalTime = totalTime;
//    }


    // Generate combinations based on the available people you have on the right side
    public static ArrayList<Tuple2> generateCombinations(ArrayList<Person> currentSide) {
        ArrayList<Tuple2> possibleCombinations = new ArrayList<>();
        for (int i = 0; i < currentSide.size() - 1; i++) {
            for (int j = i + 1; j < currentSide.size(); j++) {
                Person a = currentSide.get(i);
                Person b = currentSide.get(j);
                Tuple2<Person, Person> set = new Tuple2<>(a, b);
                possibleCombinations.add(set);

            }

        }
        return possibleCombinations;
    }

    public void printCombinations(ArrayList<Tuple2> possibleCombinations) {
        for (Tuple2<Person, Person> tuple : possibleCombinations) {
            System.out.println(tuple);
        }
    }

    public static ArrayList<State> generateStates(State currentState, ArrayList<Tuple2> combinations) {
        ArrayList<State> generatedStates = new ArrayList<>();
        // for each combination create a new state
        for (Tuple2 tuple : combinations) {
            ArrayList<Person> newRightSide = new ArrayList<>(currentState.getRightSide());
            ArrayList<Person> newLeftSide = new ArrayList<>(currentState.getLeftSide());
            moveLeft(tuple, newLeftSide, newRightSide);
            int g = currentState.getG();
            int heuristic = heuristic(tuple);
            int finalCost = FindTotalTime(heuristic, currentState.getG());
            State generatedState = new State(newLeftSide, newRightSide, finalCost, heuristic, finalCost, currentState, 0, tuple);
            generatedStates.add(generatedState);
        }

        return generatedStates;
    }

    public static ArrayList<State> generateStates(State currentState) {
        ArrayList<State> generatedStates = new ArrayList<>();
        for (Person person : currentState.getLeftSide()) {
            ArrayList<Person> newRightSide = new ArrayList<>(currentState.getRightSide());
            ArrayList<Person> newLeftSide = new ArrayList<>(currentState.getLeftSide());
            moveRight(person, newLeftSide, newRightSide);
            int g = currentState.getG();
            int heuristic = person.getTime();
            int finalCost = FindTotalTime(heuristic, currentState.getG());
            State generatedState = new State(newLeftSide, newRightSide, finalCost, heuristic, g, currentState, 0);
            generatedStates.add(generatedState);

        }
        return generatedStates;
    }

    public static boolean isFinalState(ArrayList<Person> RightSide) {
        return RightSide.isEmpty();

    }

    public static void moveLeft(Tuple2<Person, Person> tuple2, ArrayList<Person> l, ArrayList<Person> r) {
        l.add(tuple2.getFirst());
        l.add(tuple2.getSecond());
        r.remove(tuple2.getSecond());
        r.remove(tuple2.getFirst());
    }

    public static void moveRight(Person p, ArrayList<Person> l, ArrayList<Person> r) {
        r.add(p);
        l.remove(p);
    }

    private static Tuple2<Person, Person> findSlowestPeople(ArrayList<Person> RightSide) {
        if (!isFinalState(RightSide)) {
            RightSide.sort(Collections.reverseOrder());
            Person person1 = RightSide.get(0);
            Person person2 = RightSide.get(1);
            // Return the two slowest people to cross.
            return new Tuple2<Person, Person>(person1, person2);
        }
        return null;
    }


    public static State getMaxStateQueue(ArrayList<State> allStates) {
        MaxPQ maxTempQueue = new MaxPQ(new IntegerComparator());
        MinPQ minQueue = new MinPQ(new IntegerComparator());
        // Populate the priority queue
        for (State state : allStates) {
            maxTempQueue.add(state.getF());
        }
        // Get the optimal cost
        Integer optimalCost = (Integer) maxTempQueue.getMax();

        // Iterate through all states to find the optimal state
        for (State state : allStates) {
            if (state.getF() == optimalCost) {
                int difference = state.getCombDifference();
                minQueue.add(difference);
            }
        }
        int optimalDiff = (Integer) minQueue.getMin();
        for (State state : allStates) {
            int difference = state.getCombDifference();
            if (state.getF() == optimalCost && difference == optimalDiff) {
                return state;
            }
        }

        return null; // Return null if no optimal state is found
    }

    public static int heuristic(Tuple2<Person, Person> tuple) {
        return max(tuple.getFirst().getTime(), tuple.getSecond().getTime());
    }

    public static int FindTotalTime(int heuristicEstimate, int TimeTakenSoFar) {
        return heuristicEstimate + TimeTakenSoFar;
    }

    public static ArrayList<State> generateStatesFromComb(State state) {
        ArrayList<Tuple2> combinations;
        ArrayList<State> generatedStates;
        combinations = generateCombinations(state.getRightSide());
        generatedStates = generateStates(state, combinations);
        return generatedStates;
    }


    //    public void AstarBridgeCrossing(State state) {
//        int time = 0;
//        while (state != null && !isFinalState(state.getRightSide()) && time <= totalTime) {
//            // if the arraylist is empty(meaning the state is the initial state to be explored)
//            if (successfulStates.size() == 1) {
//                ArrayList<State> generatedStates = new ArrayList<>(generateStatesFromComb(state));
//                State newState = getMinStateQueue(generatedStates);
//                newState.setFather(state);
//                state = newState;
//                successfulStates.add(state);
//                System.out.println(state.getComb());
//                System.out.println(state.getG());
//                time = time + state.getH();
//                state.setFlashlight(1);
//                System.out.println("total time so far is: "+ time);
//            }
//            if (successfulStates.isEmpty()) {
//                successfulStates.add(state);
//                // else check on which side the flashlight to generate combinations of 2 people(if on the right side) and 1 person if on the left side
//            } else {
//                if (state.getFlashlight() == 0) {
//                    ArrayList<State> generatedStates = new ArrayList<>(generateStatesFromComb(state));
//                    State newState = getMaxStateQueue(generatedStates);
//                    newState.setFather(state);
//                    state = newState;
//                    successfulStates.add(state);
//                    System.out.println(state.getComb());
//                    System.out.println(state.getG());
//                    time = time + state.getH();
//                    state.setFlashlight(1);
//                    System.out.println("total time so far is: "+ time);
//
//                } else if (state.getFlashlight() == 1) {
//                    ArrayList<State> generatedStates;
//                    generatedStates = generateStates(state);
//                    State newState = getMinStateQueue(generatedStates);
//                    newState.setFather(state);
////                    newState.setG(state.getF());
//                    state = newState;
//                    successfulStates.add(state);
//                    System.out.println(state.getG());
//                    System.out.println(state.getF());
//                    time = time + state.getH();
//                    state.setFlashlight(0);
//                    System.out.println("total time so far is: "+ time);
//                }
//            }
//            if (time > totalTime) {
//                backTrack(state);
//            }
//
////            throw new RuntimeException("Error: The path couldn't be found under the given time limit. Please try another time limit!");
//        }
//
//    }
    public void AstarBridgeCrossing(State state) {
        int time = 0;
        while (state != null && !isFinalState(state.getRightSide()) && time <= totalTime) {
            // if the arraylist is empty(meaning the state is the initial state to be explored)
            if (successfulStates.size() == 1) {
                ArrayList<State> generatedStates = new ArrayList<>(generateStatesFromComb(state));
                State newState = getMinStateQueue(generatedStates);
                newState.setFather(state);
                state = newState;
                successfulStates.add(state);
                System.out.println(state.getComb());
                System.out.println(state.getG());
                time = time + state.getH();
                state.setFlashlight(1);
                System.out.println("total time so far is: " + time);
            }
            if (successfulStates.isEmpty()) {
                successfulStates.add(state);
                // else check on which side the flashlight to generate combinations of 2 people(if on the right side) and 1 person if on the left side
            } else {
                if (state.getFlashlight() == 0) {
                    state = selectStateRight(state);
                    successfulStates.add(state);
                    time = time + state.getH();
                    state.setFlashlight(1);

                } else if (state.getFlashlight() == 1) {
                    state = selectStateLeft(state);
                    successfulStates.add(state);
                    time = time + state.getH();
                    state.setFlashlight(0);
                }
                System.out.println("\ntotal time so far is: " + time);
            }
            if (time > totalTime) {
                System.out.println("the path explored exceeded the time limit, exploring another path... ");
                backTrack(state);
                AstarBridgeCrossing(state);
            }

//            throw new RuntimeException("Error: The path couldn't be found under the given time limit. Please try another time limit!");
        }

    }

    private State selectStateLeft(State state) {
        if (state.getChildren().isEmpty()) {
            ArrayList<State> generatedStates;
            generatedStates = generateStates(state);
            State newState = getMinStateQueue(generatedStates);
            newState.setFather(state);
            state = newState;
        } else {
            State newState = getMinStateQueue(state.getChildren());
            newState.setFather(state);
            state = newState;
        }
        return state;
    }

    private State selectStateRight(State state) {
        if (state.getChildren().isEmpty()) {

            ArrayList<State> generatedStates = new ArrayList<>(generateStatesFromComb(state));
            state.setChildren(generatedStates);
            State newState = getMaxStateQueue(generatedStates);
            newState.setFather(state);
            state = newState;
        } else {
            State newState = getMaxStateQueue(state.getChildren());
            newState.setFather(state);
            state = newState;

        }
        return state;
    }

    //generate children left
    private void backTrack(State currentState) {
        while (currentState != null) {
            successfulStates.remove(currentState); // Remove the last state
            State previousState;
            previousState = currentState.getFather();
            previousState.getChildren().remove(currentState);
            currentState = previousState;
        }
    }

    public void printSuccessfulStates() {
        for (State st : successfulStates) {
            System.out.println("people on the left: " + st.getLeftSide() + " people on the right side: " + st.getRightSide());
        }
    }

    private State getMinStateQueue(ArrayList<State> allStates) {
        MinPQ tempQueue = new MinPQ(new IntegerComparator());
        for (State state : allStates) {
            tempQueue.add(state.getF());
        }
        Integer optimalCost = (Integer) tempQueue.getMin();
        for (State state : allStates) {
            if (state.getF() == optimalCost) {
                return state;
            }

        }
        return null;
    }

    public static void main(String[] args) {
        Astar algo = new Astar();
        ArrayList<Person> leftSide = new ArrayList<>();
        ArrayList<Person> rightSide = new ArrayList<>();
        Person a = new Person("Sasha", 1);
        Person b = new Person("Sasha", 3);
        Person c = new Person("Jo", 6);
        Person d = new Person("Sara", 8);
        Person e = new Person("Max", 12);
        rightSide.add(a);
        rightSide.add(b);
        rightSide.add(c);
        rightSide.add(d);
        rightSide.add(e);
        State initialState = new State(leftSide, rightSide, 0, 0, 0, null, 0);
        algo.AstarBridgeCrossing(initialState);
    }

}
