package application;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.util.Stack;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;

public class Game {
	@FXML
	private Button reset;
	@FXML
	private Button sound;
	@FXML
	private Button topLeft;
	@FXML
	private Button topMid;
	@FXML
	private Button topRight;
	@FXML
	private Button midLeft;
	@FXML
	private Button midMid;
	@FXML
	private Button midRight;
	@FXML
	private Button bottomLeft;
	@FXML
	private Button bottomMid;
	@FXML
	private Button bottomRight;
	@FXML
	private Label turn;
	@FXML
	private Label winner;
	@FXML
	private Label O;
	@FXML
	private Label X;
	
	private Button[][] buttons = new Button[3][3];
	private boolean flag = false;
	private boolean XWon = false, OWon = false;
	private int counterTaps = 0;
	private boolean statusSound = true;
	// object for the sounds of the game
	private MediaPlayer mediaplayer;
	
	private void mute() {
		if(statusSound) {
			statusSound = false;
			sound.setText("Sound Off");
			if (mediaplayer != null)
				mediaplayer.stop();
		}
		else {
			statusSound = true;
			sound.setText("Sound On");
		}
	}
	public void muteFromIo(ActionEvent e) throws IOException {
		mute();
	}
	//music if not have a winner
	public void setDisqualified() {
		if(!statusSound) return;
		if (mediaplayer != null)
			mediaplayer.stop();
		Media musicFile = new Media(getClass().getClassLoader().getResource("gameOver.wav").toString());
		mediaplayer = new MediaPlayer(musicFile);
		mediaplayer.setVolume(0.07);
		mediaplayer.setAutoPlay(true);
	}

	public void soundStartGame() {
		if(!statusSound) return;
		if (mediaplayer != null)
			mediaplayer.stop();
		Media musicFile = new Media(getClass().getClassLoader().getResource("startGame.wav").toString());
		mediaplayer = new MediaPlayer(musicFile);
		mediaplayer.setVolume(0.07);
		mediaplayer.setAutoPlay(true);
	}
	//taps sound
	public void markButton() {
		if(!statusSound) return;
		if (mediaplayer != null)
			mediaplayer.stop();
		Media musicFile = new Media(getClass().getClassLoader().getResource("pickCard.wav").toString());
		mediaplayer = new MediaPlayer(musicFile);
		mediaplayer.setVolume(0.07);
		mediaplayer.setAutoPlay(true);
	}

	public void winnerSound() {
		if(!statusSound) return;
		if (mediaplayer != null)
			mediaplayer.stop();
		Media musicFile = new Media(getClass().getClassLoader().getResource("Snoop-Dogg.mp3").toString());
		mediaplayer = new MediaPlayer(musicFile);
		mediaplayer.setVolume(0.07);
		mediaplayer.setAutoPlay(true);
	}
	
	public void restart(ActionEvent e) throws IOException {
		soundStartGame();
		restartGame();
		reset.setText("Restart");
	}

