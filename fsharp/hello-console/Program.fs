// Learn more about F# at http://fsharp.org

open System
open FR

[<EntryPoint>]
let main argv = 
    printfn "Hello World from F#!"
    printfn "%i" (add 5 3)
    printfn "%i" (less 5 4)
    less 4 5 |> printfn "this is a super test %i"
    0 // return an integer exit code
