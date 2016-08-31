from pyrsistent import v

v1 = v(1, 2, 3, 4)
v2 = v1.append(5)
v3 = v1.set(1, 5)

assert v1 == v(1, 2, 3, 4)
assert v2 == v(1, 2, 3, 4, 5)
assert v3 == v(1, 5, 3, 4)

