import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * 
 * @author Tobiasz
 * @version 22.10.2016
 *
 *	Controls the whole game by being a link between View and a Model
 */
public class GameController {

	private GameModel gameModel;
	private JFrame gameView;
	
	/**
	 * Constructor for game controller.
	 * Creates a view and a model.
	 */
	public GameController(){
		 gameModel = new GameModel(this);
		 gameView = new GameView(this);
		 gameView.setVisible(true);	 
	}
	
	/**
	 * Getter for player X name
	 * @return String representation of player X name
	 */
	public String getPlayerXName(){
		return gameModel.getPlayerXName();
	}
	
	/**
	 * Getter for player O name
	 * @return String representation of player O name
	 */
	public String getPlayerOName(){
		return gameModel.getPlayerOName();
	}
	
	/**
	 * Sets new name for player X
	 * @param xName new player X name
	 */
	public void setPlayerXName(String xName){
		gameModel.setPlayerXName(xName);
	}
	
	/**
	 * Sets new name for player O
	 * @param oName new player O name
	 */
	public void setPlayerOName(String oName){
		gameModel.setPlayerOName(oName);
	}
	
	/**
	 * Returns current player as a String
	 * @return String representing current player
	 */
	public String getCurrentPlayerName() {
		return gameModel.getCurrentPlayerName();
	}
	
	/**
	 * Returns current player as a int
	 * @return int representing current player
	 */
	public int getCurrentPlayer(){
		return gameModel.getCurrentPlayer();
	}
	
	/**
	 * returns String representation of player
	 * @param player int representing a player
	 * @return String representing a player
	 */
	public String getPlayerName(int player){
		return gameModel.getPlayerName(player);
	}
	
	/**
	 * Using method move from the model checks if the move is possible, and if it is returns player number making that move.
	 * @param field a filed on the board on which player is attempting to make a move
	 * @return
	 */
	public int makeMove(int[] field){
		int moveResult = gameModel.move(field, this.getCurrentPlayer());		
		return moveResult;
	}
	
	/**
	 * Returns a number of winning player or a 0 if no one has won yet
	 * @return returns: -1, 0 or 1 depending on the result
	 */
	public int getWinner(){
		int result = gameModel.calcResult();
		if (result !=0){
			gameModel.createBoard();
			gameModel.addPoint(result);
		}
		//changes current player
		gameModel.changePlayer();		

		//returns player that has won
		return result;
		
	}
	

	/**
	 * Returns players scores in an array
	 * @return sized 2, array of scores
	 */
	public int[] getPlayersScores(){
		return new int[]{gameModel.getXScore(), gameModel.getOScore()};
	}
	
	/**
	 * Checks wheteher ther is any move possible
	 * @return true if no move is possible
	 */
	public boolean anyMovePossible() {
		if(gameModel.isBoardFull()){
			gameModel.createBoard();
			return false;
		}
		else return true;
	}
	
	/**
	 * Returns boards fields to defaults,
	 * clears scores.
	 */
	public void restartGame(){
		gameModel.createBoard();
		gameModel.setXScore(0);
		gameModel.setOScore(0);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					GameController gameController = new GameController();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
