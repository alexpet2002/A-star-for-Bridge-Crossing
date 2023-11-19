import java.util.ArrayList;

import static java.lang.Math.max;

public class Astar {
    //Astar algorithm implementation using MinPq and MaxPQ
    // use max priority queue when moving from right
    // use min priority queue when moving from left
    int totalTime = 30; // time limit is 30
    ArrayList<State> successfulStates = new ArrayList<>();
    ArrayList<Tuple2<Person, Person>> successfulCombinations = new ArrayList<>();

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
    // print combinations
    public void printCombinations() {
        for (Tuple2<Person, Person> tuple : successfulCombinations) {
            try{
                if (tuple.getSecond() == null) {
                    System.out.print(tuple.getFirst().toString()+ "->");
                } else {
                    System.out.print(tuple.getFirst().toString() + tuple.getSecond().toString()+"->");
                }
            }catch (Exception ignore){}
        }
    }

    // Generate states based on the available combinations generated(for right side)
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
            currentState.setChildren(generatedStates);

        }

        return generatedStates;
    }
    // Generate states based on the available people(for left side)
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
            Tuple2<Person, Person> singlePerson = new Tuple2<>(person, null);
            generatedState.setComb(singlePerson);
            generatedStates.add(generatedState);
            currentState.setChildren(generatedStates);
        }
        return generatedStates;
    }

    public static boolean isFinalState(State state) {
        return state.getRightSide().isEmpty();

    }
    // move a combination to the left
    public static void moveLeft(Tuple2<Person, Person> tuple2, ArrayList<Person> l, ArrayList<Person> r) {
        l.add(tuple2.getFirst());
        l.add(tuple2.getSecond());
        r.remove(tuple2.getSecond());
        r.remove(tuple2.getFirst());
    }
    // move a single person to the right
    public static void moveRight(Person p, ArrayList<Person> l, ArrayList<Person> r) {
        r.add(p);
        l.remove(p);
    }

    // get the state with the greatest value of f(n) and the smallest difference between consecutive values
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

    // calculate heuristic cost
    public static int heuristic(Tuple2<Person, Person> tuple) {
        return max(tuple.getFirst().getTime(), tuple.getSecond().getTime());
    }
    // calculate g(n)
    public static int FindTotalTime(int heuristicEstimate, int TimeTakenSoFar) {
        return heuristicEstimate + TimeTakenSoFar;
    }

    // helper method to generate an arraylist of states from an arraylist of combinations
    public static ArrayList<State> generateStatesFromComb(State state) {
        ArrayList<Tuple2> combinations;
        ArrayList<State> generatedStates;
        combinations = generateCombinations(state.getRightSide());
        generatedStates = generateStates(state, combinations);
        return generatedStates;
    }

    //A* algorithm
    public void AstarBridgeCrossing(State state) {
        int time = state.getTime();

        while (state != null && !isFinalState(state) && time < totalTime) {
            // if the arraylist has 1 element(the initial state) and the side is left
            if (successfulStates.size() == 1) {
                ArrayList<State> generatedStates = new ArrayList<>(generateStatesFromComb(state));
                State newState = getMinStateQueue(generatedStates);
                newState.setFather(state);
                state = newState;
                addStateandComb(state);
                state.setTime(time + state.getH());
                time = state.getTime();
                state.setFlashlight(1);
                System.out.println("\ntotal time so far is: " + time);
                System.out.println("L:" + state.getLeftSide().toString() + " R:" + state.getRightSide().toString());
                System.out.println();
            }
            // if the arraylist is empty(meaning the state is the initial state to be explored)
            if (successfulStates.isEmpty()) {
                addStateandComb(state);
                System.out.println("\ntotal time so far is: " + time);
                System.out.println("L:" + state.getLeftSide().toString() + " R:" + state.getRightSide().toString());
                System.out.println();
                // else check on which side the flashlight to generate combinations of 2 people(if on the right side) and 1 person if on the left side
            } else {
                // generally for right side
                if (state.getFlashlight() == 0) {
                    state = selectStateRight(state);
                    addStateandComb(state);
                    state.setTime(time + state.getH());
                    time = state.getTime();
                    state.setFlashlight(1);
                    System.out.println("\ntotal time so far is: " + time);
                    System.out.println("L:" + state.getLeftSide().toString() + " R:" + state.getRightSide().toString());
                    System.out.println();

                }
                // generally for left side
                else if (state.getFlashlight() == 1) {
                    state = selectStateLeft(state);
                    addStateandComb(state);
                    state.setTime(time + state.getH());
                    time = state.getTime();
                    state.setFlashlight(0);
                    System.out.println("L:" + state.getLeftSide().toString() + " R:" + state.getRightSide().toString());
                    System.out.println();
                }
            }
            // if the time limit is exceeded, retrace the path
            if (time > totalTime) {
                System.out.println("the path explored exceeded the time limit, exploring another path... \n");
                state = retracePath(state);
                AstarBridgeCrossing(state);
            }

        }
    }

    private void addStateandComb(State state) {
        successfulStates.add(state);
        successfulCombinations.add(state.getComb());
    }

    //select state based on min heuristic cost for left side
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
    //select state based on max heuristic cost for right side
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

    private State retracePath(State currentState) {
        if (isFinalState(currentState)) {
            //if state is final,go 2 states backwards
            removeStateandComb(currentState);
            State previousState;
            State newPreviousState;
            previousState = currentState.getFather();
            newPreviousState = previousState.getFather();
            removeStateandComb(previousState);
            newPreviousState.getChildren().remove(previousState);
            currentState = newPreviousState;
            return currentState;
        } else {
            removeStateandComb(currentState);
            // Remove the last state
            State previousState;
            previousState = currentState.getFather();
            previousState.getChildren().remove(currentState);
            removeStateandComb(previousState);
            currentState = previousState;
            if (currentState.getChildren().isEmpty()) {
                State parentState = currentState.getFather();
                parentState.getChildren().remove(currentState);
                currentState = parentState;
            }
            return currentState;
        }
    }

    //remove state and combination from successful lists
    private void removeStateandComb(State currentState) {
        successfulStates.remove(currentState); // Remove the last state
        successfulCombinations.remove(currentState.getComb()); // Remove the last combination
    }


    public void printSuccessfulStates() {
        for (State st : successfulStates) {
            System.out.println("people on the left: " + st.getLeftSide().toString() + " people on the right: " + st.getRightSide().toString());
        }
    }

    // get the state with the least value of f(n)
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

}
