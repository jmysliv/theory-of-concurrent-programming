%%%-------------------------------------------------------------------
%%% @author kuba
%%% @copyright (C) 2020, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 12. Dec 2020 13:40
%%%-------------------------------------------------------------------
-module(pk).
-author("kuba").

%% API
-export([createProcesses/0, producer/1, consumer/1, buffer/2]).

createProcesses() ->
  Pid = spawn(pk, buffer, [10, []]),
  spawn(pk, producer, [Pid]),
  spawn(pk, producer, [Pid]),
  spawn(pk, consumer, [Pid]),
  spawn(pk, consumer, [Pid]).

producer(BuforPid) ->
  Num = rand:uniform(10),
  BuforPid ! {self(), produce_request, Num},
  receive
    {BuforPid, accepted} ->
      io:format("Producent ~p, wyprodukowal: ~p~n", [self(), Num])
  end,
  timer:sleep(rand:uniform(10000)),
  producer(BuforPid).

consumer(BuforPid) ->
  BuforPid ! {self(), consume_request},
  receive
    {BuforPid, accepted, Num} ->
      io:format("Konsument ~p, skonsumowal: ~p~n", [self(), Num])
  end,
  timer:sleep(rand:uniform(10000)),
  consumer(BuforPid).


buffer(FreeSpace, []) ->
  receive
    {Pid, produce_request, Num} ->
      Pid ! {self(), accepted},
      buffer(FreeSpace - 1, [Num])
  end;

buffer(0, [Head | Tail]) ->
  receive
    {Pid, consume_request} ->
      Pid ! {self(), accepted, Head},
      buffer(1, Tail)
  end;

buffer(FreeSpace, [Head | Tail]) ->
  receive
    {Pid, consume_request} ->
      Pid ! {self(), accepted, Head},
      buffer(FreeSpace + 1, Tail);
    {Pid, produce_request, Num} ->
      Pid ! {self(), accepted},
      buffer(FreeSpace - 1, [Head] ++ Tail ++ [Num])
  end.



