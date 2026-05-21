
def initially_feasible(tableau):
    for eq in tableau[1:]:
        if eq[-1]<0:
            return False
    return True

def form(cube):
    l=len(cube)-1
    lis=[0]*l
    first_l=len(cube[0])
    cube[0].extend([0]*(l+1))

    for i in range(len(cube)-1):
        tem=cube[i+1][-1]
        cube[i+1][-1]=0
        cube[i+1].extend(lis)
        cube[i+1][-1]=tem
        cube[i+1][i+first_l]=1
    return cube

def readfile(filepath):
    cube=[]
    with open(filepath,'r') as f:
        for line in f:
            line=list(line.strip('\n').split())
            line=[Fraction(x) for x in line]
            cube.append(line)
    return cube


def improved(tableau):
    z = tableau[0]
    for x in z[:-1]:
        if x > 0:
            return True
    return False


def get_pivot_position_lar(tableau, impr, basis_ind):
    ta = np.asarray(tableau)
    columns = np.where(ta[0][:-1] > 0)[0].tolist()
    constr = ta[1:].T[columns]
    if min(np.max(constr, axis=1)) <= 0:
        print("unbounded")
        return False
    if impr == 0:
        res = constr[0]
        const = ta[1:, -1]
        ind1 = res > 0
        ind2 = (res <= 0)
        res[ind1] = 1 / res[ind1] * const[ind1]
        res[ind2] += math.inf
        row = np.where(res == min(res))[0][0] + 1
        return row, columns[0]
    else:
        const = ta[1:, -1]  # constants
        objv = ta[0][:-1][columns]
        ind1 = constr > 0
        ind2 = constr <= 0
        res1 = constr
        reconst = np.tile(const, (len(ind1), 1))
        res1[ind1] = 1 / res1[ind1] * reconst[ind1]
        res1[ind2] += math.inf
        minres1 = np.min(res1, 1)
        res2 = minres1 * objv
        res3 = np.where(res2 == max(res2))
        coli = res3[0][0]
        col = columns[coli]
        row = np.where(res1[coli] == min(res1[coli]))[0][0] + 1
        return row, col


def pivot_step(tableau, pivot_position):
    new_tableau = [[] for eq in tableau]
    i, j = pivot_position
    pivot_value = tableau[i][j]
    new_tableau[i] = np.array(tableau[i]) / pivot_value
    for eq_i, eq in enumerate(tableau):
        if eq_i != i:
            multiplier = np.array(new_tableau[i]) * tableau[eq_i][j]
            new_tableau[eq_i] = np.array(tableau[eq_i]) - multiplier
    return new_tableau


def get_solution(tableau, basis_ind):
    solutions = [0] * (len(tableau[0]) - 1)
    columns = np.array(tableau).T
    x = list(range(0, len(tableau[0]) - 1))
    for i in x:
        solution = 0
        if i in basis_ind:
            one_index = columns[i].tolist().index(1)
            solution = columns[-1][one_index]
        solutions[i] = float(solution)
    return solutions

def improved_dualsimplex(tableau):
    for eq in tableau[1:]:
        if eq[-1]<0:
            return True
    return False

##largest increase rule + bland's rule + improvement
def get_pivot_position_dualsimplex_5_3(cube, nonbasis_ind, basis_ind, impro):
    obj = np.asarray(cube[0])
    c1 = np.array(cube[1:]).T.tolist()[-1]  # just the constraint constants
    c1_a = np.asarray(c1)
    rows_1 = np.where(c1_a < 0)[0]
    rows = (rows_1 + 1).tolist()
    ar_basis = np.asarray(basis_ind)
    rows_projection = ar_basis[rows_1]
    sort_nonbasis = sorted(nonbasis_ind)
    objv = obj[sort_nonbasis]  # ordered nonbasis index
    ta = np.asarray(cube)
    cube_extr = ta[rows]  # [[one row],[]]
    snon = list(range(len(sort_nonbasis)))

    cube_costr = cube_extr.T[sort_nonbasis]  # first row is first column
    if max(np.min(cube_costr,
                  axis=0)) >= 0:  # as long as there is one column that has minimum coefficient that is >=0, then it is unbounded
        print("infeasible")
        return False
    if impro == 0:
        inde = min(rows_projection)  # bland's rule in dual
        the_row_po = np.where(ar_basis == inde)[0][0] + 1
        rows_a = np.asarray(rows)
        ind_r = np.where(rows_a == the_row_po)[0][0]
        tem = cube_costr[:, ind_r]
        tem1 = tem[snon]
        ind1 = tem1 < 0
        ind2 = tem1 >= 0
        tem1[ind1] = 1 / tem1[ind1] * objv[ind1]
        tem1[ind2] += math.inf
        coli = np.where(tem1 == min(tem1))[0][0]
        col = sort_nonbasis[coli]
        return the_row_po, col
    else:
        cube_costr_t = cube_costr.T  # get back to row
        ind1 = cube_costr_t < 0
        ind2 = cube_costr_t >= 0
        res1 = cube_costr_t
        reconst = np.tile(objv, (len(ind1), 1))
        res1[ind1] = 1 / res1[ind1] * reconst[ind1]
        res1[ind2] += math.inf
        the_consts = c1_a.T[rows_1]
        tem = np.min(res1, 1) * the_consts * (-1)
        tem1 = max(tem)
        the_row_ind = np.where(tem == tem1)[0][0]
        the_row_po = rows[the_row_ind]
        con = res1[the_row_ind]
        coli = np.where(con == min(con))[0][0]
        col = sort_nonbasis[coli]
        return the_row_po, col


