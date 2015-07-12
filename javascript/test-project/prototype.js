QUnit.test("add function to String prototype", function(assert){

  String.prototype.countAll = function(letter) {
    var count = 0;
    var currentString = this;
    _(currentString.length).times(function(n){
      if (currentString.charAt(n).toUpperCase() == letter.toUpperCase()) {
        count++;
      }
    });
    return count;
  }

  assert.equal("this is a test".countAll("i"), 2)

});

QUnit.test("create object from existing one", function(assert){
  var macbook = {
    ram: 8,
    ssd: 128,
    mouse: false,
    inches: 13
  };

  var betaMackbook = Object.create(macbook);
  betaMackbook.withSim = true;

  assert.deepEqual(betaMackbook, {ram: 8, ssd: 128, mouse: false, inches: 13, withSim: true});
  assert.ok(macbook.isPrototypeOf(betaMackbook));
  assert.notOk(betaMackbook.isPrototypeOf(macbook));
});

QUnit.test("class constructor", function(assert){
  function Shoe(shoeSize, shoeColor, forGender, constructionStyle) {
    this.size = shoeSize;
    this.color = shoeColor;
    this.gender = forGender;
    this.construction = constructionStyle;
  }

  Shoe.prototype = {
    putOn: function() {return "putting on a shoe with color " + this.color},
    takeOff: function() {return "takint off a shoe with size " + this.size}
  };

  var boot = new Shoe(42, "black", "male", "nice");
  boot.lace = "brown";
  assert.deepEqual(boot.color, "black");
  assert.equal(boot.lace, "brown");
  assert.equal(boot.putOn(), "putting on a shoe with color " + boot.color);
  assert.equal(boot.takeOff(), "takint off a shoe with size " + boot.size);

});

QUnit.test("with class keyword", function(assert){
  "use strict";

  class Polygon {
    constructor(height, width) {
      this.height = height;
      this.width = width;
    }
  }

  class Square extends Polygon {
    constructor(sideLength) {
      super(sideLength, sideLength);
    }
    get area() {
      return this.height * this.width;
    }
  }

  var square = new Square(2);
  assert.equal(square.area, 4)
});
