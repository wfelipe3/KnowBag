from hypothesis import given
from hypothesis import strategies as st
from hypothesis import example
from operator import add
from functools import reduce


def add2(num):
    return num + 2


def test_adds_2():
    assert add2(0) == 2
    assert add2(5) == 7
    assert add2(-5) == -3


test_adds_2()


@given(st.integers())
def test_adds_2(num):
    assert add2(num) == num + 2


test_adds_2()


def mysum(nums):
    return reduce(add, nums, 0)


def test_mysum_plain():
    assert mysum([1, 2, 3]) == 6


test_mysum_plain()


@given(st.lists(st.integers()))
def test_mysum(nums):
    assert mysum(nums) == sum(nums)


test_mysum()


class Herd(object):
    def __init__(self, animal, number):
        self.animal = animal
        self.number = number

    def __repr__(self):
        return "<Herd of animal=%s number=%d>" % (self.animal, self.number)

    def __eq__(self, other):
        return (type(self) is type(other)
                and self.animal == other.animal
                and self.number == other.number)


animals = st.sampled_from(['moose', 'cow', 'cat'])
herds = st.builds(Herd, animals, st.integers(min_value=0))
print(herds.example())


def parse_herd(s):
    try:
        name, num = s.split(',')
        num = int(num)
    except ValueError:
        raise SyntaxError()
    return Herd(name, num)


def serialize_herd(herd):
    return '%s,%d' % (herd.animal, herd.number)


@given(herds)
def test_herd_roundtrip(herd):
    assert parse_herd(serialize_herd(herd)) == herd


test_herd_roundtrip()


@given(st.text())
def fuzz_parser(s):
    try:
        parse_herd(s)
    except SyntaxError:
        pass


fuzz_parser()


def guess_number(num):
    """Returns a message and how far away you are"""
    if num == answer:
        return "you got it right", 0
    else:
        return "Sorry", answer - num


answer = 35273


@given(st.integers())
@example(answer)
def test_guess_number(num):
    print(num)
    assert guess_number(num)[1] == answer - num


test_guess_number()