def getnewobjec(cube,oriobext,basis_ind):
    original_obj=oriobext
    new_obj=original_obj
    for i in range(len(cube[0])-len(cube)):# 0,1,2
        if i in basis_ind and oriobext[i]!=0:
            r=basis_ind.index(i)+1
            pvalue=cube[r][i]
            new_obj1= np.array(cube[r]) / pvalue
            multi= np.array(new_obj1) * oriobext[i]
            new_obj = np.array(new_obj)-multi
    return new_obj


def verify(solu, oricube):
    a = np.asarray(oricube[1:])
    b = a[:, -1]
    c = a[:, :-1]
    d = np.dot(c, np.asarray(solu).T)
    sub = b - d
    if len(np.where(sub < 0)) - 1 > 0:
        return False
    else:
        return True


import sys
import math
import numpy as np
from fractions import Fraction
import random
import time
import re


# filename='./input2/netlib_share1b.txt'
# # filename='./input/optimal_3x3_1.txt'
# ori=readfile(filename)
# cube=form(readfile(filename))

ori = []
cube0=[]
while True:
        try:
            _input = input()
            # print("_input type",type(_input))
        except EOFError:
            break

        temp = re.findall(r"[-+]?(?:\d*\.\d+|\d+)", _input)
        temp1 = re.findall(r"[-+]?(?:\d*\.\d+|\d+)", _input)
        if len(temp) > 0:
            temp = [Fraction(i) for i in temp]
            temp1 = [Fraction(i) for i in temp1]
            ori.append(temp)
            cube0.append(temp1)
cube=form(cube0)







oriob=ori[0]
oriobext=cube[0]
ononbasis_ind=[]
obasis_ind=[]

for i in range(len(ori[0])):
    ononbasis_ind.append(i)
for i in range(len(cube[0])-1-len(ori[0])):
    obasis_ind.append(i+len(ononbasis_ind))
# start=time.time()
if initially_feasible(cube) == False:
    trans = np.array(cube[1:]).T.tolist()[:-1]
    nonbasis_ind = ononbasis_ind
    basis_ind = obasis_ind
    # print("not initially feasible")
    l1 = [-1] * len(nonbasis_ind)
    l1.extend([0] * (len(basis_ind) + 1))

    cube[0] = l1  # modify objective function
    impro=1
    while improved_dualsimplex(cube):
        pivot_position = get_pivot_position_dualsimplex_5_3(cube, nonbasis_ind, basis_ind, impro)
        if pivot_position == False:  # infeasible
            break
        tem = nonbasis_ind[nonbasis_ind.index(pivot_position[1])]
        nonbasis_ind[nonbasis_ind.index(pivot_position[1])] = basis_ind[pivot_position[0] - 1]
        basis_ind[pivot_position[0] - 1] = tem
        prev = cube[0][-1]
        cube = pivot_step(cube, pivot_position)
        impro=cube[0][-1]-prev

    cube[0] = getnewobjec(cube, oriobext, basis_ind)
    if pivot_position != False:
        # print("finished initializaton!")
        imp = 1
        while improved(cube):
            pivot_position = get_pivot_position_lar(cube,imp,basis_ind)
            if pivot_position == False:
                break
            tem = nonbasis_ind[nonbasis_ind.index(pivot_position[1])]
            nonbasis_ind[nonbasis_ind.index(pivot_position[1])] = basis_ind[pivot_position[0] - 1]
            basis_ind[pivot_position[0] - 1] = tem
            prev = cube[0][-1]
            cube = pivot_step(cube, pivot_position)
            imp = cube[0][-1] - prev

else:
    imp = 1
    # print("initially feasible")
    nonbasis_ind = ononbasis_ind
    basis_ind = obasis_ind
    while improved(cube):
        pivot_position = get_pivot_position_lar(cube, imp, basis_ind)
        if pivot_position == False:
            break
        tem = nonbasis_ind[nonbasis_ind.index(pivot_position[1])]
        nonbasis_ind[nonbasis_ind.index(pivot_position[1])] = basis_ind[pivot_position[0] - 1]
        basis_ind[pivot_position[0] - 1] = tem
        prev = cube[0][-1]
        cube = pivot_step(cube, pivot_position)
        imp = cube[0][-1] - prev

# end=time.time()
# print("running time:",end-start)
if pivot_position != False:
    re = get_solution(cube, basis_ind)
    print("optimal")
    print(float(-cube[0][-1]))
    print(*re[:len(cube[0]) - len(cube)])
    solution=re[:len(cube[0]) - len(cube)]
    # print(verify(solution,ori))