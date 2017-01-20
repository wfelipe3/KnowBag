def random(seed=0):
    seed = ((seed * 214013) + 2531011) % (2 ** 32)
    return seed, lambda: random(seed)


def randomt(times=0, seed=0):
    if times is 0:
        return seed
    else:
        return randomt(times - 1, random(seed)[0])


print(randomt(times=19))
print(randomt(times=100))
