// Learn more about F# at http://fsharp.org
open System

let x = 0
let message = "this is very nice"
let add a b = a + b
let inline twice x = (+) x x // inlines the expression for every call making possible the use of different types in the same context

let test () =
    let x = 5
    do
        let x = 10
        printfn "%i" x
    x

let testWithAssigment() =
    let mutable x = 5
    do
        x <- 10 // mutable assigment
        printfn "%i" x
    x

let sum = fun a b -> a + b
let sum' = fun a -> fun b -> a + b // curried function
let sumPlusTwo = sum 2 // by default all functions are curried

let res = List.map (fun x -> x + 1) [1; 2; 3]

let rec fib n =
    if n < 2 then 1
    else fib(n - 1) + fib (n - 2)

let rec fib2 n =
    match n with
        | 0 -> 0
        | 1 -> 1
        | x -> fib(x - 2) + fib(x - 1)

let rec fib3 n =
    match n with
        | 0 | 1 as x -> x
        | x -> fib(x - 2) + fib(x - 1)

let rec fib4 n =
    match n with
        | x when x < 2 -> x
        | x -> fib(x - 2) + fib(x - 1)

let is_even x =
    match x with
        | i when i % 2 = 0 -> true 
        | _ -> false

let tuple = 1001, "this is a test"

type Employee =
    {
        firstName: string
        lastName: string
        gender: string
        phoneNumber: string
        salary: float
    }

type AlphaNumeric =
    | Alphabetic of string
    | Numeric of int

type Test = Employee*AlphaNumeric
let printfib fib n =
    n |> fib |> sprintf "fib number for %i is %i" n

let comparexy (xy: int*int) =
    match xy with
        | (0, _) -> "x is 0"
        | (_, 0) -> "y is 0"
        | (x, y) -> sprintf "x is %i and y is %i" x y

let patternMatchType v =
    match v with 
    | {firstName = "pepito"} -> true
    | _ -> false

let getFistName v =
    match v with 
    | {firstName = x} -> x

let value (x: AlphaNumeric) =
    match x with 
    | Alphabetic x -> sprintf "the value %s is Alphabetic" x
    | Numeric x -> sprintf "the value %i is Numeric" x

[<EntryPoint>]
let main argv = 
    printfn "Hello World! %s" message
    printfn "the sum value is %i" (add 5 5)
    twice 2.0 |> Console.WriteLine
    twice 2 |> Console.WriteLine
    (fun (x: float) -> x + x)(2.0) |> Console.WriteLine
    (fun (x: int) -> x + x)(2) |> Console.WriteLine
    test() |> Console.WriteLine
    testWithAssigment() |> Console.WriteLine
    sum 1 2 |> Console.WriteLine
    sumPlusTwo 1 |> Console.WriteLine
    res |> Console.WriteLine
    [1; 2; 3] |> List.map (fun x -> x + 1) |> List.filter (fun x -> x > 3) |> Console.WriteLine
    10 |> printfib fib |> Console.WriteLine
    10 |> printfib fib2 |> Console.WriteLine
    10 |> printfib fib3 |> Console.WriteLine
    10 |> printfib fib4 |> Console.WriteLine
    1 |> is_even |> printfn "is even 1 %b"
    2 |> is_even |> printfn "is even 1 %b"
    fst tuple |> printfn "tuple first values is %i"
    snd tuple |> printfn "tuple second values is %s"
    let value1, value2 = tuple
    let _, message = tuple
    (1,9) |> comparexy |> Console.WriteLine

    let emp =
        {
            firstName = "pepito"
            lastName = "gomez"
            gender = "M"
            phoneNumber = "9383947"
            salary = 23.0
        }

    let dave = {emp with firstName = "juan david"}

    printfn "emp name is %s and dave name is %s" emp.firstName dave.firstName
    
    emp |> Console.WriteLine
    emp.salary |> Console.WriteLine
    patternMatchType emp |> printfn "pattern match type is %b"
    patternMatchType emp |> printfn "pattern match type is %b"
    getFistName emp |> printfn "the value is %s"
    getFistName dave |> printfn "the value is %s"

    value (Numeric 1) |> Console.WriteLine
    value (Alphabetic "hello world") |> Console.WriteLine

    x
