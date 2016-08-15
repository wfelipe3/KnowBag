f = open("data.csv", mode='r')
data = f.read()
print(data)
f.close()


def openData(file="data.csv", mode='r', error="warn"):
    if error not in {"warn", "silent", "raise"}:
        raise ValueError("Error must be warn, silent or raise")
    try:
        return open(file, mode)
    except FileNotFoundError as e:
        if error == "warn":
            print("the file was not found {}".format(e))
        elif error == "raise":
            raise
        else:
            pass
    finally:
        print("after all")


lines = openData(file="test.txt", mode='r', error="pass")

with openData() as f:
    for rown, line in enumerate(f, start=1):
        print("the line is [{}]".format(line))
        print("row number {}".format(rown))

print(data[3])
print(data[-3])
print(data[4:].strip())
print(data[2:4])
print(len(data))
