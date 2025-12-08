import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args); // запуск JavaFX
    }

    @Override
    public void start(Stage stage) {
        Button btn = new Button("Click me!");
        btn.setOnAction(e -> System.out.println("Button clicked"));

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("JavaFX Demo");
        stage.setScene(scene);
        stage.show();
        System.out.println("Complication");
    }
}
