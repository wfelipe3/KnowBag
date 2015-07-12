QUnit.test( "literal objets", function( assert ) {
  var book = {
    name: "the lord of the rings",
    editorial: "norma"
  };
  assert.deepEqual(
    book, {
      name: "the lord of the rings",
      editorial: "norma"
    },
    "not the same objects"
  );
});

QUnit.test("object with array", function(assert) {
  var phone = {
    company: "apple",
    model: "5s",
    apps: ["clash of clans", "kindle", "safari books"]
  };
  assert.deepEqual(
    phone, {
      company: "apple",
      model: "5s",
      apps: ["clash of clans", "kindle", "safari books"]
    }, "is not the same phone"
  );
});

QUnit.test("modified object", function(assert) {
  var phone ={
    name: "iphone",
    model: "6"
  }
  phone.name = "samsung";
  assert.equal(phone.name, "samsung", "the phone is not samsung");
});

QUnit.test("added value to object", function(assert){
  var phone = {
    name:"iphone",
    model: 6,
    active: true
  };

  phone.apps = ["clash of clans", "kindle"];
  assert.deepEqual(phone, {
    name:"iphone",
    model: 6,
    active: true,
    apps: ["clash of clans", "kindle"]
  }, "is not the same phone");
});

QUnit.test("add value to object with []", function(assert){
  var phone = {
    name:"iphone",
    model: 6,
    active: true
  };
  phone["# of apps"] = 10;
  assert.equal(phone["# of apps"], 10, "number of apps is not the same");
});

QUnit.test("add books to box", function(assert){
  var box = {
    count: 0
  }

  addBook(box, "las voces en el desierto", "morgan pirata");
  addBook(box, "las aventuras de don juan", "juan castaneda");

  assert.equal(box.book1.name, "las voces en el desierto", "no es el mismo libro");
  assert.equal(box.book2.author, "juan castaneda", "no es el mismo libro");
});

QUnit.test("object with behavior", function(assert){
  var box = {
    put: function(name, item) {
      this[name] = item;
      return box;
    },
    get: function(name) {
      var item = this[name];
      delete this[name];
      return item;
    }
  };

  box.put("pen", { color:"blue", size:20})
     .put("notebook", {
       type:"square",
       new:true
    }
  );

  assert.deepEqual(box.get("pen"), {color: "blue", size:20}, "test");
  assert.deepEqual(box.get("pen"), undefined, "second time is undefined");
});

QUnit.test("iterate keys over an object", function(assert){
  var box = {
    pen: {type: "office", color: "blue", size:20},
    nobebook: {type: "office", pages:100, company: "norma"},
    water: {type: "river"},
    fish: {type:"river", height:30}
  }

  box.numberOfOfficeItems = function() {
      var count = 0;
      for(key in box) {
        if (box[key].type === "office") {
          count++;
        }
      }
      return count;
  };

  box.filter = function() {
    var typeItems = function(item) {return item.type === "office"};
    var items = _.filter(box, typeItems);
    return items.length;
  }

  assert.equal(box.numberOfOfficeItems(), 2, "with for each");
  assert.equal(box.filter(), 2, "with filter");
});

function addBook(box, name, author) {
  box.count++;
  box["book" + box.count] = {name: name, author: author};
}
