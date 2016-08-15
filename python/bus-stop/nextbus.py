#!/usr/bin/env python3

import urllib.request

import sys

if len(sys.argv) != 3:
    raise SystemExit('Usage: nextbus.py route stopid')

route = sys.argv[1]
stop_id = sys.argv[2]

# import pdb
# pdb.set_trace() stop point in execution

u = urllib.request.urlopen(
    'http://ctabustracker.com/bustime/map/getStopPredictions.jsp?route={}&stop={}'.format(route, stop_id))

data = u.read()

from xml.etree.ElementTree import XML

doc = XML(data)

for pt in doc.findall('.//pt'):
    print(pt.text)
