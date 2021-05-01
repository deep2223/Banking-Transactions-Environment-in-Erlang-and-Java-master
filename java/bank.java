import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class bank  extends Thread  {

    public static HashMap<String,Integer> HashBank = new HashMap<>();  // { a=800 , b=500}
    public static ArrayList<String> BankNames = new ArrayList<>();    //   [a ,b ]
    public static ArrayList<String> BankNames2 = new ArrayList<>();
    public static ArrayList<String> BankNames3 = new ArrayList<>();
    public static ArrayList<String> BankNames4 = new ArrayList<>();
    Thread ttemp;
    public String a,b;
    public int c;


    public  static   ArrayList<String>  BankNamestoCustomers()
    {
        return BankNames;
    }


    public  bank(String name, String bankname,  int loanAmount)
    {
        this.a=name;
        this.b=bankname;
        this.c=loanAmount;
    }


    public  bank()
    {
        BufferedReader reader;
        try {

            new money("** Banks and financial resources **");

            reader = new BufferedReader(new FileReader("banks.txt"));
            String line = reader.readLine();
            while (line != null) {
                line=line.replace("{", "");
                line=line.replace("}", "");
                line=line.replace(".", "");
                String[] Ttemp=line.split(",");
                new money(Ttemp[0]+":"+Ttemp[1]);
                BankNames.add(Ttemp[0]);
                BankNames2.add(Ttemp[0]);
                HashBank.put(Ttemp[0],Integer.parseInt(Ttemp[1]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        Random rand = new Random();
        synchronized (this) {

            try {

                String str = Thread.currentThread().getName();
                String[] temp = str.split(",");
                int amount = Integer.parseInt(temp[2]);
                String cusname = temp[0];
                String cusbankname = temp[1];
                // new money(cusname+" requests a loan of "+amount+" dollar(s) from "+ cusbankname);


                int remaining = HashBank.get(cusbankname) - amount;

                if (remaining >= 0) {


                    int camont = customer.HashCustomer.get(cusname) - amount;
                    customer.HashCustomer.put(cusname, camont);

                    bank.HashBank.put(cusbankname, remaining);
                    new money(cusbankname + " approves a loan of " + amount + " dollar(s) from " + cusname);

                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {


                    ArrayList<String> arr = customer.CustomerBank.get(cusname);

                    customer.CustomerBank.get(cusname).remove(cusbankname);

                    if (customer.CustomerBank.get(cusname).size() == 0) {
                        customer.CustomerBank.remove(cusname);
                    }


                    new money(cusbankname + " denies a loan of " + amount + " dollar(s) from " + cusname);


                }
                if (HashBank.get(cusbankname) == 0 && BankNames2.contains(cusbankname)) {
                    if (!BankNames3.contains(cusbankname)) {
                        // BankNames2.remove(cusbankname);
                        BankNames3.add(cusbankname);
                        // System.out.println("-->>" + cusbankname);


                        new money(cusbankname + " has " + 0 + " dollar(s) remaining. ", 1);
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    for (int i = 0; i < customer.CustomerBank.size(); i++) {
                        if (customer.CustomerBank.get(cusname).contains(cusbankname)) {
                            ArrayList<String> temp4 = customer.CustomerBank.get(cusname);
                            temp4.remove(cusbankname);
                            customer.CustomerBank.put(cusname, temp4);
                        }
                    }
                }

        }catch(Exception e){
        }
    }
        //System.out.println(amount+cusname+cusbankname);
    }
}


