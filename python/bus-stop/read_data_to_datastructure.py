def openFile(file="data.csv", mode='r', error="warn"):
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


def get_data_as_list(file):
    with openFile(file) as f:
        list = []
        for line in f:
            row = line.split(",")
            record = {
                'age': row[0],
                'name': row[1],
                'gender': row[2]
            }
            list.append(record)
        return list


def get_data_as_json(as_tuple):
    with openFile(file="data.csv", mode='r', error='warn') as f:
        list = []
        for line in f:
            row = line.split(",")
            if as_tuple is True:
                record = tuple(row)
                list.append(record)
            else:
                record = {
                    'age': row[0],
                    'name': row[1],
                    'gender': row[2]
                }
                list.append(record)
        print(list)

        if as_tuple:
            for name, age, gender in list:
                print("name: {}, age: {}, gender: {}".format(name, age, gender))
        else:
            for values in list:
                print("name: {}, age: {}, gender: {}".format(values['name'], values['age'], values['gender']))

        import json
        return json.dumps(list)


print(get_data_as_json(as_tuple=True))
print(get_data_as_json(as_tuple=False))

data = get_data_as_list(file="data.csv")
string_values = ["name: {}, age: {}".format(value["name"], value["age"]) for value in data]
print(string_values)

sum = sum([int(value['name']) for value in data if int(value['name']) > 20])
print(sum)
