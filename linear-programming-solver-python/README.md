# Solving Linear Programming Problems | Python

This project implements a Linear Programming (LP) solver in Python. The solver runs from the Linux command line, reads LP problem instances from standard input, and determines whether each problem is infeasible, unbounded, or feasible and bounded. If an optimal solution exists, the solver outputs the optimal objective value and the corresponding solution vector.

## Project Overview

Linear Programming is a mathematical optimization method used to optimize a linear objective function subject to a set of linear constraints.

This project implements a simplex-based LP solver that can handle:

- Initially feasible LP problems
- Initially infeasible LP problems
- Unbounded LP problems
- Feasible and bounded LP problems with optimal solutions

The solver uses simplex pivoting, feasibility checks, unboundedness detection, and a dual-simplex / two-phase style process for LPs that are not initially feasible.

## Tools and Technologies

- Python 3
- NumPy
- Linux command line
- Simplex method
- Dual simplex method
- Bland's rule
- Largest-increase pivot rule

## Files

| File | Description |
|---|---|
| `lp.py` | Main Python implementation of the LP solver |
| `README.md` | Project documentation |

## How to Run

The solver reads input from standard input.

Example command:

```bash
python3 lp.py < input_file.txt
```

Example using a test LP file:

```bash
python3 lp.py < ./test_LPs_volume1/input/vanderbei_exercise2.8.txt
```

## Input Format

The solver expects the LP problem to be provided as a text file containing numeric coefficients.

In general:

- The first row represents the objective function coefficients
- The following rows represent the constraints
- Each constraint row includes the coefficients and the right-hand-side value
- Values are read as fractions or numeric values

The program parses the input file line by line and constructs the simplex tableau internally.

## Output Format

The solver prints one of the following statuses:

```text
optimal
infeasible
unbounded
```

If the LP problem is optimal, the solver also prints:

1. The optimal objective value
2. The optimal solution vector

Example output:

```text
optimal
12.5
1.0 0.0 3.5
```

## Solver Architecture

The solver first checks whether the initial LP tableau is feasible.

### 1. Initial Feasibility Check

The solver checks the right-hand-side values of the constraints.

- If all constraint constants are non-negative, the LP is treated as initially feasible.
- If at least one constraint constant is negative, the LP is treated as initially infeasible.

This determines which solving path the algorithm follows.

## Case 1: Initially Feasible LP

If the LP is initially feasible, the solver applies the primal simplex method.

The process is:

1. Select an entering variable
2. Select a leaving variable
3. Perform a pivot step
4. Update the simplex tableau
5. Check whether the objective value improves
6. Continue until the LP is optimal or unbounded

### Pivot Selection

The solver mainly uses the largest-increase rule to choose pivot positions. This rule attempts to select the pivot that produces the largest improvement in the objective value.

If the objective value does not improve, the solver falls back to Bland's rule to reduce the risk of cycling caused by degeneracy.

### Unboundedness Detection

During pivot selection, the solver checks whether there is a valid leaving variable for the selected entering variable.

If no valid leaving variable exists, the LP is identified as unbounded.

## Case 2: Initially Infeasible LP

If the LP is not initially feasible, the solver applies a dual-simplex / two-phase style approach.

The process is:

1. Modify the original objective function to construct a feasible dual condition
2. Apply dual simplex pivoting on the modified primal LP
3. Choose entering and leaving variables in reverse order
4. Continue pivoting until a feasible point for the original LP is found
5. Reconstruct the original objective function
6. Continue solving using the primal simplex method

If the dual process indicates that the modified problem is unbounded, the original primal LP is identified as infeasible.

## Key Functions

### `initially_feasible(tableau)`

Checks whether the initial tableau is feasible by verifying that all constraint constants are non-negative.

### `form(cube)`

Constructs the simplex tableau by adding slack variables to the original LP structure.

### `improved(tableau)`

Checks whether the objective row still has positive coefficients, meaning the objective can still be improved.

### `get_pivot_position_lar(tableau, impr, basis_ind)`

Selects the pivot position for the primal simplex method using the largest-increase rule, with support for Bland's rule fallback.

### `pivot_step(tableau, pivot_position)`

Performs the simplex pivot operation and updates the tableau.

### `get_solution(tableau, basis_ind)`

Extracts the solution vector from the final simplex tableau.

### `improved_dualsimplex(tableau)`

Checks whether the dual-simplex phase still needs to continue by identifying negative constraint constants.

### `get_pivot_position_dualsimplex_5_3(cube, nonbasis_ind, basis_ind, impro)`

Selects the pivot position during the dual-simplex phase.

### `getnewobjec(cube, oriobext, basis_ind)`

Reconstructs the original objective function after the solver finds an initially feasible tableau.

## Algorithm Summary

The solver follows this general workflow:

```text
Read LP input
        |
        v
Build initial simplex tableau
        |
        v
Check initial feasibility
        |
        |-- Initially feasible --> Primal simplex method
        |
        |-- Initially infeasible --> Dual-simplex / two-phase initialization
                                      |
                                      v
                              Rebuild original objective
                                      |
                                      v
                              Primal simplex method
        |
        v
Detect final result:
optimal / infeasible / unbounded
```

## Extra Features

This implementation includes:

- Largest-increase pivot selection
- Bland's rule fallback for degeneracy and cycling risk
- Dual-simplex method for initially infeasible LPs
- Infeasibility detection
- Unboundedness detection
- Command-line execution using input redirection
- Fraction-based input parsing

## Skills Demonstrated

- Python programming
- Algorithm implementation
- Linear programming
- Simplex method
- Dual simplex method
- Optimization problem solving
- Numerical computation with NumPy
- Command-line program execution
- Feasibility and unboundedness analysis
- Debugging and handling edge cases

## Resume Summary

This project demonstrates experience implementing a Python-based Linear Programming solver capable of classifying LP problems as infeasible, unbounded, or optimal. The solver applies simplex and dual-simplex methods, uses pivot selection strategies, and outputs optimal solutions when the problem is feasible and bounded.
