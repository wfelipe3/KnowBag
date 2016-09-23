from pyrsistent import PClass, pmap_field, field, pmap


class Location(PClass):
    name = field(str)
    description = field(str)
    exits = pmap_field(str, str)


home = Location(name="Home", description="Home is where the heart is!", exits={"east": "Street"})
street = Location(name="Street", description="The street next to your house.", exits={"west": "Home"})


class GameState(PClass):
    location = field(Location)
    world = pmap_field(str, Location)


world = pmap({x.name: x for x in [home, street]})
initial_state = GameState(location=home, world=world)


def render(state):
    exits = "\n".join('* {} to {}'.format(exit, location) for exit, location in state.location.exits.items())
    return "* {name} *\n\n{description}\n\nExits:\n{exits}".format(
        name=state.location.name,
        description=state.location.description,
        exits=exits
    )


print(render(initial_state))


def move(state, exit_name):
    location_name = state.location.exits.get(exit_name)
    if location_name is None:
        return None
    target_location = state.world.get(location_name)
    return GameState(location=target_location, world=state.world)


print(render(move(initial_state, "east")))
