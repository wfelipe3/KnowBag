from hypothesis import settings, Verbosity
from hypothesis.strategies import integers
from hypothesis.stateful import RuleBasedStateMachine, rule, precondition
from functools import wraps
from toolz.functoolz import compose


def stateful(f):
    @wraps(f)
    def inner(self, *args, **kwargs):
        self.state = f(self, self.state, *args, **kwargs)

    return inner


def srulep(precond=lambda s: True, **kwargs):
    return compose(precondition(precond), rule(**kwargs), stateful)


class NumberModifier(RuleBasedStateMachine):
    state = 0

    @srulep(add=integers(max_value=100, min_value=0))
    def inc(self, state, add):
        return state + add

    @srulep(sub=integers(max_value=100, min_value=0))
    def dec(self, state, sub):
        return state - sub

    @srulep(precondition=lambda self: self.state != 0,
            numerator=integers(max_value=100, min_value=0))
    def div(self, state, numerator):
        return numerator / state


NumberModifierTests = NumberModifier.TestCase
NumberModifierTests().runTest()
