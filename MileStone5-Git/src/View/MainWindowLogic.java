package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ViewModel.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowLogic implements Observer {
	
	
	ViewModel vm;
	@FXML
	Circle smallCircle,bigCircle; 
	@FXML
	TextArea script;
	@FXML
	Button HomeButton,aboutButton;
	@FXML
	AnchorPane ManualPane,HomePane,MapPane,AutoPilotPane,aboutPane;
	@FXML
	Slider rudderSlider,throttleSlider;
	@FXML
	MapLogic mapLogic;
	@FXML
	HomePicture homePicture;
	
	DoubleProperty aileron,elevator;
	IntegerProperty destX,destY,planeY,planeX;
	StringProperty mapSolution;
	
	 public MainWindowLogic() {
		aileron = new SimpleDoubleProperty();
		elevator = new SimpleDoubleProperty();
		destX = new  SimpleIntegerProperty();
		destY = new SimpleIntegerProperty();
		planeX = new  SimpleIntegerProperty();
		planeY = new SimpleIntegerProperty();
		mapSolution = new SimpleStringProperty();
		
	}
	 
	public void setViewModel(ViewModel vm) {
		this.vm=vm;
		vm.script.bind(script.textProperty());
		vm.rudder.bind(this.rudderSlider.valueProperty());
		vm.throttle.bind(this.throttleSlider.valueProperty());
		vm.aileron.bind(aileron);
		vm.elevator.bind(elevator);
		vm.destX.bind(destX);
		vm.destY.bind(destY);
		vm.planeX.bind(planeX);
		vm.planeY.bind(planeY);
		mapSolution.bind(vm.mapSolution);
	}
	
	public void HomeShower() {
		HomePane.setVisible(true);
		MapPane.setVisible(true);
		ManualPane.setVisible(false);
		AutoPilotPane.setVisible(false);
		aboutPane.setVisible(false);
	}
	
	public void ManualShower() {
		HomePane.setVisible(false);
		MapPane.setVisible(false);
		ManualPane.setVisible(true);
		AutoPilotPane.setVisible(false);
		aboutPane.setVisible(false);
	}
	public void AutoPilotShower() {
		HomePane.setVisible(false);
		MapPane.setVisible(false);
		ManualPane.setVisible(false);
		AutoPilotPane.setVisible(true);
		aboutPane.setVisible(false);
	}
	
	public void aboutShower() {
		HomePane.setVisible(false);
		MapPane.setVisible(false);
		ManualPane.setVisible(false);
		AutoPilotPane.setVisible(false);
		aboutPane.setVisible(true);
		
		homePicture.ImportPicture();
	}
	
	public void PopupHandler() throws IOException {
	
		FXMLLoader fxl = new FXMLLoader(getClass().getResource("PopUpWindow.fxml"));
		AnchorPane root = (AnchorPane)fxl.load();
		Scene scene = new Scene(root,250,250);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
	
		PopUpLogic pul =fxl.getController(); //view
		pul.setViewModel(vm);
		vm.addObserver(pul);
		
	}
	

	public void mapPopupHandler() throws IOException {
		
		FXMLLoader fxl = new FXMLLoader(getClass().getResource("mapPopUp.fxml"));
		AnchorPane root = (AnchorPane)fxl.load();
		Scene scene = new Scene(root,250,250);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
	
		mapPopUpLogic mpul =fxl.getController(); //view
		mpul.setViewModel(vm);
		vm.addObserver(this);
		
		
	}
    public void loadScript() {
		
		FileChooser fc = new FileChooser();
		Stage primaryStage = new Stage();
		fc.setTitle("open Script");
		fc.setInitialDirectory(new File("./resources"));
		File chosen = fc.showOpenDialog(null);
		
		showScript(chosen);
	}
	
	public void loadMap() {
		FileChooser fc = new FileChooser();
		Stage primaryStage = new Stage();
		fc.setTitle("open Map");
		fc.setInitialDirectory(new File("./resources"));
		File chosen = fc.showOpenDialog(null);
		
		int[][] arr = vm.convert(chosen);
		mapLogic.setMapData(arr);
	}
	
	public void showScript(File f) {
		script.setWrapText(true);
		try {
		
			Scanner s = new Scanner(f);
			while(s.hasNextLine()) {
				script.appendText(s.nextLine()+"\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void interpret() {
		vm.interpret();
	}

	
	public void moveJoyStick() {
		
			smallCircle.setOnMouseDragged((MouseEvent e)->{
				
				if(e.getX()<(bigCircle.getRadius()-smallCircle.getRadius())
				&& e.getX()> -(bigCircle.getRadius()-smallCircle.getRadius())
				&& e.getY()<(bigCircle.getRadius()-smallCircle.getRadius())
				&& e.getY()> -(bigCircle.getRadius()-smallCircle.getRadius()))
				 {
					smallCircle.setCenterX(e.getX());
					smallCircle.setCenterY(e.getY());
					aileron.set(e.getX()/bigCircle.getRadius());
					elevator.set(e.getY()/bigCircle.getRadius());
					vm.moveAileron();
					vm.moveElevator();
				}
				});
			
			smallCircle.setOnMouseReleased((MouseEvent e)->{
				smallCircle.setCenterX(0);
				smallCircle.setCenterY(0);
			});
	}
	
	public void close() {
		
		vm.close();
	}
	
	public void moveRudderSlider() {
		
		vm.moveRudderSlider();	
	}
	
	public void moveThrottleSlider() {
		
		vm.moveThrottleSlider();	
	}

	
	public void setDestOnMap() {
		mapLogic.setOnMouseClicked((MouseEvent e)->{
			
			destX.set((int)e.getX());
			destY.set((int)e.getY());
			mapLogic.drawMap(planeX.get(),planeY.get(),destX.get(),destY.get());
			//System.out.println("fixed X:"+(int)((float)destX.get()/451*16)+", fixed Y:"+(int)((float)destY.get()/383*14));
			destX.set((int)((float)destX.get()/451*16));
			destY.set((int)((float)destY.get()/383*14));
			vm.calculateMap();
			
		});
	}
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		
	new Thread(()->{
			destX.set((int)((float)destX.get()*451/16));
			destY.set((int)((float)destY.get()*383/14));
		int cDown = 0,cRight = 0;
		if(arg.equals("done calculting map")) {   //came from viewModel
			mapLogic.drawPath(mapSolution.get());
			String[] sol = mapSolution.get().split(",");
			for (String string : sol) {
				if(string.equals("Down")) {
					cDown++;
					try {
						Thread.sleep(150000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					}
					mapLogic.drawMapwithoutplane(destX.get(), destY.get());
					mapLogic.movePlane((int)(float)451/16*cRight,(int)(float)383/15*cDown);
					//planeX.set((int)(float)451/16*cRight);
					//planeY.set((int)(float)383/15*cDown);
					mapLogic.drawPath(mapSolution.get());
					
					
				}
					if(string.equals("Right")) {
						cRight++;
						try {
							Thread.sleep(150000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						//	e.printStackTrace();
						}
					mapLogic.drawMapwithoutplane(destX.get(), destY.get());
					mapLogic.movePlane((int)(float)451/16*cRight,(int)(float)383/15*cDown);
					//planeX.set((int)(float)451/16*cRight);
					//planeY.set((int)(float)383/15*cDown);
					mapLogic.drawPath(mapSolution.get());
					
					
				}
					if(string.equals("Left")) {
						cRight--;
						try {
							Thread.sleep(150000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					mapLogic.drawMapwithoutplane(destX.get(), destY.get());
					mapLogic.movePlane((int)(float)451/16*cRight,(int)(float)383/15*cDown);
					//planeX.set((int)(float)451/16*cRight);
					//planeY.set((int)(float)383/15*cDown);
					mapLogic.drawPath(mapSolution.get());
					
					
				}
					
					if(string.equals("Up")) {
						cDown--;
						try {
							Thread.sleep(150000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
						mapLogic.drawMapwithoutplane(destX.get(), destY.get());
						mapLogic.movePlane((int)(float)451/16*cRight,(int)(float)383/15*cDown);
						//planeX.set((int)(float)451/16*cRight);
						//planeY.set((int)(float)383/15*cDown);
						mapLogic.drawPath(mapSolution.get());
						
						
					}
			}
		}
		}).start();
		
	}

	
}