%%% TESTS FOR ADT GRAPH %%%
-module(test).

% Exports
-export([main/0, testAddVertex/0, testDeleteVertex/0, testAddEdge/0, testDeleteEdge/0, testSetAtE/0, testSetAtV/0,
  testGetValE/0, testGetValV/0, testGetIncident/0, testPrintG/0, testGetAdjacent/0, testGetTargetd/0,
  testGetTargetnd/0, testGetSourced/0, testGetSourcend/0, testGetEdges/0, testGetVertexes/0,
  printGR/1]).

% Specs
-spec main() -> no_return().
-spec testCreateG() -> no_return().
-spec testAddVertex() -> no_return().
-spec testDeleteVertex() -> no_return().
-spec testAddEdge() -> no_return().
-spec testDeleteEdge() -> no_return().
-spec testSetAtE() -> no_return().
-spec testSetAtV() -> no_return().
-spec testGetIncident() -> no_return().
-spec testGetAdjacent() -> no_return().
-spec testGetTargetd() -> no_return().
-spec testGetTargetnd() -> no_return().
-spec testGetEdges() -> no_return().
-spec testGetVertexes() -> no_return().
-spec testPrintG() -> no_return().

% Function entry point
main() ->
%%  testCreateG(),
%%  testAddVertex(),
%%  testDeleteVertex(),
%%  testAddEdge(),
%%  testDeleteEdge(),
%%  testSetAtE(),
%%  testSetAtV(),
%%  testGetValE(),
%%  testGetValV(),
  testGetIncident().
%%  testGetAdjacent(),
%%  testGetTargetd(),
%%  testGetTargetnd(),
%%  testGetSourced(),
%%  testGetSourcend(),
%%  testGetEdges(),
%%  testGetVertexes(),
%%  testPrintG().

% Test if adtgraph:createG creates an empty d or nd graph
testCreateG() ->
  % Should result in a empty d and und graph
  G1 = adtgraph:createG(d),
  G2 = adtgraph:createG(nd),
  case (G1 == {[], [], d}) and (G2 == {[], [], nd}) of
    true -> io:fwrite("TEST 'createG' passed.\n");
    false -> io:fwrite("TEST 'createG' failed.\n")
  end.

% Test if adtgraph:addVertex return a graph with added vertex
testAddVertex() ->
  % Graph with vertices 1,2.
  % After added vertex 4, vertics should be like 1,2,3
  G = {[{1, []}, {2, []}], [], d},
  T = adtgraph:addVertex(G, 3),
  case T == {[{1, []}, {2, []}, {3, []}], [], d} of
    true -> io:fwrite("TEST 'addVertex' passed.\n");
    false -> io:fwrite("TEST 'addVertex' failed.\n")
  end.

% Test if adtgraph:deleteVertex return a graph with deleted vertex
testDeleteVertex() ->
  % Graph with vertices 1,2,3 and edges (1,2), (1,3).
  % After deleting vertex 2, vertics should be like 1,3 and edges should be like (1,3)
  G = {[{1, []}, {2, []}, {3, []}], [{1, 2, []}, {1, 3, []}], d},
  T = adtgraph:deleteVertex(G, 2),
  case T == {[{1, []}, {3, []}], [{1, 3, []}], d} of
    true -> io:fwrite("TEST 'deleteVertex' passed.\n");
    false -> io:fwrite("TEST 'deleteVertex' failed.\n")
  end.

% Test if adtgraph:addEdge return a graph with added edge
testAddEdge() ->
  % Graph with vertices 1,2,3 and edge (1,3).
  % After adding edge (1,2), edges should be like (1,3), (1,2)
  G = {[{1, []}, {2, []}, {3, []}], [{1, 3, []}], d},
  T = adtgraph:addEdge(G, 1, 2),
  case (G /= T) of
    true -> io:fwrite("TEST 'addEdge' passed.\n");
    false -> io:fwrite("TEST 'addEdge' failed.\n")
  end.

