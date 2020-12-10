%%%-------------------------------------------------------------------
%%% @author kuba
%%% @copyright (C) 2020, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 09. Dec 2020 18:40
%%%-------------------------------------------------------------------
-module(example).
-author("kuba").

%% API
-export([createProcesses/0, a/1, b/1, c1/0, c1_A/0, c1_B/0, c2/2, c3/0]).

createProcesses() ->
  PidC = spawn(example, c3, []),
  spawn(example, a, [PidC]),
  spawn(example, b, [PidC]).

a(PidC) ->
  timer:sleep(500),
  PidC ! aaa,
  a(PidC).

b(PidC) ->
  timer:sleep(500),
  PidC ! bbb,
  b(PidC).

c1() ->
  receive
    aaa ->
      io:format("C1: odebrano: aaa~n"),
      c1_B();
    bbb ->
      io:format("C1: odebrano: bbb~n"),
      c1_A()
  end.

c1_A() ->
  receive
    aaa ->
      io:format("C1: odebrano: aaa~n"),
      c1_B()
  end.

c1_B() ->
  receive
    bbb ->
      io:format("C1: odebrano: bbb~n"),
      c1_A()
  end.

c2(Acounter, Bcounter) ->
  receive
    aaa ->
      io:format("C2: odebrano: aaa, licznik: ~p~n", [Acounter]),
      c2(Acounter + 1, Bcounter);
    bbb ->
      io:format("C2: odebrano: bbb, licznik: ~p~n", [Bcounter]),
      c2(Acounter, Bcounter + 1);
    _ -> c2(Acounter, Bcounter)
  end.

c3() ->
  receive
    Something -> io:format("C3: odebrano: ~p~n", [Something])
  end,
  c3().
