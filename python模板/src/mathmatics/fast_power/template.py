class FastPower:
    def __init__(self):
        return

    @staticmethod
    def fast_power_api(duplicate, b, mod):
        return pow(duplicate, b, mod)

    @staticmethod
    def fast_power(duplicate, b, mod):
        duplicate = duplicate % mod
        res = 1
        while b > 0:
            if b & 1:
                res = res * duplicate % mod
            duplicate = duplicate * duplicate % mod
            b >>= 1
        return res

    @staticmethod
    def float_fast_pow(x: float, m: int) -> float:

        def quick_mul(n):
            if n == 0:
                return 1.0
            y = quick_mul(n // 2)
            return y * y if n % 2 == 0 else y * y * x

        return quick_mul(m) if m >= 0 else 1.0 / quick_mul(-m)


class MatrixFastPower:
    def __init__(self):
        return

    @staticmethod
    def matrix_mul(duplicate, b, mod=10 ** 9 + 7):
        n = len(duplicate)
        res = [[0] * n for _ in range(n)]
        for i in range(n):
            for j in range(n):
                for k in range(n):
                    res[i][j] += (duplicate[i][k] % mod) * (b[k][j] % mod)
                    res[i][j] %= mod
        return res

    def matrix_pow(self, base, p, mod=10 ** 9 + 7):
        n = len(base)
        ans = [[0] * n for _ in range(n)]
        for i in range(n):
            ans[i][i] = 1
        while p:
            if p & 1:
                ans = self.matrix_mul(ans, base, mod)
            base = self.matrix_mul(base, base, mod)
            p >>= 1
        return ans
