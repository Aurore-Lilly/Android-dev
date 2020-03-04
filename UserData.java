package entities;

public class UserData {
    private int id;
    private String currentGold;
    private int num_packs;

    public int getId() {
        return id;
    }

    public String getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(String currentGold) {
        this.currentGold = currentGold;
    }

    public int getNum_packs() {
        return num_packs;
    }

    public void setNum_packs(int num_packs) {
        this.num_packs = num_packs;
    }

    public UserData (int id,String currentGold, int num_packs){
        this.id  = id;
        this.currentGold = currentGold;
        this.num_packs = num_packs;
    }
}
