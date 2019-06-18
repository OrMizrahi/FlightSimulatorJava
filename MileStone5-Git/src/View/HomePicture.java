package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HomePicture extends Canvas {

	public HomePicture() {
		// TODO Auto-generated constructor stub
	}
	
	public void ImportPicture(){
		GraphicsContext gc = getGraphicsContext2D();	
		Image Planeimg = null;
		try {
			Planeimg = new Image(new FileInputStream("./resources/airPlane1.jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(getHeight()+""+getWidth());
		gc.drawImage(Planeimg, 0, 0);
	}
}