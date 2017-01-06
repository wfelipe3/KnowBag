// Learn more about F# at http://fsharp.org

open System

let x = 0
let message = "this is very nice"
let add a b = a + b
let inline twice x = x + x

type A = {thing: int}
type B = {label: string}

[<EntryPoint>]
let main argv = 
    argv[1] |> Console.WriteLine
    printfn "Hello World! %s" message
    printfn "the sum value is %i" (add 5 5)
    twice 2.0 |> Console.WriteLine
    twice 2 |> Console.WriteLine
    (fun (x: float) -> x + x)(2.0) |> Console.WriteLine
    (fun (x: int) -> x + x)(2) |> Console.WriteLine
    x
