data CredicCard = CreditCard {number :: String, date :: String, csv :: Int}
data Coffee = Coffee {price :: Int}

charge :: CreditCard -> Int -> IO 
charge cc value = putStr ("charging to credit card " ++ show (number cc) ++ "the price " ++ (show value))

buyCoffee :: CreditCard -> IO Coffee
buyCoffee cc = 
    let cup = Coffee 100
        buy = charge cc (price cup)
    in buy


	
	
	


