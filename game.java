
package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	final private int ROW 			= 10;
	final private int COL 			= 10;
	final private int ROW_ONE_CELL 	= 4;
	final private int CHAR_A 		= 65;
	final private char CHAR_ROW 	= '-';
	final public char HIT 			= 'X';
	final public char MISS 			= '-';
	private char[][] board;
	// Map the index of rows
	public static Map<Character, Integer> mapRowCol = new HashMap<Character, Integer>();
	static {
		mapRowCol.put(new Character('A'), new Integer(9));
		mapRowCol.put(new Character('B'), new Integer(8));
		mapRowCol.put(new Character('C'), new Integer(7));
		mapRowCol.put(new Character('D'), new Integer(6));
		mapRowCol.put(new Character('E'), new Integer(5));
		mapRowCol.put(new Character('F'), new Integer(4));
		mapRowCol.put(new Character('G'), new Integer(3));
		mapRowCol.put(new Character('H'), new Integer(2));
		mapRowCol.put(new Character('I'), new Integer(1));
		mapRowCol.put(new Character('J'), new Integer(0));
	}
	// Map the index of rows to character
	public static Map<Integer, Character> mapRowName = new HashMap<Integer, Character>();
	static {
		mapRowName.put(new Integer(9), new Character('A'));
		mapRowName.put(new Integer(8), new Character('B'));
		mapRowName.put(new Integer(7), new Character('C'));
		mapRowName.put(new Integer(6), new Character('D'));
		mapRowName.put(new Integer(5), new Character('E'));
		mapRowName.put(new Integer(4), new Character('F'));
		mapRowName.put(new Integer(3), new Character('G'));
		mapRowName.put(new Integer(2), new Character('H'));
		mapRowName.put(new Integer(1), new Character('I'));
		mapRowName.put(new Integer(0), new Character('J'));
	}
	
	

	/**
	 * Constructor
	 */
	public Board() {
		board = new char[ROW][COL];
		init();
	}

	/**
	 * Set/Reset the board back to all empty values.
	 */
	public void init() {
		// Loop through rows
		for (int i = 0; i < ROW; i++) {
			// Loop through columns
			for (int j = 0; j < COL; j++) {
				board[i][j] = ' ';
			}
		}
	}

	/**
	 * Print one row
	 */
	private void printRow() {
		for (int i = 0; i < ROW_ONE_CELL * COL; i++) {
			System.out.print(CHAR_ROW);
		}
		System.out.print(CHAR_ROW + "" + CHAR_ROW);
	}

	/**
	 * Print board
	 */
	public void printBoard() {
		printRow();
		System.out.println();
		int nameRow = CHAR_A + ROW - 1;
		for (int i = 0; i < ROW && nameRow >= CHAR_A; i++, nameRow--) {
			System.out.printf("%c| ", nameRow);
			for (int j = 0; j < COL; j++) {
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();
			printRow();
			System.out.println();
		}
		for (int idxRow = 0; idxRow < COL; idxRow++) {
			System.out.printf("%4d", idxRow);
		}
	}

	/**
	 * Convert coordinate of user to real coordinate of board ex: 1B -> (row = 8,
	 * col = 1)
	 * 
	 * @param string
	 * @return Point
	 */
	public Point convertStringToRowCol(String str) {
		int leng = str.length();
		if (leng > 0 && leng < 3) {
			Point retPoint = new Point(-1, -1);
			try {
				retPoint.x = mapRowCol.get(str.charAt(1));
				retPoint.y = Integer.parseInt(String.valueOf(str.charAt(0)));
			} catch (Exception e) {
				System.out.println("Error: Input invalid! ");
			}
			return retPoint;
		}
		return null;
	}

	/**
	 * Convert coordinate of user to real coordinate of board ex: (row = 2, col = 2)
	 * -> 2H
	 * 
	 * @param point
	 * @return String
	 */
	public String convertRowColToString(Point point) {
		return point.y + "" + mapRowName.get(point.x);
	}

	/**
	 * Place "X" : hit or "o" : miss on board
	 * 
	 * @param row
	 * @param col
	 * @param isHitting
	 * @return true if row and col valid, false on the contrary
	 */
	public boolean placeMark(int row, int col, boolean isHitting) {
		// Make sure that row and column are in bounds of the board.
		if ((row >= 0) && (row < ROW)) {
			if ((col >= 0) && (col < COL)) {
				if (board[row][col] == ' ') {
					board[row][col] = isHitting == true ? HIT : MISS;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Print a array coordinate of board to user view (3F, 2A, etc)
	 * 
	 * @param arr
	 */
	public void printArrCoorToUserView(List<Point> arr) {
		System.out.println("\t> Coordinate: ");
		for (Point point : arr) {
			System.out.println("\t" + convertRowColToString(point));
		}
	}

	/**
	 * Place a coordinate array on board
	 * 
	 * @param arr
	 */
	public void placeArrMark(List<Point> arr) {
		for (Point coor : arr) {
			if (!placeMark(coor.x, coor.y, true)) {
				System.out.println("This coordinate not correct! " + "(" + coor.x + "," + coor.y + ")");
			}
		}
	}
}
//==============
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boat {
	private final int HORIZONTAL 	= 0;
	private final int VERTICAL 		= 1;
	
	/*to easier for random coordinate of boat
	 * avoid lost coordinate
	 */
	private final int IDX_SAFE_START= 2;
	private final int IDX_SAFE_END 	= 6;
	
	private List<Point> coorBoat;

	/**
	 * Getter
	 * @return coordinates of boat
	 */
	public List<Point> getcoorBoat() {
		return coorBoat;
	}
	/**
	 * Setter
	 * @param coorBoat
	 */
	public void setcoorBoat(List<Point> coorBoat) {
		this.coorBoat = new ArrayList<>(coorBoat);
	}

	/**
	 * Constructor
	 * @param coorBoat (3 coordinate)
	 */
	public Boat(List<Point> coorBoat) {
		this.coorBoat = coorBoat;
	}
	/**
	 * Print 3 coordinates of boat
	 */
	public void printCoorBoat() {
		System.out.println("\t> Coordinates of boat: ");
		for (Point point : this.coorBoat) {
			System.out.println("\t(" + point.x + ", " + point.y + ")");
		}
	}

	/**
	 * Returns a random number between min and max, inclusive.
	 *
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();

		return rand.nextInt((max - min) + 1) + min;
	}
	/**
	 * Generate a random boat (with 3 coordinates)
	 */
	public void generate() {
		List<Point> arrPoint = new ArrayList<>();
		// Random direction (Horizontal or Vertical)
		int direction = randInt(0, 1) == 0 ? this.HORIZONTAL : this.VERTICAL;
		Point firstPoint = new Point(randInt(IDX_SAFE_START, IDX_SAFE_END), randInt(IDX_SAFE_START, IDX_SAFE_END));

		if (direction == this.HORIZONTAL) {
			Point secondPoint = new Point(firstPoint.x, firstPoint.y + 1);
			Point thirdPoint = new Point(firstPoint.x, secondPoint.y + 1);
			arrPoint.add(firstPoint);
			arrPoint.add(secondPoint);
			arrPoint.add(thirdPoint);
		} else { // VERTICAL
			Point secondPoint = new Point(firstPoint.x + 1, firstPoint.y);
			Point thirdPoint = new Point(secondPoint.x + 1, secondPoint.y);
			arrPoint.add(firstPoint);
			arrPoint.add(secondPoint);
			arrPoint.add(thirdPoint);
		}
		this.coorBoat = new ArrayList<>(arrPoint);
	}
	/**
	 * Initialize
	 */
	public void init() {
		this.generate();
	}
	/**
	 * Constructor
	 */
	public Boat() {
		this.coorBoat = new ArrayList<>();
		init();
	}
}
//======
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Point;
import model.Board;
import model.Boat;

public class Game {
	public enum CurrentTurn{ PLAYER, MACHINE }
	private Board board;
	private Boat boat;
	
	public CurrentTurn currentTurn;
	public List<String> lstPointGuess;
	public List<String> lstStepPlayer;
	public List<String> lstStepMachine;
	
	public void printAllStepOfPlayer() {
		if(this.lstStepPlayer.size() > 0) {
			for (String step : lstStepPlayer) {
				System.out.print(step + ", ");
			}
		}
	}
	public void printAllStepOfMachine() {
		if(this.lstStepMachine.size() > 0) {
			for (String step : lstStepMachine) {
				System.out.print(step + ", ");
			}
		}
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat boat) {
		this.boat = boat;
	}

	// Constructor
	public Game() {
		board = new Board();
		boat = new Boat();
		lstPointGuess = new ArrayList<>();
		lstStepMachine = new ArrayList<>();
		lstStepPlayer = new ArrayList<>();
		currentTurn = CurrentTurn.PLAYER; 
	}

	/**
	 * Check input string whether valid or invalid
	 * @param str
	 * @return
	 * 		true if valid,
	 * 		false if invalid
	 */
	public boolean checkInput(String str) {
		if (str.length() == 0 || str.length() > 2) {
			return false;
		}

		// Pattern to check whether input is "<0-9><A-J>"
		Pattern patternObi = Pattern.compile("[0-9][A-J]");
		// Check with input string
		Matcher matcherObj = patternObi.matcher(str);
		if (!matcherObj.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * Get input from machine
	 * @return
	 */
	public String getInputFromMachine() {
		String retStr = "";
		Random rand = new Random();
		
		while (true) {
			retStr = this.board.convertRowColToString(
					(new Point((rand.nextInt((9 - 0) + 1) + 0), (rand.nextInt((9 - 0) + 1) + 0)))
					);
			if (!this.lstPointGuess.contains(retStr)) {
				break;
			}
		}
		
		this.lstPointGuess.add(retStr);
		return retStr;
	}
	
	public String getInput() {
		String retStr = "";
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("\n\n> Input your coordinate shooting: ");
			retStr = input.nextLine();
			if (!checkInput(retStr) || lstPointGuess.contains(retStr)) {
				System.out.println("Invalid input! Please try again... <Number><Upper character>. ex: 8A");
				System.out.println("Or this coordinate was guessed!");
			} else {
				break;
			}
		}
		
		this.lstPointGuess.add(retStr);
		
		return retStr;
	}

	public void wellcomeToGame() {
		System.out.println("\tWellcome to the game!\n\t** ~ BOAT SHOOTING ~ ** ");
		System.out.println("");
		System.out.println("    _,________    ___ BANG!!!   _~");
		System.out.println("   _T _==____()  (___)       _~ )_)_~");
		System.out.println("  /##(_)-'                   )_))_))_)");
		System.out.println(" /##/                        _!__!__!_");
		System.out.println(" \"\"\"                         \\______t/");
		System.out.println("                            ~~~~~~~~~~~~~");
	}

	public void summary(int countShot, int hit, int miss) {
		System.out.println("\n\tSUMMARY");
		System.out.println("\t> Number of shooting: " + countShot);
		System.out.println("\t> Number of hitting : " + hit);
		System.out.println("\t> Number of missing : " + miss);
		System.out.println("=================================");
		System.out.println("Below is the information of Boat:");
		board.printArrCoorToUserView(boat.getcoorBoat());
		board.init();
		board.placeArrMark(boat.getcoorBoat());
		board.printBoard();
	}
	
	public void runHumanVsMachine() {
		wellcomeToGame();
		// For testing
		// Print coordinate of boat
//		board.printArrCoorToUserView(boat.getcoorBoat());
		board.printBoard();
		
		String inputUser;
		String inputMachine;
		boolean flagContinue = true;
		List<Point> coorBoat = new ArrayList<>(boat.getcoorBoat());
		int countShotUser = 0;
		int countShotMachine = 0;
		int totalHit = 0;
		int hitUser = 0;
		int hitMachine = 0;
		int missUser = 0;
		int missMachine = 0;
		Point coorShot = new Point();
		while (flagContinue) {
			// Get input from user
			switch (this.currentTurn) {
			case PLAYER:				
				System.out.println("\n\t> ================");
				System.out.println("\t> = TURN: PLAYER =");
				System.out.println("\t> ================");
				inputUser = getInput();
				lstStepPlayer.add(inputUser);
				countShotUser++;
				coorShot = board.convertStringToRowCol(inputUser);
				this.currentTurn = CurrentTurn.MACHINE;
				// If hitting
				if (coorBoat.contains(coorShot)) {
					hitUser++;
					totalHit++;
					board.placeMark(coorShot.x, coorShot.y, true);
					System.out.println("GOOD!!!");
				} else {
					missUser++;
					board.placeMark(coorShot.x, coorShot.y, false);
					System.out.println("MISS!!! TRY AGAIN!");
				}
				board.printBoard();
				if (totalHit == 3) {
					flagContinue = false;
					System.out.println("\nCONGARTULATION!!!");
					System.out.println("\tPLAYER WIN!");
					System.out.println("\t=== PLAYER ===");
					summary(countShotUser, hitUser, missUser);
					System.out.println("\n\t=== MACHINE ===");
					summary(countShotMachine, hitMachine, missMachine);
				}
				break;
			case MACHINE:
				System.out.println("\n\t> =================");
				System.out.println("\t> = TURN: MACHINE =");
				System.out.println("\t> =================");
				inputMachine = getInputFromMachine();
				lstStepMachine.add(inputMachine);
				System.out.println("\tMachine guess: " + inputMachine);
				countShotMachine++;
				coorShot = board.convertStringToRowCol(inputMachine);
				this.currentTurn = CurrentTurn.PLAYER;
				// If hitting
				if (coorBoat.contains(coorShot)) {
					hitMachine++;
					totalHit++;
					board.placeMark(coorShot.x, coorShot.y, true);
					System.out.println("\t1 TARGET WAS DESTROYED!");
				} else {
					missMachine++;
					board.placeMark(coorShot.x, coorShot.y, false);
					System.out.println("MISS!!!");
				}
				//board.printBoard();
				if (totalHit == 3) {
					flagContinue = false;
					System.out.println("\n\t==============");
					System.out.println("\tMACHINE WIN!");
					System.out.println("\t=== MACHINE ===");
					summary(countShotMachine, hitMachine, missMachine);
					System.out.println("\n\t=== PLAYER ===");
					summary(countShotUser, hitUser, missUser);
				}
				break;
			default:
				break;
			}
		}
	}

	public void runSingle() {
		wellcomeToGame();
		// For testing
		// Print coordinate of boat
//		board.printArrCoorToUserView(boat.getcoorBoat());
		board.printBoard();

		String inputUser;
		boolean flagContinue = true;
		List<Point> coorBoat = new ArrayList<>(boat.getcoorBoat());
		int countShot = 0;
		int hit = 0;
		int miss = 0;
		
		while (flagContinue) {
			// Get input from user
			inputUser = getInput();
			lstStepPlayer.add(inputUser);
			countShot++;
			// Get coordinate from input
			Point coorShot = board.convertStringToRowCol(inputUser);
			// If hitting
			if (coorBoat.contains(coorShot)) {
				hit++;
				board.placeMark(coorShot.x, coorShot.y, true);
				System.out.println("GOOD!!!");
			} else {
				miss++;
				board.placeMark(coorShot.x, coorShot.y, false);
				System.out.println("MISS!!! TRY AGAIN!");
			}
			board.printBoard();
			if (hit == 3) {
				flagContinue = false;
				System.out.println("\nCONGARTULATION!!!");
			}
		}
		summary(countShot, hit, miss);
		System.out.println("\nAll steps: ");
		this.printAllStepOfPlayer();
	}
}
//===
package view;

import controller.Game;

import java.util.Scanner;

public class MainView {

	public static void menu() {
		System.out.println("\n\t=======================");
		System.out.println("\t=  ~ BOAT SHOOTING ~  =");
		System.out.println("\t=======================");
		System.out.println("\t= 1. SINGLE           =");
		System.out.println("\t= 2. HUMAN VS MACHINE =");
		System.out.println("\t=======================");
		System.out.print("\t -> Select mode: ");
	}

	public static void main(String[] args) {
		String choice;
		byte method;
		Scanner sc = new Scanner(System.in);

		while (true) {
			Game game = new Game();
			do {
				menu();
				method = Byte.parseByte(sc.nextLine());
			} while (method != 1 && method != 2);
			
			if (method == 1) {
				game.runSingle();
			} else if (method == 2) {
				game.runHumanVsMachine();
			}

			System.out.print("\nDo you want to play again?! y/n  > ");
			choice = sc.nextLine();
			if (!choice.equals("y")) {
				System.out.println("GOOD BYE! SEE YOU AGAIN!");
				break;
			}
		}
	}
}
