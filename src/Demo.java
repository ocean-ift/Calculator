import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Demo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.getChildren().add(closeBtn);
        }
    }

    double xOff = 0;
    double yOff = 0;

    public void start(Stage primaryStage) {
        //remove window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: green;");

        ToolBar toolBar = new ToolBar();

//        toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                xOff = mouseEvent.getSceneX();
//                yOff = mouseEvent.getSceneY();
//            }
//        });
//
//        toolBar.setOnMouseDragged(event -> {
//           primaryStage.setX(event.getScreenX() - xOff);
//           primaryStage.setY(event.getScreenY() - yOff);
//        });


        int height = 25;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
        toolBar.getItems().add(new WindowButtons());

        borderPane.setTop(toolBar);

        primaryStage.setScene(new Scene(borderPane, 300, 250));
        primaryStage.show();
    }
}
