module Main where

import Lib


main :: IO ()
main = do
  putStrLn (message "add" (5 + 3))
  putStrLn (message "booleanAnd" (booleanAnd True False))
  putStrLn (message "booleanAnd" (booleanAnd True True))
  putStrLn (message "booleanOr" (booleanOr True True))
  putStrLn (message "negate with booleanOr" (negateBool (booleanOr True True)))
  putStrLn (message "equality" (5 == 5))
  putStrLn (message "equality" (5 /= 5))
  putStrLn (message "succ" (succ 4))
  putStrLn (message "doubleSmallNumber" (doubleSmallNumber 100))
  putStrLn (message "list" lostNumbers)
  putStrLn (message "add lists" ([1,2,3] ++ [4,5,6]))
  putStrLn (message "add with cons" (2:[3]))
  putStrLn (message "get element at index" ("this is a test" !! 5))
  putStrLn (message "get head of list" (head [1,2,3,4]))
  putStrLn (message "get tail of list" (tail [1,2,3,4]))
  putStrLn (message "get last of list" (last [1,2,3,4]))
  putStrLn (message "get init of list" (init [1,2,3,4]))
  putStrLn (message "length of list" (length [1,2,3]))
  putStrLn (message "checking if empty" (null [1,2,3]))
  putStrLn (message "checking if empty" (null []))
  putStrLn (message "reverse list" (reverse [2,3,3,4,5]))
  putStrLn (message "take elements from list" (take 3 [2,3,3,4,5]))
  putStrLn (message "dropping elements from list" (drop 3 [2,3,3,4,5]))
  putStrLn (message "dropping when para is bigger than list" (drop 100 [2,3,3,4,5]))
  putStrLn (message "maximum from list" (maximum [1,2,4,5,6,3,5,6,7]))
  putStrLn (message "sum list" (sum [1,2,4,5,6,3,5,6,7]))
  putStrLn (message "product list" (product [1,2,4,5,6,3,5,6,7]))
  putStrLn (message "elem from list" (elem 1 [1,2,4,5,6,3,5,6,7]))
  putStrLn (message "ranges" ([1..10]))

booleanAnd b1 b2 = b1 && b2
booleanOr b1 b2 = b1 || b2
negateBool b = not b

message m v = "executing " ++ m ++ " => value is: " ++ (show v)

doubleSmallNumber x = if x > 100
                        then x
                        else x * 2

lostNumbers = [2]
