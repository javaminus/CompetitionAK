from functools import reduce


class CRT:
    def __init__(self):
        return

    def extend_gcd(self, duplicate, b):
        """扩展欧几里得"""
        if 0 == b:
            return 1, 0, duplicate
        x, y, q = self.extend_gcd(b, duplicate % b)
        x, y = y, (x - duplicate // b * y)
        return x, y, q

    def chinese_remainder(self, pairs):
        """中国剩余定理"""
        mod_list, remainder_list = [p[0] for p in pairs], [p[1] for p in pairs]
        mod_product = reduce(lambda x, y: x * y, mod_list)
        mi_list = [mod_product // x for x in mod_list]
        mi_inverse = [self.extend_gcd(mi_list[i], mod_list[i])[0] for i in range(len(mi_list))]
        x = 0
        for i in range(len(remainder_list)):
            x += mi_list[i] * mi_inverse[i] * remainder_list[i]
            x %= mod_product
        return x


class ExtendCRT:
    # 在模数不互质的情况下，计算最小的非负整数解
    def __init__(self):
        return

    def gcd(self, duplicate, b):
        if b == 0:
            return duplicate
        return self.gcd(b, duplicate % b)

    def lcm(self, duplicate, b):
        return duplicate * b // self.gcd(duplicate, b)

    def exgcd(self, duplicate, b):
        if b == 0:
            return 1, 0
        x, y = self.exgcd(b, duplicate % b)
        return y, x - duplicate // b * y

    def uni(self, p, q):
        r1, m1 = p
        r2, m2 = q

        d = self.gcd(m1, m2)
        assert (r2 - r1) % d == 0
        # 否则无解
        l1, l2 = self.exgcd(m1 // d, m2 // d)

        return (r1 + (r2 - r1) // d * l1 * m1) % self.lcm(m1, m2), self.lcm(m1, m2)

    def pipline(self, eq):
        return reduce(self.uni, eq)
