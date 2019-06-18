package View;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MapLogic extends Canvas {
	
	int[][] map;
	int planeX,planeY;
	double blockWidth,blockHeight;
	
	public void setMapData(int[][] map) {
		this.map=map;
		drawMap(0,0,428,355);
	}
	
	public void drawMap(int planeX,int planeY,int destX,int destY ) {
	
		double width = getWidth();
		double height = getHeight();
		blockWidth = width/map[0].length;
		blockHeight = height/map.length;
		
		GraphicsContext gc = getGraphicsContext2D();
		
		for(int i=0; i<map.length;i++) 
			for(int j=0;j< map[i].length;j++){
				                                    //lets say 15 is the greeniest, 0 is reddest
				/*
				 * if(map[i][j] >7) {
				 * 
				 * gc.setFill(new Color(0,(double)map[i][j]/15,0.2,1));
				 * gc.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight); } else {
				 * 
				 * gc.setFill(new Color((double)(1-map[i][j]/15),0,0.2,1));
				 * gc.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight); }
				 */
				switch (map[i][j]) {
				case 0:
					gc.setFill(Color.web("#f90000"));
					break;
				case 1:
					gc.setFill(Color.web("#f9462a"));
					break;
				case 2:
					gc.setFill(Color.web("#f46049"));
					break;
				case 3:
					gc.setFill(Color.web("#ed8c47"));
					break;
				case 4:
					gc.setFill(Color.web("#e8d64e"));
					break;
				case 5:
					gc.setFill(Color.web("#e4f791"));
					break;
				case 6:
					gc.setFill(Color.web("#d7f455"));
					break;
				case 7:
					gc.setFill(Color.web("#dee050"));
					break;
				case 8:
					gc.setFill(Color.web("#93bf2d"));
					break;
				case 9:
					gc.setFill(Color.web("#a2ce8e"));
					break;
				case 10:
					gc.setFill(Color.web("#9dd882"));
					break;
				case 11:
					gc.setFill(Color.web("#82c663"));
					break;
				case 12:
					gc.setFill(Color.web("#0aaf26"));
					break;
				case 13:
					gc.setFill(Color.web("#15a82e"));
					break;
				case 14:
					gc.setFill(Color.web("#099e22"));
					break;
				case 15:
					gc.setFill(Color.web("#06812b"));
					break;
				default:
					break;
				}
				gc.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight);
				gc.setFill(Color.BLACK);
				gc.fillText(""+map[i][j],j*blockWidth +4 , i*blockHeight + blockHeight -4 );
			}
	try {
		Image plane = new Image(new FileInputStream("./resources/plane.png"));
		Image dest = new Image(new FileInputStream("./resources/X.png"));
		gc.drawImage(plane,planeX,planeY);
		gc.drawImage(dest, destX, destY);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
}

public void drawPath(String path) {
		
		GraphicsContext gc = getGraphicsContext2D();
		int i=planeX;
		int j=planeY;
		gc.setFill(Color.BLACK);
		String[] pathMoves=path.split(",");
		for (String move : pathMoves) {
			if(move.equals("Right")) {
				++i;
				gc.fillRect(i *blockWidth, j * blockHeight + blockHeight/3, blockWidth/2, blockHeight/6);
			}
			else if(move.equals("Down")) {
				++j;
				gc.fillRect(i*blockWidth + blockWidth/3, j * blockHeight, blockWidth/6, blockHeight/2);
			}
			else if(move.equals("Left")) {
				--i;
				gc.fillRect(i* blockWidth, j * blockHeight + blockHeight/3, blockHeight/2, blockHeight/6);
			}
			else if(move.equals("Up")) {
				--j;
				gc.fillRect(i* blockWidth + blockWidth/3, j * blockHeight, blockWidth/6, blockHeight/2);

			}
		}
	}		
    
public void movePlane(int x, int y) {
	try {
		Image img = new Image(new FileInputStream("./resources/plane.png"));
		GraphicsContext gc = getGraphicsContext2D();
		//redraw(max); // redraw the map
		gc.drawImage(img, x, y); // draw plane
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void drawMapwithoutplane(int destX,int destY ) {
	
	double width = getWidth();
	double height = getHeight();
	blockWidth = width/map[0].length;
	blockHeight = height/map.length;
	
	GraphicsContext gc = getGraphicsContext2D();
	
	for(int i=0; i<map.length;i++) 
		for(int j=0;j< map[i].length;j++){
			                                    //lets say 15 is the greeniest, 0 is reddest
			/*
			 * if(map[i][j] >7) {
			 * 
			 * gc.setFill(new Color(0,(double)map[i][j]/15,0.2,1));
			 * gc.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight); } else {
			 * 
			 * gc.setFill(new Color((double)(1-map[i][j]/15),0,0.2,1));
			 * gc.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight); }
			 */
			switch (map[i][j]) {
			case 0:
				gc.setFill(Color.web("#f90000"));
				break;
			case 1:
				gc.setFill(Color.web("#f9462a"));
				break;
			case 2:
				gc.setFill(Color.web("#f46049"));
				break;
			case 3:
				gc.setFill(Color.web("#ed8c47"));
				break;
			case 4:
				gc.setFill(Color.web("#e8d64e"));
				break;
			case 5:
				gc.setFill(Color.web("#e4f791"));
				break;
			case 6:
				gc.setFill(Color.web("#d7f455"));
				break;
			case 7:
				gc.setFill(Color.web("#dee050"));
				break;
			case 8:
				gc.setFill(Color.web("#93bf2d"));
				break;
			case 9:
				gc.setFill(Color.web("#a2ce8e"));
				break;
			case 10:
				gc.setFill(Color.web("#9dd882"));
				break;
			case 11:
				gc.setFill(Color.web("#82c663"));
				break;
			case 12:
				gc.setFill(Color.web("#0aaf26"));
				break;
			case 13:
				gc.setFill(Color.web("#15a82e"));
				break;
			case 14:
				gc.setFill(Color.web("#099e22"));
				break;
			case 15:
				gc.setFill(Color.web("#06812b"));
				break;
			default:
				break;
			}
			gc.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight);
			gc.setFill(Color.BLACK);
			gc.fillText(""+map[i][j],j*blockWidth +4 , i*blockHeight + blockHeight -4 );
		}
try {
	
	Image dest = new Image(new FileInputStream("./resources/X.png"));
	
	gc.drawImage(dest, destX, destY);
	} catch (FileNotFoundException e) {
	e.printStackTrace();
	}
	}
}