package sudoku_solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class NumberBox implements Box{
	
	private final int num;
	private final IntVar var;


	public NumberBox(char val, int rowIdx, int colIdx, Model model) {
		num = Character.getNumericValue(val);
		var = model.intVar(num);
	}


	@Override
	public IntVar getVar() {
		return var;
	}

	@Override
	public String toString(boolean isSolve) {
		return "" + num;
	}

	
}
