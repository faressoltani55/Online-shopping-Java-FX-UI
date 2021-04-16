package utils;

import utils.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {

    private List<Special> offresEtPromos;
    private static final String baseImageUrl = "images";

    public Database() {
        offresEtPromos = new ArrayList<>();
        initialize();
    }

    public void initialize(){

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String stringDate = "01/05/2021";

        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        offresEtPromos.add(new Promotion("Promotion Fraises", "Promotion Marché frais du printemps", new Item("Fraises", 4, Category.MARCHE_FRAIS), 3.5, new Date(), baseImageUrl+"/fraises.jpg"));
        offresEtPromos.add(new Promotion("Promotion Bananes", "Réduction sur les Bananes importées", new Item("Bananes", 5, Category.MARCHE_FRAIS), 3, date, baseImageUrl+"/bananes.jpg"));
        offresEtPromos.add(new Promotion("Promotion iPad", "A ne pas rater! Apple store offre une réduction de 10% sur iPad!", new Item("iPad", 1500, Category.ELECTRONIQUE), 1350, new Date(), baseImageUrl+"/ipad.png"));
        offresEtPromos.add(new Promotion("Promotion Oppo A72", "50dt de réduction sur Oppo A72", new Item("Oppo A72", 700, Category.ELECTRONIQUE), 650, date, baseImageUrl+"/oppoA72.png"));

        List<Item> offre1 = new ArrayList<>();
        offre1.add(new Item("Crème", 0.9, Category.ALIMENTAIRE));
        offre1.add(new Item("Poudre", 0.9, Category.ALIMENTAIRE));

        offresEtPromos.add(new Offre("Offre vanoise", "Crème chantillez et Génoise Vanoise à 1DT au lieu de 1.8DT", offre1, 1, new Date(), baseImageUrl+"/vanoise.jpg"));

        offresEtPromos.add(new Promotion("Promortion Délishake", "30% Off sur Délishake", new Item("Délishake", 1.2, Category.ALIMENTAIRE), 0.84, date, baseImageUrl+"/délishake.png"));
        offresEtPromos.add(new Promotion("Promotion Classic Skinny Jeans H&M", "Promotion de 20% sur les Classic Skinny Jeans H&M", new Item("Jeans", 80, Category.PRETS_A_PORTER), 64, date, baseImageUrl+"/h&m.jpg"));
        offresEtPromos.add(new Promotion("Promotion Clean&Clear cleanser", "Promotion de 10% sur le cleanser Clean&Clear", new Item("Clean&Clear", 40, Category.BEAUTE), 36, new Date(), baseImageUrl+"/clean&clear.jpg"));

        List<Item> offre2 = new ArrayList<>();
        offre2.add(new Item("2 à 10", 12, Category.RESTAURANTS));

        offresEtPromos.add(new Offre("Offre 2 à 10 Baguette&Baguette", "Profitez de deux menus sandwich à 2 personnes uniquement à 10dt!", offre2, 10, date, baseImageUrl+"/b&b.jpg"));

    }

    public List<Special> getOffresEtPromos() {
        return  offresEtPromos;
    }
}
