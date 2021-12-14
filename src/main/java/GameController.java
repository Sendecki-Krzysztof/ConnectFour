import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameController {
	private String howToPlayString = "The name of the game is connect-four, the goal is to get 4 of your color in a row. "
			+ "Be it diagonal, vertical, or horizontal. To Start deciding who player one and player 2, "
			+ "player one will go first. Select the spot you want to place a piece by clicking on it. "
			+ "(In order to place a piece, it must be on the bottom of the board or have a piece under it)."
			+ " Continue until either the board is filled up (a tie) or one of the players has succeeded in "
			+ "connecting four of their color.";
	
	private String p1Name = "Red";
	private String p2Name = "Blue";
	private String p1Theme = "red";
	private String p2Theme = "blue";
	private String background = " -fx-background";
	private String buttonColor = "lightgrey";
	
	private TextField text;
	
	private Game connectFour;
	
	/*
	 *  Default Constructor Takes in a Game class as parameter as well as setting up the text box
	 */
	public GameController(Game game) {
		this.connectFour = game;
		
		// TextBox
		this.text = new TextField();
		this.text.setDisable(true);
		this.text.setFont(Font.font(16));
		this.text.setStyle("-fx-text-inner-color: black" );
	}
	
	/*
	 * Handles the button events for when a button is pressed
	 */
	public void buttonEvents(GameButton pressedButton) {
		pressedButton.setOnAction(new EventHandler<ActionEvent>() {
			
		@Override
		public void handle(ActionEvent event) {
			GameButton button = connectFour.placeButton(pressedButton);
				button.setDisable(true);
				button.pressed = true;
				connectFour.moveNum += 1;
				
				if(!connectFour.playingAI) {
					
					playerVSPlayer(button);
				} else {
					
					playerVSAI(button);
				}
			}
		});
	}

	/*
	 * Helper Method that Handles the game board if the player selected player VS. player on the welcome screen. 
	 * This is designed to be played with a second person.
	 */
	private void playerVSPlayer(GameButton button) {
			
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
	
	/*
	 * The variant of the game when the user clicks the player VS. AI option on the start menu. Its the same as PlayervsPlayer 
	 * but after the player places a piece the AI places a piece.
	 */
	private void playerVSAI(GameButton button) {
		
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
			
			playAI(button);
		}
	}
	
	/*
	 * Handles the AI, how it places pieces etc.
	 */
	private void playAI(GameButton button) {
		
		button = connectFour.placeButton(connectFour.ai.AIMoves());
		if(button != null && !connectFour.gameWon) {
			
			connectFour.moveNum += 1;
			button.setDisable(true);
			button.pressed = true;
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
	
	/*
	 * Method to handle the events for the MenuItems in the Themes
	 * menu within the MenuBar
	 */
	public void themeEvents(MenuItem theme1, MenuItem theme2, MenuItem theme3, MenuBar menubar, BorderPane root) {
		theme1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				background = "white";
				buttonColor = "lightgray";
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
				Scene howToPlayScene = new Scene(setHTP(), 350, 200);
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
						if(connectFour.playingAI) {
							
							connectFour.undoMove(buttonColor); 
							connectFour.setplayer(1);
							text.setText("Move Undone! Its " + p1Name +"'s turn!");
						}
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
	 * Getter method for text
	 */
	public TextField getText() {
		
		return this.text;
	}
	
	/*
	 * Helper Method for setting up the "how to play" box.
	 */
	private Label setHTP() {
		
		Label htp = new Label();
		htp.setPadding(new Insets(0, 10, 10, 10));
		htp.setMaxWidth(180);
		htp.setWrapText(true);
		htp.setText(howToPlayString);
		return htp;
	}
	
}
