package FileOperations;
import Model.Invoice;
import Model.Service;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFiles {
    public static  ArrayList<Invoice> invoices= new ArrayList<>();
   public static ArrayList<Service> items = new ArrayList<>();
  //  protected final List<Invoice> invoicesHeaderList = new ArrayList<Invoice>();
   //MyFrame frameObject = new MyFrame();
   Invoice itemsList ;

    public ArrayList<Service> readItemsCSV (ArrayList<Service>items) throws FileNotFoundException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader("InvoiceLine.csv"));
        while (true) {
            try {
                itemsList = new Invoice();
                if (!((line = br.readLine()) != null)) break;
                String[] itemList = line.split(",");
                Service itemObject = new Service();
                itemObject.setInvNum(Integer.parseInt(itemList[0]));
                itemObject.setServiceName(itemList[1]);
                itemObject.setPrice(Double.parseDouble(itemList[2]));
                itemObject.setServiceCount(Integer.parseInt(itemList[3]));
                items.add(itemObject);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(items.size());
        }
        return items;
    }
    public ArrayList<Invoice> readInvoiceCSV() throws FileNotFoundException {
        //read lines
       // ArrayList
       readItemsCSV(items);
      // itemsList.setLines(items);
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader("InvoiceHeader.csv"));
            while ((line = br.readLine()) != null) {
                String [] invoiceList = line.split(",");
                Invoice invoice = new Invoice();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                invoice.setInvoiceNo(Integer.parseInt(invoiceList[0]));
                try {
                    invoice.setInvoiceDate(sdf.parse(invoiceList[1]));
                } catch (ParseException e) {

                    JOptionPane.showMessageDialog(null, "Please choose right CSV date format your date should be like that day/month/year", "Wrong date format", 1);
                }
                invoice.setName(invoiceList[2]);
                ArrayList<Service> tempItemList=new ArrayList<>();
                for(int i = 0;i<items.size();i++)
                {
                    if(invoice.getInvoiceNo()==items.get(i).getInvNum())
                    {
                        tempItemList.add(items.get(i));

                    }
                    //tempItemList.clear();
                }
                invoice.setLines(tempItemList);
                invoices.add(invoice);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return invoices;
    }

}

