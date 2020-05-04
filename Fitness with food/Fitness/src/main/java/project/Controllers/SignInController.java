package project.Controllers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import project.FileService.Accounts;
import project.FileService.FileService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class SignInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameField;

    @FXML
    private Button registrableButton;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button backButton;
    @FXML
    private Label incorrectInput;

    @FXML
    void initialize() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("src/main/java/project/FileService/Accounts.xml"));
        incorrectInput.setVisible(false);

        registrableButton.disableProperty().bind(telephoneField.textProperty().isEmpty().or(surnameField.textProperty().isEmpty())
                .or(nameField.textProperty().isEmpty()).or(numberField.textProperty().isEmpty())
                .or(loginField.textProperty().isEmpty()).or(passwordField.textProperty().isEmpty()));

        registrableButton.setOnAction(event ->{
            try{
                Integer.parseInt(numberField.getText());
                Long.parseLong(telephoneField.getText());
                if(Long.parseLong(telephoneField.getText()) >=375*Math.pow(10,9) && Integer.parseInt(numberField.getText())<=12 && Long.parseLong(telephoneField.getText()) <=376*Math.pow(10,9) && Integer.parseInt(numberField.getText())>=1) {
                    Accounts accounts = new Accounts(nameField.getText(), surnameField.getText(), loginField.getText(),
                            passwordField.getText(), Long.parseLong(telephoneField.getText()), Integer.parseInt(numberField.getText()));
                    try {
                        FileService.writeToXML(document, accounts);
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    registrableButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/ChooseView.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }else
                {
                    incorrectInput.setVisible(true);
                }
            }catch (Exception e)
            {
                incorrectInput.setVisible(true);
            }
        });

        backButton.setOnAction(event ->{
            backButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Welcome.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        }
    }




