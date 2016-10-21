main = print ( doubleUs 10 10, doubleSmallNumber 10, lostNumbers )
        where
            let lostNumbers = [4,8,15,16,23,42]

doubleMe x = x + x

doubleUs x y = doubleMe x + doubleMe y

doubleSmallNumber x = if x > 100
                        then x
                        else x*2