% Test if adtgraph:deleteEdge return a graph with deleted edge
testDeleteEdge() ->
  % Graph with vertices 1, 2, 3 and edges (1,2),(1,3).
  % After deleting edge (1, 3), edges should be (1,2)
  G = {[{1, []}, {2, []}, {3, []}], [{1, 2, []}, {1, 3, []}], d},
  T = adtgraph:deleteEdge(G, 1, 3),
  case (G /= T) of
    true -> io:fwrite("TEST 'deleteEdge' passed.\n");
    false -> io:fwrite("TEST 'deleteEdge' failed.\n")
  end.

% Test if adtgraph:setAtE returns a graph with set attribute on edge
testSetAtE() ->
  % Graph with vertices 1, 2, 3 and edges (1,2),(1,3).
  % After setting attribute 'weight' with value 5 to edge (1,3),
  % edge should be like (1,3, [weight, 5]).
  % Remind: adtgraph:setAtE can handle wrong ordered input vertices (3, 1)
  % to keep right ordering in direced graphs
  G = {[{1, []}, {2, []}, {3, []}], [{1, 2, []}, {1, 3, []}], d},
  T = adtgraph:setAtE(G, {1, 3}, 'weight', 5),
  case (G /= T) of
    true -> io:fwrite("TEST 'setAtE' passed.\n");
    false -> io:fwrite("TEST 'setAtE' failed.\n")
  end.

testSetAtV() ->
  % Graph with vertices 1, 2, 3 and edges (1,2),(1,3).
  % After setting attribute 'weight' with value 5 to vertex 1,
  % vertex 1 should be like (1, ['weight', 5])
  G = {[{1, [{'attr', 10}]}, {2, []}, {3, []}], [], d},
  T = adtgraph:setAtV(G, 1, 'attr1', 1000000),
  io:fwrite("~p~n", [G]),
  io:fwrite("~p~n", [T]),
  case (G /= T) of
    true -> io:fwrite("TEST 'setAtV' passed.\n");
    false -> io:fwrite("TEST 'setAtV' failed.\n")
  end.

testGetValE() ->
  % Graph with vertices 1, 2, 3 and edges (1,2, [capacity, 3]),(1,3, [weight, 10]).
  % Get the value of attribute 'weight' of edge (1, 3),
  % Should return 10
  G = {[{1, []}, {2, []}, {3, []}], [{1, 2, [{'capacity', 3}]}, {1, 3, [{'weight', 10}]}], d},
  T = adtgraph:getValE(G, {1, 3}, 'weight'),
  case (T == 10) of
    true -> io:fwrite("TEST 'getValE' passed.\n");
    false -> io:fwrite("TEST 'getValE' failed.\n")
  end.

testGetValV() ->
  % Graph with vertices (1, []), (2, ['weight', 6]), (3, []).
  % Get the value of attribute 'weight' of vertice 2,
  % Should return 6
  G = {[{1, []}, {2, [{'weight', 6}]}, {3, []}], [], d},
  T = adtgraph:getValV(G, 2, 'weight'),
  case (T == 6) of
    true -> io:fwrite("TEST 'getValV' passed.\n");
    false -> io:fwrite("TEST 'getValV' failed.\n")
  end.

testGetIncident() ->
  % Graph with vertices (1,2,3) and edges (1,2), (1,3).
  % Get all vertices of incident edges.
  % Passing 1 should return 1,2,1,3
  G = {[{1, []}, {2, []}, {3, []}], [{1, 2, []}, {1, 3, []}], nd},
  T = adtgraph:getIncident(G, 2),
  case (T == [1, 2]) of
    true -> io:fwrite("TEST 'getIncident' passed.\n");
    false -> io:fwrite("TEST 'getIncident' failed.\n")
  end.

testGetAdjacent() ->
  % Graph with vertices (1,2,3,4) and edges (1,2), (3,1).
  % Get all adjacent vertices.
  G = {[{1, []}, {2, []}, {3, []}, {4, []}], [{1, 2, []}, {3, 1, []}], d},
  T = adtgraph:getAdjacent(G, 1),
  case (T == [2]) of
    true -> io:fwrite("TEST 'getAdjacent' passed.\n");
    false -> io:fwrite("TEST 'getAdjacent' failed.\n")
  end.

