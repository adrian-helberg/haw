-module(bellmannford).

-export([bellmannford/3, initial/2, initialR/2, algorithm/2, algorithmR/2, giveResult/2, hasNegativeCycle/2]).

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

%% BELLMANN-FORD ALGORITHMUS
%% Verfahren zur Suche nach kürzesten Wegen in Graphen
%% Akzeptiert wird ein gerichteter oder ungerichteter Graph mit positiven und negativen Kantengewichtungen
%% Zyklen mit negativer Gewichtung die vom Startknoten ereichbar sind, werden ausgeschlossen
%% Negative Zyklen werden im Algorithmus erkannt.

%% INITIAL
%% Für jeden Knoten v: Distanz(v) := nil (unendlich), Vorgänger(v) := 0 (keiner)
%% Für Startknoten s: Distanz(s) := 0

%% ALGORITHMUS
%% Knoten V (v)
%% Kante E (u,v)
%% Anzahl der Knoten n
%%
%% LOOP n-1 mal:
%%    FOR ALL E:
%%        IF (Distanz(u) + Gewicht(u,v)) < Distanz(v) THEN
%%          Distanz(v) := Distanz(u) + Gewicht(u,v)
%%          Vorgänger(v) := u
%% FOR ALL E:
%%    IF (Distanz(u) + Gewicht(u,v)) < Distanz(v) THEN
%%        RETURN AND STOP "negativ gewichteter Zyklus"
%% RETURN Distanz

-spec bellmannford(FN :: atom(), S :: integer(), D :: d | ud) -> [{integer(), integer(), integer()}].
bellmannford(FN, S, D) ->
  G = adtgraph:importG(FN, D),
  % INITIAL
  I = initial(G, S),
  % LOOP
  {VL1, _, _} = I,

  BF = algorithm(I, lists:flatlength(VL1) - 1),

  {X, Y, _} = BF,
  case hasNegativeCycle(BF, Y) of
    true -> io:fwrite("Zyklus negativen Gewichtes!");
    false -> io:fwrite("~p", [giveResult(X, [])])
  end.

% INITIAL
-spec initial(G :: graph(), S :: integer()) -> G :: graph().
initial(G, S) ->
  {VL, _, _} = G,
  % Startknoten
  SG = adtgraph:setAtV(adtgraph:setAtV(G, S, "Entf", 0), S, "Vorg", 0),
  % Restliche Knoten
  initialR(SG, lists:keydelete(S, 1, VL)).

-spec initialR(G :: graph(), VL :: [integer()]) -> G :: graph().
initialR(G, []) -> {VL, EL, D} = G, {lists:reverse(VL), EL, D};
initialR(G, [H|T]) ->
  {V, _} = H,
  initialR(adtgraph:setAtV(adtgraph:setAtV(G, V, "Entf", nil), V, "Vorg", 0), T).

% OUTER LOOP
-spec algorithm(G :: graph(), C :: integer()) -> G :: graph().
algorithm(G, 0) -> G;
algorithm(G, C) ->
  % Process all edges recursivly
  SG = algorithmR(G, adtgraph:getEdges(G)),
  % Process n - 1 times
  algorithm(SG, C - 1).

% INNER LOOP
-spec algorithmR(G :: graph(), EL :: [integer()]) -> G :: graph().
algorithmR(G, []) -> G;
algorithmR(G, L) ->
  SL = lists:sublist(L, 2),
  SSL = lists:subtract(L, SL),
  [U, V] = SL,
  % Get distance from U and V
  EntfU = adtgraph:getValV(G, U, "Entf"),
  EntfV = adtgraph:getValV(G, V, "Entf"),
  % Get weight from (u,v)
  GewUV = adtgraph:getValE(G, {U, V}, "weight"),
  % Check whether distance of V or U is infinity (represented as nil)
  case EntfU /= nil of
    % distance U is not infinity
    true ->
      case EntfV == nil of
        % distance V is infinity
        true -> algorithmR(adtgraph:setAtV(adtgraph:setAtV(G, V, "Entf", (EntfU + GewUV)), V, "Vorg", U), SSL);
        % distance V has actual value
        false ->
          case (EntfU + GewUV) < EntfV of
            % Loop condition matches
            true -> algorithmR(adtgraph:setAtV(adtgraph:setAtV(G, V, "Entf", (EntfU + GewUV)), V, "Vorg", U), SSL);
            % Do nothing and continue
            false -> algorithmR(G, SSL)
          end
      end;
    % Do nothing and continue
    false -> algorithmR(G, SSL)
  end.

% HASNEGATIVECYCLE
-spec hasNegativeCycle(G :: graph(), [edge()]) -> G :: graph().
hasNegativeCycle(_, []) -> false;
hasNegativeCycle(G, [H|T]) ->
  {U, V, _} = H,
  EntfU = adtgraph:getValV(G, U, "Entf"),
  EntfV = adtgraph:getValV(G, V, "Entf"),
  GewUV = adtgraph:getValE(G, {U, V}, "weight"),

  case (EntfU + GewUV) < EntfV of
    true -> true;
    false -> hasNegativeCycle(G, T)
  end.

-spec giveResult(VL :: [vertex()], L :: [{integer(), integer(), integer()}]) -> [{integer(), integer(), integer()}].
giveResult([], L) -> L;
giveResult([H|T], L) ->
  {V, AR} = H,
  [{_, Val1}, {_, Val2}] = AR,
  giveResult(T, lists:append(L, [{V, Val1, Val2}])).