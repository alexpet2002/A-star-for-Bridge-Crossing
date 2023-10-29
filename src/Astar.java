import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.max;

public class Astar {
    //    State currentState = new State();
    ArrayList<State> collectionOfStates = new ArrayList<>();

    public static boolean isFinalState(ArrayList<Person> RightSide) {
        if (RightSide.isEmpty()) {
            return true;
        }
        return false;

    }

    public void moveLeft(Tuple2<Person, Person> tuple2, State currentState) {
        currentState.getLeftSide().add(tuple2.getFirst());
        currentState.getLeftSide().add(tuple2.getSecond());
    }

    public void moveRight(Tuple2<Person, Person> tuple2, State currentState) {
        currentState.getRightSide().add(tuple2.getFirst());
        currentState.getRightSide().add(tuple2.getSecond());
    }

    private static Tuple2<Person, Person> findSlowestPeople(ArrayList<Person> RightSide) {
        int numPeopleOnTheRight = RightSide.size();

        if (!isFinalState(RightSide)) {
            Collections.sort(RightSide, Collections.reverseOrder());
            Person person1 = RightSide.get(0);
            Person person2 = RightSide.get(1);

            // Return the two slowest people to cross.
            return new Tuple2<Person, Person>(person1, person2);
        }
        return null;
    }
    //split the heuristic cost based on left/right side
    public static int heuristic(Tuple2<Person, Person> tuple) {
        return max(tuple.getFirst().getTime(), tuple.getSecond().getTime());

    }

    public int FindTotalTime(int heuristicEstimate, int TimeTakenSoFar) {
        return heuristicEstimate + TimeTakenSoFar;
    }

    public ArrayList<State> AstarForBridgeCrossing(State currentState, int timeLimit) {
        // need to keep track of the side needs to be explored each time
        //
        int heuristicCost = heuristic(findSlowestPeople(currentState.getRightSide()));
        currentState.setH(heuristicCost);
        if (currentState.getTotalTime() < timeLimit) {
            collectionOfStates.add(currentState);
            // copy the current state and move the slowest people
        }

        return null;
    }

}