testGetTargetd() ->
  % Graph with vertices (1, 2, 3) and edges (1,2), (3,1)
  % Get all target vertices
  G = adtgraph:createG(d),
  E1 = adtgraph:addEdge(G, 1, 2),
  E2 = adtgraph:addEdge(E1, 3, 1),
  P = adtgraph:getTarget(E2, 1),
  case (P == [2])of
    true -> io:fwrite("TEST 'getTargetd' passed.\n");
    false -> io:fwrite("TEST 'getTargetd' failed.\n")
  end.

testGetTargetnd() ->
  % Graph with vertices (1, 2, 3) and edges (1,2), (3,1)
  % Get all source vertices (und)
  G = adtgraph:createG(nd),
  E1 = adtgraph:addEdge(G, 1, 2),
  E2 = adtgraph:addEdge(E1, 3, 1),
  P = adtgraph:getTarget(E2, 1),
  case (P == [2, 3])of
    true -> io:fwrite("TEST 'getTargetnd' passed.\n");
    false -> io:fwrite("TEST 'getTargetnd' failed.\n")
  end.

testGetSourced() ->
  % Graph with vertices (1, 2, 3) and edges (1,2), (3,1)
  % Get all source vertices (d)
  G = adtgraph:createG(d),
  E1 = adtgraph:addEdge(G, 1, 2),
  E2 = adtgraph:addEdge(E1, 3, 1),
  P = adtgraph:getSource(E2, 1),
  case (P == [3]) of
    true -> io:fwrite("TEST 'getSourced' passed.\n");
    false -> io:fwrite("TEST 'getSourcetd' failed.\n")
  end.

testGetSourcend() ->
  % Graph with vertices (1, 2, 3) and edges (1,2), (3,1)
  % Get all source vertices (und)
  G = adtgraph:createG(und),
  E1 = adtgraph:addEdge(G, 1, 2),
  E2 = adtgraph:addEdge(E1, 3, 1),
  P = adtgraph:getSource(E2, 1),
  case (P == [2, 3]) of
    true -> io:fwrite("TEST 'getSourcend' passed.\n");
    false -> io:fwrite("TEST 'getSourcetnd' failed.\n")
  end.

testGetEdges() ->
  % Graph with vertices (1, 2, 3, 4) and edges (1,1), (1,2), (3,1)
  % Get all edges
  G = {[{1, []}, {2, []}, {3, []}, {4, []}], [{1, 1, []}, {1, 2, []}, {3, 1, []}], d},
  EL = adtgraph:getEdges(G),
  case (EL == [1,1,1,2,3,1]) of
    true -> io:fwrite("TEST 'getEdges' passed.\n");
    false -> io:fwrite("TEST 'getEdges' failed.\n")
  end.

testGetVertexes() ->
  % Graph with vertices (1, 2, 3, 4) and edges (1,2), (3,1)
  % Get all vertices
  G = {[{1, []}, {2, []}, {3, []}, {4, []}], [{1, 1, []}, {1, 2, []}, {3, 1, []}], d},
  EL = adtgraph:getVertexes(G),
  case (EL == [1, 2, 3, 4]) of
    true -> io:fwrite("TEST 'getVertices' passed.\n");
    false -> io:fwrite("TEST 'getVertices' failed.\n")
  end.

testPrintG() ->
  % Uncomment to create png images of *.graph files
  adtgraph:printG(adtgraph:importG("graph1.graph", d), "graph1").
  %nil.

printGR(12) -> nil;
printGR(C) ->
  adtgraph:printG(adtgraph:importG("graph" ++ util:to_String(C) ++ ".graph", d),"graph" ++ util:to_String(C)),
  %% Call printGR after 500ms to give time to kill previous process
  receive
  after 500 ->
    printGR(C + 1)
  end.