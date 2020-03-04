package entities;


public class Collection {

    private String name;
    private int card_num;

    public Collection(String name, int card_num)
    {
        this.name = name;
        this.card_num = card_num;
    }

    public int getCard_num() {
        return card_num;
    }

    public String getName() {
        return name;
    }
}

