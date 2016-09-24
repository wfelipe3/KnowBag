from pyrsistent import PClass, pmap_field, field, pmap, pvector_field


class Thing(PClass):
    name = field(str)


class Location(PClass):
    name = field(str)
    description = field(str)
    exits = pmap_field(str, str)
    items = pmap_field(str, Thing)


key = Thing(name="rusty key")
home = Location(name="Home", description="Home is where the heart is!", exits={"east": "Street"})
street = Location(name="Street", description="The street next to your house.", exits={"west": "Home"},
                  items={key.name: key})


class GameState(PClass):
    location_name = field(str)
    world = pmap_field(str, Location)
    inventory = pvector_field(Thing)

    @property
    def location(self):
        return self.world[self.location_name]


world = pmap({x.name: x for x in [home, street]})

ROOM_FORMAT = """
* {name} *
{description}

Exits:
{exits}

Items here: {items}
Your inventory: {inventory}
"""


def render(state):
    exits = "\n".join('* {} to {}'.format(exit, location) for exit, location in state.location.exits.items())
    items = ', '.join(state.location.items.keys())
    inventory = ", ".join(item.name for item in state.inventory)
    return ROOM_FORMAT.format(
        name=state.location.name,
        description=state.location.description,
        exits=exits,
        items=items,
        inventory=inventory
    )


def move(state, exit_name):
    location_name = state.location.exits.get(exit_name)
    if location_name is None:
        return None
    return state.set(location_name=location_name)


def take(state, item_name):
    item = state.location.items.get(item_name)
    if item is None: return None
    return state.transform(
        ["world", state.location.name, "items"], lambda items: items.remove(item_name),
        ["inventory"], lambda inv: inv.append(item)
    )


initial_state = GameState(location_name="Home", world=world)
in_street = move(initial_state, "east")
after_taken = take(in_street, "rusty key")
print(render(in_street))
print(render(after_taken))
print(render(move(move(after_taken, "west"), "east")))
