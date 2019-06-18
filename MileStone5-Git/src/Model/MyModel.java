package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import ServerClient.Client;
import ServerClient.Server;
import algorithms.BestFirstSearch;
import algorithms.MatrixProblem;
import interpreter.MyInterpreter;
import server_side.FileCacheManager;
import server_side.MySerialServer;
import server_side.SearchableClientHandler;
import server_side.SearcherSolver;
import algorithms.Position;

public class MyModel extends Observable implements Model  {

	String mapSolution;
	MySerialServer mapServer;
	String mapServerIp;
	int mapServerPort;
	String[] path;
	Client c;
	MyInterpreter interpreter;
	boolean connectStatus;
	boolean calculateStatus;
	Server s;
	
	public MyModel(MyInterpreter interpreter,Client c) {
		this.c=c;
		this.interpreter=interpreter;
		connectStatus = false;
		calculateStatus = false;
	}
	@Override
	public void connect(String ip, int port) {
		c.connect(ip, port);
		connectStatus = true;
		System.out.println("Connected to the server");
		setChanged();
		notifyObservers("Connected");
	}

	
	@Override
	public void interpret(String[] lines) {
		
		new Thread(()->{
		interpreter.interpret(lines);
		connectStatus = true;
		setChanged();
		notifyObservers("Interpreted");
		}).start();
	}


	@Override
	public void close() {
		
		s.stop();
	}
	@Override
	public void setRudder(double d) {
		
		if(connectStatus == false)
			this.connect("127.0.0.1",5402);
		
			c.setPathValue("/controls/flight/rudder", d);
	}
	@Override
	public void setThrottle(double d) {
		if(connectStatus == false)
			this.connect("127.0.0.1",5402);
		
	    	c.setPathValue("controls/engines/current-engine/throttle", d);	
	}
	
	@Override
	public void setAileron(double d) {
		
		if(connectStatus == false)
			this.connect("127.0.0.1",5402);
		
	    	c.setPathValue("controls/flight/aileron", d);
	}
	
	@Override
	public void setElevator(double d) {
		
		if(connectStatus == false)
			this.connect("127.0.0.1",5402);
		
	    	c.setPathValue("controls/flight/elevator", d);
	}
	
	@Override
	public void connectToMapServer(String ip, int port, String mapCor, int planeX, int planeY, int destX,
			int destY){
		mapServerIp = ip;
		mapServerPort = port;
		
        mapServer = new MySerialServer(6420); 
		
		// Starting the Map Solver Server in new thread
		new Thread(()-> {
			try {
				SearchableClientHandler<String, Position> ch;
				ch = new SearchableClientHandler<>(
						new SearcherSolver<MatrixProblem, String, Position>(new BestFirstSearch<Position>()),
						new FileCacheManager<MatrixProblem, String>("./resources/maze.xml")
				);
				
				mapServer.start(ch, "end"); // running the server
			} catch (Exception e) {
				mapServer = null;
				
			}
			
		}).start();
				
		
		calculateMap(mapCor, planeX, planeY, destX, destY);
		calculateStatus = true;
	}
	
	@Override
	public void calculateMap(String mapCor, int planeX, int planeY, int destX, int destY) {
		Socket s=null;
		PrintWriter out=null;
		BufferedReader in=null;
		try {
			//if(connectStatus == false)   //no need to connect again
			s = new Socket(mapServerIp, mapServerPort);
			
			s.setSoTimeout(3000);
			out=new PrintWriter(s.getOutputStream());
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			String[] rows = mapCor.split("\n");

			for(int i = 0; i < rows.length; i++) {
				//System.out.println(rows[i].trim());
				out.println(rows[i].trim());
				
				out.flush();
			}
			out.println("end");
			out.flush();
			
			System.out.println("Plane COORDS: " + planeX + "," + planeY);
			System.out.println("DEST COORDS: " + destX + "," + destY);
			out.println(planeX + "," + planeY);
			out.flush();
			out.println(destX + "," + destY);
			out.flush();
			
			mapSolution = in.readLine();
			System.out.println("sol: " + mapSolution);
			
			setChanged();
			notifyObservers("calculated");
			
			out.close();
			in.close();
			s.close();
			
		} catch (IOException e) {
			System.out.println("Could not connect to the map server!");
			mapServer = null;

		}
		
		
	}
	@Override
	public String getMapSolution() {
		return this.mapSolution;
	}
	@Override
	public boolean getCalculateStatus() {
		return this.calculateStatus;
	}
	
	
}
