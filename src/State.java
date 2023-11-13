import java.util.*;

public class State implements Comparable<State>
{
    private ArrayList<Person> LeftSide = new ArrayList<>();
    private ArrayList<Person> RightSide = new ArrayList<>();
    private int f, h, g;
    private State father;
    private int totalTime;
    int flashlight = 0;


    //constructor - fill with arguments if necessary
    public State()
    {
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.father = null;
//        this.totalTime = 0;
    }

    // copy constructor
    public State(State s)
    {
        // create a state similar with s...
    }

    public State(ArrayList<Person> leftSide, ArrayList<Person> rightSide, int f, int h, int g, State father, int flashlight) {
        LeftSide = leftSide;
        RightSide = rightSide;
        this.f = f;
        this.h = h;
        this.g = g;
        this.father = father;
        this.totalTime = totalTime;
        this.flashlight = flashlight;
    }

    public int getF()
    {
        return this.f;
    }

    public int getG()
    {
        return this.g;
    }

    public int getH()
    {
        return this.h;
    }

    public State getFather()
    {
        return this.father;
    }

    public void setF(int f)
    {
        this.f = f;
    }

    public void setG(int g)
    {
        this.g = g;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public void setFather(State f)
    {
        this.father = f;
    }

    public int getTotalTime()
    {
        return this.totalTime;
    }

    public void setTotalTime(int time)
    {
        this.totalTime = time;
    }

    public void evaluate()
    {
        //calculate f...
    }

    public void print() {}

    public ArrayList<State> getChildren() {return null;}

    public boolean isFinal() {return true;}

    @Override
    public boolean equals(Object obj) {return true;}

    @Override
    public int hashCode() {return 0;}

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.getF()); // compare based on the heuristic score.
    }

    public ArrayList<Person> getLeftSide() {
        return LeftSide;
    }

    public void setLeftSide(ArrayList<Person> leftSide) {
        LeftSide = leftSide;
    }

    public ArrayList<Person> getRightSide() {
        return RightSide;
    }

    public void setRightSide(ArrayList<Person> rightSide) {
        RightSide = rightSide;
    }

    public int getFlashlight() {
        return flashlight;
    }

    public void setFlashlight(int flashlight) {
        this.flashlight = flashlight;
    }
}