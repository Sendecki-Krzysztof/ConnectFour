import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {

	// Important Game parts
	private Game connectFour = new Game();
	private Label htp = new Label();
	private BorderPane root = new BorderPane();
	MenuBar menubar = new MenuBar();
	VBox vbox = new VBox();
	
	// Strings and text fields 
	private TextField text;
	private String p1Theme = "red";
	private String p2Theme = "blue";
	private String buttonColor = "lightgrey";
	private String p1Name = "Red";
	private String p2Name = "Blue";
	private String background = " -fx-background";
	private String howToPlayString = "The name of the game is connect-four, the goal is to get 4 of your color in a row. "
			+ "Be it diagonal, vertical, or horizontal. To Start deciding who player one and player 2, "
			+ "player one will go first. Select the spot you want to place a piece by clicking on it. "
			+ "(In order to place a piece, it must be on the bottom of the board or have a piece under it)."
			+ " Continue until either the board is filled up (a tie) or one of the players has succeeded in "
			+ "connecting four of their color.";
	
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
		setHTP();
		fillMenu(menubar);
		
		weclomeScreen(primaryStage);
	}
	
	/*
	 * Method that displays the welcome screen on application launch.
	 */
	public void weclomeScreen(Stage stage) {
		
		BorderPane root = new BorderPane();
		
		Button play = new Button();
		Image playButton = new Image("https://i.imgur.com/C39QSfF.png");
		ImageView viewPlay = new ImageView(playButton);
		play.setGraphic(viewPlay);
		play.setStyle("fx-background-color: lightgray");
		play.setOnAction(e -> gameScreen(stage));
		
		root.setStyle("-fx-background-image: url('https://i.imgur.com/9fAePdJ.png')");
		root.setCenter(play);
		
		Scene scene = new Scene(root, 700,700);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * Screen that the game is in. Has as the game board and other menus
	 */
	public void gameScreen(Stage primaryStage) {
		
		// TextBox
		text = new TextField();
		text.setDisable(true);
		text.setFont(Font.font(16));
		text.setStyle("-fx-text-inner-color: black" );
		
		
		GridPane gameBoard = new GridPane();
		
		// GameBoard gap size
		gameBoard.setHgap(7.5);
		gameBoard.setVgap(7.5);
		gameBoard.setPadding(new Insets(150));
		
		createBoard(gameBoard);
		vbox.getChildren().addAll(menubar, text);
		root.setTop(vbox);
		root.setCenter(gameBoard);
				
		Scene scene = new Scene(root, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/*
	 * Method for setting up the how to play box.
	 */
	public void setHTP() {
		
		// Settings for the HowToPlay TextBox 
		htp.setPadding(new Insets(0, 10, 10, 10));
		htp.setMaxWidth(180);
		htp.setWrapText(true);
		htp.setText(howToPlayString);
	}
	
	/*
	 * Method to fill the MenuBar with Game-play, Themes, and Options
	 */
	public MenuBar fillMenu(MenuBar menubar) {
		// Create the menus and name them accordingly
		Menu gameplay = new Menu("Gameplay");
		Menu theme = new Menu("Themes");
		Menu options = new Menu("Options");
		
		//Fill Game-play menu
		MenuItem reverseMove = new MenuItem("Reverse Move");
		gameplayEvents(reverseMove);
		gameplay.getItems().add(reverseMove);
		
		
		//Fill Theme menu
		MenuItem Theme1 = new MenuItem("Default");
		MenuItem Theme2 = new MenuItem("Dark Mode");
		MenuItem Theme3 = new MenuItem("Underground");
		themeEvents(Theme1, Theme2,Theme3);
		theme.getItems().add(Theme1);
		theme.getItems().add(Theme2);
		theme.getItems().add(Theme3);
		
		//Fill Options menu
		MenuItem howToPlay = new MenuItem("How to Play");
		MenuItem newGame = new MenuItem("New Game");
		MenuItem exit = new MenuItem("Exit");
		optionEvents(howToPlay, newGame, exit);
		options.getItems().add(howToPlay);
		options.getItems().add(newGame);
		options.getItems().add(exit);
		
		//Add menus to menuBar
		menubar.getMenus().addAll(gameplay, theme, options);
		
		return menubar;
	}
	
	/*
	 * Method to handle the events for the MenuItems in the Game-play
	 * menu within the MenuBar
	 */
	public void gameplayEvents(MenuItem reverseMove) {
		
		reverseMove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if (connectFour.moveNum != 0) { // If there some filled spots are on the board
					
					/*
					 * Undo the last move and set the text-field based on the current player after
					 *  the undo
					 */
					connectFour.undoMove(buttonColor); 
					if(connectFour.getPlayer() == 1) { 
						
						connectFour.setplayer(2);
						text.setText("Move Undone! Its " + p2Name +"'s turn!");
					} else if (connectFour.getPlayer() == 2){
						
						connectFour.setplayer(1);
						text.setText("Move Undone! Its " + p1Name +"'s turn!");
					}
				
					// Enable all the buttons that have not been pressed. This is for in the last move causes a win.
					connectFour.enableAllButtons(); 
					}
				
				else { // If there are no filled spots then just change the text box with an error
					
					text.setText("No Move to undo, Its "+ p1Name + "'s turn!");
				}
			}
		});
	}
	
	/*
	 * Method to handle the events for the MenuItems in the Themes
	 * menu within the MenuBar
	 */
	public void themeEvents(MenuItem theme1, MenuItem theme2, MenuItem theme3) {
		theme1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				background = " -fx-background";
				buttonColor = "lightgrey";
				p1Theme = "red";
				p1Name = "Red";
				p2Theme = "blue";
				p2Name = "Blue";
				
				text.setText("Theme has been changed!");
				text.setStyle("-fx-text-inner-color: black" );
				root.setStyle("-fx-background-color: " + background);
				menubar.setStyle("-fx-background-color: " + background);
				connectFour.setStyle(p1Theme, p2Theme, buttonColor);
			}
		});
		
		theme2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				background = "#2a272b";
				buttonColor = "#3e3940";
				p1Theme = "#ba5d61";
				p1Name = "Dark Red";
				p2Theme = "#b45dba";
				p2Name = "Purple";
				
				text.setText("Theme has been changed!");
				text.setStyle("-fx-text-inner-color: red");
				root.setStyle("-fx-background-color: " + background);
				connectFour.setStyle(p1Theme, p2Theme, buttonColor);
			}
		});
		
		theme3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				background = "#89d1fe";
				buttonColor = "#a5d9fa";
				p1Theme = "#ff6700";
				p1Name = "Orange";
				p2Theme = "#26c926";
				p2Name = "Green";
				
				text.setText("Theme has been changed!");
				text.setStyle("-fx-text-inner-color: #be00fe");
				root.setStyle("-fx-background-color: " + background);
				connectFour.setStyle(p1Theme, p2Theme, buttonColor);
			}
		});
	}
	 
	/*
	 * Method to handle the events for the MenuItems in the Options
	 * menu within the MenuBar
	 */
	public void optionEvents(MenuItem howToPlay, MenuItem newGame, MenuItem exit) {
		// Pop-up when user clicks the how to play menu option.
		howToPlay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage rules = new Stage();
				
				// Sets How to play String here
				rules.setTitle("How to Play");
				Scene howToPlayScene = new Scene(htp, 350, 200);
				rules.setScene(howToPlayScene);
				rules.show();
			}
		});
		
		newGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				connectFour.newGameBoard(buttonColor);
				text.setText(" ");
			}
		});

		// Event For exiting the game
		exit.setOnAction(e -> Platform.exit());
	}

	/*
	 *  Method that creates the Game Board. it is filled with GameButtons
	 */
	public GridPane createBoard(GridPane gameBoard) {
		for (int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				GameButton button = new GameButton(i,j);
				button.setStyle("-fx-background-color: " + buttonColor);
				
				button.setMinSize(50, 50);
				buttonEvents(button);
				
				connectFour.setArr(i, j, button);
				gameBoard.add(button, i, j);
			}
		}
		return gameBoard;
	}

	/*
	 * Handles the button events for when a button is pressed
	 */
	public void buttonEvents(GameButton button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if(!button.pressed) {
					if(connectFour.isValid(button)) {
						button.setDisable(true);
						button.pressed = true;
						connectFour.moveNum += 1;
						if(connectFour.getPlayer() == 1) {
							
							button.setStyle("-fx-background-color: " + p1Theme);
							button.player = 1;
							
							
							if(connectFour.checkWin()) {
								text.setText(p1Name +" has won the game!");
								connectFour.disableAllButtons();
							} else if (connectFour.checkTie()){
								text.setText("No more possible moves, the game is Tied!");
							} else {
								text.setText(p1Name +" has placed. Its " + p2Name +"'s turn!");
								
							}
							
							connectFour.setplayer(2);
						}
						else {
							
							button.setStyle("-fx-background-color: " + p2Theme);
							button.player = 2;
							if(connectFour.checkWin()) {
								text.setText(p2Name +" has won the game!");
								connectFour.disableAllButtons();
							} else if (connectFour.checkTie()){
								text.setText("No more possible moves, the game is Tied!");
							} else {
								text.setText(p2Name +" has placed. Its " + p1Name +"'s turn!");
							}
							connectFour.setplayer(1);

						}
					}
					else {
						text.setText("Invaild move, try again Player " + connectFour.getPlayer());
					}
				} 
			}
		});
	}

}
