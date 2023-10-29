public class Person {
    private String name;
    private int time;

    public Person(String name,int time) {
        this.name = name;
        this.time = time;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public String toString(){
        return time +""+ name;
    }
}
