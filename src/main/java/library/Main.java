package library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        stage.setTitle("Library Management System");

        Parent root = FXMLLoader.load(Main.class.getResource("/view/login.fxml"));
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setMaximized(true);   // ✅ окно сразу на весь экран
        stage.show();
    }

    public static void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
            scene.setRoot(root);            // ✅ не создаём новый Scene каждый раз
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}