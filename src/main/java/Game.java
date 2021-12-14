import java.util.Random;

public class Game {
	
	private GameButton[][] boardArr;
	private GameButton[] pressOrder;
	private int currPlayer;
	public int tempCurrPlayer;
	public int moveNum = 0;
	public boolean playingAI = false;
	public boolean gameWon = false;
	
	Game() {
		this.currPlayer = 1;
		this.boardArr = new GameButton[7][6];
		this.pressOrder = new GameButton[42];
	}
	
	/*
	 * Takes a button input and finds the lowest point in the column that it can go into. 
	 */
	public GameButton placeButton(GameButton clickedButton) {
		
		GameButton tempButton = validMove(clickedButton);
		pressOrder[moveNum] = tempButton; // Sets the button to the move number
		return tempButton;
	}
	
	
	private GameButton validMove(GameButton button) {
		
		GameButton temp = null;
		
		for(int i = 0; i <= 5; i++) {
			if(boardArr[button.column][i].pressed == false) {
				
				temp = boardArr[button.column][i];
			} 
		}
		
		return temp;
	}
	
	
	private GameButton checkWinAI(GameButton button) {
		

			button.player = getPlayer();
			if(checkWin()) {
				
				//System.out.print("Win Found \n");
				return button;
			} 
			
		return null;
	}
	
	
	private GameButton checkLoseAI(GameButton button) {
		
		if(getPlayer() == 2) {
			
			tempCurrPlayer = 2;
			setplayer(1);
			button.player = 1;
			if(checkWin()) {
				
				System.out.print("Found lose for player 2");
				return button;
			}
		} else if(getPlayer() == 1) {
			
			tempCurrPlayer = 2;
			setplayer(2);
			button.player = 2;
			if(checkWin()) {
				
				System.out.print("Found lose for player 2");
				return button;
			}
		}

		return null;
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
	 * Basic AI Picks a random number from 0-6 and places it into a row 
	 */
	public GameButton AI() {
		Random rand = new Random();
		int min = 0;
		int max = 6;
		int randomRange = rand.nextInt(max - min + 1) + min;
		GameButton tempButton = null;
		GameButton winButton = null;
		GameButton preventLoseButton = null;
		
		for(int i = 0; i != max; i++) {
			
			if(validMove(boardArr[i][0]) != null) {
				
				winButton = checkWinAI(validMove(boardArr[i][0]));
				validMove(boardArr[i][0]).player = 0;
				if(winButton != null) {
					
					break;
				}
			}
		}
		
		for(int i = 0; i != max; i++) {
					
				if(validMove(boardArr[i][0]) != null) {
						
				preventLoseButton = checkLoseAI(validMove(boardArr[i][0]));
				setplayer(tempCurrPlayer);
				validMove(boardArr[i][0]).player = 0;
				if(preventLoseButton != null) {
					
					break;
				}
			}
		}
		
		if(winButton != null) {
			tempButton = winButton;
		} else if (preventLoseButton != null) {
			tempButton = preventLoseButton;
		} else {
			//System.out.print("TempButton was Null \n");
			while(boardArr[randomRange][0].pressed == true) {
				
				// System.out.print("Looking for not filled column... Ran =" + randomRange +"\n");
				randomRange++; 
				
				if(randomRange == 7) {
					randomRange = 0;
				}
			}
			// System.out.print("RandRange =" + randomRange +"\n");
			tempButton = boardArr[randomRange][0];
		}

		return tempButton;
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
