import java.util.Random;

public class AI {
	private GameButton[][] boardArr;
	private Game game;
	private int tempCurrPlayer;

	AI(GameButton[][] board, Game connectFour){
		
		this.boardArr = board;
		this.game = connectFour;
	}
	
	/*
	 * Basic AI Picks a random number from 0-6 and places it into a row 
	 */
	public GameButton AIMoves() {
		Random rand = new Random();
		int min = 0;
		int max = 6;
		int randomRange = rand.nextInt(max - min + 1) + min;
		GameButton tempButton = null;
		GameButton winButton = null;
		GameButton preventLoseButton = null;
		
		// Checks if the AI can win in the next turn for every column
		for(int i = 0; i != max; i++) {
			
			if(game.validMove(boardArr[i][0]) != null) {
				
				winButton = checkWinAI(game.validMove(boardArr[i][0]));
				game.validMove(boardArr[i][0]).player = 0;
				if(winButton != null) {
					
					break;
				}
			}
		}
		
		// Checks if the AI can lose in the next turn for every column
		for(int i = 0; i != max; i++) {
					
				if(game.validMove(boardArr[i][0]) != null) {
						
				preventLoseButton = checkLoseAI(game.validMove(boardArr[i][0]));
				game.setplayer(tempCurrPlayer);
				game.validMove(boardArr[i][0]).player = 0;
				if(preventLoseButton != null) {
					
					break;
				}
			}
		}
		// If the AI can win
		if(winButton != null) { 
			
			tempButton = winButton; // Place that piece first
		} // If the AI will lose, but can't win
		else if (preventLoseButton != null) { 
			
			tempButton = preventLoseButton; // Place to avoid losing
			
		} // If there is no possible lose or win this turn, place in a random slot 
		else { 
			
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
	 * Helper Method to AIMoves, Checks if the AI can win in the next turn. IF it can its sent to the back 
	 * to AImoves and the AI reacts accordingly
	 */
	private GameButton checkWinAI(GameButton button) {
		

			button.player = game.getPlayer();
			if(game.checkWin()) {
				
				//System.out.print("Win Found \n");
				return button;
			} 
			
		return null;
	}
	
	/*
	 *  Helper method to AIMoves, it checks if the AI will lose the game in a turn to the player. 
	 *  If a lose is found sends it to the AImoves method and the AI reacts accordingly
	 */
	private GameButton checkLoseAI(GameButton button) {
		
		if(game.getPlayer() == 2) {
			
			tempCurrPlayer = 2;
			game.setplayer(1);
			button.player = 1;
			if(game.checkWin()) {
				
				System.out.print("Found lose for player 2");
				return button;
			}
		} else if(game.getPlayer() == 1) {
			
			tempCurrPlayer = 2;
			game.setplayer(2);
			button.player = 2;
			if(game.checkWin()) {
				
				System.out.print("Found lose for player 2");
				return button;
			}
		}

		return null;
	}
	
}
