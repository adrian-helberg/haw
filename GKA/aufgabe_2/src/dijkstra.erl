%% Dijkstra-Algorithm
-module(dijkstra).

-export([dijkstra/3]).

% Specs ---
-spec dijkstra(FN :: atom(), V :: integer(), D :: directed | nondirected) ->  [{V :: integer(), W :: integer(), VO :: integer()}].

%% Operations
dijkstra(FN, S, D) ->
  % Should create Graph from importG
  G = adtgraph:importG(FN, D),
  %G = createGr(ud),
  % Set Attribute for all vertices in G
  G1 = initialR(G, adtgraph:getVertexes(G)),

  % Set Attribute distance for V to 0 
  G3 = adtgraph:setAtV(adtgraph:setAtV(adtgraph:setAtV(G1,S,'prev', 0),S,'dist', 0), S, 'ok', true),
  % Call dijkstra algorithm with Graph, Vertex and list of all vertices in graph
  Dijkstra = loop(G3,S, S, getVertexesWithOKFalse(G3, [], adtgraph:getVertexes(G3))),
  {X,_,_} = Dijkstra,
  io:fwrite("~p~n",[giveResult(X,[])]).

getVertexesWithOKFalse(G, L, []) -> L;
getVertexesWithOKFalse(G, L, [H|T]) ->
  case adtgraph:getValV(G,H, 'ok') == false of
    true ->
      L1 = lists:append([H],L),
      getVertexesWithOKFalse(G, L1 ,T);
    false ->
      getVertexesWithOKFalse(G, L, T)
  end.

% Loop as long as Q is not empty
loop(G,_,_,[]) -> G;
loop(G,S,V,Q) ->
  % Get cheapest adjacent Vertex from Vertex v
  {G1 ,CDI} = updateAndGetCheapest(G, V, adtgraph:getAdjacent(G, V), nil),
  case CDI == nil of
    true ->
      loop(G1, S, adtgraph:getValV(G1,V,'prev'), Q);
    false ->
      Q1 = lists:delete(S,Q),
      % Add distance to Vertex with cheapest Distance9
      case adtgraph:getValV(G1, CDI, 'ok') == false of
        true ->
          G2 = adtgraph:setAtV(G1, CDI,'ok', true),
          G3 = loop2(G2, CDI, adtgraph:getAdjacent(G2,CDI)),
          loop(G3, S, CDI, lists:delete(CDI,Q1));
        false ->
          G3 = loop2(G1, CDI, adtgraph:getAdjacent(G1,CDI)),
          loop(G3, S, CDI, lists:delete(CDI,Q1))
      end
  end.

% Update distance if possible
loop2(G, _, []) -> G;
loop2(G, V, [H|T]) ->
  case adtgraph:getValV(G, H, 'ok') == false of
    true ->
      G2 = distUpdate(G, V, H),
      loop2(G2,V,T);
    false ->
      loop2(G,V,T)
  end.
  
% Updates distance
distUpdate(G, V, H) ->
  HD = adtgraph:getValV(G, H, 'dist'),
  VHW = adtgraph:getValE(G, {V, H}, 'weight'),
  VD = adtgraph:getValV(G, V, 'dist'),
  case HD == infinity of
    true ->
      adtgraph:setAtV(adtgraph:setAtV(G, H, 'dist', VHW + VD), H, 'prev', V);
	  false ->
      case HD > (VD + VHW) of
        true ->
          adtgraph:setAtV(adtgraph:setAtV(G,H,'dist',VD + VHW),H,'prev',V);
        false -> G
      end
  end.

% Get the cheapest distance from adjacent vertices to vertex v
updateAndGetCheapest(G, _, [], V) -> {G,V};
updateAndGetCheapest(G, S, [H|T], V) ->
  VD = adtgraph:getValV(G, V, 'dist'),
  HD = adtgraph:getValV(G, H, 'dist'),
  SD = adtgraph:getValV(G, S, 'dist'),
  SHW = adtgraph:getValE(G, {S, H}, 'weight'),
  G1 = case HD == infinity of
    true ->
      adtgraph:setAtV(adtgraph:setAtV(G, H, 'dist', SHW + SD), H, 'prev', S);
    false ->
      case HD > SHW + SD of
        true ->
          adtgraph:setAtV(adtgraph:setAtV(G, H, 'dist', SHW + SD), H, 'prev', S);
        false ->
          G
      end
  end,


  case V == nil of
    true ->
      case adtgraph:getValV(G1, H, 'ok') == true of
        true ->
          updateAndGetCheapest(G1, S, T, V);
        false ->
          updateAndGetCheapest(G1, S, T, H)
      end;
    false ->
      SVW = adtgraph:getValE(G1, {S, V}, 'weight'),
      case (SVW > SHW) of
        true ->
          case adtgraph:getValV(G1, H, 'ok') == true of
            true ->
              updateAndGetCheapest(G1, S, T, V);
            false ->
              updateAndGetCheapest(G1, S, T, H)
          end;
        false ->
          updateAndGetCheapest(G1, S, T, V)
      end
  end.



% Initial vertices with attributes
initialR(G, []) -> G;
initialR(G, [H|T]) ->
  G1 = adtgraph:setAtV(G, H, 'dist', infinity),
  G2 = adtgraph:setAtV(G1, H, 'prev', nil),
  G3 = adtgraph:setAtV(G2, H, 'ok', false),
  initialR(G3, T).

giveResult([], L) -> L;
giveResult([H|T], L) ->
  {V, AR} = H,
  {_, D} = lists:keyfind('dist',1,AR),
  {_, P} = lists:keyfind('prev',1,AR),
  giveResult(T, lists:append(L, [{V, D,P}])).