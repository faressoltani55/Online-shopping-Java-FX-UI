package vues;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Category;
import utils.Database;
import utils.Receipt;
import utils.Special;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Home {

    private Stage primaryStage;
    private Receipt receipt; //recette de paiement
    private Database database; //base de données des offres et des promotions
    private Forms forms; //classes contructrice de formulaires
    private HashMap<String, Category> categoryMap; //ensemble des catégories
    private ListView<Node> offresEtPromos; // listes des offres et des promotions
    private EventHandler<ActionEvent> paymentHandler; // handler de la soumission de la demande de paiement

    public Home(Stage primaryStage){

        this.primaryStage = primaryStage;
        this.receipt = new Receipt();
        this.forms = new Forms(receipt);
        this.database = new Database();
        this.offresEtPromos = new ListView();
        this.offresEtPromos.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        this.categoryMap = new HashMap<>();

        init();

    }

    // Iinitialisation des catégories ainsi que du handler du paiement
    public void init(){

        categoryMap.put("Marché frais", Category.MARCHE_FRAIS);
        categoryMap.put("Alimentaire" ,Category.ALIMENTAIRE);
        categoryMap.put("Beauté" ,Category.BEAUTE);
        categoryMap.put("Électronique" ,Category.ELECTRONIQUE);
        categoryMap.put("Prêts-à-porter" ,Category.PRETS_A_PORTER);
        categoryMap.put("Restaurants" ,Category.RESTAURANTS);

        paymentHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                final Stage payment = new Stage();
                payment.setTitle("Finalisation du paiement");
                payment.initModality(Modality.APPLICATION_MODAL);
                payment.initOwner(primaryStage);

                BorderPane container = new BorderPane();
                container.setMinHeight(300);
                container.setMinWidth(200);

                VBox paymentBox = new VBox();
                paymentBox.setStyle("-fx-background-color: #FFFFFF;");
                paymentBox.setAlignment(Pos.CENTER);
                Text title = new Text("Reçu de paiement \n");
                title.setFont(new Font("System Bold", 24));
                Text person = new Text(receipt.getSurname() + " " + receipt.getName() + "\n");
                Text sum = new Text(String.valueOf(receipt.getPromOffer().getPrice()) + "DT");
                paymentBox.getChildren().addAll(title, person, new Text(receipt.getPromOffer().getName()), sum);
                container.setCenter(paymentBox);

                Scene receiptScene = new Scene(container);
                payment.setScene(receiptScene);
                payment.setResizable(false);
                payment.show();

                paymentBox.translateYProperty().set(-250);
                Timeline tl = new Timeline();
                KeyValue kv = new KeyValue(paymentBox.translateYProperty(), 0, Interpolator.EASE_OUT);
                KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);
                tl.getKeyFrames().add(kf);
                tl.play();
            }
        };
    }

    // Construction d'une titled pane (une pour le menu des catégories et l'autres pour la liste des offres et des promos)
    public TitledPane buildTitledPane(String text, double prefHeight, double prefWidth){

        TitledPane tpane = new TitledPane();
        tpane.setCollapsible(false);
        tpane.setText(text);
        tpane.setPrefWidth(prefWidth);
        tpane.setPrefHeight(prefHeight);

        return tpane;
    }

    // Création d'un bouton de filtrage par catégorie
    public Button buildCategoryButton(String text, double prefHeight, double prefWidth) {

        Button catButton = new Button();
        catButton.setPrefHeight(prefHeight);
        catButton.setPrefWidth(prefWidth);
        catButton.setMnemonicParsing(false);
        catButton.setText(text);
        catButton.setFont(new Font("System Bold", 18));
        catButton.setAlignment(Pos.BASELINE_CENTER);
        catButton.setMnemonicParsing(false);
        catButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<Special> op = database.getOffresEtPromos();
                op = op.stream().filter(s -> s.getCategory() == categoryMap.get(text))
                        .collect(Collectors.toList());
                offresEtPromos.getItems().remove(1, offresEtPromos.getItems().size());
                for(int i=0; i<op.size(); i++)
                    offresEtPromos.getItems().add(buildItemPane(op.get(i)));
            }
        });
        return catButton;
    }

    // Affichage de la liste des promos et des offres
    public GridPane buildItemPane(Special special){

        GridPane gPane = new GridPane();
        gPane.setPrefHeight(50);
        gPane.setPrefWidth(200);

        ImageView imgView = new ImageView(getClass().getResource(special.getImageUrl()).toExternalForm());
        imgView.setFitHeight(60);
        imgView.setFitWidth(120);
        imgView.setPreserveRatio(true);

        Text oldPrice = new Text(String.valueOf(special.getOldPrice()));
        oldPrice.setFont(new Font("System Bold", 22));
        oldPrice.setFill(Color.BLACK);
        oldPrice.setStrikethrough(true);

        Text newPrice = new Text(String.valueOf(special.getPrice()));
        newPrice.setFont(new Font("System Bold", 22));
        newPrice.setFill(Color.RED);

        Text name = new Text(special.getName());
        name.setFont(new Font("System Bold", 14));

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(25);
        col.setHalignment(HPos.CENTER);

        ColumnConstraints col2 = new ColumnConstraints();
        col.setPercentWidth(20);
        col.setHalignment(HPos.CENTER);

        gPane.getColumnConstraints().addAll(col, col, col2, col2);
        gPane.add(imgView, 0, 0);
        gPane.add(name, 1, 0);
        gPane.add(oldPrice, 2, 0);
        gPane.add(newPrice, 3, 0);

        gPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                receipt.setPromOffer(special);

                final Stage dialog = new Stage();
                dialog.setTitle(special.getName());
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);

                Text description = new Text(special.getDescription());
                description.setTextAlignment(TextAlignment.CENTER);
                description.setFont(new Font("System", 20));

                Text validite = new Text();
                validite.setTextAlignment(TextAlignment.JUSTIFY);
                validite.setFont(new Font("System", 20));

                Button confirmer = new Button("Passer à l'achat");

                if (new Date().compareTo(special.getEnd()) <= 0) {
                    validite.setText("Disponible jusqu'au " + special.getEnd().toString());
                    validite.setFill(Color.DARKGREEN);
                }
                else {
                    validite.setText("N'est plus disponible!");
                    validite.setFill(Color.DARKRED);
                    confirmer.setDisable(true);
                }

                VBox dialogVbox = new VBox(20);
                dialogVbox.setMinHeight(320);
                dialogVbox.setAlignment(Pos.CENTER);
                dialogVbox.getChildren().addAll(description, validite, confirmer);
                dialogVbox.setPadding(new Insets(10,10,10,10));
                Scene dialogScene = new Scene(dialogVbox);
                dialog.setResizable(false);
                dialog.setScene(dialogScene);
                dialog.show();

                confirmer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        dialogVbox.getChildren().remove(0, dialogVbox.getChildren().size());
                        GridPane infoForm = forms.buildInformationForm();
                        Button next = new Button("Confirmer");
                        next.setDisable(forms.hasEmpty(infoForm));
                        GridPane gp = new GridPane();
                        infoForm.add(next, 0, infoForm.getChildren().size()+2);
                        dialogVbox.getChildren().addAll(infoForm);

                        next.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                Button confirm = new Button("Confirmer");

                                dialogVbox.getChildren().remove(0, dialogVbox.getChildren().size());
                                GridPane paymentForm = forms.buildPaymentForm();
                                paymentForm.add(confirm, 0, infoForm.getChildren().size()+2);
                                dialogVbox.getChildren().add(paymentForm);

                                confirm.addEventHandler(ActionEvent.ACTION, paymentHandler);
                            }
                        });
                    }
                });
            }
        });
        return gPane;
    }

    // Actualiser la liste et annuler le filtrage
    public void reset(){
        offresEtPromos.getItems().remove(1, offresEtPromos.getItems().size());
        for(int i=0; i<database.getOffresEtPromos().size(); i++)
            offresEtPromos.getItems().add(buildItemPane(database.getOffresEtPromos().get(i)));
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public ListView<Node> getOffresEtPromos() {
        return offresEtPromos;
    }

    public void setOffresEtPromos(ListView<Node> offresEtPromos) {
        this.offresEtPromos = offresEtPromos;
    }

}
