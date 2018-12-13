package sudoku_solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class VarBox implements Box {
	
	private final IntVar var; 

	public VarBox(int rowIdx, int colIdx, Model model) {
		var = model.intVar("" + rowIdx + "" + colIdx, 1, 9);
	}


	@Override
	public IntVar getVar() {
		return var;
	}


	@Override
	public String toString(boolean isSolve) {
		if ( isSolve) {
			return "" + var.getValue();
		}
		else {
			return " ";
		}
	}

}
