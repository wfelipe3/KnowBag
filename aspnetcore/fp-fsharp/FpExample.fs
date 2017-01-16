namespace fp_fsharp

open System;
open Xunit;

module FpExample1 =

    type CreditCard(number: string, expDate: string, csv: int) = 
        member this.Charge (price:int)= printfn "buying element with price %i with credit card %s" price number

    type Coffee =
        {
            price: int    
        }

    let buyCoffe (cc: CreditCard) = 
        let cup = {price=2000}
        cc.Charge cup.price
        cup

    [<Fact>]
    let testBuyCoffe () =
        let cc = CreditCard ("123-123-34", "10/22", 123)
        let cup = buyCoffe cc
        Assert.Equal(cup, {price = 2000});

