public class Game {
	
	private GameButton[][] boardArr;
	private GameButton[] pressOrder;
	private int currPlayer;
	public int moveNum = 0;
	public boolean playingAI = false;
	public boolean gameWon = false;
	public AI ai;
	
	Game() {
		this.currPlayer = 1;
		this.boardArr = new GameButton[7][6];
		this.pressOrder = new GameButton[42];
		ai = new AI(boardArr, this);
	}
	
	/*
	 * Takes a button input and finds the lowest point in the column that it can go into. 
	 */
	public GameButton placeButton(GameButton clickedButton) {
		
		GameButton tempButton = validMove(clickedButton);
		pressOrder[moveNum] = tempButton; // Sets the button to the move number
		return tempButton;
	}
	
	/*
	 * Checks to find the valid move in a given column. lets the player click any column to place down a piece.
	 */
	GameButton validMove(GameButton button) {
		
		GameButton temp = null;
		
		for(int i = 0; i <= 5; i++) {
			if(boardArr[button.column][i].pressed == false) {
				
				temp = boardArr[button.column][i];
			} 
		}
		
		return temp;
	}

	/*
	 * Method that undoes the last move done on the game board
	 */
	public void undoMove(String buttonTheme) {
		
			this.moveNum--;
			GameButton tempButton = pressOrder[moveNum];
			tempButton.pressed = false;
			tempButton.player = 0;
			tempButton.setStyle("-fx-background-color: " + buttonTheme);
			tempButton.setDisable(false);
	}
	
	/*
	 * Checks if a win condition has returned true for the current board
	 */
	public boolean checkWin() {
		
		
		// Check Vertical Wins
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j< 3; j++) {
				if(boardArr[i][j].player == currPlayer &&
						boardArr[i][j].player == boardArr[i][j+1].player &&
						boardArr[i][j].player == boardArr[i][j+2].player &&
						boardArr[i][j].player == boardArr[i][j+3].player) {
				
					//System.out.print("Vert \n");
					return true;
				}
			}
		}
		
		// Check Horizontal Wins
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j< 6; j++) {
				
				if(boardArr[i][j].player == currPlayer &&
						boardArr[i][j].player == boardArr[i+1][j].player &&
						boardArr[i][j].player == boardArr[i+2][j].player &&
						boardArr[i][j].player == boardArr[i+3][j].player) {
					
					//System.out.print("Horiz \n");
					return true;
				}
			}
		}
		
		
		// Checks Positive diagonals
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j< 3; j++) { 
				
				if( boardArr[i][j].player == boardArr[i+1][j+1].player  && 
						boardArr[i+1][j+1].player == boardArr[i+2][j+2].player && 
						boardArr[i+2][j+2].player == boardArr[i+3][j+3].player && 
						boardArr[i][j].player == currPlayer) {
					
					//System.out.print("Pos Dia \n");
					return true;
					}
				}
			}
		
		// Checks Negative diagonals
		for(int i = 0; i < 4; i++) {
			for(int j = 5; j > 2; j--) { 
				if( boardArr[i][j].player == boardArr[i+1][j-1].player  && 
						boardArr[i][j].player == boardArr[i+2][j-2].player && 
						boardArr[i][j].player == boardArr[i+3][j-3].player && 
						boardArr[i][j].player == currPlayer) {
					
					//System.out.print("Neg Dia \n");
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Method that will check if all possible stops on the board have been filled
	 */
	public boolean checkTie() {
		
		if(moveNum >= 42) {
			return true;
		}
		return false;
	}
	
	/*
	 * Disables all buttons when a win condition is met, so that players can no longer press new buttons
	 */
	public void disableAllButtons() {
		
		for(int x = 0; x <7; x++) {
			for(int y = 0; y < 6; y++) {
				
				boardArr[x][y].setDisable(true);
			}
		}
		gameWon = true;
	}
	
	/*
	 * Enables all buttons when a win condition is met, so that players can no longer press new buttons
	 */
	public void enableAllButtons() {
		
		for(int x = 0; x <7; x++) {
			for(int y = 0; y < 6; y++) {
				
				if(boardArr[x][y].player == 0) {
					boardArr[x][y].pressed = false;
					boardArr[x][y].setDisable(false);
				}
			}
		}
		gameWon = false;
	}

	/*
	 * Resets the board back to its default state, this clears all wins and pieces on the board 
	 */
	public void newGameBoard(String buttonTheme) {
		for(int x = 0; x <7; x++) {
			for(int y = 0; y < 6; y++) {
				
				boardArr[x][y].pressed = false;
				boardArr[x][y].player = 0;
				boardArr[x][y].setStyle("-fx-background-color: " + buttonTheme);

				boardArr[x][y].setDisable(false);
			}
		}
		moveNum = 0;
		currPlayer = 1;
		gameWon = false;
	}
	
	/*
	 * Getters and Setter for the Private Variables
	 */
	public void setArr(int x, int y, GameButton button) {
		 boardArr[x][y] = button;
	 }
	public void setplayer(int turn) {
		this.currPlayer = turn;
	}
	public int getPlayer() {

		return this.currPlayer;
	}
	public void setStyle(String p1, String p2, String empty) {
		
		for(int x = 0; x <7; x++) {
			for(int y = 0; y < 6; y++) {
				
				if(boardArr[x][y].player == 0) {
					boardArr[x][y].setStyle("-fx-background-color: " + empty);
				} else if(boardArr[x][y].player == 1) {
					boardArr[x][y].setStyle("-fx-background-color: " + p1);
				} else {
					boardArr[x][y].setStyle("-fx-background-color: " + p2);
				}
			}
		}
	}
}
