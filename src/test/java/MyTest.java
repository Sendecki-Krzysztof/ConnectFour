import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyTest {
	
	@Test
	void checkTieFirstMove() {
		 
		Game test = new Game();
		
		assertEquals(test.checkTie(), false, "Incorrectly returned that the game has been tied on the first move");
	}
	@Test
	void checkTieLastMove() {
		
		Game test = new Game();
		
		test.moveNum = 42;
		assertEquals(test.checkTie(), true, "Incorrectly returned that the game has not been tied on the last move");
	}
	@Test
	void checkTieFullGame() {
		
		Game test = new Game();
		
		for(int i = 0; i >= 42; i++) {
			
			test.moveNum = i;
			assertEquals(test.moveNum, i, "Incorrect  move number returned");
			if(i >= 42) {
				assertEquals(test.checkTie(), true, "Incorrectly returned that the game has not been tied on the last move");
			} else {
			assertEquals(test.checkTie(), false, "Incorrectly returned that the game was tied on the " + i + "th move");
			}
		} 
	}
	@Test
	void checkTiePastBoardSize() {
		
		Game test = new Game();
		
		test.moveNum = 45;
		assertEquals(test.checkTie(), true, "Incorrectly returned that the game has not been tied passed the max board limit");
	}
	@Test
	void checkWinStart() {
		
		Game test = new Game();
		
		assertEquals(test.checkWin(), false);
	}
	
	
}
