package Appointment;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller implements Initializable {
	private String empty = "";
	private String format = "";
	public Button submitButton;
	public TextField objective;
	public TextField building;
	public TextField room;
	public TextField fromTime;
	public TextField toTime;
	public TextField repNum;
	public DatePicker appDate;
	public DatePicker repEndDate;
	public Text errorText;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		building.addEventFilter(KeyEvent.KEY_TYPED , buildValidation());
		room.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(0));
		fromTime.addEventFilter(KeyEvent.KEY_TYPED , timeValidation());
		toTime.addEventFilter(KeyEvent.KEY_TYPED , timeValidation());
		repNum.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(-1));
	}
	
	public EventHandler<KeyEvent> numericValidation(Integer bottomLimit) {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	        	TextField textField = (TextField) e.getSource();
	        	String text = textField.getText();
	        	String nextText = new StringBuilder(text).insert(textField.getCaretPosition(), e.getCharacter()).toString();
	        	if (nextText.equals("-")) {
	        		return;
	        	}
            	try {
            		int num = Integer.parseInt(nextText);
            		if (bottomLimit != null && num < bottomLimit) {
            			e.consume();
            		}
            	}
            	catch (Exception ex) {
            		e.consume();
            	}
	        }
	    };
	}
	
	public EventHandler<KeyEvent> buildValidation() {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	        	TextField textField = (TextField) e.getSource();
	        	String text = textField.getText();
	        	String nextText = new StringBuilder(text).insert(textField.getCaretPosition(), e.getCharacter()).toString();
	        	if (!nextText.matches("^[a-zA-Z0-9 -]+$")) {
	        		e.consume();
	        	}
	        }
	    };
	}
	
	public EventHandler<KeyEvent> timeValidation() {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	        	TextField textField = (TextField) e.getSource();
	        	String c = e.getCharacter();
	        	int pos = textField.getCaretPosition();
	        	String nextText = new StringBuilder(textField.getText()).insert(textField.getCaretPosition(), c).toString();
	        	if (c.matches("|")) {
	        		if (!nextText.contains(":")) {
	        			textField.setText(nextText.replaceAll(c, ":"));
	            		if (c.equals("")) {
	            			pos++;
	            		}
	            		textField.positionCaret(pos);
	        		}
	        		nextText = nextText.replaceAll(c, "");
	        	}
	        	if (!Time.isFormated(nextText)) {
	        		e.consume();
	        	}
	        }
	    };
	}
	
	private boolean checkText(TextField text, String name) {
		if (text.getText().isEmpty()) {
			empty += name + ", ";
			return false;
		}
		return true;
	}
	
	private void checkDate(DatePicker date, String name, boolean repDate) {
		if (date.getValue() == null && !repDate) {
			empty += name + ", ";
		}
	}
	
	private boolean checkTime(Time time, String name) {
		if (!time.hasTime()) {
			format += name + ", ";
			return false;
		}
		return true;
	}
	
	private void errorMessageReset() {
		empty = "";
		format = "";
	}
	
	private String generateErrorMessage() {
		checkText(objective, "Objective");
		checkText(building, "Building");
		checkDate(appDate, "Date", false);
		if (checkText(repNum, "Reapeat appointment...")) {
			boolean shouldRep = Integer.parseInt(repNum.getText()) <= 0;
			checkDate(repEndDate, "Date for when...", shouldRep);
		}
		Time tTime = new Time(toTime.getText());
		Time fTime = new Time(fromTime.getText());
		boolean validInterval = true;
		boolean validFTime = checkTime(fTime, "From Time");
		boolean validTTime = checkTime(tTime, "To Time");
		if (validFTime && validTTime) {
			validInterval = fTime.compare(tTime) <= 0;
		}
		String response = "";
		if (!empty.isEmpty()) {
			response += "Please fill inn: " + cutString(empty);
		}
		if (!format.isEmpty()) {
			response += "\n\nInvalid: " + cutString(format);
		}
		if (!validInterval) {
			response += "\n\nFrom Time can not be after To Time";
		}
		return response;
	}
	
	private String cutString(String string) {
		return string.substring(0, string.length() - 2);
	}
	
	public void onButtonPress() {
		String response = generateErrorMessage();
		if (!response.isEmpty()) {
			errorText.setFill(Color.RED);
			errorText.setText(response);
			errorMessageReset();
		}
		else {
			errorText.setFill(Color.GREEN);
			errorText.setText("Appointment has been submitted!");
		}
	}
}
