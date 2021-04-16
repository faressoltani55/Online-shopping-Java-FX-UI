package utils;

import java.util.Date;

public abstract class Special {

    protected String name;
    protected String description;
    protected double price;
    protected Date start;
    protected Date end;
    protected String imageUrl;
    protected Category  category;

    public Category getCategory() {
        return category;
    }

    public Special(String name, String description, double price, Date end, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.start = new Date();
        this.end = end;
        this.imageUrl = imageUrl;
    }

    public abstract double getOldPrice();

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
