from effect import Effect, sync_perform, sync_performer
from effect import ComposedDispatcher, TypeDispatcher, base_dispatcher
from effect.do import do

"""
Effect documentation is here https://effect.readthedocs.io/en/latest/intro.html
"""


def compliment(name):
    return "oh, %s is a nice name" % (name,)


def main():
    print(compliment(input("Enter your name>")))


@do
def pure_main():
    name = yield Effect(Input("Enter your name >"))
    yield Effect(Print(compliment(name)))


class Input(object):
    def __init__(self, prompt):
        self.prompt = prompt

    def __eq__(self, other):
        return type(self) is type(other) and self.prompt == other.prompt


class Print(object):
    def __init__(self, message):
        self.message = message

    def __eq__(self, other):
        return type(self) is type(other) and self.message == other.message


@sync_performer
def perform_input(dispatcher, intent):
    return input(intent.prompt)


@sync_performer
def perform_print(dispatcher, intent):
    print(intent.message)


io = TypeDispatcher({
    Input: perform_input,
    Print: perform_print,
})

dispatcher = ComposedDispatcher([io, base_dispatcher])

# if __name__ == '__main__':
#     sync_perform(dispatcher, pure_main())

from effect.testing import SequenceDispatcher


def test_main():
    seq = SequenceDispatcher([
        (Input("Enter your name >"), lambda i: "Felipe"),
        (Print("oh, Felipe is a nice name"), lambda i: None),
    ])

    with seq.consume():
        sync_perform(ComposedDispatcher([seq, base_dispatcher]), pure_main())


test_main()
