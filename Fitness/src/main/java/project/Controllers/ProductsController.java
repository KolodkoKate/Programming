package project.Controllers;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.FileService.FileService;

public class ProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField sumCaloriesField;

    @FXML
    private TextField maxCaloriesField;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    public ListView<String> listProductsView;

    @FXML
    private ListView<String> currentListProductsBreakfastView;

    @FXML
    private ListView<String> currentListProductsLunchView;

    @FXML
    private ListView<String> currentListProductsDinnerView;

    @FXML
    private ToggleButton breakfastButton;

    @FXML
    private ToggleButton lunchButton;

    @FXML
    private ToggleButton dinnerButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label incorrectProductLabel;

    @FXML
    private Label incorrectCaloriesLabel;

    private Label gramsLabel;
    private TextField gramsField;
    private Button okButton;
    private Label incorrectLabel;

    private ObservableList<String> currentBreakfastList = FXCollections.observableArrayList();
    private ObservableList<String> currentLunchList = FXCollections.observableArrayList();
    private ObservableList<String> currentDinnerList = FXCollections.observableArrayList();

    public static String value = "";

    @FXML
    void initialize() throws IOException {
        maxCaloriesField.setText(value);
        sumCaloriesField.setText("0");
        maxCaloriesField.setEditable(false);
        sumCaloriesField.setEditable(false);
        currentListProductsLunchView.setVisible(false);
        currentListProductsDinnerView.setVisible(false);
        incorrectProductLabel.setVisible(false);
        incorrectCaloriesLabel.setVisible(false);

        backButton.setOnAction(event ->{
            backButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/DailyMealView.fxml"));
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

        FileReader productsFile = new FileReader("src/main/java/project/FileService/Products.txt");
        ObservableList<String> list = FileService.getsProductsList(productsFile);
        listProductsView.setItems(list);
        breakfastButton.setOnAction(event -> {
            currentListProductsLunchView.setVisible(false);
            currentListProductsDinnerView.setVisible(false);
            currentListProductsBreakfastView.setVisible(true);
            currentListProductsBreakfastView.setItems(currentBreakfastList);
            addButton.setOnAction(newEvent ->{
                gramsScene();
                okButton.setOnAction(actionEvent -> {
                    try {
                        Integer.parseInt(gramsField.getText());
                        String[] itemMass = listProductsView.getSelectionModel().getSelectedItem().split(" ");
                        for (int i = 0; i < itemMass.length; i++) {
                            try {
                                Integer.parseInt(itemMass[i]);
                                sumCaloriesField.setText(String.valueOf(Integer.parseInt(sumCaloriesField.getText()) + Integer.parseInt(itemMass[i]) * Integer.parseInt(gramsField.getText()) / 100));
                                if(Double.parseDouble(sumCaloriesField.getText()) > Double.parseDouble(maxCaloriesField.getText()))
                                {
                                    incorrectCaloriesLabel.setVisible(true);
                                }else incorrectCaloriesLabel.setVisible(false);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        currentBreakfastList.add(listProductsView.getSelectionModel().getSelectedItem());
                        currentBreakfastList.add("(" + gramsField.getText() + ")");
                        okButton.getScene().getWindow().hide();
                    }catch (Exception e)
                    {
                        incorrectLabel.setVisible(true);
                    }
                });
                incorrectProductLabel.setVisible(false);
            });
            deleteButton.setOnAction(newEvent ->{
                if(!currentListProductsBreakfastView.getSelectionModel().isEmpty()) {
                    int positionSelectedItem = currentBreakfastList.indexOf(currentListProductsBreakfastView.getSelectionModel().getSelectedItem());
                    int positionSelectedItemGrams = 0;
                    int currentGrams = 0;
                    String[] itemMass;
                    if (positionSelectedItem % 2 == 0) {
                        positionSelectedItemGrams = positionSelectedItem + 1;
                        currentGrams = Integer.parseInt(currentBreakfastList.get(positionSelectedItemGrams).substring(1, currentBreakfastList.get(positionSelectedItemGrams).length() - 1));
                        itemMass = currentListProductsBreakfastView.getSelectionModel().getSelectedItem().split(" ");
                        currentBreakfastList.remove(currentListProductsBreakfastView.getSelectionModel().getSelectedItem());
                        currentBreakfastList.remove(positionSelectedItem);
                    } else {
                        positionSelectedItemGrams = positionSelectedItem;
                        currentGrams = Integer.parseInt(currentBreakfastList.get(positionSelectedItemGrams).substring(1, currentBreakfastList.get(positionSelectedItemGrams).length() - 1));
                        currentBreakfastList.remove(currentListProductsBreakfastView.getSelectionModel().getSelectedItem());
                        itemMass = currentBreakfastList.get(positionSelectedItem - 1).split(" ");
                        currentBreakfastList.remove(positionSelectedItem - 1);
                    }
                    for (int i = 0; i < itemMass.length; i++) {
                        try {
                            Integer.parseInt(itemMass[i]);
                            sumCaloriesField.setText(String.valueOf(Integer.parseInt(sumCaloriesField.getText()) - Integer.parseInt(itemMass[i]) * currentGrams / 100));
                            if(Double.parseDouble(sumCaloriesField.getText()) > Double.parseDouble(maxCaloriesField.getText()))
                            {
                                incorrectCaloriesLabel.setVisible(true);
                            }else incorrectCaloriesLabel.setVisible(false);
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    incorrectProductLabel.setVisible(false);
                }else{
                    incorrectProductLabel.setVisible(true);
                }
            });
        });
        lunchButton.setOnAction(event -> {
            currentListProductsBreakfastView.setVisible(false);
            currentListProductsDinnerView.setVisible(false);
            currentListProductsLunchView.setVisible(true);
            currentListProductsLunchView.setItems(currentLunchList);
            addButton.setOnAction(newEvent ->{
                gramsScene();
                okButton.setOnAction(actionEvent -> {
                    try {
                        Integer.parseInt(gramsField.getText());
                        String[] itemMass = listProductsView.getSelectionModel().getSelectedItem().split(" ");
                        for (int i = 0; i < itemMass.length; i++) {
                            try {
                                Integer.parseInt(itemMass[i]);
                                sumCaloriesField.setText(String.valueOf(Integer.parseInt(sumCaloriesField.getText()) + Integer.parseInt(itemMass[i]) * Integer.parseInt(gramsField.getText()) / 100));
                                if(Double.parseDouble(sumCaloriesField.getText()) > Double.parseDouble(maxCaloriesField.getText()))
                                {
                                    incorrectCaloriesLabel.setVisible(true);
                                }else incorrectCaloriesLabel.setVisible(false);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        currentLunchList.add(listProductsView.getSelectionModel().getSelectedItem());
                        currentLunchList.add("(" + gramsField.getText() + ")");
                        okButton.getScene().getWindow().hide();
                    }catch (Exception e)
                    {
                        incorrectLabel.setVisible(true);
                    }
                });
            });
            deleteButton.setOnAction(newEvent ->{
                if(!currentListProductsLunchView.getSelectionModel().isEmpty()) {
                    int positionSelectedItem = currentLunchList.indexOf(currentListProductsLunchView.getSelectionModel().getSelectedItem());
                    int positionSelectedItemGrams = 0;
                    int currentGrams = 0;
                    String[] itemMass;
                    if (positionSelectedItem % 2 == 0) {
                        positionSelectedItemGrams = currentLunchList.indexOf(currentListProductsLunchView.getSelectionModel().getSelectedItem()) + 1;
                        currentGrams = Integer.parseInt(currentLunchList.get(positionSelectedItemGrams).substring(1, currentLunchList.get(positionSelectedItemGrams).length() - 1));
                        itemMass = currentListProductsLunchView.getSelectionModel().getSelectedItem().split(" ");
                        currentLunchList.remove(currentListProductsLunchView.getSelectionModel().getSelectedItem());
                        currentLunchList.remove(positionSelectedItem);
                    } else {
                        positionSelectedItemGrams = currentLunchList.indexOf(currentListProductsLunchView.getSelectionModel().getSelectedItem());
                        currentGrams = Integer.parseInt(currentLunchList.get(positionSelectedItemGrams).substring(1, currentLunchList.get(positionSelectedItemGrams).length() - 1));
                        currentLunchList.remove(currentListProductsLunchView.getSelectionModel().getSelectedItem());
                        itemMass = currentLunchList.get(positionSelectedItem - 1).split(" ");
                        currentLunchList.remove(positionSelectedItem - 1);
                    }
                    for (int i = 0; i < itemMass.length; i++) {
                        try {
                            Integer.parseInt(itemMass[i]);
                            sumCaloriesField.setText(String.valueOf(Integer.parseInt(sumCaloriesField.getText()) - Integer.parseInt(itemMass[i]) * currentGrams / 100));
                            if(Double.parseDouble(sumCaloriesField.getText()) > Double.parseDouble(maxCaloriesField.getText()))
                            {
                                incorrectCaloriesLabel.setVisible(true);
                            }else incorrectCaloriesLabel.setVisible(false);
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    incorrectProductLabel.setVisible(false);
                }else {
                    incorrectProductLabel.setVisible(true);
                }
            });
        });
        dinnerButton.setOnAction(event -> {
            currentListProductsBreakfastView.setVisible(false);
            currentListProductsLunchView.setVisible(false);
            currentListProductsDinnerView.setVisible(true);
            currentListProductsDinnerView.setItems(currentDinnerList);
            addButton.setOnAction(newEvent ->{
                gramsScene();
                okButton.setOnAction(actionEvent -> {
                    try {
                        Integer.parseInt(gramsField.getText());
                        String[] itemMass = listProductsView.getSelectionModel().getSelectedItem().split(" ");
                        for (int i = 0; i < itemMass.length; i++) {
                            try {
                                Integer.parseInt(itemMass[i]);
                                sumCaloriesField.setText(String.valueOf(Integer.parseInt(sumCaloriesField.getText()) + Integer.parseInt(itemMass[i]) * Integer.parseInt(gramsField.getText()) / 100));
                                if(Double.parseDouble(sumCaloriesField.getText()) > Double.parseDouble(maxCaloriesField.getText()))
                                {
                                    incorrectCaloriesLabel.setVisible(true);
                                }else incorrectCaloriesLabel.setVisible(false);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        currentDinnerList.add(listProductsView.getSelectionModel().getSelectedItem());
                        currentDinnerList.add("(" + gramsField.getText() + ")");
                        okButton.getScene().getWindow().hide();
                    }catch (Exception e)
                    {
                        incorrectLabel.setVisible(true);
                    }
                });
            });
            deleteButton.setOnAction(newEvent ->{
                if(!currentListProductsDinnerView.getSelectionModel().isEmpty()) {
                    int positionSelectedItem = currentDinnerList.indexOf(currentListProductsDinnerView.getSelectionModel().getSelectedItem());
                    int positionSelectedItemGrams = 0;
                    int currentGrams = 0;
                    String[] itemMass;
                    if (positionSelectedItem % 2 == 0) {
                        positionSelectedItemGrams = currentDinnerList.indexOf(currentListProductsDinnerView.getSelectionModel().getSelectedItem()) + 1;
                        currentGrams = Integer.parseInt(currentDinnerList.get(positionSelectedItemGrams).substring(1, currentDinnerList.get(positionSelectedItemGrams).length() - 1));
                        itemMass = currentListProductsDinnerView.getSelectionModel().getSelectedItem().split(" ");
                        currentDinnerList.remove(currentListProductsDinnerView.getSelectionModel().getSelectedItem());
                        currentDinnerList.remove(positionSelectedItem);
                    } else {
                        positionSelectedItemGrams = currentDinnerList.indexOf(currentListProductsDinnerView.getSelectionModel().getSelectedItem());
                        currentGrams = Integer.parseInt(currentDinnerList.get(positionSelectedItemGrams).substring(1, currentDinnerList.get(positionSelectedItemGrams).length() - 1));
                        currentDinnerList.remove(currentListProductsDinnerView.getSelectionModel().getSelectedItem());
                        itemMass = currentDinnerList.get(positionSelectedItem - 1).split(" ");
                        currentDinnerList.remove(positionSelectedItem - 1);
                    }
                    for (int i = 0; i < itemMass.length; i++) {
                        try {
                            Integer.parseInt(itemMass[i]);
                            sumCaloriesField.setText(String.valueOf(Integer.parseInt(sumCaloriesField.getText()) - Integer.parseInt(itemMass[i]) * currentGrams / 100));
                            if(Double.parseDouble(sumCaloriesField.getText()) > Double.parseDouble(maxCaloriesField.getText()))
                            {
                                incorrectCaloriesLabel.setVisible(true);
                            }else incorrectCaloriesLabel.setVisible(false);
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    incorrectProductLabel.setVisible(false);
                }else {
                    incorrectProductLabel.setVisible(true);
                }
            });
        });



        ToggleGroup group = new ToggleGroup();
        breakfastButton.setToggleGroup(group);
        lunchButton.setToggleGroup(group);
        dinnerButton.setToggleGroup(group);
    }
    public void gramsScene()
    {
        gramsLabel = new Label("Введите кол-во грамм продукта");
        AnchorPane.setTopAnchor(gramsLabel, 10.0);
        AnchorPane.setLeftAnchor(gramsLabel, 60.0);
        AnchorPane.setRightAnchor(gramsLabel, 60.0);

        gramsField = new TextField();
        AnchorPane.setBottomAnchor(gramsField, 100.0);
        AnchorPane.setLeftAnchor(gramsField, 60.0);
        AnchorPane.setRightAnchor(gramsField, 60.0);

        okButton = new Button();
        okButton.setText("Ok");
        AnchorPane.setBottomAnchor(okButton, 10.0);
        AnchorPane.setLeftAnchor(okButton, 60.0);
        AnchorPane.setRightAnchor(okButton, 60.0);

        incorrectLabel = new Label("Неправильный ввод");
        AnchorPane.setBottomAnchor(incorrectLabel, 50.0);
        AnchorPane.setLeftAnchor(incorrectLabel, 60.0);
        AnchorPane.setRightAnchor(incorrectLabel, 60.0);
        incorrectLabel.setTextFill(Color.web("#ff0000"));
        incorrectLabel.setVisible(false);

        AnchorPane secondaryLayout = new AnchorPane(gramsLabel,gramsField,okButton,incorrectLabel);
        Scene secondScene = new Scene(secondaryLayout, 330, 200);

        Stage newWindow = new Stage();
        newWindow.setTitle("Граммовка");
        newWindow.setScene(secondScene);
        newWindow.show();
    }
}
