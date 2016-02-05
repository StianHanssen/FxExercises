package fxCode;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StickMan {
	public void draw(GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.WHITE);
        gc.setLineWidth(1);
        
      //Face
        gc.strokeLine(175, 110, 187, 125);
        gc.strokeLine(187, 110, 175, 125);
        gc.strokeLine(203, 110, 215, 125);
        gc.strokeLine(215, 110, 203, 125);
        gc.strokeOval(177, 137, 35, 35);
        gc.fillRect(160, 145, 230, 137);
        gc.strokeOval(150, 80, 90, 80);
        
        //Body
        gc.strokeLine(195, 50, 195, 79);
        gc.strokeLine(195, 270, 195, 160);
        gc.strokeLine(195, 180, 150, 220);
        gc.strokeLine(195, 270, 150, 310);
        gc.strokeLine(195, 180, 240, 220);
        gc.strokeLine(195, 270, 240, 310);
        
        //Contraption
        gc.strokeLine(80, 50, 195, 50);
        gc.strokeLine(80, 350, 80, 50);
        gc.strokeLine(30, 350, 250, 350);
	}
}
