import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class GameButton extends Button {
	
	public int row;
	public int col;
	public int player;
	public boolean pressed;

	GameButton(int row, int col) {
		this.row = row;
		this.col = col;
		this.pressed = false;
		this.player = 0;
		this.setShape(new Circle(10));
	}
}