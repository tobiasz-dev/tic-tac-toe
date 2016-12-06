import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 
 * @author Tobiasz
 * @version 22.10.2016
 * 
 * Defines model for tic tac toe game.
 * Creates board and rules for the game, keep track of score.
 */

public class GameModel {
	private int[][] board;
	private int currentPlayer;
	private GameController gameController;
	private static final int PLAYERX = -1;
	private static final int PLAYERO = 1;
	private static final int SIZE = 3;
	private String playerXName;
	private String playerOName;
	private int playerXScore, playerOScore;
	private File settingsFile;
	private Properties settings;
	
	public GameModel(GameController gc){
		gameController = gc;
		playerXScore = 0;
		playerOScore = 0;
		String userDir = System.getProperty("user.home");
		File propertiesDir = new File(userDir, "tictactoe");
		if(!propertiesDir.exists()) propertiesDir.mkdir();
		
		settingsFile = new File(propertiesDir, "tictactoe.properties");
		
		Properties defaultSettings = new Properties();
		defaultSettings.put("playerXName", "Player 1");
		defaultSettings.put("playerOName", "Player 2");
		
		settings = new Properties(defaultSettings);
		
		if(settingsFile.exists()){
			readSettings();
		}
		
		playerXName = settings.getProperty("playerXName");
		playerOName = settings.getProperty("playerOName");
		
		this.currentPlayer = GameModel.PLAYERX;
		board = new int[3][3];
		
		this.createBoard();
	}
	
	/**
	 * Creates a board for tic tac toe game, sized 3x3
	 */
	public void createBoard() {
		for (int i = 0; i <  SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				board[i][j] = 0;
			}
		}
		
	}
	
	/**
	 * Returns given player name as a String 
	 * @param player int representing a player
	 * @return player name as a String
	 */
	public String getPlayerName(int player){
		String playerName = null;
		
		switch (player){
			case  -1: playerName = this.playerXName;
				break;
			
			case 1: playerName = this.playerOName;
				break;
		}
		return playerName;
	}
	
	/**
	 * Getter for player X name
	 * @return String representation of player X name
	 */
	public String getPlayerXName(){
		return this.playerXName;
	}
	
	/**
	 * Getter for player O name
	 * @return String representation of player O name
	 */
	public String getPlayerOName(){
		return this.playerOName;
	}
	
	/**
	 * Sets new name for player X
	 * @param xName new player X name
	 */
	public void setPlayerXName(String xName){
		this.settings.setProperty("playerXName", xName);
		writeSettings();
		readSettings();
	}
	
	/**
	 * Sets new name for player O
	 * @param oName new player O name
	 */
	public void setPlayerOName(String oName){
		this.settings.setProperty("playerOName", oName);
		writeSettings();
		readSettings();
	}
	
	/**
	 * Returns current player as a String
	 * @return String representing player name
	 */
	public String getCurrentPlayerName(){
		return getPlayerName(getCurrentPlayer());
	}
	
	/**
	 * Returns current player number
	 * @return player as an int
	 */
	public int getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	/**
	 * Writing settings to a file
	 */
	private void writeSettings(){
		try{
			FileOutputStream out = new FileOutputStream(settingsFile);
			settings.store(out, "Program Settings");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Reading settings
	 */
	private void readSettings(){
		try{
			FileInputStream in = new FileInputStream(settingsFile);
			settings.load(in);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
		playerXName = settings.getProperty("playerXName");
		playerOName = settings.getProperty("playerOName");
	}
	
	/**
	 * If it is possible places player sign on a given field
	 * @param field filed on board where player is attempting to make move to
	 * @param player number of player that is making the move
	 * @return
	 */
	public int move(int[] field, int player){		
		//making a move
		
		//determing whether this move is possible
		if(checkMove(field)){
			this.board[field[0]][field[1]] = player;
			return player;
		}
		
		return 0;
		
		
		
	}
	
	/**
	 * Gets player x current score
	 * @return player x score
	 */
	public int getXScore(){
		return this.playerXScore;
	}
	
	/**
	 * Gets player o current score
	 * @return player o score
	 */
	public int getOScore(){
		return this.playerOScore;
	}
	
	/**
	 * Sets X score to score parameter value.
	 * @param score this will be new playerXScore value
	 */
	public void setXScore(int score){
		this.playerXScore = score;
	}
	
	/**
	 * Sets O score to score parameter value.
	 * @param score this will be new playerOScore value
	 */
	public void setOScore(int score){
		this.playerOScore = score;
	}
	
	/**
	 * Adds a point to player score
	 * @param playerNumber number of player that will ahve a point added
	 */
	public void addPoint(int playerNumber){
		switch (playerNumber){
			case -1: playerXScore +=1;
			break;
			
			case 1: playerOScore +=1;
			break;	
		}
	}
	
	/**
	 * Checks whether move to given field is possible (field may be already occupied)
	 * @param field coordinates of a filed
	 * @return true if move is possible otherwise false
	 */
	private boolean checkMove(int[] field){
		//check whether filed is equal to 0
		return (this.board[field[0]][field[1]] == 0 );
	}
	
	/**
	 * Changing player, for example after a turn
	 */
	public void changePlayer(){
		//changing player, depending on current player
		if (this.currentPlayer == GameModel.PLAYERX){
			this.currentPlayer = GameModel.PLAYERO;
		}
		else{
			this.currentPlayer = GameModel.PLAYERX;
		}
	}
	
	/**
	 * Checks if the board is full
	 * @return true if board is full, otherwise false
	 */
	public boolean isBoardFull(){
		for (int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				if(board[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}
	 
	
	/**
	 * Checks whether there is a winner
	 * @return -1 or 1 when one player wins, 0 where there is no winner
	 */
	public int calcResult(){
		int sum = 0;
		int diagonal_1 = 0;
		int diagonal_2 = 0;
		
		//looking for winner vertically
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
			sum += this.board[i][j];
			}
			if(sum == -3 || sum == 3) return sum / 3;		
			else sum = 0;
		}
		
		//looking for winner horizontally
		for (int j = 0; j < SIZE; j++){
			for (int i = 0; i < SIZE; i++){
			sum += this.board[i][j];
			}
			if(sum == -3 || sum == 3) return sum / 3;		
			else sum = 0;
		}
		
		//looking for winner diagonally
		diagonal_1 = this.board[0][0] + this.board[1][1] + this.board[2][2];
		diagonal_2 = this.board[0][2] + this.board[1][1] + this.board[2][0];



		if (diagonal_1 == -3  || diagonal_1 == 3){
			return diagonal_1 / 3;
		}
		else if(diagonal_2 == 3 || diagonal_2 == -3){
			return diagonal_2 / 3;
		}
		
		return 0;
	}
}
