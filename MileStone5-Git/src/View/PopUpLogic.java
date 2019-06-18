package View;

import java.util.Observable;
import java.util.Observer;

import ViewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopUpLogic implements Observer {

	ViewModel vm;
	@FXML
	TextField varIP,varPort;
	@FXML
	Button connect;
	
	public void setViewModel(ViewModel vm) {
		this.vm=vm;
		vm.ip.bind(varIP.textProperty());
		vm.port.bind(varPort.textProperty());
	}
	public void connect() {
		vm.connect();
		Stage stage = (Stage) connect.getScene().getWindow();
	    stage.close();
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
