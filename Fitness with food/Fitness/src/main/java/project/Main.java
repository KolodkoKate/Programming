package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import project.FileService.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/Welcome.fxml"));

        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }
    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        logger.info("Application started");
        try {
            FileService service = new FileService();
        }catch (IOException e)
        {
            logger.error("Exception in open xml file");
        }
        logger.info("File opened");
        launch(args);
    }
}
