import java.util.ArrayList;
import java.util.Random;

public class money implements Runnable  {
   public static boolean flag1 = true;

   public static String Str ="\n";

    public void run()
    {
        System.out.println(Str);
    }

    public  money(String msg)
    {
        //Str=Str+msg+"\n";
        synchronized (this) {
            System.out.println(msg);
        }
    }

    public  money(String msg,int i)
    {
       synchronized (this){

           Str = Str + msg + "\n";
//            if(!customer.CustomerNames3.contains(Thread.currentThread().getName())) {
//                customer.CustomerNames3.add(Thread.currentThread().getName());
//              //  Str = Str + msg + "\n";
//            }
        }
        //Str=Str+msg +"\n";
        //System.out.println(msg);
    }

    public  money(String msg,int i,int j)
    {
        String str = Thread.currentThread().getName();
        String[] temp = str.split(",");
        String Bnkname = temp[1];
        if(!bank.BankNames4.contains(Bnkname)) {
            bank.BankNames4.add(Bnkname);
           // Str = Str + msg + "\n";
            Str=Str+msg +"\n";
        }
        //System.out.println(msg);
    }

    public  money() { }

    public void printt(String msg)
    {
        System.out.println(msg);
    }

    public static void main(String[] args) throws InterruptedException {

        try{
        Thread t1 = new Thread(new bank());
        t1.setName("Temp");
         Thread t2 = new Thread(new customer());
        t2.setName("Temp");
        t1.start();
        t2.start();
            t1.join();
            t2.join();

        customer.customer1(1);
        Random rand = new Random();
        int k = customer.CustomerNames.size();
        Thread Customer[] = new Thread[k];

        for(int i=0;i<k;i++)
        {
            String n=customer.CustomerNames.get(i);
           // System.out.println(n);
            Customer[i] = new Thread(new customer(n));
            Customer[i].setName(customer.CustomerNames.get(i));
        }

        for(int i=0;i<k;i++)
        {
                Customer[i].start();
        }


       for(int i=0 ;i<Customer.length;i++)
       {
                    while(Customer[i].isAlive())
           {

           }
                    //System.out.println(Customer[i].isAlive());
       }

            ArrayList<Boolean> flag = new ArrayList<>();
            for(int i=0;i<k;i++)
            {

                        flag.add(true);

            }

            int kk = flag.size();
            do
            {
                if(flag.isEmpty() || flag.size()==0)
                {
                    Thread.currentThread().sleep(600);
                  //  System.out.println(bank.BankNames3);
                    flag1=false;

                   // System.out.println(bank.BankNames2);
                    bank.BankNames.removeAll(bank.BankNames3);
                   // System.out.println(bank.BankNames);
                   // System.out.println(bank.BankNames);

//                   if(bank.BankNames.size()!=0){
//                       for (String s : bank.BankNames) {
//                           Str +=  s + " has " + bank.HashBank.get(s) + " dollar(s) remaining. \n";
//                       }
//                   }
                   Str=Str.trim();
                    for(String s : customer.HashCustomer.keySet())
                    {
                        if(!Str.contains(s))
                        {
                            if (customer.HashCustomer.get(s) == 0) {
                                Str+="\n"+s + " has reached the objective of " + customer.HashCustomercopy.get(s) + " dollar(s). Woo Hoo!";
                            } else {
                                Str+="\n"+s + " was only able to borrow " + (customer.HashCustomercopy.get(s) - customer.HashCustomer.get(s)) + " dollar(s). Boo Hoo!";
                            }
                            break;
                        }
                    }
                    System.out.println(Str);
                  //  System.out.println("987398723743749283792387937492372937");
                  // System.out.println(customer.HashCustomer.keySet()+" "+bank.HashBank+"   "+bank.BankNames3 + " "+ customer.CustomerNames3);
                    break;
                }
                else {
                     //kk= flag.size();
                    for (int i = 0; i < kk; i++) {

                       // System.out.println("current ->" + Customer[i].getName());

                        if (!Customer[i].isAlive()) {
                            flag.remove(i);
                            break;
                        }
                        //System.out.println(flag + ""+flag.size());
                    }
                    kk= flag.size();
                }
            }
            while (flag1==true);

        }catch (Exception e){}

        Str=Str.trim();

        for(String s : customer.HashCustomer.keySet())
        {
            if(!Str.contains(s))
            {
                if (customer.HashCustomer.get(s) == 0) {
                    System.out.println(s + " has reached the objective of " + customer.HashCustomercopy.get(s) + " dollar(s). Woo Hoo!");
                } else {
                    System.out.println(s + " was only able to borrow " + (customer.HashCustomercopy.get(s) - customer.HashCustomer.get(s)) + " dollar(s). Boo Hoo!");
                }
            }
        }
        for(String s : bank.BankNames)
        {
            if(!Str.contains(s))
            {
                    System.out.println(s + " has " + bank.HashBank.get(s) + " dollar(s) remaining.");
            }
        }
    }
}