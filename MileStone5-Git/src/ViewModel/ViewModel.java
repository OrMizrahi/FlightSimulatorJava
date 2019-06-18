package ViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.regex.Pattern;

import Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel extends Observable implements Observer  {
	
	Model m;
	public String mapToText;
	public StringProperty ip,port,mapSolution,script,mapIp,mapPort;
    public DoubleProperty rudder,throttle,aileron,elevator;
    public IntegerProperty destX,destY,planeY,planeX;
	
	public ViewModel(Model m) {
		this.m=m;
		port=new SimpleStringProperty();
		ip=new SimpleStringProperty();
		mapSolution= new SimpleStringProperty();
		script=new SimpleStringProperty();
		rudder = new SimpleDoubleProperty();
		throttle = new SimpleDoubleProperty();
		aileron  = new SimpleDoubleProperty();
		elevator = new SimpleDoubleProperty();
		destX = new SimpleIntegerProperty();
		destY = new SimpleIntegerProperty();
		planeX = new SimpleIntegerProperty();
		planeY = new SimpleIntegerProperty();
		mapPort=new SimpleStringProperty();
		mapIp=new SimpleStringProperty();
	}
	
	public void interpret() {
		m.interpret(script.get().split("\n"));
	}
	
	public void connect() {
		m.connect(ip.get(),Integer.parseInt(port.get()));
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("Connected")) {
			
		}
		else if(arg.equals("Interpreted")) {

		}
		else {
			mapSolution.set(m.getMapSolution());
		    setChanged();
		    notifyObservers("done calculting map"); //sending to mainWindowLogic
		}
	}

	public void moveRudderSlider() {
		
			m.setRudder(rudder.get());
	}

	public void close() {
		m.close();
	}
	
	public void moveThrottleSlider() {
		
			m.setThrottle(throttle.get());		
	}

	public void moveAileron() {
		m.setAileron(aileron.get());
	}

	public void moveElevator() {
		m.setElevator(elevator.get());
		
	}

	public int[][] convert(File chosen) {
		
		int [][] arr = new int [14][16];
		List<String[]> lst = new ArrayList<String[]>();
		
		try {
			Scanner s = new Scanner(chosen);
			mapToText = (s.useDelimiter("\\A").next().trim());
			
			s = new Scanner(chosen);
			while(s.hasNextLine()) {
				lst.add(s.nextLine().split(","));
			}
			
			for(int i = 0;i<14;i++)
				for(int j=0; j<16;j++) {
				arr[i][j] = Integer.parseInt(lst.get(i)[j]);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("coudlnt open CSV file");
		}
		return arr;
	}
	
	public void connectToMapServer() {
		m.connectToMapServer(mapIp.get(),Integer.parseInt(mapPort.get()),mapToText,planeX.get(),planeY.get(),destY.get(),destX.get());
	}

	public void calculateMap() {
		if(m.getCalculateStatus() == true) {
			m.calculateMap(mapToText, planeX.get(), planeY.get(), destY.get(), destX.get());
		}
	}

	
}
