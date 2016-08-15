"""Naval Fate.

Usage:
  naval_fate.py ship new <name>...
  naval_fate.py ship <name> move <x> <y> [--speed=<kn>]
  naval_fate.py ship shoot <x> <y>
  naval_fate.py mine (set|remove) <x> <y> [--moored | --drifting]
  naval_fate.py (-h | --help)
  naval_fate.py --version

Options:
  -h --help     Show this screen.
  --version     Show version.
  --speed=<kn>  Speed in knots [default: 10].
  --moored      Moored (anchored) mine.
  --drifting    Drifting mine.

"""

from docopt import docopt
from subprocess import call
import subprocess

def execute(command):
    process = subprocess.Popen(command, stdout=subprocess.PIPE)
    lines = process.stdout.readlines()
    print("this is a super test" + str(process.stdout.readline()))
    print(lines)
    out, err = process.communicate()
    return out

def test_method():
    this_is_a_test = "this is a test"
    print(this_is_a_test)

if __name__ == '__main__':
    arguments = docopt(__doc__, version='Naval Fate 2.0')
    print(arguments)
    res = execute(['ls', '-la'])
    print("{res} is res".format(res = res))
    test_method()
