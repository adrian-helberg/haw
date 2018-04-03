-module(testBellmannford).

-spec main() -> no_return().
-export([main/0]).

main() ->
  adtgraph:printG(adtgraph:importG("graph4.graph", ud), "graph4"),
  bellmannford:bellmannford("graph4.graph", 11, ud),
  nil.
