%%% ADT GRAPH %%%
-module(adtgraph).

-export_type([attribute/0, vertex/0, edge/0, graph/0]).

% Types ---

% Attribute on vertex and edge ( name of Attribute, value of attribute )
-type attribute() :: {atom(), integer()}.
% Vertex ( id, attributes )
-type vertex()    :: {integer(), [attribute()]}.
% Edge ( vertex 1 id, vertex 2 id, attributes)
- type edge()     :: {integer(), integer(), [attribute()]}.
% ADT graph ( vertices, edges, direction )
% ADT graph rolled out: { [ { integer(), [ { atom(), integer() } ] } ], [ integer(), integer(), [ { atom(), integer() } ] ], d | nd }
-type graph()     :: {[vertex()], [edge()], d | ud}.

% Exports
-export([createG/1, addVertex/2, deleteVertex/2, addEdge/3, deleteEdge/3, findTupleCombination/3, setAtV/4,
  keyDeleteR/3, setAtE/4, getValE/3, getValV/3, getIncident/2, findRD/3, findRND/3, importG/2, importDataR/2, exportG/2, printG/2,
  addNodeR/1, addEdgeR/1, getAdjacent/2, getAdjacentDR/3, getAdjacentNDR/3, getTarget/2, getSource/2,
  getVertexesR/2, getVertexes/1, graphvizDigraph/1, graphvizGraph/1, graphvizDelete/0, add_node/1, add_edge/3, graph_server/1,
  to_dot/1, to_file/2, getEdges/1, getEdgesDR/2, getEdgesNDR/2]).

% Specs ---
-spec createG     (D :: d | ud) -> G :: graph().
-spec addVertex   (G :: graph(), V :: integer()) -> G :: graph().
-spec deleteVertex(G :: graph(), V :: integer()) -> G :: graph().
-spec addEdge     (G :: graph(), V1 :: integer(), V2 :: integer()) -> G :: graph().
-spec deleteEdge  (G :: graph(),V1 :: integer(), V2 :: integer()) -> G :: graph().
-spec setAtE      (G :: graph(), {V1 :: integer(), V2 :: integer()}, N :: atom(), Val :: integer()) -> G :: graph().
-spec setAtV      (G :: graph(), V :: integer(), N :: atom(), Val :: integer()) -> G :: graph().
-spec getValE     (G :: graph(), {V1 :: integer(), V2 :: integer()}, N :: atom()) -> Val :: integer().
-spec getValV     (G :: graph(), V :: integer(), N :: atom()) -> Val :: integer().
-spec getIncident (G :: graph(), V :: integer()) -> VL :: [integer()].
-spec getAdjacent (G :: graph(), V :: integer()) -> VL :: [integer()].
-spec getTarget   (G :: graph(), V :: integer()) -> VL :: [integer()].
-spec getSource   (G :: graph(), V :: integer()) -> VL :: [integer()].
-spec getVertexes (G :: graph()) -> VL ::[integer()].

-spec importG     (FN :: atom(), D :: d | nd) -> G :: graph().
-spec exportG     (G :: graph(), FN :: atom()) -> F :: no_return().
-spec printG      (G :: graph(), FN :: atom()) -> no_return().

%% Operations

% Return empty d or nd graph
createG(D) ->
  % Build up graph
  {[], [], D}.

% Return graph with added vertex
addVertex(G, V) ->
  {VL, EL, D} = G,
  % Check if vertex already in vertexlist
  case lists:keymember(V, 1, VL) of
    true -> G;
    false -> {lists:append(VL, [{V, []}]), EL, D}
  end.


% Delete vertex V, return modified G
% getAdjecent V -> delete all edges between V and adjecent vertices
deleteVertex(G, V) ->
  {VL, EL, D} = G,
  % Delete edges with V as first vertex
  L = keyDeleteR(V, 1, EL),
  % Delete edges with V as second vertex
  SL = keyDeleteR(V, 2, L),
  {lists:keydelete(V, 1, VL), SL, D}.

% Add edge E
%     If d ->
%         (edge direction represented as vertices ordered in edge list)
%         If vertices in vertexlist and not in edge list  -> add edge to edgelist
%         Else                                            -> return unmodified graph
%     Else ->
%         (edge direction is trivial)
%         If vertices in vertexlist and not in edge list  -> add edge to edgelist
%         Else                                            -> return unmodified graph
addEdge(G, V1, V2) ->
  {VL, EL, D} = G,
  % Are vertex 1 and vertex 2 in vertexlist?
  case (lists:keymember(V1, 1, VL)) and (lists:keymember(V2, 1, VL)) of
    true ->
      % Is there a tuple where the combination of V1 and V2 are an entry?
      TE = findTupleCombination(V1, V2, EL),
      case lists:flatlength(TE) > 0 of
        true -> G; % Edge already in graph
        false -> {VL, lists:append(EL, [{V1, V2, [{'weight', nil}]}]), D}
      end;
    false -> addEdge(addVertex(addVertex(G, V1), V2), V1, V2)
  end.

