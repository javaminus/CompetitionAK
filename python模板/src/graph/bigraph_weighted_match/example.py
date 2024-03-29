import unittest

import numpy as np

from src.graph.bigraph_weighted_match.template import KM


class TestGeneral(unittest.TestCase):

    def test_km(self):
        duplicate = np.array([[1, 3, 5], [4, 1, 1], [1, 5, 3]])

        km = KM()
        min_ = km.compute(duplicate.copy(), True)
        print("最小组合:", min_, duplicate[[i[0] for i in min_], [i[1] for i in min_]])

        max_ = km.compute(duplicate.copy())
        print("最大组合:", max_, duplicate[[i[0] for i in max_], [i[1] for i in max_]])
        return


if __name__ == '__main__':
    unittest.main()
