from toolz.functoolz import compose
from toolz.functoolz import curry
from toolz.functoolz import identity, memoize


def g(x):
    return x + 10


def f(x):
    return x * 5


def composed(x):
    return f(g(x))


assert composed(3) == 65

assert compose(f, g)(3) == 65
assert compose(g, f)(3) == 25

map(compose(f, g), [1, 2, 3])


@curry
@memoize
def plus(x, y):
    print("the first time")
    return x + y


print(plus(1, 2))
print(plus(1))
print(plus(1)(2))


def show(values):
    [print(i) for i in values]


def value(x):
    return "the value is {x}".format(x=x)


show_value = compose(value, plus(1))
show(map(show_value, [1, 2, 3]))
show(map(show_value, [1, 2, 3]))
show(map(compose(value, identity), [2, 3, 4]))

i = iter([1, 2, 3])
# i[2] this does not work

assert next(i);
next(i);
next(i) == 3

from toolz.itertoolz import nth, last, drop, take, tail, groupby, interpose, first

i = iter([1, 2, 3])
assert nth(1, i) == 2
assert next(i) == 3
assert last([1, 2, 3]) == 3

assert list(drop(2, iter([1, 2, 3]))) == [3]
assert list(take(2, [1, 2, 3])) == [1, 2]
assert first("abcde") == "a"
assert groupby(first, ["ABC", "ABA", "BAB", "BAA"]) == {"A": ["ABC", "ABA"], "B": ["BAB", "BAA"]}
assert groupby(len, ["ABC", "ABA", "BAB", "BAA"]) == {3: ["ABC", "ABA", "BAB", "BAA"]}
assert list(interpose("foo", [1, 2, 3])) == [1, "foo", 2, "foo", 3]
assert tail(3, [1, 2, 3, 4]) == [2, 3, 4]

from toolz.dicttoolz import merge, assoc, dissoc, get_in

d1 = {'foo': 'bar'}
d2 = {'baz': 'quux'}

new_d = d1.copy()
new_d.update(d2)

assert new_d == {'foo': 'bar', 'baz': 'quux'}
assert merge(d1, d2) == {'foo': 'bar', 'baz': 'quux'}
assert assoc(d1, 'a', 1) == {'foo': 'bar', 'a': 1}
assert dissoc(d2, 'baz') == {}
struct = {'a': [{'c': 'hello'}]}
assert get_in(['a', 0, 'c'], struct) == struct['a'][0]['c'] == 'hello'
assert get_in(['b', 0, 'c'], struct, "not found") == "not found"
