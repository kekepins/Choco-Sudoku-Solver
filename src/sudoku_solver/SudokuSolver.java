package sudoku_solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SudokuSolver {
	
	private final int SIZE = 9;
		
	private Box[][] grid = new Box[SIZE][SIZE];
	
	private boolean isSolve = false;
	
	private Model model = new Model("Sudoku solver");
	
	public SudokuSolver(String[] arr) {
		parse(arr);
	}
	
	public void solve() {
		
		
		for ( int idx = 0; idx < SIZE; idx++) {
			// Add constraints on rows, all differents ...
			model.allDifferent(getRowVars(idx)).post();
			
			// Add constraints on cols, all differents ...
			model.allDifferent(getColVars(idx)).post();
			
			// Add on inner grid
			model.allDifferent(getInnerGridVars(idx)).post();
		}
		
		isSolve = model.getSolver().solve();
	
		if ( isSolve) {
			show();
		}
		else {
			System.out.println("No solution found");
		}
	}
	
	private IntVar[] getRowVars(int rowIdx) {
		IntVar[] res = new IntVar[SIZE];
		
		for ( int idx = 0; idx < SIZE; idx++) {
			res[idx] = grid[rowIdx][idx].getVar(); 
		}
		
		return res;
	}
	
	private IntVar[] getColVars(int colIdx) {
		IntVar[] res = new IntVar[SIZE];
		
		for ( int idx = 0; idx < SIZE; idx++) {
			res[idx] = grid[idx][colIdx].getVar(); 
		}
		
		return res;
	}
	
	/**
	 *  ----
	 *  |123|
	 *  |456|
	 *  |789|
	 *  ----- 
	 * 
	 */
	private IntVar[] getInnerGridVars(int idx) {
		IntVar[] res = new IntVar[SIZE];
		
		int toAddCol = (idx % 3) * 3;
		int toAddRow = (idx / 3) * 3;
		
		int idxRes = 0;
		for ( int idxRow = 0; idxRow < 3; idxRow++ ) {
			for ( int idxCol = 0; idxCol < 3; idxCol++ ) {
				res[idxRes++] = grid[idxRow + toAddRow][idxCol + toAddCol].getVar(); 
				
			}
			
		}
		
		return res;
	}

	
	private void parse(String[] arr) {
		int rowIdx = 0; 
		for ( String row : arr) {
			int colIdx = 0;
			for (char val : row.toCharArray() ) {
				
				if ( '*' != val ) {
					grid[rowIdx][colIdx] = new NumberBox(val, rowIdx, colIdx, model); 
				}
				else {
					grid[rowIdx][colIdx] = new VarBox(rowIdx, colIdx, model);
				}
				
				colIdx ++;
			}
			
			rowIdx ++;
		}
	}
	
	public void show() {

		int rowIdx = 0;
		for (Box[] row : grid) {
			
			if ( rowIdx % 3 == 0 ) {
				System.out.println("-------------");
			}
			int colIdx = 0;
			for( Box box : row) {
				if ( colIdx % 3 == 0 ) {
					System.out.print("|");
				}
				System.out.print(box.toString(isSolve));
				
				colIdx ++;
			}
			System.out.print("|" + System.lineSeparator());
			
			rowIdx ++;
		}
		
		System.out.println("-------------");
		
	}
	
	public static void main(String[] args ) {
		String[] sudoku = new String[] {
				"**2**8***",
				"6*5*9418*",
				"*4*****95",
				"****1*629",
				"****2*357",
				"*****9***",
				"5**243***",
				"*2398*57*",
				"9*1*6*2*4"
		};
		
		SudokuSolver grid = new SudokuSolver(sudoku);
		grid.show();
		grid.solve();
	}
}
