package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DailyMealController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button chooseProductsButton;

    @FXML
    private Button sumButton;

    @FXML
    private RadioButton manButton;

    @FXML
    private RadioButton womanButton;


    @FXML
    private TextField ageField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button backButton;
    @FXML
    private Label incorrectInputLabel;
    @FXML
    private RadioButton noneButton;

    @FXML
    private RadioButton littleButton;

    @FXML
    private RadioButton mediumButton;

    @FXML
    private RadioButton fullButton;

    @FXML
    private RadioButton hardButton;

    @FXML
    void initialize() {
        incorrectInputLabel.setVisible(false);
        backButton.setOnAction(event ->{
            backButton.getScene().getWindow().hide();
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
        });
        chooseProductsButton.setOnAction(event ->{
            chooseProductsButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ProductsView.fxml"));
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

        ToggleGroup group = new ToggleGroup();
        manButton.setToggleGroup(group);
        womanButton.setToggleGroup(group);

        ToggleGroup group1 = new ToggleGroup();
        noneButton.setToggleGroup(group1);
        littleButton.setToggleGroup(group1);
        mediumButton.setToggleGroup(group1);
        fullButton.setToggleGroup(group1);
        hardButton.setToggleGroup(group1);


        sumButton.setOnAction(event ->{
            if(group.getSelectedToggle() == manButton)
            {
                try {
                    Integer.parseInt(ageField.getText());
                    Integer.parseInt(weightField.getText());
                    Integer.parseInt(heightField.getText());
                    if (group1.getSelectedToggle() == noneButton)
                    {
                        resultArea.setText(String.valueOf(1.2*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())+5));
                    }else
                        if(group1.getSelectedToggle()==littleButton)
                        {
                            resultArea.setText(String.valueOf(1.375*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())+5));

                        }else
                        if(group1.getSelectedToggle()==mediumButton)
                        {
                            resultArea.setText(String.valueOf(1.55*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())+5));

                        }else
                        if(group1.getSelectedToggle()==fullButton)
                        {
                            resultArea.setText(String.valueOf(1.725*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())+5));

                        }else
                        if(group1.getSelectedToggle()==hardButton)
                        {
                            resultArea.setText(String.valueOf(1.9*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())+5));

                        }
                }catch (Exception e)
                {
                    incorrectInputLabel.setVisible(true);
                }

            }else
            {
                try {
                Integer.parseInt(ageField.getText());
                Integer.parseInt(weightField.getText());
                Integer.parseInt(heightField.getText());
                    if (group1.getSelectedToggle() == noneButton)
                    {
                        resultArea.setText(String.valueOf(1.2*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())-161));
                    }else
                    if(group1.getSelectedToggle()==littleButton)
                    {
                        resultArea.setText(String.valueOf(1.375*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())-161));

                    }else
                    if(group1.getSelectedToggle()==mediumButton)
                    {
                        resultArea.setText(String.valueOf(1.55*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())-161));

                    }else
                    if(group1.getSelectedToggle()==fullButton)
                    {
                        resultArea.setText(String.valueOf(1.725*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())-161));

                    }else
                    if(group1.getSelectedToggle()==hardButton)
                    {
                        resultArea.setText(String.valueOf(1.9*Integer.parseInt(weightField.getText())*10+6.25*Integer.parseInt(heightField.getText())-5*Integer.parseInt(ageField.getText())-161));

                    }
                }catch (Exception e)
                {
                    incorrectInputLabel.setVisible(true);
                }
            }
        });
    }
}
