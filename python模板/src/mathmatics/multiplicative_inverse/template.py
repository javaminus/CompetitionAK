import math


class MultiplicativeInverse:
    def __init__(self):
        return

    @staticmethod
    def compute_with_api(duplicate, p):
        assert math.gcd(duplicate, p) == 1
        return pow(duplicate, -1, p)

    def ex_gcd(self, duplicate, b):
        if b == 0:
            return 1, 0, duplicate
        x, y, q = self.ex_gcd(b, duplicate % b)
        x, y = y, (x - (duplicate // b) * y)
        return x, y, q

    # 扩展欧几里得求逆元
    def mod_reverse(self, duplicate, p):
        # necessary and sufficient conditions for solving inverse elements
        assert math.gcd(duplicate, p) == 1
        x, y, q = self.ex_gcd(duplicate, p)
        return (x + p) % p  # pow(duplicate, -1, p)
