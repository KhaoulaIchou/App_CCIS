package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign extends Application implements Initializable
{

    public TextField SignEmail;
    public TextField SignQualite;
    public TextField SignName;
    public PasswordField SignPwd;

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private boolean validerEmail(){
        Pattern p= Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m=p.matcher(SignEmail.getText());
        if(m.find() && m.group().equals(SignEmail.getText())){
            return true;
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un mail valide");
            alert.showAndWait();
            //JFXDialogLayout dialoglayout=new JFXDialogLayout();
            return false;
        }
    }
    public void validerAction(ActionEvent actionEvent) {
        Connection con;
        Statement stm;
        ResultSet rst;
        if(validerEmail()) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "khaoula18", "sirinayy");
                String query = "INSERT INTO CCIS VALUES('" + SignName.getText() + "','" + SignEmail.getText() + "','" + SignQualite.getText() + "','" + SignPwd.getText() + "')";
                stm = con.prepareStatement(query);
                int row = stm.executeUpdate(query);
                if (row != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("les informations sont bien envoyées");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("les informations ne sont pas envoyées");
                    alert.showAndWait();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void logAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }
}
