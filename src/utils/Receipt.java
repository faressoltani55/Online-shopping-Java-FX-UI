package utils;

public class Receipt {

    private Special promOffer;
    private String surname;
    private String name;

    public Receipt(){
    }

    public Receipt(Special promOffer, String surname, String name) {
        this.promOffer = promOffer;
        this.surname = surname;
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Special getPromOffer() {
        return promOffer;
    }

    public void setPromOffer(Special promOffer) {
        this.promOffer = promOffer;
    }

}
