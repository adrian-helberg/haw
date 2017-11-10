-module(testBellmannford).

-spec main() -> no_return().
-export([main/0]).

main() ->
  adtgraph:printG(adtgraph:importG("graph3.graph", d), "graph3"),
  %bellmannford:bellmannford("graph4.graph", 11, ud),
  nil.
