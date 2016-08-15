tuple = (12, "test", True)
age, name, male = tuple

assert age is 12
assert name is "test"
assert male is True

list = [1, 2, 3, 4, 5]

list.append(6)

assert 6 in list

disctint_values = {1, 2, 3, 2, 5}

assert len(disctint_values) is 4
assert len(set(list)) is 6

assert 2 in disctint_values
assert 10 not in disctint_values

prices = {
    'python': 1,
    'java': 2,
    'scala': 3
}

assert prices['python'] is 1
assert prices['java'] is 2
assert 'scala' in prices

