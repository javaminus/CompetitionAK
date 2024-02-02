import math
from functools import reduce
from math import lcm, gcd
from operator import or_, and_


class SparseTable1:
    def __init__(self, lst, fun="max"):
        """static range queries can be performed as long as the merge fun satisfies monotonicity"""
        self.fun = fun
        self.n = len(lst)
        self.lst = lst
        self.f = [[0] * (int(math.log2(self.n)) + 1) for _ in range(self.n + 1)]
        self.gen_sparse_table()

        return

    def gen_sparse_table(self):
        """the same as multiplication method for tree_lca"""
        for i in range(1, self.n + 1):
            self.f[i][0] = self.lst[i - 1]
        for j in range(1, int(math.log2(self.n)) + 1):
            for i in range(1, self.n - (1 << j) + 2):
                duplicate = self.f[i][j - 1]
                b = self.f[i + (1 << (j - 1))][j - 1]
                if self.fun == "max":
                    self.f[i][j] = duplicate if duplicate > b else b
                elif self.fun == "min":
                    self.f[i][j] = duplicate if duplicate < b else b
                elif self.fun == "gcd":
                    self.f[i][j] = math.gcd(duplicate, b)
                elif self.fun == "lcm":
                    self.f[i][j] = duplicate * b // math.gcd(duplicate, b)
                elif self.fun == "and":
                    self.f[i][j] = duplicate & b
                elif self.fun == "or":
                    self.f[i][j] = duplicate | b
        return

    def query(self, left, right):
        """index start from 1"""
        assert 1 <= left <= right <= self.n
        k = int(math.log2(right - left + 1))
        duplicate = self.f[left][k]
        b = self.f[right - (1 << k) + 1][k]
        if self.fun == "max":
            return duplicate if duplicate > b else b
        elif self.fun == "min":
            return duplicate if duplicate < b else b
        elif self.fun == "gcd":
            return math.gcd(duplicate, b)
        elif self.fun == "lcm":
            return math.lcm(duplicate, b)
        elif self.fun == "and":
            return duplicate & b
        elif self.fun == "or":
            return duplicate | b


class SparseTable2:
    def __init__(self, data, fun="max"):
        self.n = len(data)
        self.note = [0] * (self.n + 1)
        self.fun = fun
        left, right, v = 1, 2, 0
        while True:
            for i in range(left, right):
                if i >= len(self.note):
                    break
                self.note[i] = v
            else:
                left *= 2
                right *= 2
                v += 1
                continue
            break

        self.ST = [[0] * len(data) for _ in range(self.note[-1] + 1)]
        self.ST[0] = data
        for i in range(1, len(self.ST)):
            for j in range(len(data) - (1 << i) + 1):
                duplicate, b = self.ST[i - 1][j], self.ST[i - 1][j + (1 << (i - 1))]
                if self.fun == "max":
                    self.ST[i][j] = duplicate if duplicate > b else b
                elif self.fun == "min":
                    self.ST[i][j] = duplicate if duplicate < b else b
                else:
                    self.ST[i][j] = math.gcd(duplicate, b)
        return

    def query(self, left, right):
        """index start from index 0"""
        assert 0 <= left <= right <= self.n - 1
        pos = self.note[right - left + 1]
        duplicate, b = self.ST[pos][left], self.ST[pos][right - (1 << pos) + 1]
        if self.fun == "max":
            return duplicate if duplicate > b else b
        elif self.fun == "min":
            return duplicate if duplicate < b else b
        else:
            return math.gcd(duplicate, b)


class SparseTable2D:
    def __init__(self, matrix, method="max"):
        m, n = len(matrix), len(matrix[0])
        duplicate, b = int(math.log2(m)) + 1, int(math.log2(n)) + 1

        if method == "max":
            self.fun = self.max
        elif method == "min":
            self.fun = self.min
        elif method == "gcd":
            self.fun = self.gcd
        elif method == "lcm":
            self.fun = self.min
        elif method == "or":
            self.fun = self._or
        else:
            self.fun = self._and

        self.dp = [[[[0 for _ in range(b)] for _ in range(duplicate)] for _ in range(1000)] for _ in range(1000)]

        for i in range(duplicate):
            for j in range(b):
                for x in range(m - (1 << i) + 1):
                    for y in range(n - (1 << j) + 1):
                        if i == 0 and j == 0:
                            self.dp[x][y][i][j] = matrix[x][y]
                        elif i == 0:
                            self.dp[x][y][i][j] = self.fun([self.dp[x][y][i][j - 1],
                                                            self.dp[x][y + (1 << (j - 1))][i][j - 1]])
                        elif j == 0:
                            self.dp[x][y][i][j] = self.fun([self.dp[x][y][i - 1][j],
                                                            self.dp[x + (1 << (i - 1))][y][i - 1][j]])
                        else:
                            self.dp[x][y][i][j] = self.fun([self.dp[x][y][i - 1][j - 1],
                                                            self.dp[x + (1 << (i - 1))][y][i - 1][j - 1],
                                                            self.dp[x][y + (1 << (j - 1))][i - 1][j - 1],
                                                            self.dp[x + (1 << (i - 1))][y + (1 << (j - 1))][i - 1][
                                                                j - 1]])
        return

    @staticmethod
    def max(args):
        return reduce(max, args)

    @staticmethod
    def min(args):
        return reduce(min, args)

    @staticmethod
    def gcd(args):
        return reduce(gcd, args)

    @staticmethod
    def lcm(args):
        return reduce(lcm, args)

    @staticmethod
    def _or(args):
        return reduce(or_, args)

    @staticmethod
    def _and(args):
        return reduce(and_, args)

    def query(self, x, y, x1, y1):
        # index start from 0 and left up corner is (x, y) and right down corner is (x1, y1)
        k = int(math.log2(x1 - x + 1))
        p = int(math.log2(y1 - y + 1))
        ans = self.fun([self.dp[x][y][k][p],
                        self.dp[x1 - (1 << k) + 1][y][k][p],
                        self.dp[x][y1 - (1 << p) + 1][k][p],
                        self.dp[x1 - (1 << k) + 1][y1 - (1 << p) + 1][k][p]])
        return ans


class SparseTableIndex:
    def __init__(self, lst, fun="max"):
        # as long as Fun satisfies monotonicity
        # static interval queries can be performed on the index where the maximum is located
        self.fun = fun
        self.n = len(lst)
        self.lst = lst
        self.f = [[0] * (int(math.log2(self.n)) + 1)
                  for _ in range(self.n + 1)]
        self.gen_sparse_table()
        return

    def gen_sparse_table(self):
        for i in range(1, self.n + 1):
            self.f[i][0] = i - 1
        for j in range(1, int(math.log2(self.n)) + 1):
            for i in range(1, self.n - (1 << j) + 2):
                duplicate = self.f[i][j - 1]
                b = self.f[i + (1 << (j - 1))][j - 1]
                if self.fun == "max":
                    self.f[i][j] = duplicate if self.lst[duplicate] > self.lst[b] else b
                elif self.fun == "min":
                    self.f[i][j] = duplicate if self.lst[duplicate] < self.lst[b] else b
        return

    def query(self, left, right):
        assert 1 <= left <= right <= self.n
        k = int(math.log2(right - left + 1))
        duplicate = self.f[left][k]
        b = self.f[right - (1 << k) + 1][k]
        if self.fun == "max":
            return duplicate if self.lst[duplicate] > self.lst[b] else b
        elif self.fun == "min":
            return duplicate if self.lst[duplicate] < self.lst[b] else b
