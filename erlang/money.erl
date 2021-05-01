%%%-------------------------------------------------------------------
%%% @author Deep
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 16. Jun 2019 21:40
%%%-------------------------------------------------------------------

-module(money).
-author("Deep").
%-compile(export_all).
-import('customer', [print_customer/0]).
-export([start/0,print_master/1,loop/2]).



start() ->

  CusData = file:consult("customers.txt"),
  Amount = element(2,CusData),     %  [{a,2},{b,1},{c,1}]
  HM_Cus=maps:from_list(Amount),     %  #{a => 2,b => 1,c => 1}
  Keys_c=maps:keys(HM_Cus),        %    [a,b,c]

BankData = file:consult("banks.txt"),
  Amount2 = element(2,BankData),     %    [{bmo => 30,inc => 3,rbc => 10}]
  HM_Bank=maps:from_list(Amount2),   %   #{bmo => 30,inc => 3,rbc => 10}
  Keys_b=maps:keys(HM_Bank),     %     [bmo,inc,rbc]
   Lst = [],

  Ct = spawn(money,print_master,[Lst]),
  register(print,Ct),

  io:format("~n** Customers and loan objectives **~n",[]),
  lists:foreach( fun(Tuple)->
    {Sender,Receiver_List} = Tuple,
    io:format("~w: ~w ~n",[Sender,Receiver_List]),
    Process_id = spawn(customer,print_customer,[]),
    register(Sender,Process_id),
    whereis(Sender)!{Sender , Receiver_List , Receiver_List,Keys_b ,1}
                 end,Amount
  ),

  io:format("~n** Banks and financial resources **~n",[]),
  lists:foreach( fun(Tuple)->
    {Sender,Receiver_List} = Tuple,
    Tmp= maps:get(Sender,HM_Bank),
    io:format("~w: ~w ~n",[Sender,Receiver_List]),
   Process_id = spawn(bank,bank_call,[Tmp,Sender]),
   register(Sender,Process_id)
   %whereis(Sender)!{reply,Tmp}
                 end,Amount2
  ),

io:format("~n",[]).


print_master(List_t)->
  receive

    {summary,cust_s,Name2,Amount_t,Flag2} ->

      if
        Flag2 ==1 ->
              Str =[cuss,Name2,Amount_t,Flag2];
        true ->
              Str =[cuss,Name2,Amount_t,Flag2]
      end,
      List_t2 = lists:append(List_t,[Str]),
      print_master(List_t2);

    {summary,bank_s,Name22,Amount_t2} ->


      Str2=[bankk,Name22,Amount_t2],
   %   Str2= Name22 ++" has "++ Amount_t2 ++" dollar(s) remaining",

      C= lists:member(Str2, List_t),

      if
        C ==true  ->
          List_t2=List_t;

        true ->
          List_t2 = lists:append(List_t,[Str2])
      end,

      print_master(List_t2);

    {customers,Name,Amount,Bank} ->
      io:fwrite("~p requests a loan of ~p dollar(s) from ~p~n",[Name,Amount,Bank]),
      print_master(List_t);


    {bank,Name,Amount,Bank,Flg} ->

      if
        Flg == 1 -> io:fwrite("~p approves a loan of ~p dollars from ~p~n",[Bank,Amount,Name]);

        true -> io:fwrite("~p denies a loan of ~p dollars from ~p~n",[Bank,Amount,Name])
      end,

      print_master(List_t)
  after
    700->
      Lnth=length(List_t),
      loop(Lnth,List_t)
  end.

loop(0,_) ->
  io:format("",[]);
loop(Lnth,List_t) ->
   L=rand:uniform(Lnth),
   Tuple=lists:nth(L,List_t),
   T1=lists:nth(1,Tuple),
  if
    T1==cuss ->

      T4=lists:nth(4,Tuple),
      T2=lists:nth(2,Tuple),
      T3=lists:nth(3,Tuple),
      if
        T4==1 ->
          io:fwrite("~p has reached the objective of ~p dollar(s). Woo Hoo!~n",[T2,T3])
      ;
        true ->
          io:fwrite("~p was only able to borrow ~p dollar(s). Boo Hoo!~n",[T2,T3])
      end;
    true ->
      T2=lists:nth(2,Tuple),
      T3=lists:nth(3,Tuple),
      io:fwrite("~p has ~p dollar(s) remaining.~n",[T2,T3])
  end,

  New_list=lists:delete(Tuple, List_t),
  New_length=length(New_list),
  timer:sleep(rand:uniform(50)),

loop(New_length,New_list).
