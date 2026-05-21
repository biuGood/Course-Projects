# Solving Linear Programming Problems | Python

This project implements a Linear Programming (LP) solver in Python. The solver reads LP problem instances from standard input, determines whether each problem is infeasible, unbounded, or feasible and bounded, and outputs the optimal objective value and solution when an optimum exists.

## Project Overview

Linear Programming is used to optimize a linear objective function subject to linear constraints. This project implements a simplex-based solver that can handle both initially feasible and initially infeasible LP problems.

The solver supports:

- Detecting whether an LP is initially feasible
- Solving feasible LPs using simplex pivot operations
- Detecting unbounded LP problems
- Handling initially infeasible LPs using a dual-simplex / two-phase style approach
- Outputting the optimal objective value and decision variable solution when the LP is feasible and bounded

## Tools Used

- Python
- NumPy
- Linux command line
- Simplex method
- Dual simplex method
- Fraction-based arithmetic for input parsing

## Solver Architecture

### 1. Input Parsing

The solver reads LP problem data from standard input. Input files can be passed through the Linux command line using redirection.

Example:

```bash
python3 lp.py < input_file.txt