% Delete edge E, return modified graph
% Check if d or nd
%     If d -> delete actual sequence from edgelist
%     Else        -> delete every combination of sequence from edgelist
deleteEdge(G, V1, V2) ->
  {VL, EL, D} = G,
  % Is there an edge with passed vertices?
  TE = findTupleCombination(V1, V2, EL),
  L = lists:subtract(EL, TE),
  {VL, L, D}.


% Set attribute with value to an edge
setAtE(G, {V1, V2}, N, Val) ->
  {VL, EL, D} = G,
  % Is there an edge with passed vertices?
  TE = findTupleCombination(V1, V2, EL),
  case lists:flatlength(TE) > 0 of
    true -> % Delete the edge ...
      L = lists:subtract(EL, TE),
      % We need to do this because findTupleCombination(...) returns exact ordering of upcoming vertices
      [{SV1, SV2, _}] = TE,
      % And add it again with given attribute name and value
      SL = lists:append(L, [{SV1, SV2, [{N, Val}]}]),
      {VL, SL, D};
    false -> G
  end.

% Set attribute with value to a vertex
setAtV(G, V, N, Val) ->
  {VL, EL, D} = G,
  % Try to find vertex
  L = lists:keyfind(V, 1, VL),
  case L == false of
    % Vertex not found, return unmodified
    true -> G;
    % Vertex found
    false -> {_, AL} = L,
      % Try to find attrubute
      SL = lists:keyfind(N, 1, AL),
      case SL == false of
        % Attribute not found
        true -> {lists:append([{V, lists:append(AL, [{N, Val}])}], lists:delete(L, VL)), EL, D};
        % Attribute found
        false -> {lists:append([{V, lists:append(lists:delete(SL, AL), [{N, Val}])}], lists:delete(L, VL)), EL, D}
      end
  end.

% Return Value Val of attribute with name N of edge {V1, V2}
getValE(G, {V1, V2}, N) ->
  {_, EL, _} = G,
  % Is there an edge with passed vertices?
  TE = findTupleCombination(V1, V2, EL),
  case lists:flatlength(TE) > 0 of
    true -> [{_, _, AL}] = TE,
      % Find an atribute in attribute list with name N
      A = lists:keyfind(N, 1, AL),
      case A /= false of
        % Return Val of found attribute
        true -> {_, Val} = A,
          Val;
        % No such attribute with name n
        false -> nil
      end;
    % Edge not found
    false -> nil
  end.

% Return Value Val of attribute N of Vertex V
getValV(G, V, N) ->
  {VL, _, _} = G,
  T = lists:keyfind(V, 1, VL),
  case T /= false of
    true -> {_, AL} = T,
      A = lists:keyfind(N, 1, AL),
      case A /= false of
        true -> {_, Val} = A,
          Val;
        false -> nil
      end;
    false -> nil
  end.

% Return incident vertices of vertex V
% (All edges connected to V)
getIncident(G, V) ->
  {_, EL, D} = G,
  % Find all edges connected to vertex V
  case D == d of
    true -> findRD(V, EL, []);
    false -> findRND(V, EL, [])
  end.


% Return adjacent vertices list VL of vertex V
% (all vertices that are neighbours to V)
getAdjacent(G, V) ->
  {_, EL, D} = G,
  case D == d of
    true -> getAdjacentDR(V, EL, []);
    false -> getAdjacentNDR(V, EL, [])
  end.

% Return target vertex list VL fo vertex V
getTarget(G, V) ->
  {_, EL, D} = G,
  case (D == d) of
    true -> getAdjacentDR(V, EL, []);
    false -> getAdjacentNDR(V, EL, [])
  end.

% Return source vertex list VL of vertex V
getSource(G, V) ->
  {_, EL, D} = G,
  case (D == d) of
    true -> getSourceR(V, EL, []);
    false -> getAdjacentNDR(V, EL, [])
  end.

% Return edges list EL
getEdges(G) ->
  {_, EL, D} = G,
  case D == d of
    true ->getEdgesDR(EL, []);
    false -> getEdgesNDR(EL, [])
  end.

% Return vertices list VL
getVertexes(G) ->
  {VL, _, _} = G,
  getVertexesR(VL, []).

% Import graph from file with name FN with direction D
importG(FN, D) ->
  Data = util:readlist(FN),
  G = createG(D),
  importDataR(G, Data).

% Export graph from file with name FN to file F
exportG(G, FN) ->
  {_, EL, _} = G,
  L = exportDataR(EL, []),
  util:writelist(L, FN).

