package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
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
import org.apache.commons.io.IOUtils.*;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demarche extends Application implements Initializable {
    @FXML
    public javafx.scene.control.Button DAdministrative;
    @FXML
    public javafx.scene.control.ComboBox ComboLieu;
    @FXML
    public javafx.scene.control.TextField NomPrenom;
    @FXML
    public javafx.scene.control.TextField Deno;
    @FXML
    public javafx.scene.control.TextField NomRep;
    @FXML
    public javafx.scene.control.CheckBox accepter;
    @FXML
    public javafx.scene.control.CheckBox certifier;
    @FXML
    public javafx.scene.control.DatePicker Date1;
    @FXML
    public javafx.scene.control.DatePicker Date2;
    @FXML
    public javafx.scene.control.ToggleGroup Objt;
    public CheckBox CarteBox;
    public CheckBox AttestationBox;
    public CheckBox CertificatOR;
    public CheckBox VisaF;
    public CheckBox CertificatVB;
    public CheckBox VisaDC;
    public CheckBox VisaC;
    public CheckBox Recommdation;
    public javafx.scene.control.TextField AutreField;
    public ToggleGroup forme;
    public ToggleGroup taille;
    public ToggleGroup etat;
    public ToggleGroup suite;
    public ToggleGroup retrait;
    public RadioButton DInfo;
    public RadioButton DDoc;
    public TextField Mdemande;
    public TextField Tel;
    public TextField Email;
    public TextField Adresse;
    public TextField Ville;
    public TextField ICE;
    public TextField site;
    public TextField Activité;
    public TextField RepCCIS;
    public TextField qualité;
    public TextArea Obrev;
    public RadioButton PP;
    public RadioButton SARL;
    public RadioButton SA;
    public RadioButton AutoE;
    public RadioButton Petite;
    public RadioButton Moyenne;
    public RadioButton Grande;
    public CheckBox Industrie;
    public CheckBox Commerce;
    public CheckBox Services;
    public RadioButton A;
    public RadioButton Accom;
    public RadioButton Cours;
    public RadioButton CC;
    public RadioButton In;
    public RadioButton N;
    public RadioButton Accep;
    public RadioButton Rejet;
    public RadioButton Entre;
    public RadioButton Porteur;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboLieu.getItems().add("MARRAKECH");
        ComboLieu.getItems().add("ESSAOUIRA");
        ComboLieu.getItems().add("EL KELAA DES SRAGHNA");
        ComboLieu.getItems().add("SAFI");
        accepter.setSelected(true);
        certifier.setSelected(true);
        Date1.setValue(LocalDate.now());
        Date2.setValue(LocalDate.now());
        RepCCIS.setText("Rachid BNINHA");
        qualité.setText("Chef DA");
    }

    @FXML
    public void NomDeno(javafx.scene.input.KeyEvent keyEvent) {
        String Nom = NomPrenom.getText();
        Deno.setText(Nom);
        NomRep.setText(Nom);
    }


    //valider les informations saisies dans la fiche DA
    public void valider(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if(validerEmail()&&validerCode()&&validerNumero()&&validerText()&&validercheckbox()&&validerSite()){
            Connection con;
            Statement stm;
            ResultSet rst;
            String dateC = Date1.getValue().toString();
            String Lieu =ComboLieu.getValue().toString();
            String Objet = "";
            if (DInfo.isSelected()) {
                Objet += DInfo.getText();
            }
            if (DDoc.isSelected()) {
                Objet += DDoc.getText();
            }
            String Document = "";
            if (CarteBox.isSelected()) {
                Document += CarteBox.getText();
            }
            if (AttestationBox.isSelected()) {
                Document += AttestationBox.getText();
            }
            if (CertificatVB.isSelected()) {
                Document += CertificatVB.getText();
            }
            if (CertificatOR.isSelected()) {
                Document += CertificatOR.getText();
            }
            if (VisaDC.isSelected()) {
                Document += VisaDC.getText();
            }
            if (VisaC.isSelected()) {
                Document += VisaC.getText();
            }
            if (VisaF.isSelected()) {
                Document += VisaF.getText();
            }
            if (Recommdation.isSelected()) {
                Document += Recommdation.getText();
            } else {
                Document += AutreField.getText();
            }
            String NomPre = NomPrenom.getText();
            String statut="";
            if(Entre.isSelected()){
                statut+= Entre.getText();
            }if(Porteur.isSelected()){
                statut+= Porteur.getText();
            }
            String Tele = Tel.getText();
            String mail = Email.getText();
            String adr = Adresse.getText();
            String ville = Ville.getText();
            String deno = Deno.getText();
            String ice = ICE.getText();
            String Site = site.getText();
            String Rep = NomRep.getText();
            String FormeJur = "";
            if (PP.isSelected()) {
                FormeJur += PP.getText();
            }
            if (SARL.isSelected()) {
                FormeJur += SARL.getText();
            }
            if (SA.isSelected()) {
                FormeJur += SA.getText();
            }
            if (AutoE.isSelected()) {
                FormeJur += AutoE.getText();
            }// else {
            // FormeJur += A.getText();
            //}
            String Taille = "";
            if (Petite.isSelected()) {
                Taille += Petite.getText();
            }
            if (Moyenne.isSelected()) {
                Taille += Moyenne.getText();
            }
            if (Grande.isSelected()) {
                Taille += Grande.getText();
            }
            String Secteur = "";
            if (Industrie.isSelected()) {
                Secteur += Industrie.getText();
            }
            if (Commerce.isSelected()) {
                Secteur += Commerce.getText();
            }
            if (Services.isSelected()) {
                Secteur += Services.getText();
            }
            String Activite = Activité.getText();
            String EtatDossier = "";
            if (CC.isSelected()) {
                EtatDossier += CC.getText();
            }
            if (In.isSelected()) {
                EtatDossier += In.getText();
            }
            if (N.isSelected()) {
                EtatDossier += N.getText();
            }
            String Avis = "";
            if (Accep.isSelected()) {
                Avis += Accep.getText();
            }
            if (Rejet.isSelected()) {
                Avis += Rejet.getText();
            }
            String DateD = Date2.getValue().toString();
            String RepCC = RepCCIS.getText();
            String qlt = qualité.getText();
            String retA = "";
            if (Accom.isSelected()) {
                retA = Accom.getText();
            }
            if (Cours.isSelected()) {
                retA = Cours.getText();
            }
            String E1 = "", E2 = "";
            if (accepter.isSelected()) {
                E1 = "OUI";
                E2 = "OUI";
            }else{
                E1 = "NON";
                E2 = "NON";
            }
            FileInputStream inputStream=null;
            FileOutputStream fileOut=null;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","khaoula18","sirinayy");
                String query="INSERT INTO DEMA VALUES('DA','"+Lieu+"',TO_DATE('"+dateC+"','YYYY-MM-DD'),'"+Objet+"','"+NomPre+"','"+statut+"','"+Tele+"','"+mail+"','"+E1+"','"+E2+"','"+adr+"','"+ville+"','"+deno+"','"+ice+"','"+Rep+"','"+Site+"','"+FormeJur+"','"+Taille+"','"+Secteur+"','"+Activite+"','"+Document+"','"+EtatDossier+"','"+Avis+"',TO_DATE('"+DateD+"','YYYY-MM-DD'),NULL,NULL,NULL)";
                stm=con.prepareStatement(query);
                stm.executeQuery(query);
                String query1 = "SELECT * FROM DEMA WHERE ICE ='"+ice+"'";
                stm=con.prepareStatement(query1);
                rst=stm.executeQuery(query1);
                inputStream = new FileInputStream("C:\\Users\\hp\\IdeaProjects\\App_CCIS\\src\\sample\\DocImg\\Demarche.xlsx");
                ZipSecureFile.setMinInflateRatio(0);

                XSSFWorkbook wb = new XSSFWorkbook(inputStream);
                // XSSFWorkbook wb= new XSSFWorkbook();
                //XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
                String name = wb.getSheetName(0);
                Sheet sheet=wb.getSheet(name);
          /*  System.out.println(sheet.getSheetName());
            System.out.println(sheet.getLastRowNum());
            System.out.println(sheet.getPhysicalNumberOfRows());*/

                // XSSFRow header = sheet.getRow(0);
                int index=sheet.getLastRowNum()+1;
                while (rst.next()) {
                    //JOptionPane.showMessageDialog(null,"Connection bien établie");
                    XSSFRow row = (XSSFRow) sheet.createRow(index);
                    row.createCell(0).setCellValue(rst.getString("Prestation"));
                    row.createCell(1).setCellValue(rst.getString("Lieu"));
                    row.createCell(2).setCellValue(rst.getString("DateC"));
                    row.createCell(3).setCellValue(rst.getString("Objet"));
                    row.createCell(4).setCellValue(rst.getString("NomPre"));
                    row.createCell(5).setCellValue(rst.getString("Statut"));
                    row.createCell(6).setCellValue(rst.getString("Tele"));
                    row.createCell(7).setCellValue(rst.getString("Mail"));
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
                    row.createCell(21).setCellValue(rst.getString("Document"));
                    row.createCell(22).setCellValue(rst.getString("EtatDossier"));
                    row.createCell(23).setCellValue(rst.getString("Suite"));
                    row.createCell(24).setCellValue(rst.getString("DateD"));
                    row.createCell(25).setCellValue(rst.getString("NombreDoc"));
                    row.createCell(26).setCellValue(rst.getString("Recette"));
                    row.createCell(27).setCellValue(rst.getString("Note"));
                    index++;
                }


                fileOut = new FileOutputStream("C:\\Users\\hp\\Desktop\\Demarche.xlsx");
                wb.write(fileOut);

                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("les informations sont bien envoyées");
                alert.showAndWait();
                stm.close();
                rst.close();
            } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(inputStream);
                IOUtils.close(fileOut);
            }
            writeWord();  //Générer une copie de fiche de renseignement DA
        }

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
    /*******validate email***************/
    private boolean validerEmail(){
        Pattern p= Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m=p.matcher(Email.getText());
        if(m.find() && m.group().equals(Email.getText())){
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
        Matcher m=p.matcher(ICE.getText());
        if(m.find()&& m.group().equals(ICE.getText())){
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
        Matcher m=p.matcher(Tel.getText());
        if(m.find()&& m.group().equals(Tel.getText())){
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
        if(site.getText().contains("www")){
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
        if( NomPrenom.getText().isEmpty()| Tel.getText().isEmpty()| Email.getText().isEmpty()| Adresse.getText().isEmpty()|
                Ville.getText().isEmpty()| Deno.getText().isEmpty()| ICE.getText().isEmpty()| site.getText().isEmpty()| NomRep.getText().isEmpty()| Activité.getText().isEmpty()|
                RepCCIS.getText().isEmpty()| qualité.getText().isEmpty()){
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
        if(!CarteBox.isSelected()| AttestationBox.isSelected()| CertificatVB.isSelected()| CertificatOR.isSelected()| VisaDC.isSelected()| VisaC.isSelected()| VisaF.isSelected()| Recommdation.isSelected() /*|Autre.isSelected()*/){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Séléctionnez un document demandé");
            alert.showAndWait();
            return false;
        }
        if(!Industrie.isSelected()| Commerce.isSelected()| Services.isSelected()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Séléctionnez un secteur d'activité");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    /***********************************************Générer un fichier word***********************************************************************/
    private boolean writeWord() throws FileNotFoundException, InvalidFormatException, IOException{
        XWPFDocument doc=new XWPFDocument();
        String dateC = Date1.getValue().toString();
        String Lieu =ComboLieu.getValue().toString();
        String Objet = "";
        // String Objet = Objt.getElements().nextElement().getText();
        String MD=Mdemande.getText();
        String OB =Obrev.getText();
        if (DInfo.isSelected()) {
            Objet += DInfo.getText();
        }
        if (DDoc.isSelected()) {
            Objet += DDoc.getText();
        }
        String Document = "";
        if (CarteBox.isSelected()) {
            Document += CarteBox.getText();
        }
        if (AttestationBox.isSelected()) {
            Document += AttestationBox.getText();
        }
        if (CertificatVB.isSelected()) {
            Document += CertificatVB.getText();
        }
        if (CertificatOR.isSelected()) {
            Document += CertificatOR.getText();
        }
        if (VisaDC.isSelected()) {
            Document += VisaDC.getText();
        }
        if (VisaC.isSelected()) {
            Document += VisaC.getText();
        }
        if (VisaF.isSelected()) {
            Document += VisaF.getText();
        }
        if (Recommdation.isSelected()) {
            Document += Recommdation.getText();
        } else {
            Document += AutreField.getText();
        }
        String NomPre = NomPrenom.getText();
        String statut="";
        if(Entre.isSelected()){
            statut+= Entre.getText();
        }if(Porteur.isSelected()){
            statut+= Porteur.getText();
        }
        String Tele = Tel.getText();
        String mail = Email.getText();
        String adr = Adresse.getText();
        String ville = Ville.getText();
        String deno = Deno.getText();
        String ice = ICE.getText();
        String Site = site.getText();
        String Rep = NomRep.getText();
        String FormeJur = "";
        if (PP.isSelected()) {
            FormeJur += PP.getText();
        }
        if (SARL.isSelected()) {
            FormeJur += SARL.getText();
        }
        if (SA.isSelected()) {
            FormeJur += SA.getText();
        }
        if (AutoE.isSelected()) {
            FormeJur += AutoE.getText();
        }// else {
        // FormeJur += A.getText();
        //}
        String Taille = "";
        if (Petite.isSelected()) {
            Taille += Petite.getText();
        }
        if (Moyenne.isSelected()) {
            Taille += Moyenne.getText();
        }
        if (Grande.isSelected()) {
            Taille += Grande.getText();
        }
        String Secteur = "";
        if (Industrie.isSelected()) {
            Secteur += Industrie.getText();
        }
        if (Commerce.isSelected()) {
            Secteur += Commerce.getText();
        }
        if (Services.isSelected()) {
            Secteur += Services.getText();
        }
        String Activite = Activité.getText();
        String EtatDossier = "";
        if (CC.isSelected()) {
            EtatDossier += CC.getText();
        }
        if (In.isSelected()) {
            EtatDossier += In.getText();
        }
        if (N.isSelected()) {
            EtatDossier += N.getText();
        }
        String Avis = "";
        if (Accep.isSelected()) {
            Avis += Accep.getText();
        }
        if (Rejet.isSelected()) {
            Avis += Rejet.getText();
        }
        String DateD = Date2.getValue().toString();
        String RepCC = RepCCIS.getText();
        String qlt = qualité.getText();
        String retA = "";
        if (Accom.isSelected()) {
            retA = Accom.getText();
        }
        if (Cours.isSelected()) {
            retA = Cours.getText();
        }
        String E1 = "", E2 = "";
        if (accepter.isSelected()) {
            E1 = "OUI";
            E2 = "OUI";
        }else{
            E1 = "NON";
            E2 = "NON";
        }



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
        XWPFParagraph p=doc.createParagraph();
        XWPFRun run=p.createRun();
        run.setText("Démarches" +" "+ "administratives");
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
        XWPFParagraph p4=doc.createParagraph();
        XWPFRun run3=p4.createRun();
        run3.setText("");

        table.setWidth(10000);
        XWPFParagraph p3=doc.createParagraph();
        XWPFRun run2=p3.createRun();
        run2.setText("Document demandé:");
        run2.setFontSize(10);

        run2.setBold(true);
        XWPFTable table2=doc.createTable();
        XWPFTableRow r4=table2.getRow(0);
        r4.getCell(0).setText(Document);
        XWPFTableRow r5=table2.createRow();
        r5.getCell(0).setText("Motif de la demande : "+MD);
        table2.setWidth(10000);
        XWPFParagraph pp6=doc.createParagraph();
        XWPFRun run11=pp6.createRun();
        run11.setText("");
        XWPFParagraph p5=doc.createParagraph();
        XWPFRun run4=p5.createRun();
        run4.setText("IDENTIFICATION DU DEMANDEUR :");
        run4.setFontSize(10);
        run4.setBold(true);
             /*XWPFParagraph pp5=doc.createParagraph();
        XWPFRun run10=pp5.createRun();
        run10.setText("");*/

        XWPFTable table3=doc.createTable();
        XWPFTableRow rr1=table3.getRow(0);
        rr1.getCell(0).setText("Nom et prénom :");
        rr1.addNewTableCell().setText(NomPre);
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
        XWPFParagraph p7=doc.createParagraph();
        XWPFRun run6=p7.createRun();
        run6.setText("NB : la CCIS décline toute responsabilité de l’usage non conforme du document délivré au demandeur de droit ou à son mandaté.");
        run6.setFontSize(10);
        run6.setColor("0055ff");
        run6.setBold(true);
        XWPFParagraph p8=doc.createParagraph();
        XWPFRun run7=p8.createRun();
        run7.setText("Je certifie l’authenticité des informations susmentionnées et autorise la CCIS-MS à les utiliser pour toute fin utile ");
        run7.setFontSize(10);
        run7.setBold(true);
        p8.setAlignment(ParagraphAlignment.RIGHT);
        XWPFParagraph p9=doc.createParagraph();
        XWPFRun run8=p9.createRun();
        run8.setText("Signature du demandeur :");
        run8.setFontSize(10);
        run8.setBold(true);
        p9.setAlignment(ParagraphAlignment.CENTER);
        run8.addBreak();

        XWPFTable table5=doc.createTable();
        XWPFTableRow rrrr1=table5.getRow(0);
        rrrr1.getCell(0).setText("Cadre réservé à la CCIS :" +" "+" "+" "+" "+" ");
        rrrr1.getCell(0).setColor("ddeff8");

        XWPFTableRow rrrr2=table5.createRow();
        rrrr2.getCell(0).setText("Etat du dossier fourni:"+EtatDossier);
        rrrr2.addNewTableCell().setText(" Suite accordée à la demande :"+Avis);
        rrrr2.addNewTableCell().setText(" Date de délivrance du document administratif :"+DateD);


        XWPFTableRow rrrr3=table5.createRow();
        rrrr3.getCell(0).setText("Observations :"+OB);
        rrrr3.addNewTableCell().setText(" Nom et prénom du représentant de la CCIS :" +"\n" + RepCC+"Sa qualité:"+qlt);

        rrrr3.addNewTableCell().setText(" Retrait :"+retA);
        table5.setWidth(10000);



        doc.write(new FileOutputStream("C:\\Users\\hp\\IdeaProjects\\App_CCIS\\src\\sample\\wordFilesa\\DA_"+NomPre+".docx"));
        return true;
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
        if (DInfo.isSelected()) {
            DInfo.setSelected(false);
        }
        if (DDoc.isSelected()) {
            DDoc.setSelected(false);
        }
        if (CarteBox.isSelected()) {
            CarteBox.setSelected(false);
        }
        if (AttestationBox.isSelected()) {
            AttestationBox.setSelected(false);
        }
        if (CertificatVB.isSelected()) {
            CertificatVB.setSelected(false);
        }
        if (CertificatOR.isSelected()) {
            CertificatOR.setSelected(false);
        }
        if (VisaDC.isSelected()) {
            VisaDC.setSelected(false);
        }
        if (VisaC.isSelected()) {
           VisaC.setSelected(false);
        }
        if (VisaF.isSelected()) {
           VisaF.setSelected(false);
        }
        if (Recommdation.isSelected()) {
           Recommdation.setSelected(false);
        }
        NomPrenom.setText("");
        if(Entre.isSelected()){
           Entre.setSelected(false);
        }if(Porteur.isSelected()){
           Porteur.setSelected(false);
        }
          Tel.setText("");
          Email.setText("");
          Adresse.setText("");
          Ville.setText("");
          Deno.setText("");
          ICE.setText("");
          site.setText("");
          NomRep.setText("");
        if (PP.isSelected()) {
             PP.setSelected(false);
        }
        if (SARL.isSelected()) {
            SARL.setSelected(false);
        }
        if (SA.isSelected()) {
            SA.setSelected(false);
        }
        if (AutoE.isSelected()) {
            AutoE.setSelected(false);
        }
        if (Petite.isSelected()) {
             Petite.setSelected(false);
        }
        if (Moyenne.isSelected()) {
             Moyenne.setSelected(false);
        }
        if (Grande.isSelected()) {
             Grande.setSelected(false);
        }
        if (Industrie.isSelected()) {
           Industrie.setSelected(false);
        }
        if (Commerce.isSelected()) {
            Commerce.setSelected(false);
        }
        if (Services.isSelected()) {
            Services.setSelected(false);
        }
        Activité.setText("");
        if (CC.isSelected()) {
            CC.setSelected(false);
        }
        if (In.isSelected()) {
            In.setSelected(false);
        }
        if (N.isSelected()) {
             N.setSelected(false);
        }
        if (Accep.isSelected()) {
            Accep.setSelected(false);
        }
        if (Rejet.isSelected()) {
            Rejet.setSelected(false);
        }
        if (Accom.isSelected()) {
            Accom.setSelected(false);
        }
        if (Cours.isSelected()) {
            Cours.setSelected(false);
        }
Obrev.setText("");
    }
}

