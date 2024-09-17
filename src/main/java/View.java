import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class View extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("一键启动所有应用");
        primaryStage.getIcons().add(new Image("/dog.png"));
        Pane root = FXMLLoader.load(getClass().getResource("/view.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
