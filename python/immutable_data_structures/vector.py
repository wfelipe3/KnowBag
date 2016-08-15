from pyrsistent import pvector, v

vec = pvector([1,2,3])
print(vec)

assert vec == v(1,2,3)
assert vec[2] == 3
newv = vec.append(4)
assert vec == v(1,2,3)
assert newv == v(1,2,3,4)
mv = newv.set(0, "hello world")
assert mv == v("hello world", 2,3,4)
assert newv == v(1,2,3,4)

from pyrsistent import pmap, m

d = pmap({1:1, 'foo':"test"})
print(d)
print(d[1])
print(d['foo'])
newd = d.set('foo', 'bar')
m(b=1, foo='test')
assert d['foo'] == 'test'
assert newd['foo'] == 'bar'

from pyrsistent import PClass, field

class Point(PClass):
    x = field()
    y = field()

    def transalte(self, x_trans, y_trans):
        return self.set(x=self.x + x_trans, 
                        y=self.y + y_trans)

p = Point(x=12, y=15)
print(p.transalte(3,43))

try: p.x = 10
except Exception as e: print(e)

pnew = p.set('x', 10)
print("{p1}:{p2}".format(p1= pnew, p2= p))

print(p.serialize())

import timeit

def bm_copy_and_append(size):
    l = list(range(size))
    def run(): 
        l.append('foo bar')
    return timeit.timeit(run)

def bm_pyrsist_append(size):
    vec = pvector(range(size))
    def run():
        return vec.append('foo bar')
    return timeit.timeit(run)

print(bm_copy_and_append(10000000))
print(bm_pyrsist_append(10000000))

