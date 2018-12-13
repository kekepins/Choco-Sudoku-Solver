package sudoku_solver;

import org.chocosolver.solver.variables.IntVar;

interface Box {
	public IntVar getVar();

	public String toString(boolean isSolve);
}
