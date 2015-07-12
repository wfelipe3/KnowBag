QUnit.test("_ each", function(assert) {
  var squareValues = [1,2,3,4,5].map(
    function( i ){
      return i*i
    }
  );

  var i = 1;
  _.each(squareValues, function(value){
    assert.equal(value, i*i);
    i++;
  });
});
