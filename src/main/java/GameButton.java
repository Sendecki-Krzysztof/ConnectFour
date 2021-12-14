import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class GameButton extends Button {
	
	public int column;
	public int row;
	public int player;
	public boolean pressed;

	GameButton(int col, int row) {
		this.column = col;
		this.row = row;
		this.pressed = false;
		this.player = 0;
		this.setShape(new Circle(10));
	}
}