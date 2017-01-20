class Random(object):
    def __init__(self, a, b, m):
        self.a = a
        self.b = b
        self.m = m
        self.seed = 0

    def randomp(self, seed):
        return (seed * self.a + self.b) % self.m

    def random(self):
        self.seed = self.randomp(self.seed)
        return self.seed


def randomt(r=Random(5, 3, 7), times=0, seed=0):
    if times is 0:
        return seed
    else:
        return randomt(r, times - 1, r.randomp(seed))


print(randomt(times=0))
print(randomt(times=1))
print(randomt(times=2))
print(randomt(times=5))
print(randomt(times=6))

print(randomt(r=Random(4, 3, 8), times=0))
print(randomt(r=Random(4, 3, 8), times=1))
print(randomt(r=Random(4, 3, 8), times=5))
print(randomt(r=Random(4, 3, 8), times=6))


def random_value(seed):
    seed = seed * 1140671485 + 12820163 % (2 ** 24)
    return seed, lambda: random_value(seed)


print("random value is {r}".format(r=random_value(0)))

v, next = random_value(0)
print(v, next()[0], next()[1]()[0])
