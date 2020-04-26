package entities;

public class Product {

    private String id;

    private String description;

    private double weight;

    private int priority;

    private int pieces;


    private int daystoexpire;

    private long barcode;

    public Product() {
    }

    public Product(String id, String description, long weight, int priority, int pieces, int daysToExpire , long barcode) {
        this.id = id;
        this.description = description;
        this.weight = weight;
        this.priority = priority;
        this.pieces = pieces;
        this.daystoexpire = daysToExpire;
        this.barcode = barcode;
    }

    public int getDaystoexpire() {
        return daystoexpire;
    }

    public void setDaystoexpire(int daystoexpire) {
        this.daystoexpire = daystoexpire;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }
}
