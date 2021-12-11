
public class Game {
	
	private GameButton[][] boardArr;
	private GameButton[] pressOrder;
	private int currPlayer;
	public int moveNum = 0;
	
	Game() {
		this.currPlayer = 1;
		this.boardArr = new GameButton[7][6];
		this.pressOrder = new GameButton[42];
	}
	
	/*
	 * Method that checks if the pressed button is a valid move. This is done by checking if the
	 * pressed button was in the bottom row, or if a button underneath it was pressed. If the move was valid
	 * it is added into the array of moves.
	 */
	public boolean isValid(GameButton clickedButton) {
		
		if(clickedButton.col == 5 || boardArr[clickedButton.row][clickedButton.col + 1].pressed == true) {
			
			addPressedButton(clickedButton);
			return true;
		} 

		return false;
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
	 * Loops through the grid going columns then rows in a nested for loop. if 4 button pressed by the 
	 * current player are found this will return true. 
	 */
	private boolean checkHorizontal() {
		
		int count = 0;
		// Loops through the entire grid. if a row of 4 is found returns true
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j< 7; j++) {
				
				if(boardArr[j][i].player == currPlayer) {				   
					count++;
					if(count >= 4) {
						return true;
					}
				}
				else {
					count = 0;
				}
			}
		}
		return false;
	}
	
	/*
	 * Loops through the grid to see if there is a vertical row of 4 buttons pressed by the current player
	 * If there is, then true is returned. 
	 */
	private boolean checkVertical() {
		
		int count = 0;
		// Loops through the entire grid. if a row of 4 is found returns true
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j< 6; j++) {
				
				if(boardArr[i][j].player == currPlayer) {				   
					count++;
					if(count >= 4) {
						return true;
					}
				}
				else {
					count = 0;
				}
			}
		}
		return false;
	}
	
	/*
	 * Manually checks for every single possible diagonal win condition. might replace with a 2 for loops later
	 */
	private boolean checkDiagonal() {
		if(boardArr[3][0].player == boardArr[4][1].player && boardArr[4][1].player == boardArr[5][2].player && boardArr[5][2].player == boardArr[6][3].player && boardArr[6][3].player == currPlayer) {return true;}
		if(boardArr[2][0].player == boardArr[3][1].player && boardArr[3][1].player == boardArr[4][2].player && boardArr[4][2].player == boardArr[5][3].player && boardArr[5][3].player == currPlayer) {return true;}
		if(boardArr[1][0].player == boardArr[2][1].player && boardArr[2][1].player == boardArr[3][2].player && boardArr[3][2].player == boardArr[4][3].player && boardArr[4][3].player == currPlayer) {return true;}
		if(boardArr[0][0].player == boardArr[1][1].player && boardArr[1][1].player == boardArr[2][2].player && boardArr[2][2].player == boardArr[3][3].player && boardArr[3][3].player == currPlayer) {return true;}
		
		if(boardArr[3][1].player == boardArr[4][2].player && boardArr[4][2].player == boardArr[5][3].player && boardArr[5][3].player == boardArr[6][4].player && boardArr[6][4].player == currPlayer) {return true;}
		if(boardArr[2][1].player == boardArr[3][2].player && boardArr[3][2].player == boardArr[4][3].player && boardArr[4][3].player == boardArr[5][4].player && boardArr[5][4].player == currPlayer) {return true;}
		if(boardArr[1][1].player == boardArr[2][2].player && boardArr[2][2].player == boardArr[3][3].player && boardArr[3][3].player == boardArr[4][4].player && boardArr[4][4].player == currPlayer) {return true;}
		if(boardArr[0][1].player == boardArr[1][2].player && boardArr[1][2].player == boardArr[2][3].player && boardArr[2][3].player == boardArr[3][4].player && boardArr[3][4].player == currPlayer) {return true;}
		
		if(boardArr[3][2].player == boardArr[4][3].player && boardArr[4][3].player == boardArr[5][4].player && boardArr[5][4].player == boardArr[6][5].player && boardArr[6][5].player == currPlayer) {return true;}
		if(boardArr[2][2].player == boardArr[3][3].player && boardArr[3][3].player == boardArr[4][4].player && boardArr[4][4].player == boardArr[5][5].player && boardArr[5][5].player == currPlayer) {return true;}
		if(boardArr[1][2].player == boardArr[2][3].player && boardArr[2][3].player == boardArr[3][4].player && boardArr[3][4].player == boardArr[4][5].player && boardArr[4][5].player == currPlayer) {return true;}
		if(boardArr[0][2].player == boardArr[1][3].player && boardArr[1][3].player == boardArr[2][4].player && boardArr[2][4].player == boardArr[3][5].player && boardArr[3][5].player == currPlayer) {return true;}
		
		
		if(boardArr[3][0].player == boardArr[2][1].player && boardArr[2][1].player == boardArr[1][2].player && boardArr[1][2].player == boardArr[0][3].player && boardArr[0][3].player == currPlayer) {return true;}
		if(boardArr[4][0].player == boardArr[3][1].player && boardArr[3][1].player == boardArr[2][2].player && boardArr[2][2].player == boardArr[1][3].player && boardArr[1][3].player == currPlayer) {return true;}
		if(boardArr[5][0].player == boardArr[4][1].player && boardArr[4][1].player == boardArr[3][2].player && boardArr[3][2].player == boardArr[2][3].player && boardArr[2][3].player == currPlayer) {return true;}
		if(boardArr[6][0].player == boardArr[5][1].player && boardArr[5][1].player == boardArr[4][2].player && boardArr[4][2].player == boardArr[3][3].player && boardArr[3][3].player == currPlayer) {return true;}
		
		if(boardArr[3][1].player == boardArr[2][2].player && boardArr[2][2].player == boardArr[1][3].player && boardArr[1][3].player == boardArr[0][4].player && boardArr[0][4].player == currPlayer) {return true;}
		if(boardArr[4][1].player == boardArr[3][2].player && boardArr[3][2].player == boardArr[2][3].player && boardArr[2][3].player == boardArr[1][4].player && boardArr[1][4].player == currPlayer) {return true;}
		if(boardArr[5][1].player == boardArr[4][2].player && boardArr[4][2].player == boardArr[3][3].player && boardArr[3][3].player == boardArr[2][4].player && boardArr[2][4].player == currPlayer) {return true;}
		if(boardArr[6][1].player == boardArr[5][2].player && boardArr[5][2].player == boardArr[4][3].player && boardArr[4][3].player == boardArr[3][4].player && boardArr[3][4].player == currPlayer) {return true;}
		
		if(boardArr[3][2].player == boardArr[2][3].player && boardArr[2][3].player == boardArr[1][4].player && boardArr[1][4].player == boardArr[0][5].player && boardArr[0][5].player == currPlayer) {return true;}
		if(boardArr[4][2].player == boardArr[3][3].player && boardArr[3][3].player == boardArr[2][4].player && boardArr[2][4].player == boardArr[1][5].player && boardArr[1][5].player == currPlayer) {return true;}
		if(boardArr[5][2].player == boardArr[4][3].player && boardArr[4][3].player == boardArr[3][4].player && boardArr[3][4].player == boardArr[2][5].player && boardArr[2][5].player == currPlayer) {return true;}
		if(boardArr[6][2].player == boardArr[5][3].player && boardArr[5][3].player == boardArr[4][4].player && boardArr[4][4].player == boardArr[3][5].player && boardArr[3][5].player == currPlayer) {return true;}
		
		return false;
	}
	
	/*
	 * Disables all buttons when a win condition is met, so that players can no longer press new buttons
	 */
	public void disableAllButtons() {
		
		for(int x = 0; x <7; x++) {
			for(int y = 0; y < 6; y++) {
				
				boardArr[x][y].pressed = true;
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
	public GameButton getArr(int x, int y) {
		 
		 return this.boardArr[x][y];
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