% Print a graph of file with name FN to file F
% Needs the graph to be printed and the dot filename (withour extension - *.dot)
printG(G, FN) ->
  {VL, EL, D} = G,
  case D == d of
    true -> graphvizDigraph(FN);
    false -> graphvizGraph(FN)
  end,
  addNodeR(VL),
  addEdgeR(EL),
  to_dot(FN ++ ".dot"),
  os:cmd("release\\bin\\dot.exe -Tpng " ++ FN ++ ".dot > " ++ FN ++ ".png"),
  graphvizDelete().

% Helpers
findTupleCombination(_, _, []) -> [];
findTupleCombination(V1, V2, EL) ->
  L = lists:sublist(EL, 1),
  [{E1, E2, _}] = L,
  if
  % Current tuple with V1, V2 in the same order or twisted order?
    (E1 == V1) and (E2 == V2) -> L;
    (E2 == V1) and (E1 == V2) -> L;
    true -> findTupleCombination(V1, V2, lists:subtract(EL, L))
  end.

% Recursive implementation of lists:keydelete to delete all elements
keyDeleteR(V, N, TL) ->
  case lists:keymember(V, N, TL) of
    true -> keyDeleteR(V, N, lists:keydelete(V, N, TL));
    false -> TL
  end.

% Recursive implementation of lists:keyfind to return a list of all members
findRD(V, TL, L) ->
  case (lists:keymember(V, 1, TL)) of
    true -> E = lists:keyfind(V, 1, TL),
      {V1, V2, _} = E,
      SL = lists:append(L, [V1]),
      SSL = lists:append(SL, [V2]),
      findRD(V, lists:keydelete(V, 1, TL), SSL);
    false -> L
  end.

findRND(V, TL, L) ->
  case (lists:keymember(V, 1, TL)) of
    true -> E =lists:keyfind(V, 1, TL),
      {V1, V2, _} = E,
      SL = lists:append(L, [V1]),
      SSL = lists:append(SL, [V2]),
      findRND(V, lists:keydelete(V, 1, TL), SSL);
    false ->
      case (lists:keymember(V, 2, TL)) of
        true -> E =lists:keyfind(V, 2, TL),
          {V1, V2, _} = E,
          SL = lists:append(L, [V1]),
          SSL = lists:append(SL, [V2]),
          findRND(V, lists:keydelete(V, 2, TL), SSL);
        false -> L
      end
  end.

% Recursive implementation of reading data from read_file into graph
importDataR(G, []) -> G;
importDataR(G, Data) ->
  % Process a chunk of 3 values per recursve iteration
  L = lists:sublist(Data, 3),
  [V1, V2, Val] = L,
  T1 = addVertex(addVertex(G, V1), V2),
  T2 = addEdge(T1, V1, V2),
  T3 = setAtE(T2, {V1, V2}, "weight", Val),
  importDataR(T3, lists:subtract(Data, L)).

% Recursive implementation of writing data form graph into file
exportDataR([], L) -> L;
exportDataR(EL, L) ->
  SL = lists:sublist(EL, 1),
  [{V1, V2, [{ _, Val}]}] = SL,
  SSL = lists:append(L, [V1, V2, Val]),
  exportDataR(lists:subtract(EL, SL), SSL).

% Recursive implementation of adding vertices to a graph (graphviz)
addNodeR([]) -> [];
addNodeR([H|T]) ->
  {V, _} = H,
  add_node(util:to_String(V)),
  addNodeR(T).

% Recursive implementation of adding edges to a graph (graphviz)
addEdgeR([]) -> [];
addEdgeR([H|T]) ->
  {V1, V2, [{_, Val}]} = H,
  add_edge(util:to_String(V1), util:to_String(V2),  util:to_String(Val)),
  addEdgeR(T).

% Recursive implementation to find all adjacent vertexes to vertex V (d)
getAdjacentDR(V, TL, L) ->
  case (lists:keymember(V, 1, TL)) of
    true -> {_, V2, _} = lists:keyfind(V, 1, TL),
      getAdjacentDR(V, lists:keydelete(V, 1, TL), lists:append(L, [V2]));
    false -> L
  end.

% Recursive implementation to find all adjacent vertexes to vertex V (nod)
getAdjacentNDR(V, TL, L) ->
  case (lists:keymember(V, 1, TL)) of
    true -> {_, V2, _} = lists:keyfind(V, 1, TL),
      getAdjacentNDR(V, lists:keydelete(V, 1, TL), lists:append(L, [V2]));
    false ->
      case (lists:keymember(V, 2, TL)) of
        true -> {V1, _, _} = lists:keyfind(V, 2, TL),
          getAdjacentNDR(V, lists:keydelete(V, 2, TL), lists:append(L, [V1]));
        false -> L
      end
  end.

