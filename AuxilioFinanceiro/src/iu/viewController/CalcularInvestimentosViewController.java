package iu.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.Main;

public class CalcularInvestimentosViewController implements Initializable{
		@FXML
		private ImageView imgVoltar;

		@FXML
		private Button btnVoltar;
		
		@FXML
		private ImageView imgTutoriais;

		@FXML
		private Button btnTutoriais;
		
		@Override
		public void initialize(URL location, ResourceBundle rb) {
			btnVoltar.setGraphic(imgVoltar);
			btnTutoriais.setGraphic(imgTutoriais);
		}
		
		@FXML
		protected void btnVoltarAction(ActionEvent e) {
			Main.mudarTela("inicial");
		}
		
		@FXML
		protected void btnAction(ActionEvent e) {
			Main.mudarTela("investimento");
		}
		
		@FXML
		protected void btnTutoriaisAction(ActionEvent e) {
			Main.mudarTela("tutoriais");
		}
		
}