package vues;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.Receipt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Forms {

    Receipt receipt;

    public Forms(Receipt receipt){
        this.receipt = receipt;
    }

    //Vérifier si le formulaire possède un champ vide
    public boolean hasEmpty(GridPane form){
        ObservableList<Node> formChildren = form.getChildren();
        for (Node child: formChildren) {
            if (child instanceof GridPane) {
                ObservableList<Node> nodes = ((GridPane) child).getChildren();
                if (nodes.get(1) instanceof TextField) {
                    if (((TextField) nodes.get(1)).getCharacters().toString().isEmpty())
                        return true;
                }
            }
        }
        return false;
    }

    // Construire un champ d'un formulaire selon les paramètres données
    public void buildFormField(String fieldLabel, String fieldPattern, GridPane fields, boolean isPassword, String errorMessage){

        GridPane fieldGrid = new GridPane();

        ColumnConstraints colLabel = new ColumnConstraints();
        colLabel.setPercentWidth(30);
        colLabel.setHalignment(HPos.LEFT);

        fieldGrid.getColumnConstraints().addAll(colLabel, colLabel, colLabel);

        Label label = new Label(fieldLabel);
        TextField field = new TextField();
        if (isPassword) {
            field = new PasswordField();
        }
        field.setMinWidth(100);
        Text errorText = new Text(errorMessage);
        errorText.setFont(new Font("System", 10));
        errorText.setFill(Color.DARKRED);

        fieldGrid.add(label,0,0);
        fieldGrid.add(field, 1, 0);
        fields.add(fieldGrid,0, fields.getChildren().size() + 2);


        TextField finalField = field;
        field.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                fields.getChildren().get(fields.getChildren().size()-1).setDisable(hasEmpty(fields));
                Pattern pattern = Pattern.compile(fieldPattern);
                Matcher matcher = pattern.matcher(finalField.getCharacters().toString());
                if (! matcher.matches() || finalField.getCharacters().toString().isEmpty()) {
                    if(!fieldGrid.getChildren().contains(errorText))
                        fieldGrid.add(errorText, 2, 0);
                    fields.getChildren().get(fields.getChildren().size()-1).setDisable(true);
                }
                else {
                    if(fieldGrid.getChildren().contains(errorText))
                        fieldGrid.getChildren().remove(errorText);
                }
                if (fieldLabel == "Nom:") {
                    receipt.setSurname(finalField.getCharacters().toString());
                }
                if (fieldLabel == "Prénom:") {
                    receipt.setName(finalField.getCharacters().toString());
                }
            }
        });
    }

    // Construire un formulaire de paiement
    public GridPane buildPaymentForm(){

        GridPane formFields = new GridPane();
        Text title = new Text("Veuillez saisir les détails de payement \n");
        title.setFont(Font.font("System Bold", 16));
        formFields.add(title, 0, 0);


        Label type = new Label("Type de la carte: ");
        final ToggleGroup paymentCard = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Carte Visa");
        rb1.setToggleGroup(paymentCard);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Carte Technologique");
        rb2.setToggleGroup(paymentCard);

        formFields.add(type, 0, 1);
        formFields.add(rb1, 0, 2);
        formFields.add(rb2, 0, 3);

        buildFormField("Numéro de la carte:", "^[0-9]{16}$", formFields, false, "Numéro non valide");

        ColumnConstraints colLabel = new ColumnConstraints();
        colLabel.setPercentWidth(30);
        colLabel.setHalignment(HPos.LEFT);

        GridPane fieldGrid = new GridPane();
        fieldGrid.add(new Label("Date d'expiration"), 0, 0);
        fieldGrid.add(new DatePicker(), 1, 0);

        fieldGrid.getColumnConstraints().addAll(colLabel, colLabel, colLabel);

        formFields.add(fieldGrid, 0, formFields.getChildren().size() + 2);
        buildFormField("Code:", "^[0-9]{4}$", formFields,true, "Code non valide");

        return formFields;
    }


    // Construire un formulaire des données personnelles
    public GridPane buildInformationForm(){

        GridPane formFields = new GridPane();
        formFields.setAlignment(Pos.BOTTOM_LEFT);

        ColumnConstraints colLabel = new ColumnConstraints();
        colLabel.setPercentWidth(100);
        colLabel.setHalignment(HPos.LEFT);
        formFields.getColumnConstraints().addAll(colLabel);

        Text title = new Text("Veuillez saisir vos informations \n ");
        title.setFont(Font.font("System Bold", 16));
        formFields.add(title, 0, 0);

        buildFormField("Nom:", "^[A-Za-z]{2,}$", formFields, false, "Minimum 2 lettres");
        buildFormField("Prénom:", "^[A-Za-z]{2,}$", formFields, false, "Minimum 2 lettres");
        buildFormField("Rue:", "^[A-Za-z0-9]{2,}.*$", formFields, false, "Minimum 2 lettres ou chiffres");
        buildFormField("Ville:", "^[A-Za-z]{3,}$", formFields, false, "Minimum 3 lettres");
        buildFormField("Code Postal:", "^[0-9]{4}$", formFields, false, "Exactement 4 chiffres");
        buildFormField("Numéro:", "^[0-9]{8}$", formFields, false, "Exactement 8 chiffres");

        return formFields;
    }
}
