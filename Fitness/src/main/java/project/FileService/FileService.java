package project.FileService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ScriptAppenderSelector;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import project.Controllers.ProductsController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.Namespace;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class FileService {
    private static ArrayList<String> loginList = new ArrayList<String>();
    private static ArrayList<String> passwordList = new ArrayList<String>();
    private static ObservableList<String> itemProductsList = FXCollections.observableArrayList();


    public static List<String> getLoginList() {
        return loginList;
    }

    public static List<String> getPasswordList() {
        return passwordList;
    }

    public static ObservableList<String> getItemProductsList() {
        return itemProductsList;
    }

    public FileService() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("src/main/java/project/FileService/Accounts.xml"));
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        takeElements(nodeList);


    }

    static void takeElements(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                Text text = (Text) nodeList.item(i).getFirstChild();
                if (((Element) nodeList.item(i)).getTagName() == "login") {
                    loginList.add(text.getData().trim());
                    System.out.println(loginList);
                }
                if (((Element) nodeList.item(i)).getTagName() == "password") {
                    passwordList.add(text.getData().trim());
                    System.out.println(passwordList);
                }

                if (nodeList.item(i).hasChildNodes()) {
                    takeElements(nodeList.item(i).getChildNodes());
                }
            }
        }
    }

    public static void writeToXML(Document document, Accounts accounts) throws TransformerException, FileNotFoundException {
        Element accountTag = document.createElement("account");
        Element nameTag = document.createElement("name");
        Element surnameTag = document.createElement("surname");
        Element telephoneTag = document.createElement("telephone");
        Element numberTag = document.createElement("number");
        Element loginTag = document.createElement("login");
        Element passwordTag = document.createElement("password");

        Text nameText = document.createTextNode(accounts.getName());
        Text surnameText = document.createTextNode(accounts.getSurname());
        Text telephoneText = document.createTextNode(accounts.getTelephone());
        Text numberText = document.createTextNode(accounts.getNumber());
        Text loginText = document.createTextNode(accounts.getLogin());
        Text passwordText = document.createTextNode(accounts.getPassword());

        Element Accounts = document.getDocumentElement();
        Accounts.appendChild(accountTag);
        accountTag.appendChild(nameTag);
        nameTag.appendChild(nameText);
        accountTag.appendChild(surnameTag);
        surnameTag.appendChild(surnameText);
        accountTag.appendChild(telephoneTag);
        telephoneTag.appendChild(telephoneText);
        accountTag.appendChild(numberTag);
        numberTag.appendChild(numberText);
        accountTag.appendChild(loginTag);
        loginTag.appendChild(loginText);
        accountTag.appendChild(passwordTag);
        passwordTag.appendChild(passwordText);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("Accounts.xml")));
    }

    public static ObservableList<String> getsProductsList(FileReader filename) throws IOException {
        char[] symbols = new char[1000];
        filename.read(symbols);
        ArrayList<String> productsList = new ArrayList<String>();
        String line = "";
        for (int i = 0; i < symbols.length; i++) {
            if (!String.valueOf(symbols[i]).equals("\n")) {
                line += symbols[i];
            } else {
                productsList.add(line.trim());
                line = "";
            }
        }
        itemProductsList.addAll(productsList);
        return itemProductsList;
    }
}
