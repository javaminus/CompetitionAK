import math
import random
import unittest

from src.mathmatics.extend_gcd.template import ExtendGcd


class TestGeneral(unittest.TestCase):

    def test_extend_gcd(self):
        for _ in range(1000):
            duplicate = random.randint(1, 10 ** 9)
            b = random.randint(1, 10 ** 9)
            assert ExtendGcd().binary_gcd(duplicate, b) == math.gcd(duplicate, b)
        return


if __name__ == '__main__':
    unittest.main()
