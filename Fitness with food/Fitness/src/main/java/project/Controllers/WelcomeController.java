
package project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import project.FileService.FileService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    @FXML
    private Text correctInput;

    @FXML
    private Button registrationButton;



    @FXML
    void initialize() throws IOException, SAXException, ParserConfigurationException {
               correctInput.setVisible(false);
        registrationButton.setOnAction(event -> {
            registrationButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/SignInView.fxml"));
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

        enterButton.disableProperty().bind(passwordField.textProperty().isEmpty().or(loginField.textProperty().isEmpty()));
        enterButton.setOnAction(event -> {
            for (int i = 0; i < FileService.getLoginList().size(); i++) {
                if (loginField.getText().equals(FileService.getLoginList().get(i)) && passwordField.getText().equals(FileService.getPasswordList().get(i))) {
                    enterButton.getScene().getWindow().hide();
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

                } else {
                    correctInput.setVisible(true);
                }
            }
        });
    }
}



