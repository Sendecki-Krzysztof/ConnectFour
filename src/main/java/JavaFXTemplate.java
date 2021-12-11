import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {

	private Game connectFour = new Game();
	private GameController gameController = new GameController(connectFour);
	private BorderPane root = new BorderPane();
	private MenuBar menubar = new MenuBar();
	private VBox vbox = new VBox();

	/*
	 * Main...
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 *  JavaFX's start Method. Runs the welcome and gameScreen scene
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Connect-Four");
		fillMenu(menubar);
		
		weclomeScreen(primaryStage);
	}
	
	/*
	 * Displays the Welcome Screen.
	 */
	private void weclomeScreen(Stage stage) {
		
		BorderPane root = new BorderPane();
		
		Button playButton = setPlayButton(stage, new Button());

		Image image = new Image("WelcomeScreen.png");
		ImageView view = new ImageView(image);

		root.setStyle("-fx-background-color: white");
		root.setTop(view);
		root.setCenter(playButton);
		
		Scene scene = new Scene(root, 700,700);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * Helper function that Sets the Play button for WelcomeScreen
	 */
	private Button setPlayButton(Stage stage, Button button) {
		
		button.setMinSize(300, 100);
		button.setStyle("-fx-background-color: #00bff3; -fx-background-radius: 100px; -fx-font-size:50; -fx-text-fill: white");
		button.setText("Play");
		button.setOnAction(e -> gameScreen(stage)); // pressing button changes stage to the game screen
		return button;
	}
	
	/*
	 * Screen that the game is in. Has as the game board and other menus
	 */
	private void gameScreen(Stage primaryStage) {
		
		
		GridPane gameBoard = new GridPane();
		
		// GameBoard gap size
		gameBoard.setHgap(7.5);
		gameBoard.setVgap(7.5);
		gameBoard.setPadding(new Insets(150));
		
		createBoard(gameBoard);
		vbox.getChildren().addAll(menubar, gameController.getText());
		root.setStyle("-fx-background-color: white");
		root.setTop(vbox);
		root.setCenter(gameBoard);
		
				
		Scene scene = new Scene(root, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/*
	 * Method to fill the MenuBar with Game-play, Themes, and Options
	 */
	private MenuBar fillMenu(MenuBar menubar) {
		// Create the menus and name them accordingly
		Menu gameplay = new Menu("Gameplay");
		Menu theme = new Menu("Themes");
		Menu options = new Menu("Options");
		
		//Fill Game-play menu
		MenuItem reverseMove = new MenuItem("Reverse Move");
		gameController.gameplayEvents(reverseMove);
		gameplay.getItems().add(reverseMove);
		
		
		//Fill Theme menu
		MenuItem Theme1 = new MenuItem("Default");
		MenuItem Theme2 = new MenuItem("Dark Mode");
		MenuItem Theme3 = new MenuItem("Underground");
		gameController.themeEvents(Theme1, Theme2,Theme3, menubar, root);
		theme.getItems().add(Theme1);
		theme.getItems().add(Theme2);
		theme.getItems().add(Theme3);
		
		//Fill Options menu
		MenuItem howToPlay = new MenuItem("How to Play");
		MenuItem newGame = new MenuItem("New Game");
		MenuItem exit = new MenuItem("Exit");
		gameController.optionEvents(howToPlay, newGame, exit);
		options.getItems().add(howToPlay);
		options.getItems().add(newGame);
		options.getItems().add(exit);
		
		//Add menus to menuBar
		menubar.getMenus().addAll(gameplay, theme, options);
		
		return menubar;
	}
	
	/*
	 *  Method that creates the Game Board. it is filled with GameButtons
	 */
	private GridPane createBoard(GridPane gameBoard) {
		for (int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				GameButton button = new GameButton(i,j);
				button.setStyle("-fx-background-color: lightgrey");
				
				button.setMinSize(50, 50);
				gameController.buttonEvents(button);
				
				connectFour.setArr(i, j, button);
				gameBoard.add(button, i, j);
			}
		}
		return gameBoard;
	}
}
