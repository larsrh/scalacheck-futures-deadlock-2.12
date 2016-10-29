Reproduction:

sbt test          # deadlocks
sbt ++2.11.8 test # succeeds

It also doesn't deadlock after converting the `Properties` to a ScalaTest
spec, even when using `Checkers` to embed ScalaCheck properties. It just
happens when using the ScalaCheck runner.

Code is from the "remotely" library <https://github.com/Verizon/remotely>.
Copyright (C) 2014 Verizon.  All Rights Reserved.
