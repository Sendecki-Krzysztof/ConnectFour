import java.util.Random;

public class Game {
	
	private GameButton[][] boardArr;
	private GameButton[] pressOrder;
	private int currPlayer;
	public int moveNum = 0;
	public boolean playingAI = false;
	
	Game() {
		this.currPlayer = 1;
		this.boardArr = new GameButton[7][6];
		this.pressOrder = new GameButton[42];
	}
	
	public GameButton placeButton(GameButton clickedButton) {
		
		GameButton tempButton = null;
		
		for(int i = 0; i <= 5; i++) {
			if(boardArr[clickedButton.row][i].pressed == false) {
				
				tempButton = boardArr[clickedButton.row][i];
			} 
		}
		addPressedButton(tempButton);
		return tempButton;
	}

	/*
	 * Private Method for adding the pressed button into the press order array
	 */
	private void addPressedButton(GameButton button) {
		
		pressOrder[moveNum] = button;
	}
	
	/*
	 *  Method that undoes the last move done on the game board
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
		
		return (checkVertical() || checkHorizontal() || checkDiagonal());
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
	 *  Helper function of checkWin(). Loops thru the 2d array until it can find a 
	 *  column with 4 of the same player piece in a row.
	 */
	private boolean checkVertical() {
		
		// Loops through the entire grid. if a row of 4 is found returns true
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j< 3; j++) {
				if(boardArr[i][j].player == currPlayer &&
						boardArr[i][j].player == boardArr[i][j+1].player &&
						boardArr[i][j].player == boardArr[i][j+2].player &&
						boardArr[i][j].player == boardArr[i][j+3].player) {
				
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Helper function of checkWin(). Loops thru the 2d array until it can find a 
	 * row with 4 of the sane player piece in a column.
	 */
	private boolean checkHorizontal() {
		
		// Loops through the entire grid. if a row of 4 is found returns true
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j< 6; j++) {
				
				if(boardArr[i][j].player == currPlayer &&
						boardArr[i][j].player == boardArr[i+1][j].player &&
						boardArr[i][j].player == boardArr[i+2][j].player &&
						boardArr[i][j].player == boardArr[i+3][j].player) {
					
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Helper function of checkWin(). Checks the Diagonals and if there is a valid placement of one. 
	 */
	private boolean checkDiagonal() {
		
		// Checks Positive diagonals
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j< 3; j++) { 
				
				if( boardArr[i][j].player == boardArr[i+1][j+1].player  && 
						boardArr[i+1][j+1].player == boardArr[i+2][j+2].player && 
						boardArr[i+2][j+2].player == boardArr[i+3][j+3].player && 
						boardArr[i][j].player == currPlayer) {
					
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
					
					return true;
				}
				
			}
		}
		
		return false;
		}
	
	
	public GameButton AI() {
		Random rand = new Random();
		int min = 0;
		int max = 6;
		int randomRange = rand.nextInt(max - min + 1) + min;
		while(boardArr[randomRange][0].pressed == true) {
			
			// System.out.print("Looking for not filled column... Ran =" + randomRange +"\n");
			randomRange++; 
			
			if(randomRange == 7) {
				randomRange = 0;
			}
		}
		// System.out.print("RandRange =" + randomRange +"\n");
		GameButton tempButton = boardArr[randomRange][0];
		return tempButton;
	}

	public void AIButtonPress(GameButton button) {
		
		addPressedButton(button);
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
