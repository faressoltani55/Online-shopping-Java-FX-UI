package vues;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Home home = new Home(primaryStage);
        VBox root = new VBox();
        SplitPane mainSplit = new SplitPane();
        mainSplit.setDividerPosition(0, 0.7);
        mainSplit.setPrefHeight(600);
        mainSplit.setPrefWidth(1000);

        TitledPane leftPane = home.buildTitledPane("NOUVEAUTÉS",mainSplit.getPrefHeight(),(mainSplit.getPrefWidth()/6) * 5);
        TitledPane rightPane = home.buildTitledPane("CATÉGORIES",mainSplit.getPrefHeight(),mainSplit.getPrefWidth()/6);

        GridPane categoriesGrid = new GridPane();
        categoriesGrid.setPrefHeight(600);
        categoriesGrid.setPrefWidth(220);
        categoriesGrid.setAlignment(Pos.CENTER);

        Button marcheFrais = home.buildCategoryButton("Marché frais", categoriesGrid.getPrefHeight()/6, categoriesGrid.getPrefWidth());
        Button alimentaire = home.buildCategoryButton("Alimentaire", categoriesGrid.getPrefHeight()/6, categoriesGrid.getPrefWidth());
        Button hygieneEtBeaute = home.buildCategoryButton("Beauté", categoriesGrid.getPrefHeight()/6, categoriesGrid.getPrefWidth());
        Button pretsAPorter = home.buildCategoryButton("Prêts-à-porter", categoriesGrid.getPrefHeight()/6, categoriesGrid.getPrefWidth());
        Button restaurants = home.buildCategoryButton("Restaurants", categoriesGrid.getPrefHeight()/6, categoriesGrid.getPrefWidth());
        Button electro = home.buildCategoryButton("Électronique", categoriesGrid.getPrefHeight()/6, categoriesGrid.getPrefWidth());

        categoriesGrid.addColumn(0, marcheFrais, alimentaire, hygieneEtBeaute, pretsAPorter, restaurants, electro);
        rightPane.setContent(categoriesGrid);

        Text promoText = new Text();
        promoText.setFont(new Font("System Bold", 18));
        promoText.setText("Retrouvez les meilleures promos et bons plans! \n");

        ScrollPane scroll = new ScrollPane();

        home.getOffresEtPromos().setPrefHeight(leftPane.getPrefHeight());
        home.getOffresEtPromos().setPrefWidth(leftPane.getPrefWidth());
        home.getOffresEtPromos().getItems().add(new FlowPane(promoText));
        home.getOffresEtPromos().getItems().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                home.reset();
            }
        });
        home.reset();

        scroll.setContent(home.getOffresEtPromos());
        leftPane.setContent(scroll);
        mainSplit.getItems().addAll(leftPane ,rightPane);
        root.getChildren().add(mainSplit);
        primaryStage.setTitle("PromoOffer");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
