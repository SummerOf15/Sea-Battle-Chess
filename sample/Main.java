package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;

        primaryStage.setTitle("Sea Battle Chess");
        initRootLayout();
    }

    public void initRootLayout(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));

            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
