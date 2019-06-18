package Model;

import java.io.File;

public interface Model {

	public void connect(String ip,int port);
	public String getMapSolution();
	public void interpret(String[] lines);
	public void close();
	public void setRudder(double d);
	public void setThrottle(double d);
	public void setElevator(double d);
	public void setAileron(double d);
	public void calculateMap(String mapCor, int planeX, int planeY, int destX, int destY);
	void connectToMapServer(String ip, int port, String mapCor, int planeX, int planeY, int destX, int destY);
	public boolean getCalculateStatus();
}
