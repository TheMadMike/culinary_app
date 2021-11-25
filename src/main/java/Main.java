import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static final String WINDOW_TITLE = "Culinary App";
    private static final String FXML_FILE_NAME = "App.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = loadFXML();
        stage.setScene(new Scene(root));
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Parent loadFXML() {
        try {
            URL resourceUrl = getClass().getResource(FXML_FILE_NAME);
            return FXMLLoader.load(resourceUrl);
        } catch(IOException exception) {
            System.err.println(exception.getMessage());
        }
        return new Group();
    }

}
