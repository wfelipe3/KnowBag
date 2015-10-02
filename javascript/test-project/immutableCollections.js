QUnit.test( "immutable list", function( assert ) {
  var list = Immutable.List.of(1,2);
  var listWithValues = list.push(3,4,5);
  assert.deepEqual(list.toArray(), [1,2]);
  assert.deepEqual(listWithValues.toArray(), [1,2,3,4,5]);
});

QUnit.test( "bacon interval", function( assert ) {
  var timer = Bacon.interval(1000).map(function() {
    var date = new Date().getTime();
    return date;
  });

  timer.take(5).onValue(function(value) {
    console.log(value);
  });

  assert.equal(true, true);
});