	private void restartGame() {
		winner.setVisible(false);
		buttons[0][0] = topLeft;
		buttons[0][1] = topMid;
		buttons[0][2] = topRight;
		buttons[1][0] = midLeft;
		buttons[1][1] = midMid;
		buttons[1][2] = midRight;
		buttons[2][0] = bottomLeft;
		buttons[2][1] = bottomMid;
		buttons[2][2] = bottomRight;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setText("");
				buttons[i][j].setDisable(false);
				buttons[i][j].setVisible(true);
			}
		}
		flag = false;
		turn.setText("X's turn");
		O.setVisible(false);
		X.setVisible(true);
		turn.setStyle("-fx-text-fill: coral;");
		turn.setVisible(true);
		counterTaps = 0;

	}

	private void changeFlag() {
		if (flag) {
			flag = false;
			turn.setText("X's turn");
			turn.setStyle("-fx-text-fill: lightgreen;");
			X.setVisible(true);
			O.setVisible(false);
			check("O");
		} else {
			flag = true;
			turn.setText("O's turn");
			X.setVisible(false);
			O.setVisible(true);
			turn.setStyle("-fx-text-fill: coral;");
			check("X");
		}
		counterTaps++;
		if (counterTaps == 9)
			GameOver();
		if (counterTaps != 9)
			markButton();

	}

	public void GameOver() {
		setDisqualified();
		turn.setStyle("-fx-text-fill: red;");
		turn.setText("Game Over press Restart to play Again");
		X.setVisible(false);
		O.setVisible(false);
		lockButtons();
	}

	public void valueButton(Button btn, String str) {
		if (str.equals("O")) {
			btn.setStyle("-fx-text-fill: coral;");
			btn.setText("O");
		} else {
			btn.setStyle("-fx-text-fill: lightgreen;");
			btn.setText("X");
		}
	}

	public void pressTopLeft(ActionEvent e) throws IOException {
		if (flag && topLeft.getText().equals("")) {
			valueButton(topLeft, "O");
			changeFlag();
		}
		else if(!flag && topLeft.getText().equals("")) {
			valueButton(topLeft, "X");
			changeFlag();
		}
	}

	public void pressTopMid(ActionEvent e) throws IOException {
		if (flag && topMid.getText().equals("")) {
			valueButton(topMid, "O");
			changeFlag();
		}
		else if(!flag && topMid.getText().equals("")) {
			valueButton(topMid, "X");
			changeFlag();
		}
	}

	public void pressTopRight(ActionEvent e) throws IOException {
		if (flag && topRight.getText().equals("")) {
			valueButton(topRight, "O");
			changeFlag();
		}
		else if(!flag && topRight.getText().equals("")) {
			valueButton(topRight, "X");
			changeFlag();
		}
	}

	public void pressMidLeft(ActionEvent e) throws IOException {
		if (flag && midLeft.getText().equals("")) {
			valueButton(midLeft, "O");
			changeFlag();
		}
		else if(!flag && midLeft.getText().equals("")) {
			valueButton(midLeft, "X");
			changeFlag();
		}
	}

	public void pressMidMid(ActionEvent e) throws IOException {
		if (flag && midMid.getText().equals("")) {
			valueButton(midMid, "O");
			changeFlag();
		}
		else if(!flag && midMid.getText().equals("")) {
			valueButton(midMid, "X");
			changeFlag();
		}
	}

	public void pressMidRight(ActionEvent e) throws IOException {
		if (flag && midRight.getText().equals("")) {
			valueButton(midRight, "O");
			changeFlag();
		}
		else if(!flag && midRight.getText().equals("")) {
			valueButton(midRight, "X");
			changeFlag();
		}
	}

	public void pressBottomLeft(ActionEvent e) throws IOException {
		if (flag && bottomLeft.getText().equals("")) {
			valueButton(bottomLeft, "O");
			changeFlag();
		}
		else if(!flag && bottomLeft.getText().equals("")) {
			valueButton(bottomLeft, "X");
			changeFlag();
		}
	}

	public void pressBottomMid(ActionEvent e) throws IOException {
		if (flag && bottomMid.getText().equals("")) {
			valueButton(bottomMid, "O");
			changeFlag();
		}
		else if(!flag && bottomMid.getText().equals("")) {
			valueButton(bottomMid, "X");
			changeFlag();
		}
	}

	public void pressBottomRight(ActionEvent e) throws IOException {
		if (flag && bottomRight.getText().equals("")) {
			valueButton(bottomRight, "O");
			changeFlag();
		}
		else if(!flag && bottomRight.getText().equals("")) {
			valueButton(bottomRight, "X");
			changeFlag();
		}
	}

	private void check(String str) {
		int cnt = 0;
		for (int i = 0; i < buttons.length; i++)
			if (buttons[i][0].getText().equals(str)) {
				cnt++;
				if (cnt == 3)
					if (str.equals("X"))
						XWon = true;
					else
						OWon = true;
			}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++)
			if (buttons[i][1].getText().equals(str)) {
				cnt++;
				if (cnt == 3)
					if (str.equals("X"))
						XWon = true;
					else
						OWon = true;
			}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++)
			if (buttons[i][2].getText().equals(str)) {
				cnt++;
				if (cnt == 3)
					if (str.equals("X"))
						XWon = true;
					else
						OWon = true;
			}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++)
			if (buttons[0][i].getText().equals(str)) {
				cnt++;
				if (cnt == 3)
					if (str.equals("X"))
						XWon = true;
					else
						OWon = true;
			}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++)
			if (buttons[1][i].getText().equals(str)) {
				cnt++;
				if (cnt == 3)
					if (str.equals("X"))
						XWon = true;
					else
						OWon = true;
			}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++)
			if (buttons[2][i].getText().equals(str)) {
				cnt++;
				if (cnt == 3)
					if (str.equals("X"))
						XWon = true;
					else
						OWon = true;
			}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (i == j)
					if (buttons[i][j].getText().equals(str)) {
						cnt++;
						if (cnt == 3)
							if (str.equals("X"))
								XWon = true;
							else
								OWon = true;
					}
			}
		}
		cnt = 0;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (i + j == 2)
					if (buttons[i][j].getText().equals(str)) {
						cnt++;
						if (cnt == 3)
							if (str.equals("X"))
								XWon = true;
							else
								OWon = true;
					}
			}
		}
		if (XWon == true)
			showWinner(0);
		if (OWon == true)
			showWinner(1);
	}

	private void showWinner(int num) {
		lockButtons();
		if (num == 0)
			winner.setText("PLayer X has Won");
		else if (num == 1)
			winner.setText("PLayer O has Won");
		winner.setVisible(true);
		turn.setVisible(false);
		X.setVisible(false);
		O.setVisible(false);
		XWon = false;
		OWon = false;
		final Task task = new Task() {
				@Override
				protected Object call() throws Exception {
					winnerSound();
					return null;
				}
	    };
	    Thread thread = new Thread(task);
	    thread.start();
	}

	/*
	 * all the buttons
	 */
	private void unshowButtons() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setVisible(false);
			}
		}
	}
	private void lockButtons() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setDisable(true);
			}
		}
	}
}
