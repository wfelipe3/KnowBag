// Learn more about F# at http://fsharp.org

open System

let plus1 x = x + 1

let plus2 x = x + 2

[<EntryPoint>]
let main argv = 
    let plus3 = plus1 >> plus2
            //printfn "Hello World!"
    printfn "the value is %i" (plus3 4)
    0
