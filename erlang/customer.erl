%%%-------------------------------------------------------------------
%%% @author Deep
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 17. Jun 2019 20:01
%%%-------------------------------------------------------------------
-module(customer).
-author("Deep").
-import(lists,[nth/2]).
-import(lists,[append/2]).
%% API
%-compile(export_all).
-export([print_customer/0]).



print_customer()->
  receive

    {Name , Target_money , Original_money, BankHashMap,1}->

      timer:sleep(rand:uniform(100)),
     % io:format("In print_cus -> ~p ~p ~p ~p ~n",[Name,Target_money,Original_money,BankHashMap]),

      whereis(Name)!{Name , Target_money , Original_money, BankHashMap},

      print_customer();


    {Name , Target_money , Original_money, BankHashMap} ->

      if
         Target_money == 0 ->
                  whereis(print)!{summary,cust_s,Name,Original_money,1};
                     % io:format("~n ~p ~p ~p ~p ~n~n",[Name , Target_money , Original_money, BankHashMap]);
          true ->

          if
            length(BankHashMap) == 0 ->
                  Tv=Original_money-Target_money,
                 whereis(print)!{summary,cust_s,Name,Tv,0};
             % io:format("~n ~p ~p ~p ~p ~n~n",[Name , Target_money , Original_money, BankHashMap]);
            true ->
                    timer:sleep(rand:uniform(100)),
                    % io:format("Cus_cal  ~p ~p ~p ~p ~n",[Name,Target_money,Original_money,BankHashMap]),
                    BnkInx  = rand:uniform(length(BankHashMap)),
                    BankTmp = lists:nth(BnkInx,BankHashMap),
                    if
                    Target_money >50 ->  AmtTmp=rand:uniform(50);
                    true ->  AmtTmp=rand:uniform(Target_money)
                    end,

                    whereis(print)!{customers,Name,AmtTmp,BankTmp},
                    whereis(BankTmp)!{Name, Target_money, Original_money, BankHashMap, BankTmp, AmtTmp},
                    print_customer()
          end

      end

  end.

