module HelloWorldTest exposing (..)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, list, int, string)
import Test exposing (..)

suite: Test
suite =
  describe "Hello world test suite"
    [ describe "first test"
        [ test "check test work" <|
            \() -> Expect.equal True True
        ]
      , describe "starting with elm"
        [ describe "String"
          [ test "simple string" <|
              \() -> Expect.equal
            , test "string concat" <|
              \() -> Expect.equal ("hello " ++ "world") "hello world"
          ]
          , describe "math"
            [ test "math operations" <|
                \() -> Expect.equal (2 + 3 * 4) 14
              , test "math operation with parenthesis" <|
                \() -> Expect.equal ((2 + 3) * 4) 20
              , test "floating point division /" <|
                \() -> Expect.equal (9/2) 4.5
              , test "integer division" <|
                \() -> Expect.equal (9//2) 4
            ]
          , describe "functions"
            [ test "first function" <|
              \() -> Expect.equal (isNegative 9) False
            ]
          , describe "if expressions"
            [
              test "test if" (\() -> Expect.equal (if True then "hello" else "world") "hello"),
              test "test if function" (\() -> Expect.equal (over9000 5) "meh")
            ]
          , describe "Lists"
            [
              test "create list" <|
                \() -> Expect.equal ["Alice", "bob", "Chuck"] ["Alice", "bob", "Chuck"]
              , test "is empty" <|
                \() -> Expect.equal (List.isEmpty (["test", "value"])) False
              , test "list length" <|
                \() -> Expect.equal (List.length (["test", "value"])) 2
              , test "list reverse" <|
                \() -> Expect.equal (List.reverse (["test", "value"])) ["value", "test"]
              , test "sort" <|
                \() -> Expect.equal (List.sort ([6,5,4,3,2,1])) [1,2,3,4,5,6]
              , test "map" <|
                \() -> Expect.equal (List.map (\i -> i * 2) [6,5,4,3,2,1]) [12, 10, 8, 6, 4, 2]
              , test "map with pipe" <|
                \() -> Expect.equal ([6,5,4,3,2,1] |> List.map (\i -> i * 2)) [12, 10, 8, 6, 4, 2]
            ]
          , describe "tuples"
            [ test "create tuple" <|
                \() ->
                  let
                    value = (True, "test")
                  in
                    Expect.equal (True, "test") (True, "test")
            ]
          , describe "records"
            [ test "create record" <|
                \() -> Expect.equal {x = 3, y = 4} {x = 3, y = 4}
              , test "get value from record" <|
                \() ->
                  let
                    record = {x = 3, y = 4}
                  in
                    Expect.equal record.x 3
              , test "get value from record with function" <|
                \() ->
                  let
                    record = {x = 3, y = 4}
                  in
                    Expect.equal (.y record) 4
              , test "map with tuple function" <|
                \() ->
                  let
                    record = {x = 3, y = 4}
                  in
                    Expect.equal (List.map .x [record, record, record]) [3,3,3]
              , test "functions with records" <|
                \() ->
                  let
                    record = {x = 3, y = 4}
                    under70 {age} = age < 70
                  in
                    Expect.equal (under70 {name = "felipe", age = 29}) True
              , test "change values in records" <|
                \() ->
                  let
                    record = {x = 3, y = 4}
                    record2 = {record | y = 5}
                  in
                    Expect.equal record2 {x = 3, y = 5}
            ]
            , describe "pattern matching"
              [ test "fist pattern" <|
                  \() ->
                    let
                      toString x =
                        case x of
                          1 -> "one"
                          2 -> "two"
                          3 -> "three"
                          _ -> "many"
                    in
                      Expect.equal (toString 1) "one"
                , test "pattern match record" <|
                  \() ->
                    let
                      toString x =
                        case x of
                        {x} -> "is " ++ x
                    in
                      Expect.equal (toString {x = "two"}) "is two"
              ]
          , describe "types"
            [ test "sum type" <|
                \() ->
                  let
                    toBool : Answer -> Bool
                    toBool a = case a of
                      Yes -> True
                      No -> False
                  in
                    Expect.equal (toBool Yes) True
              , test "sum type like functions" <|
                \() ->
                  let
                    value = Message 1 "hello"
                  in
                    Expect.equal value (Message 1 "hello")
              , test "sum type with function" <|
                \() -> Expect.equal ("test" |> Message 1 |> showError) "Error code: 1\"test\""
              ,test "alias types" <|
                \() -> Expect.equal ({id = 1, name = "felipe"} |> showPlayer) "player name: felipe 1"
              ,test "alias types constructors" <|
                \() -> Expect.equal (Player 1 "felipe" |> showPlayer) "player name: felipe 1"
            ]
        ]
    ]

type alias PlayerId = Int
type alias PlayerName = String
type alias Player =
  { id: PlayerId
  , name: PlayerName
  }

type Error a = Message Int a
type Answer = Yes | No

showPlayer : Player -> String
showPlayer p =
  "player name: " ++ (.name p) ++ " " ++ toString (.id p)

showError : Error a -> String
showError e =
  case e of
    Message i a -> "Error code: " ++ toString i ++ toString a

isNegative n = n < 0

over9000 powerLevel =
  if powerLevel > 9000 then
    "It's over 9000"
  else
    "meh"
