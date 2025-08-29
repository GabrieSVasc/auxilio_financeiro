module AuxilioFinanceiro {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.json;
	
	opens main to javafx.graphics, javafx.fxml;
	opens iu.viewController to javafx.graphics, javafx.fxml;
	opens fachada to javafx.base;
}
