package application;
	
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private static Stage stg;
	@Override
	public void start(Stage primaryStage) throws Exception {
		stg = primaryStage;
		primaryStage.setResizable(false);
		Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
		primaryStage.setScene(new Scene(root,900,600));
		primaryStage.setTitle("Tic Tac Toe - By Nativ Maatuk");
		//add img icon to the program
		Image img = new Image(getClass().getClassLoader().getResource("Login.jpg").toString());
		primaryStage.getIcons().add(img);
		stg.requestFocus();
		primaryStage.show();
	}
	//use to change view | to example: page for Log in an another page for the game
	public void changeScene(String fxml)throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
