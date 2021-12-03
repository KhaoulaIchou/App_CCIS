package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EE extends Application implements Initializable {
    public DatePicker Date3;
    public ComboBox ComboLieu1;
    public TextField NomPrenom1;
    public TextField Tel1;
    public TextField Email1;
    public TextField Adresse1;
    public TextField Ville1;
    public TextField Deno1;
    public TextField ICE1;
    public TextField site1;
    public TextField A1;
    public CheckBox accepter1;
    public CheckBox certifier1;
    public RadioButton PP1;
    public ToggleGroup forme;
    public RadioButton SARL1;
    public RadioButton SA1;
    public RadioButton AutoE1;
    public RadioButton Petite1;
    public ToggleGroup taille;
    public RadioButton Moyenne1;
    public RadioButton Grande1;
    public CheckBox Industrie1;
    public CheckBox Commerce1;
    public CheckBox Services1;
    public TextField Activité1;
    public TextField RepCCIS1;
    public TextField qualité1;
    public TextArea Obrev1;
    public TextField NomRep1;
    public RadioButton Entre1;
    public ToggleGroup statut;
    public RadioButton Porteur1;
    public CheckBox Program;
    public CheckBox Annuaire;
    public CheckBox Demarche;
    public CheckBox Repertoire;


    public void NomDeno(KeyEvent keyEvent) {
        String Nom = NomPrenom1.getText();
        Deno1.setText(Nom);
        NomRep1.setText(Nom);
    }
    //valider la fiche Espace de l'Entreprise
    public void validerEE(ActionEvent actionEvent) {
        if(validerEmail()&&validerCode()&&validerNumero()&&validerText()&&validercheckbox()&&validerSite()) {

            Connection con;
            Statement stm;
            ResultSet rst;
            ZipSecureFile.setMinInflateRatio(0);
            String excelFilePath = "C:\\Users\\hp\\IdeaProjects\\App_CCIS\\src\\sample\\DocImg\\EE.xlsx";
            String dateC = Date3.getValue().toString();
            String Lieu = ComboLieu1.getValue().toString();
            String Objet = "";
            // String Objet = Objt.getElements().nextElement().getText();
            if (Program.isSelected()) {
                Objet += Program.getText();
            }
            if (Demarche.isSelected()) {
                Objet += Demarche.getText();
            }
            if (Annuaire.isSelected()) {
                Objet += Annuaire.getText();
            }
            if (Repertoire.isSelected()) {
                Objet += Repertoire.getText();
            }

            String NomPre = NomPrenom1.getText();
            String statut = "";
            if (Entre1.isSelected()) {
                statut += Entre1.getText();
            }
            if (Porteur1.isSelected()) {
                statut += Porteur1.getText();
            }
            String Tele = Tel1.getText();
            String mail = Email1.getText();
            String adr = Adresse1.getText();
            String ville = Ville1.getText();
            String deno = Deno1.getText();
            String ice = ICE1.getText();
            String Site = site1.getText();
            String Rep = NomRep1.getText();
            String FormeJur = "";
            if (PP1.isSelected()) {
                FormeJur += PP1.getText();
            }
            if (SARL1.isSelected()) {
                FormeJur += SARL1.getText();
            }
            if (SA1.isSelected()) {
                FormeJur += SA1.getText();
            }
            if (AutoE1.isSelected()) {
                FormeJur += AutoE1.getText();
            }// else {
            // FormeJur += A.getText();
            //}
            String Taille = "";
            if (Petite1.isSelected()) {
                Taille += Petite1.getText();
            }
            if (Moyenne1.isSelected()) {
                Taille += Moyenne1.getText();
            }
            if (Grande1.isSelected()) {
                Taille += Grande1.getText();
            }
            String Secteur = "";
            if (Industrie1.isSelected()) {
                Secteur += Industrie1.getText();
            }
            if (Commerce1.isSelected()) {
                Secteur += Commerce1.getText();
            }
            if (Services1.isSelected()) {
                Secteur += Services1.getText();
            }
            String Activite = Activité1.getText();
            String E1, E2;
            if (accepter1.isSelected()) {
                E1 = "OUI";
                E2 = "OUI";
            } else {
                E1 = "NON";
                E2 = "NON";
            }

            String RepCC = RepCCIS1.getText();
            String qlt = qualité1.getText();
            String Obser = Obrev1.getText();
            FileInputStream inputStream = null;
            FileOutputStream fileOut = null;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "khaoula18", "sirinayy");
                String query = "INSERT INTO EE (Prestation,Lieu,DateC,Objet,NomPre,Statut,Tele,Email,Adresse,Ville,Deno,ICE,RepLegal,Site,Forme,Taille,Secteur,Activite,AccepR,AccepE) VALUES('EE','" + Lieu + "',TO_DATE('" + dateC + "','YYYY-MM-DD'),'" + Objet + "','" + NomPre + "','" + statut + "','" + Tele + "','" + mail + "','" + adr + "','" + ville + "','" + deno + "','" + ice + "','" + Rep + "','" + Site + "','" + FormeJur + "','" + Taille + "','" + Secteur + "','" + Activite + "','"+E1+"','"+E2+"')";
                stm = con.prepareStatement(query);
                stm.executeQuery(query);
                String query1 = "SELECT * FROM EE WHERE ICE ='" + ice + "'";
                stm = con.prepareStatement(query1);
                rst = stm.executeQuery(query1);
                inputStream = new FileInputStream(excelFilePath);
                ZipSecureFile.setMinInflateRatio(0);

                XSSFWorkbook wb = new XSSFWorkbook(inputStream);
                String name = wb.getSheetName(0);
                Sheet sheet = wb.getSheet(name);
                int index = sheet.getLastRowNum();
                while (rst.next()) {
                    XSSFRow row = (XSSFRow) sheet.createRow(index);
                    row.createCell(0).setCellValue(rst.getString("Prestation"));
                    row.createCell(1).setCellValue(rst.getString("Lieu"));
                    row.createCell(2).setCellValue(rst.getString("DateC"));
                    row.createCell(3).setCellValue(rst.getString("Objet"));
                    row.createCell(4).setCellValue(rst.getString("NomPre"));
                    row.createCell(5).setCellValue(rst.getString("Statut"));
                    row.createCell(6).setCellValue(rst.getString("Tele"));
                    row.createCell(7).setCellValue(rst.getString("Email"));
                    row.createCell(8).setCellValue(rst.getString("AccepE"));
                    row.createCell(9).setCellValue(rst.getString("AccepR"));
                    row.createCell(10).setCellValue(rst.getString("Adresse"));
                    row.createCell(11).setCellValue(rst.getString("Ville"));
                    row.createCell(12).setCellValue(rst.getString("Deno"));
                    row.createCell(13).setCellValue(rst.getString("ICE"));
                    row.createCell(14).setCellValue(rst.getString("RepLegal"));
                    row.createCell(15).setCellValue(rst.getString("Site"));
                    row.createCell(16).setCellValue(rst.getString("Forme"));
                    row.createCell(18).setCellValue(rst.getString("Taille"));
                    row.createCell(19).setCellValue(rst.getString("Secteur"));
                    row.createCell(20).setCellValue(rst.getString("Activite"));
                    index++;
                }
                fileOut = new FileOutputStream(excelFilePath);
                wb.write(fileOut);

                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("les informations sont bien envoyées");
                alert.showAndWait();
                writeWord();
                stm.close();
                rst.close();
            } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    IOUtils.close(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    IOUtils.close(fileOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /*******validate email***************/
    private boolean validerEmail(){
        Pattern p= Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m=p.matcher(Email1.getText());
        if(m.find() && m.group().equals(Email1.getText())){
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
    /***validate ICE***/
    private boolean validerCode(){
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(ICE1.getText());
        if(m.find()&& m.group().equals(ICE1.getText())){
            return true;
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un code ICE valide");
            alert.showAndWait();
            return false;
        }
    }
    /**********validate Tele*********/
    private boolean validerNumero(){
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(Tel1.getText());
        if(m.find()&& m.group().equals(Tel1.getText())){
            return true;
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numéro téléphone valide");
            alert.showAndWait();
            return false;
        }
    }
    /**validate site web**/
    private boolean validerSite(){
        if(site1.getText().contains("www")){
            return true; }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un site web valide");
            alert.showAndWait();
            return false;
        }
    }
    /***validate textfield****/
    private boolean validerText(){
        if( NomPrenom1.getText().isEmpty()| Tel1.getText().isEmpty()| Email1.getText().isEmpty()| Adresse1.getText().isEmpty()|
                Ville1.getText().isEmpty()| Deno1.getText().isEmpty()| ICE1.getText().isEmpty()| site1.getText().isEmpty()| NomRep1.getText().isEmpty()| Activité1.getText().isEmpty()|
                RepCCIS1.getText().isEmpty()| qualité1.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Champ vide");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    /****validate checkbox*****/
    private boolean validercheckbox(){
        if(!Program.isSelected()| Annuaire.isSelected()| Repertoire.isSelected()| Demarche.isSelected() /* | Autre1.isSelected()*/){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Séléctionnez un document demandé");
            alert.showAndWait();
            return false;
        }
        if(!Industrie1.isSelected()| Commerce1.isSelected()| Services1.isSelected()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Séléctionnez un secteur d'activité");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    //Générer une copie fiche de renseignement EE
    private boolean writeWord() throws FileNotFoundException, InvalidFormatException, IOException{
        XWPFDocument doc=new XWPFDocument();
        String dateC = Date3.getValue().toString();
        String Lieu = ComboLieu1.getValue().toString();
        String Objet = "";
        // String Objet = Objt.getElements().nextElement().getText();
        if (Program.isSelected()) {
            Objet += Program.getText();
        }
        if (Demarche.isSelected()) {
            Objet += Demarche.getText();
        }
        if (Annuaire.isSelected()) {
            Objet += Annuaire.getText();
        }
        if (Repertoire.isSelected()) {
            Objet += Repertoire.getText();
        }

        String NomPre = NomPrenom1.getText();
        String statut = "";
        if (Entre1.isSelected()) {
            statut += Entre1.getText();
        }
        if (Porteur1.isSelected()) {
            statut += Porteur1.getText();
        }
        String Tele = Tel1.getText();
        String mail = Email1.getText();
        String adr = Adresse1.getText();
        String ville = Ville1.getText();
        String deno = Deno1.getText();
        String ice = ICE1.getText();
        String Site = site1.getText();
        String Rep = NomRep1.getText();
        String FormeJur = "";
        if (PP1.isSelected()) {
            FormeJur += PP1.getText();
        }
        if (SARL1.isSelected()) {
            FormeJur += SARL1.getText();
        }
        if (SA1.isSelected()) {
            FormeJur += SA1.getText();
        }
        if (AutoE1.isSelected()) {
            FormeJur += AutoE1.getText();
        }// else {
        // FormeJur += A.getText();
        //}
        String Taille = "";
        if (Petite1.isSelected()) {
            Taille += Petite1.getText();
        }
        if (Moyenne1.isSelected()) {
            Taille += Moyenne1.getText();
        }
        if (Grande1.isSelected()) {
            Taille += Grande1.getText();
        }
        String Secteur = "";
        if (Industrie1.isSelected()) {
            Secteur += Industrie1.getText();
        }
        if (Commerce1.isSelected()) {
            Secteur += Commerce1.getText();
        }
        if (Services1.isSelected()) {
            Secteur += Services1.getText();
        }
        String Activite = Activité1.getText();
        String E1, E2;
        if (accepter1.isSelected()) {
            E1 = "OUI";
            E2 = "OUI";
        } else {
            E1 = "NON";
            E2 = "NON";
        }

        String RepCC = RepCCIS1.getText();
        String qlt = qualité1.getText();
        String Obser = Obrev1.getText();



        XWPFHeaderFooterPolicy headerFooterPolicy = doc.createHeaderFooterPolicy();
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
        XWPFPicture picture = null;//50*50px
        try {
            picture = paragraph.createRun().addPicture(new FileInputStream("C:\\Users\\hp\\IdeaProjects\\App_CCIS\\src\\sample\\DocImg\\logo-ccis.png"),
                    XWPFDocument.PICTURE_TYPE_PNG, "C:\\Users\\hp\\IdeaProjects\\App_CCIS\\src\\sample\\DocImg\\logo-ccis.png", Units.toEMU(150) , Units.toEMU(50));
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }


        String blip = header.getRelationId(header.getAllPackagePictures().get(0));
        picture.getCTPicture().getBlipFill().getBlip().setEmbed(blip);

        XWPFParagraph p=doc.createParagraph();
        XWPFRun run=p.createRun();
        run.setText("Espace de l’entreprise ");
        run.setBold(true);
        run.setFontSize(10);
        p.setAlignment(ParagraphAlignment.CENTER);

        XWPFParagraph p2=doc.createParagraph();
        XWPFRun run1=p2.createRun();
        run1.setText("");
        run1.getCTR().addNewContinuationSeparator();

        XWPFTable table=doc.createTable();
        XWPFTableRow r1=table.getRow(0);
        r1.getCell(0).setText("Date de contact:" +" "+ " "+" ");

        r1.addNewTableCell().setText(dateC);
        XWPFTableRow r2=table.createRow();
        r2.getCell(0).setText("Lieu:");
        r2.getCell(1).setText(Lieu);
        XWPFTableRow r3=table.createRow();
        r3.getCell(0).setText("Objet de la visite:" );
        r3.getCell(1).setText(Objet);

        table.setWidth(10000);
        XWPFParagraph p4=doc.createParagraph();
        XWPFRun run3=p4.createRun();
        run3.setText("");
        XWPFParagraph p5=doc.createParagraph();
        XWPFRun run4=p5.createRun();
        run4.setText("IDENTIFICATION DU DEMANDEUR :");
        run4.setFontSize(10);
        run4.setBold(true);

        XWPFTable table3=doc.createTable();
        XWPFTableRow rr1=table3.getRow(0);
        rr1.getCell(0).setText("Nom et prénom :");
        rr1.addNewTableCell().setText(NomPre);
        XWPFTableRow rrp1=table3.createRow();
        rrp1.getCell(0).setText("Statut:");
        rrp1.getCell(1).setText(statut);

        XWPFTableRow rr2=table3.createRow();
        rr2.getCell(0).setText("Téléphone (GSM) :");
        rr2.getCell(1).setText(Tele);
        XWPFTableRow rr3=table3.createRow();
        rr3.getCell(0).setText("Email de contact :" );
        rr3.getCell(1).setText(mail);
        XWPFTableRow rr4=table3.createRow();
        rr4.getCell(0).setText("Adresse de contact :" );
        rr4.getCell(1).setText(adr);
        XWPFTableRow rr5=table3.createRow();
        rr5.getCell(0).setText("Ville :" );
        rr5.getCell(1).setText(ville);
        table3.setWidth(10000);
        XWPFParagraph ppp=doc.createParagraph();
        XWPFRun run122=ppp.createRun();
        run122.setText("J’accepte de recevoir les envois de la CCIS");
        run122.addBreak();
        run122.setColor("0055ff");
        //run122.addBreak();
        XWPFParagraph p6=doc.createParagraph();
        XWPFRun run5=p6.createRun();
        run5.setText("IDENTIFICATION DE L’ENTREPRISE :");
        run5.setFontSize(10);
        run5.setBold(true);
        XWPFTable table4=doc.createTable();
        XWPFTableRow rrr1=table4.getRow(0);
        rrr1.getCell(0).setText("Dénomination :");
        rrr1.addNewTableCell().setText(deno);
        XWPFTableRow rrr2=table4.createRow();
        rrr2.getCell(0).setText("CODE ICE:");
        rrr2.getCell(1).setText(ice);
        XWPFTableRow rrr3=table4.createRow();
        rrr3.getCell(0).setText("Nom du représentant légal :" );
        rrr3.getCell(1).setText(Rep);
        XWPFTableRow rrr4=table4.createRow();
        rrr4.getCell(0).setText("Site Web de l’entreprise" );
        rrr4.getCell(1).setText(Site);
        XWPFTableRow rrr5=table4.createRow();
        rrr5.getCell(0).setText("Forme juridique :" );
        rrr5.getCell(1).setText(FormeJur);

        XWPFTableRow rrr6=table4.createRow();
        rrr6.getCell(0).setText("Taille de l'entreprise :" );
        rrr6.getCell(1).setText(Taille);

        XWPFTableRow rrr7=table4.createRow();
        rrr7.getCell(0).setText("Secteur d'activité:" );
        rrr7.getCell(1).setText(Secteur);

        XWPFTableRow rrr8=table4.createRow();
        rrr8.getCell(0).setText("Activité :" );
        rrr8.getCell(1).setText(Activite);
        table4.setWidth(10000);


        XWPFParagraph p8=doc.createParagraph();
        XWPFRun run7=p8.createRun();
        run7.setText("Je certifie l’authenticité des informations susmentionnées et autorise la CCIS-MS à les utiliser pour toute fin utile ");
        run7.setFontSize(10);
        run7.setBold(true);
        run7.setColor("0055ff");
        XWPFParagraph p9=doc.createParagraph();
        XWPFRun run8=p9.createRun();
        run8.setText("Signature du demandeur :");
        run8.setFontSize(10);
        run8.setBold(true);
        p9.setAlignment(ParagraphAlignment.CENTER);


        XWPFTable table5=doc.createTable();
        XWPFTableRow rrrr1=table5.getRow(0);
        rrrr1.getCell(0).setText("Cadre réservé à la CCIS :" +" "+" "+" "+" "+" ");
        rrrr1.getCell(0).setColor("ddeff8");

        XWPFTableRow rrrr2=table5.createRow();
        rrrr2.getCell(0).setText("Nom et prénom du conseiller de la CCIS :"+RepCC);
        rrrr2.addNewTableCell().setText(" Sa qualité:"+qlt);



        XWPFTableRow rrrr3=table5.createRow();
        rrrr3.getCell(0).setText("Observations :"+Obser);

        table5.setWidth(10000);

        doc.write(new FileOutputStream("C:\\Users\\hp\\IdeaProjects\\App_CCIS\\src\\sample\\wordFiles\\EE_"+NomPre+".docx"));
        return true;
    }


    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboLieu1.getItems().add("MARRAKECH");
        ComboLieu1.getItems().add("ESSAOUIRA");
        ComboLieu1.getItems().add("EL KELAA DES SRAGHNA");
        ComboLieu1.getItems().add("SAFI");
        accepter1.setSelected(true);
        certifier1.setSelected(true);
        Date3.setValue(LocalDate.now());
        RepCCIS1.setText("Rachid BNINHA");
        qualité1.setText("Chef DA");
    }

    public void logOutAction(ActionEvent actionEvent) {
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

    public void resetAction(ActionEvent actionEvent) {
        if (Program.isSelected()) {
            Program.setSelected(false);
        }
        if (Annuaire.isSelected()) {
            Annuaire.setSelected(false);
        }

        NomPrenom1.setText("");
        if(Entre1.isSelected()){
            Entre1.setSelected(false);
        }if(Porteur1.isSelected()){
            Porteur1.setSelected(false);
        }
        Tel1.setText("");
        Email1.setText("");
        Adresse1.setText("");
        Ville1.setText("");
        Deno1.setText("");
        ICE1.setText("");
        site1.setText("");
        NomRep1.setText("");
        if (PP1.isSelected()) {
            PP1.setSelected(false);
        }
        if (SARL1.isSelected()) {
            SARL1.setSelected(false);
        }
        if (SA1.isSelected()) {
            SA1.setSelected(false);
        }
        if (AutoE1.isSelected()) {
            AutoE1.setSelected(false);
        }
        if (Petite1.isSelected()) {
            Petite1.setSelected(false);
        }
        if (Moyenne1.isSelected()) {
            Moyenne1.setSelected(false);
        }
        if (Grande1.isSelected()) {
            Grande1.setSelected(false);
        }
        Activité1.setText("");
        if (Commerce1.isSelected()) {
            Commerce1.setSelected(false);
        }
        if (Industrie1.isSelected()) {
            Industrie1.setSelected(false);
        }
        if (Services1.isSelected()) {
            Services1.setSelected(false);
        }
        Obrev1.setText("");
    }
}