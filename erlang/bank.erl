%%%-------------------------------------------------------------------
%%% @author Deep
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 17. Jun 2019 20:01
%%%-------------------------------------------------------------------
-module(bank).
-author("Deep").
%-compile(export_all).

%% API
-export([bank_call/2]).


bank_call(Bank_amount,B_name)->

  %io:format("~p banbk ~n",[Bank_amount]),

      receive
        {Name, Target_money, Original_money, BankHashMap, RANDbank, Randmoney} ->

          timer:sleep(rand:uniform(100)),
          if

            Bank_amount == 0 ->
              Temp = Bank_amount,
              whereis(print)! {bank,Name,Randmoney,RANDbank,0},
              whereis(print)!{summary,bank_s,B_name,Bank_amount},
              UpdatedList=lists:delete(RANDbank, BankHashMap),
              whereis(Name)!{Name , Target_money , Original_money, UpdatedList}
              ;

            Bank_amount > Randmoney ->
                        Temp = Bank_amount-Randmoney,
                         Tmp2 = Target_money-Randmoney,
              whereis(print)! {bank,Name,Randmoney,RANDbank,1},
              whereis(Name)!{Name , Tmp2 , Original_money, BankHashMap};


            true ->
                        Temp = Bank_amount,
                        UpdatedList=lists:delete(RANDbank, BankHashMap),
                      whereis(print)! {bank,Name,Randmoney,RANDbank,0},
                     whereis(Name)!{Name , Target_money , Original_money, UpdatedList}
          end,

          bank_call( Temp,B_name)
      after
        710->
          whereis(print)!{summary,bank_s,B_name,Bank_amount}

      % io:fwrite("Process ~p (Bank) has received no calls for 1 second, ending...~n",[B_name])

      end

  .


