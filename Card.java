package entities;

public class Card {
    private int id;
    private String name;
    private int powerful;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPowerful() {
        return powerful;
    }

    public void setPowerful(int powerful) {
        this.powerful = powerful;
    }

    public Card (int id, String name, int powerful){
        this.id = id;
        this.name = name;
        this.powerful = powerful;
    }

    public Card() {
    }
}