% Recursive implementation to find all source vertices to vertex V
getSourceR(V, TL, L) ->
  case (lists:keymember(V, 2, TL)) of
    true -> {V1, _, _} = lists:keyfind(V, 2, TL),
      getSourceR(V, lists:keydelete(V, 2, TL), lists:append(L, [V1]));
    false -> L
  end.

% Recursive implementation to create a list of all edges without attributes of digraph
getEdgesDR([], L) -> L;
getEdgesDR([H|T], L) ->
  {V1, V2, _} = H,
  EL = lists:append(L, [V1, V2]),
  getEdgesDR(T, EL).

% Recursive implementation to create a list of all edges without attributes
getEdgesNDR([], L) -> L;
getEdgesNDR([H|T], L) ->
  {V1, V2, _} = H,
  SL = lists:append(L, [V2, V1]),
  %EL = lists:append(SL, [V1, V2]),
  getEdgesNDR(T, SL).

% Recursive implementation to create a list of all vertices without attributes
getVertexesR([], L) -> L;
getVertexesR([H|T], L) ->
  {V1, _} = H,
  VL = lists:append(L, [V1]),
  getVertexesR(T, VL).

%% printing gnraphs via graphviz
% -- Constructor
graphvizDigraph(Id) ->
  register(graph_server, spawn(?MODULE, graph_server, [{Id, {digraph, "->"}, [] ,[], []}])).

graphvizGraph(Id) ->
  register(graph_server, spawn(?MODULE, graph_server, [{Id, {graph, "--"}, [] ,[], []}])).

% -- Destructor
graphvizDelete() ->
  graph_server ! stop.

% -- Server/Dispatcher
graph_server(Graph) ->
  receive
    {add_node, Id} ->
      graph_server(add_node(Graph, Id));

    {add_edge, NodeOne, NodeTwo, Weight} ->
      graph_server(add_edge(Graph, NodeOne, NodeTwo, Weight));

    {to_dot, File} ->
      to_dot(Graph, File),
      graph_server(Graph);

    {to_file, File, Format} ->
      to_file(Graph, File, Format),
      graph_server(Graph);

    {value, Pid} ->
      Pid ! {value, Graph},
      graph_server(Graph);

    stop -> true
  end.

add_node(Id) -> graph_server ! {add_node, Id}.
add_edge(NodeOne, NodeTwo, Weight) -> graph_server ! {add_edge, NodeOne, NodeTwo, Weight}.
to_dot(File) -> graph_server ! {to_dot, File}.
to_file(File, Format) -> graph_server ! {to_file, File, Format}.

% -- Implementation

add_node(Graph, Id) ->
  {GraphId, Type, GraphOptions, Nodes, Edges} = Graph,
  io:format("Add node ~s to graph ~s !~n",[Id, GraphId]),
  {GraphId, Type, GraphOptions, Nodes ++ [Id], Edges}.

add_edge(Graph, NodeOne, NodeTwo, Weight) ->
  {GraphId, Type, GraphOptions, Nodes, Edges} = Graph,
  io:format("Add edge ~s -> ~s with weight ~s to graph ~s !~n", [NodeOne, NodeTwo, Weight, GraphId]),
  {GraphId, Type, GraphOptions, Nodes, Edges ++ [{NodeOne, NodeTwo, Weight}]}.

to_dot(Graph, File) ->
  {GraphId, Type, _, Nodes, Edges} = Graph,
  {GraphType, EdgeType} = Type,

  % open file
  {ok, IODevice} = file:open(File, [write]),

  % print graph
  io:format(IODevice, "~s ~s {~n", [GraphType, GraphId]),

  % print nodes
  lists:foreach(
    fun(Node) ->
      io:format(IODevice, "  ~s;~n",[Node])
    end,
    Nodes
  ),

  % print edges
  lists:foreach(
    fun(Edge) ->
      {NodeOne, NodeTwo, Weight} = Edge,
      case Weight == "nil" of
        true -> io:format(IODevice, "  ~s ~s ~s;~n",[NodeOne, EdgeType, NodeTwo]);
        false -> io:format(IODevice, "  ~s ~s ~s [label= ~s];~n",[NodeOne, EdgeType, NodeTwo, Weight])
      end
    %io:format(IODevice, "  ~s ~s ~s [label= ~s];~n",[NodeOne, EdgeType, NodeTwo, Weight])
    end,
    Edges
  ),

  % close file
  io:format(IODevice, "}~n", []),
  file:close(IODevice).

to_file(Graph, File, Format) ->
  {A1,A2,A3} = now(),
  DotFile = lists:concat([File, ".dot-", A1, "-", A2, "-", A3]),
  to_dot(Graph, DotFile),
  DotCommant = lists:concat(["dot -T", Format, " -o", File, " ", DotFile]),
  os:cmd(DotCommant),
  file:delete(DotFile).