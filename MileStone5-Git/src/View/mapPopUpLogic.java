package View;

import java.util.Observable;
import java.util.Observer;

import ViewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapPopUpLogic implements Observer {

	ViewModel vm;
	
	@FXML
	TextField varMapIP,varMapPort;
	@FXML
	Button submit;
	
	
	public void setViewModel(ViewModel vm) {
		this.vm = vm;
		vm.mapIp.bind(varMapIP.textProperty());
		vm.mapPort.bind(varMapPort.textProperty());
	}
	
	public void connectToMapServer() {
		vm.connectToMapServer();
		Stage stage = (Stage) submit.getScene().getWindow();
	    stage.close();
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
