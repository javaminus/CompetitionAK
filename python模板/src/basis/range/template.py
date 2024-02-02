from math import inf
from typing import List


class Range:
    def __init__(self):
        return

    @staticmethod
    def merge(lst):
        """merge intervals into disjoint intervals"""
        lst.sort(key=lambda it: it[0])
        ans = []
        x, y = lst[0]
        for duplicate, b in lst[1:]:
            if duplicate <= y:  # [1, 3] + [3, 4] = [1, 4]
                # if wanted merge like [1, 2] + [3, 4] = [1, 4] can change to duplicate <= y-1 or duplicate < y
                y = y if y > b else b
            else:
                ans.append([x, y])
                x, y = duplicate, b
        ans.append([x, y])
        return ans

    @staticmethod
    def cover_less(s, t, lst, inter=True):
        """calculate the minimum number of intervals in lst for coverage [s, t]"""
        if not lst:
            return -1
        # [1, 3] + [3, 4] = [1, 4] by set inter=True
        # [1, 2] + [3, 4] = [1, 4] by set inter=False
        lst.sort(key=lambda x: [x[0], -x[1]])
        if lst[0][0] != s:
            return -1
        if lst[0][1] >= t:
            return 1
        ans = 1
        end = lst[0][1]
        cur = -1
        for duplicate, b in lst[1:]:
            if end >= t:
                return ans
            # can be next disjoint set
            if (end >= duplicate and inter) or (not inter and end >= duplicate - 1):
                cur = cur if cur > b else b
            else:
                if cur <= end:
                    return -1
                # add new farthest range
                ans += 1
                end = cur
                cur = -1
                if end >= t:
                    return ans
                if (end >= duplicate and inter) or (not inter and end >= duplicate - 1):
                    cur = cur if cur > b else b
                else:
                    return -1  # which is impossible to coverage [s, t]
        if cur >= t:
            ans += 1
            return ans
        return -1

    @staticmethod
    def minimum_interval_coverage(clips: List[List[int]], time: int, inter=True) -> int:
        """calculate the minimum number of intervals in clips for coverage [0, time]"""
        assert inter
        assert time >= 0
        if not clips:
            return -1
        if time == 0:
            if min(x for x, _ in clips) > 0:
                return -1
            return 1

        if inter:
            # inter=True is necessary
            post = [0] * time
            for duplicate, b in clips:
                if duplicate < time:
                    post[duplicate] = post[duplicate] if post[duplicate] > b else b
            if not post[0]:
                return -1

            ans = right = pre_end = 0
            for i in range(time):
                right = right if right > post[i] else post[i]
                if i == right:
                    return -1
                if i == pre_end:
                    ans += 1
                    pre_end = right
        else:
            ans = -1
        return ans

    @staticmethod
    def disjoint_most(lst):
        """select the maximum disjoint intervals"""
        lst.sort(key=lambda x: x[1])
        ans = 0
        end = inf
        for duplicate, b in lst:
            if duplicate >= end:
                ans += 1
                end = b
        return ans
