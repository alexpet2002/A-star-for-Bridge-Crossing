import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.max;

public class Astar {
    ArrayList<State> successfulOfStates = new ArrayList<>();
    ArrayList<State> generatedStates = new ArrayList<>();


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
    // Generate States based on the combinations of people generated previously

    public static ArrayList<State> generateStates(State currentState,ArrayList<Tuple2> combinations) {
        ArrayList<State> generatedStates = new ArrayList<>();
        int size = currentState.getRightSide().size();
        for (Tuple2 tuple:combinations) {
            ArrayList<Person> newRightSide = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                newRightSide.add(currentState.getRightSide().get(i));
                moveLeft(combinations.get(i),currentState);

            }
            int g = currentState.getG() ;
            //TODO: fix findSlowestPeople so that it takes tuple as parameter
            int heuristic = heuristic(findSlowestPeople(newRightSide));
            int finalCost = FindTotalTime(heuristic,currentState.getG());
            State generatedState = new State(currentState.getLeftSide(), newRightSide,finalCost,heuristic,g,currentState,finalCost,0);
            generatedStates.add(generatedState);
        }

        return generatedStates;
    }

    public static boolean isFinalState(ArrayList<Person> RightSide) {
        if (RightSide.isEmpty()) {
            return true;
        }
        return false;

    }
    //TODO: fix moveLeft & moveRight
    public static void moveLeft(Tuple2<Person, Person> tuple2, State currentState) {
        currentState.getLeftSide().add(tuple2.getFirst());
        currentState.getLeftSide().add(tuple2.getSecond());
    }

    public void moveRight(Tuple2<Person, Person> tuple2, State currentState) {
        currentState.getRightSide().add(tuple2.getFirst());
        currentState.getRightSide().add(tuple2.getSecond());
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

    //TODO: split the heuristic cost based on left/right side
    public static int heuristic(Tuple2<Person, Person> tuple) {
        return max(tuple.getFirst().getTime(), tuple.getSecond().getTime());

    }
    //Node f(n)
    //g(n)

    public static int FindTotalTime(int heuristicEstimate, int TimeTakenSoFar) {
        return heuristicEstimate + TimeTakenSoFar;
    }
    //TODO: implement Astar
//    public ArrayList<State> AstarForBridgeCrossing(State currentState, int timeLimit) {
//        // need to keep track of the side needs to be explored each time
//        //
//        int heuristicCost = heuristic(findSlowestPeople(currentState.getRightSide()));
//        currentState.setH(heuristicCost);
//        if (currentState.getTotalTime() < timeLimit) {
//            collectionOfStates.add(currentState);
//            // copy the current state and move the slowest people
//        }
//
//        return null;
//    }

    public static void main(String[] args) {
        ArrayList<Person> people= new ArrayList<>();
        Person p1 = new Person("a", 1);
        Person p2 = new Person("b", 3);
        Person p3 = new Person("c", 6);
        Person p4 = new Person("d", 8);
        Person p5 = new Person("e", 12);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);
        Astar test = new Astar();
        test.printCombinations(generateCombinations(people));
    }

}
