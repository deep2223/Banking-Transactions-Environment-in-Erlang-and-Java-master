import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//  if(!customer.CustomerNames3.contains(Thread.currentThread().getName()))

public class customer  extends Thread {

    public static HashMap<String, Integer> HashCustomer = new HashMap<>(); //{joe=157, sue=125, bob=100, jill=450}
    public static HashMap<String, Integer> HashCustomercopy = new HashMap<>(); //{joe=157, sue=125, bob=100, jill=450}

    public static HashMap<String, ArrayList<String>> CustomerBank = new HashMap<>();
    //   {joe=[rbc, bmo, ing], sue=[rbc, bmo, ing], bob=[rbc, bmo, ing], jill=[rbc, bmo, ing]}

    public static ArrayList<String> CustomerNames = new ArrayList<>();  // [jill, joe, bob, sue]
    public static ArrayList<String> CustomerNames2 = new ArrayList<>();
    public static ArrayList<String> CustomerNames3 = new ArrayList<>();


    public static ArrayList<String> BankName = new ArrayList<>();   //   [rbc, bmo, ing]
    public String cname;
    Random rand = new Random();

    public static void customer1(int j) {
        BankName = bank.BankNamestoCustomers();

        ArrayList<String> artmp;

        for (String name : CustomerNames) {
            artmp = new ArrayList<>();
            for(String s : BankName)
            {
                artmp.add(s);
            }

            CustomerBank.put(name, artmp);
        }

    }

    public customer() {

        //System.out.println("** Customers and loan objectives **");
        new money("\n** Customers and loan objectives **");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("customers.txt"));
            String line = reader.readLine();
            while (line != null) {
                line = line.replace("{", "");
                line = line.replace("}", "");
                line = line.replace(".", "");
                String[] Ttemp = line.split(",");
                new money(Ttemp[0] + ":" + Ttemp[1]);
                CustomerNames.add(Ttemp[0]);
                HashCustomer.put(Ttemp[0], Integer.parseInt(Ttemp[1]));
                HashCustomercopy.put(Ttemp[0], Integer.parseInt(Ttemp[1]));
                line = reader.readLine();
            }
            reader.close();
            new money("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public customer(String n) {
        this.cname = n;
    }

    public void run() {

        if (Thread.currentThread().getName() != "Temp") {
            synchronized (this) {
                Thread bankThred = null;
                String cname2 = Thread.currentThread().getName();
                String cbank;


                while (HashCustomer.get(cname) > 0 && CustomerBank.containsKey(cname) && CustomerBank.get(cname2).size() > 0) {


                    //System.out.println(CustomerBank.get(cname2) + cname2 + CustomerBank.get(cname2).size());


                    cbank = CustomerBank.get(cname2).get(rand.nextInt(CustomerBank.get(cname2).size()));

                    int ctamount;
                    if (HashCustomer.get(cname2) > 50) {
                        ctamount = rand.nextInt(50) + 1;
                    } else {
                        ctamount = rand.nextInt(HashCustomer.get(cname)) + 1;
                    }


                    if (HashCustomer.get(cname2) > 0) {
                        bankThred = new Thread(new bank(cname2, cbank, ctamount));
                        bankThred.setName(cname2 + "," + cbank + "," + ctamount);

                        new money(cname2 + " requests a loan of " + ctamount + " dollar(s) from " + cbank);
                        bankThred.start();

                        try {
                            bankThred.join();
                            bankThred.sleep(rand.nextInt(100));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        //CustomerBank.remove(cname2);
                    }


                }


                if (bankThred != null) {
                    while (bankThred.isAlive()) {

                    }




                }


                ArrayList<String> art = new ArrayList<>();
                ArrayList<Integer> art2 = new ArrayList<>();


                for (int i = 0; i < bank.BankNames2.size(); i++) {
                    art2.clear();
                    ArrayList<Integer> art3 = new ArrayList<>();
                    for (int j = 0; j < CustomerBank.keySet().size(); j++) {
                        art3.add(0);
                    }
                    for (String s : CustomerBank.keySet()) {
                        if (CustomerBank.get(s).size() != 0) {

                            if(CustomerBank.containsKey(s)){
                                if (CustomerBank.get(s).contains(bank.BankNames2.get(i))) {
                                    art2.add(1);
                                } else {
                                    art2.add(0);
                                }}
                        }
                    }

                    if (art2.containsAll(art3)) {

                        if(bank.BankNames2.size()<i){

                            if (bank.BankNames2.get(i) != null) {
                                String btmp = bank.BankNames2.get(i);

                                if (!bank.BankNames3.contains(btmp)) {
                                    if (bank.BankNames2.contains(btmp)) {
                                        bank.BankNames2.remove(btmp);
                                        bank.BankNames3.add(btmp);


                                        new money(btmp + " has " + bank.HashBank.get(btmp) + " dollar(s) remaining. ",1);

                                    }
                                }
                            }
                        }
                    }
                }

                if (HashCustomer.get(cname2) == 0) {

                        new money(cname2 + " has reached the objective of " + HashCustomercopy.get(cname2) + " dollar(s). Woo Hoo!", 1);

                //   new money(cname2 + " has reached the objective of " + HashCustomercopy.get(cname2) + " dollar(s). Woo Hoo!",1);
                    Thread.currentThread().interrupt();
                } else {

                        new money(cname2 + " was only able to borrow " + (HashCustomercopy.get(cname2) - HashCustomer.get(cname2)) + " dollar(s). Boo Hoo!", 1);

                    Thread.currentThread().interrupt();
                }

                Thread.currentThread().interrupt();
                //  System.out.println(bank.HashBank + "       " + customer.HashCustomer + " " + customer.CustomerBank + " " + customer.CustomerNames + " " + customer.BankName);
            }
        }
    }
}