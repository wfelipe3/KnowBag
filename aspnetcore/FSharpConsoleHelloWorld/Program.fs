// Learn more about F# at http://fsharp.org

open System

[<EntryPoint>]
let main argv = 
    let hello = helloWorld "felipe"
    printfn hello
    0 // return an integer exit code

let helloWorld name = "Hello World" + name
