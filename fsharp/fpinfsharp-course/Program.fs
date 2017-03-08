// Learn more about F# at http://fsharp.org

open System

type ImmList<'a> = 
    | Cons of 'a * ImmList<'a>
    | Nil 

module IL =

    let add<'a> (v: 'a) (l: ImmList<'a>): ImmList<'a> =
        Cons(v, l)

    let rec create<'a> (v: List<'a>): ImmList<'a> =
        match v with
        | head :: tail -> Cons(head, create tail)
        | [] -> Nil
    let rec foldRight<'a, 'b> (f: 'a -> 'b -> 'b) (zero: 'b) (l: ImmList<'a>) : 'b =
        match l with
        | Cons(h, t) -> f h (foldRight f zero t)
        | Nil -> zero 

    let rec toString<'a> (f: 'a -> string) (l: ImmList<'a>) : string =
        foldRight (fun x y -> sprintf "%s:%s" (f x) y) "" l

    let rec filter<'a> (f: 'a -> bool) (l: ImmList<'a>): ImmList<'a> = 
        match l with
        | Cons(h, t) -> if f h then Cons(h, filter f t) else filter f t
        | Nil -> Nil

    let rec map<'a,'b> (f: 'a -> 'b)(l: ImmList<'a>): ImmList<'b> =
        match l with
        | Cons(h, t) ->Cons(f h, map f t)
        | Nil -> Nil


[<EntryPoint>]
let main argv = 
    let l = Cons(2, Cons(1, Nil))
    let fInt x = sprintf "%i" x
    IL.create [1;2;3;4] |> IL.toString(fInt) |> Console.WriteLine
    IL.create [1;2;3;4] |> IL.filter(fun x -> x % 2 = 0) |> IL.toString(fInt) |> Console.WriteLine
    IL.create [1;2;3;4] |> IL.map(fun x -> sprintf "value:%i" x) |> IL.toString(fun x -> sprintf "%s" x) |> Console.WriteLine
    IL.create [] |> IL.foldRight (+) 0 |> Console.WriteLine
    IL.create [1;2;3;4] |> IL.foldRight (+) 0 |> Console.WriteLine
    0
