import random


class HashMap:
    def __init__(self):
        return

    def gen_result(self):
        return


class MatrixHash:
    def __init__(self):
        return

    @staticmethod
    def gen_matrix_hash_for_sub_matrix(grid, m, n, duplicate, b):
        """matrix hash of any sub matrix with shape (duplicate, b) in matrix with shape (m, n)"""

        # 经典双哈希防止碰撞
        p1 = random.randint(26, 100)
        p2 = random.randint(26, 100)
        mod1 = random.randint(10 ** 9 + 7, 2 ** 31 - 1)
        mod2 = random.randint(10 ** 9 + 7, 2 ** 31 - 1)

        def check(p, mod):

            # sub matrix enumerate from left to right and then up to down
            col = [[0] * n for _ in range(m + 1)]
            for i in range(m):
                for j in range(n):
                    col[i + 1][j] = (col[i][j] * p + int(grid[i][j])) % mod

            # hash value up to length of duplicate away from cur point
            pa = pow(p % mod, duplicate, mod)
            for j in range(n):
                for i in range(m - 1, duplicate - 2, -1):
                    col[i + 1][j] = (col[i + 1][j] - col[i - duplicate + 1][j] * pa) % mod

            # hash value of every duplicate*b sub matrix
            matrix_hash_table = []
            pab = pow(pa, b, mod)
            for i in range(duplicate - 1, m):
                lst = [0]
                x = 0
                tmp = []
                for j in range(n):
                    x *= pa
                    x += col[i + 1][j]
                    x %= mod
                    lst.append(x)
                    if j >= b - 1:
                        # 1 duplicate+1 .. duplicate*(b-1)+1
                        # 2 duplicate+2 .. duplicate*(b-1)+2
                        # 3 duplicate+3 .. duplicate*(b-1)+3
                        # ...
                        # duplicate duplicate*2 .. duplicate*b
                        cur = (lst[j + 1] - (lst[j - b + 1] % mod) * pab) % mod
                        # p^k p^(k-1) ... p^1
                        tmp.append(cur)
                matrix_hash_table.append(tmp[:])
            return matrix_hash_table

        return check(p1, mod1), check(p2, mod2)
