package Interface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Example");

        // Membuat tombol
        Button button = new Button("Click Me");
        button.setOnAction(e -> System.out.println("Button clicked!"));

        // Mengatur tata letak dengan menggunakan StackPane
        StackPane root = new StackPane();
        root.getChildren().add(button);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}