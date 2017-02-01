namespace fp_fsharp

open System;
open Xunit;

module FpExample2 =

    type CreditCard = 
        {
            number: string
            expDate: string
            csv: int
        }

    type Coffee =
        {
            price: int    
        }

    let purchase (cc: CreditCard) (cup: Coffee) =
        printfn "buying element with price %i with credit card %s" cup.price cc.number

    let buyCoffe (cc: CreditCard) (pur: CreditCard -> Coffee -> unit) =
        let cup = {price = 2000}
        pur cc cup
        cup

    let testBuyCoffe2 () =
        let cc = {number = "123-123-34"; expDate = "10/22"; csv = 123}
        let purcc = purchase cc
        let cup = buyCoffe cc purchase
        Assert.Equal(cup, {price = 2000});
    



