package View;
	
import Model.MyModel;
import ServerClient.DataReaderServer;
import ServerClient.MyClient;
import ViewModel.ViewModel;
import interpreter.MyInterpreter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		MyClient c=new MyClient();
		MyInterpreter interpreter=new MyInterpreter();
		interpreter.setClient(c);
		MyModel m=new MyModel(interpreter,c);
		ViewModel vm=new ViewModel(m);
		m.addObserver(vm);
		 
		
		try {	
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			AnchorPane root = (AnchorPane)fxl.load();
			Scene scene = new Scene(root,700,450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("FlightGear application");
			primaryStage.show();
			
			MainWindowLogic mwl =fxl.getController(); //view
			mwl.setViewModel(vm);
			vm.addObserver(mwl);
			
		} catch(Exception e) {
			//e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}