package utils;

import java.util.Date;

public class Promotion extends Special{
    private Item item;

    public Promotion(String name, String description, Item item, double price, Date end, String imageUrl) {
        super(name, description, price, end, imageUrl);
        this.item = item;
        this.category = item.getCategory();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public double getOldPrice() {
        return item.getPrice();
    }
}
