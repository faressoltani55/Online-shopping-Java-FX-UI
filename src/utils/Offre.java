package utils;

import java.util.Date;
import java.util.List;

public class Offre extends Special{

    private List<Item> items;

    public Offre(String name, String description, List<Item> items, double price, Date end, String imageUrl) {
        super(name, description, price, end, imageUrl);
        this.items = items;
        this.category = items.get(0).getCategory();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public double getOldPrice() {
        double oldPrice = 0;
        for (Item item: items){
            oldPrice += item.getPrice();
        }
        return  oldPrice;
    }
}
