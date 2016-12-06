import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Panel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
/**
 * @author Tobiasz
 * @version 14.10.2016
 * 
 * Defines a view and view related actions for tic-tac-toe game;
 */
public class GameView extends JFrame{
	
	private GameController gameController;
	private ImageIcon xSign;
	private ImageIcon oSign;
	private JButton[] fields;
	private JLabel currPlayerLabel;
	private JLabel xScoreLabel;
	private JLabel oScoreLabel;
	private JFrame mainFrame;


	/**
	 * Create the application.
	 */
	public GameView(GameController gc) {
		gameController = gc;
		//load images
		try{
			oSign = new ImageIcon(ImageIO.read(getClass().getResource("circle.jpg")));
			xSign = new ImageIcon(ImageIO.read(getClass().getResource("cross.jpg")));
		}
		catch(IOException e){
			JOptionPane.showInternalMessageDialog(this, "Error reading program resources");
		}

		initialize();
		mainFrame = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setResizable(false);
		this.setTitle("tic-tac-toe");
		this.setBounds(100, 100, 450, 460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		Panel scorePanel = new Panel();
		FlowLayout flowLayout = (FlowLayout) scorePanel.getLayout();
		flowLayout.setHgap(10);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		
		currPlayerLabel = new JLabel("Now: " + gameController.getCurrentPlayerName());
		scorePanel.add(currPlayerLabel);
		currPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currPlayerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);

		scorePanel.add(separator);
		
		xScoreLabel = new JLabel("X: ");
		xScoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		scorePanel.add(xScoreLabel);
		
		JSeparator separator_1 = new JSeparator();
		scorePanel.add(separator_1);
		
		oScoreLabel = new JLabel("O: ");
		oScoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		scorePanel.add(oScoreLabel);
		
		this.displayScore();
		
		JPanel boardPanel = new JPanel();
		this.getContentPane().add(boardPanel, BorderLayout.CENTER);
		boardPanel.setLayout(new GridLayout(3, 3, 0, 0));
		
		JButton btnNewButton_1 = new JButton(new MoveAction(new int[] {0, 0}));
		boardPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(new MoveAction(new int[] {0, 1}));
		boardPanel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton(new MoveAction(new int[] {0, 2}));
		boardPanel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton(new MoveAction(new int[] {1, 0}));
		boardPanel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton(new MoveAction(new int[] {1, 1}));
		boardPanel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton(new MoveAction(new int[] {1, 2}));
		boardPanel.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton(new MoveAction(new int[] {2, 0}));
		boardPanel.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton(new MoveAction(new int[] {2, 1}));
		boardPanel.add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton(new MoveAction(new int[] {2, 2}));
		boardPanel.add(btnNewButton_9);
		
		this.fields = new JButton[]{btnNewButton_1, btnNewButton_2, btnNewButton_3, btnNewButton_4, btnNewButton_5,
				btnNewButton_6, btnNewButton_7, btnNewButton_8, btnNewButton_9};
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnProgram = new JMenu("Program");
		menuBar.add(mnProgram);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					System.exit(0);
			}
			
		});
		
		JMenuItem mntmRestartGame = new JMenuItem("Restart game");
		mntmRestartGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearBoard();
				gameController.restartGame();
				displayScore();
			}
			
		});
		
		//Window and menu item for adding a plaeyrs names
		JMenuItem mntmSetPlayersNames = new JMenuItem("Set player's names");
		mntmSetPlayersNames.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame playerNameInput = new JFrame("Name players");
				JTextField player1Name = new JTextField(6);
				JTextField player2Name = new JTextField(6);
				
				playerNameInput.setLayout(new FlowLayout());
				playerNameInput.add(new JLabel("Player 1 name: "));
				playerNameInput.add(player1Name);
				playerNameInput.add(new JLabel("Player 2 name: "));
				playerNameInput.add(player2Name);
				
				JButton setButton = new JButton("Set");
				
				setButton.addActionListener(new ActionListener(){
					//when set button is pressed players names are set only if they are at least 1 character long 
					@Override
					public void actionPerformed(ActionEvent e) {
						String player1NameString = player1Name.getText().trim();
						String player2NameString = player2Name.getText().trim();
						
						if(player1NameString.length() < 1 || player2NameString.length() < 1){
							JOptionPane.showMessageDialog(mainFrame, "Please input new names.");
						}
						else{
							gameController.setPlayerXName(player1NameString);
							gameController.setPlayerOName(player2NameString);
							((GameView) mainFrame).displayScore();
							((GameView) mainFrame).currentPlayerDisplay();
							playerNameInput.setVisible(false);
						}
						
					}
					
				});
		
				playerNameInput.add(setButton);
				playerNameInput.setLocationRelativeTo(mainFrame);
				
				playerNameInput.pack();
				playerNameInput.setVisible(true);
				
			}
			
		});
		
		
		mnProgram.add(mntmSetPlayersNames);
		mnProgram.add(mntmRestartGame);
		mnProgram.add(mntmExit);
	}
	
	/**
	 * Changes graphics displayed on the button.
	 * 
	 * @param button button that will be affected by this change
	 * @param playerNumber number representing a player; this determines
	 * the symbol that will appear on the button
	 */
	public void setIcon(JButton button, int playerNumber){
		switch (playerNumber){
		case -1: button.setIcon(xSign);
			break;
			
		case 1:button.setIcon(oSign);
			break;
		}
	}
	
	/**
	 * Clears the graphics displayed on buttons
	 */
	public void clearBoard(){
		for (JButton b : fields){
			b.setIcon(null);
		}
	}
	
	/*
	 * Displays a player having to make a move
	 */
	private void currentPlayerDisplay(){
		this.currPlayerLabel.setText("Now: " + gameController.getCurrentPlayerName());
	}
	
	/**
	 * Displays score above the board
	 */
	private void displayScore(){
		int[] scores = gameController.getPlayersScores();
		
		this.xScoreLabel.setText(gameController.getPlayerXName() + " : " + scores[0]);
		this.oScoreLabel.setText(gameController.getPlayerOName() + " : " + scores[1]);
	}
	
	/**
	 * Display a message indicating which player has won.
	 * @return 
	 */
	private boolean isThereAWinner(){
		//There can be a winner only if getWinner returns player number
		int winner = gameController.getWinner();
		if (winner != 0){
			JOptionPane.showMessageDialog(this,gameController.getPlayerName(winner) + " scores!");
			return true;
		}
		else return false;
	}
	
	/**
	 * checks and if needed displays a message if there is no move possible
	 * @return true if no move is possible otherwise false
	 */
	private boolean noMovePossible() {
		if(gameController.anyMovePossible()){
			return false;
		}
		else{
			JOptionPane.showMessageDialog(this, "No move possible! It's a draw!");
			return true;
		}
	}	
	
	/**
	 * Displays message warning about impossible move
	 */
	private void cantMoveMessage(){
		JOptionPane.showMessageDialog(this, "You can't do that!");
	}
	
	/**
	 * 
	 * @author Tobiasz
	 *
	 * Class for move action. 
	 * 
	 */
	private class MoveAction extends AbstractAction{
		private int[] field;
		
		/**
		 * Constructor that crates a move action for a specific field.
		 * This determines what will happen if there is a move on this field.
		 * Move action is connected with a specific filed on the board,
		 * represented by a button.
		 * @param f field on the boar represented by an array that contains two ints.
		 */
		public MoveAction(int[] f){
			super();
			this.field = f;
		}
		
		/**
		 * This is what happens when a button is pressed.
		 * Since buttons are representing a board move if possible is made,
		 * after a move it is checked whether 
		 * @param e action event created by pressing a button
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int cp = gameController.makeMove(field);
			if (cp != 0){
				/*if the move is possible player makes a move, 
				*then method checks if anybody has won yet,
				and changes player*/
				
				setIcon((JButton) e.getSource(), cp);
				if(isThereAWinner() || noMovePossible()){
					clearBoard();
				}
				currentPlayerDisplay();
				displayScore();
			}
			
			else{
				//if move is not possible message dialog is displayed
				cantMoveMessage();	
			}
		}


	}
}
