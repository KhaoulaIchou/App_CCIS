package sample;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.lang.String;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.rgb;

public class Controller extends Application {
    public Parent fxml;
    public AnchorPane root;
    public Button DAdministrative;
    public Button EEntrepriseAction;
    public Button GuichetAction;

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
    }

    @FXML
    public void DeAdministrativeAction(ActionEvent actionEvent) {
        try {
             fxml= FXMLLoader.load(getClass().getResource("Demarche.fxml")); //charger la page Démarche Administrative
             root.getChildren().removeAll();
             root.getChildren().setAll(fxml);
        }catch (IOException ex) {
            ex.toString();
        }
    }
    @FXML
    public void EspaceEntreprise(ActionEvent actionEvent) {
        try {
            fxml= FXMLLoader.load(getClass().getResource("EE.fxml")); //charger la page Espace de l'Entreprise
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        }catch (IOException ex) {
            ex.toString();
        }
    }
    public void Guichet(ActionEvent actionEvent) {
        try {
            fxml= FXMLLoader.load(getClass().getResource("Guichet.fxml")); //charger la page Guichet de proximité
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        }catch (IOException ex) {
            ex.toString();
        }
    }
// la fonction se déconnecter
    public void logOutAction(ActionEvent actionEvent) {
        //afficher un outputbox pour confirmer la validation
        int response= JOptionPane.showConfirmDialog(null,"Voulez vous vraiment se déconnecter ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(response==JOptionPane.YES_OPTION){
            try {
                ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                System.out.println("Can't load new window");
            }
        }else{
            return;
        }
    }
}
