package entities;

public class Pack {

    private int id;
    private String name;
    private Double price;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public  Pack(int id,String name,Double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }


}
