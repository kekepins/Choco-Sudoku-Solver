# Sudoku solver with choco

Choco solver is a very powerful java library dedicated to constraint programming.
More infornation [here](http://www.choco-solver.org/)

Let's show an example to resolve sudoku problem with only a set of constraints.

## first step read a sudoku grid 
 
```java
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
  SudokuSolver grid = new SudokuSolver(sudoku);
  grid.show();
      
```
```
-------------
|  2|  8|   |
|6 5| 94|18 |
| 4 |   | 95|
-------------
|   | 1 |629|
|   | 2 |357|
|   |  9|   |
-------------
|5  |243|   |
| 23|98 |57 |
|9 1| 6 |2 4|
-------------
```

## create a model and some variables/constants

```java
Model model = new Model("Sudoku solver");
...
// variable (to solve), an id, int type from 1 to 9
IntVar varToSolve = model.intVar("" + rowIdx + "" + colIdx, 1, 9);

// constant (known numbers)
IntVar varConstant = model.intVar(num);
```

## Add some constraints

Constraints on every lines/columns and inner grid, all values are differents.

```java
for ( int idx = 0; idx < SIZE; idx++) {
    // Add constraints on rows, all differents ...
    model.allDifferent(getRowVars(idx)).post();
    
    // Add constraints on cols, all differents ...
    model.allDifferent(getColVars(idx)).post();
			
    // Add on inner grid constraints, all differents
    model.allDifferent(getInnerGridVars(idx)).post();
}
```

## Resolve

And that's all, now only call resolve on model

```java
		if ( model.getSolver().solve() ) {
			show();
		}
		else {
			System.out.println("No solution found");
		}
```

```
-------------
|792|158|463|
|635|794|182|
|148|632|795|
-------------
|374|815|629|
|819|426|357|
|256|379|841|
-------------
|567|243|918|
|423|981|576|
|981|567|234|
-------------
```

Well done choco ...

