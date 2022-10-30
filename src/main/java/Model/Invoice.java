package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Invoice {

    private  int invoiceNo ;
    private Date invoiceDate;
   private String name;

   private ArrayList<Service> lines;

   public double getTotal(){

       double total =0;
       for(int i =0 ; i < lines.size(); i++){
           Service line = lines.get(i);
           total += line.serviceTotal();
       }
       return  total;
   }
public  Invoice (){

}
    public Invoice(int invoiceNo ,  Date invoiceDate, String name)
    {
        this.invoiceDate = invoiceDate;
        this.name = name;
        this.invoiceNo = invoiceNo;

    }
    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public  Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Service> getLines(){
        return  lines;
    }
    public  void setLines(ArrayList<Service> item){
       this.lines = item;
    }
public String toString(){
       return "Invoices[" + "num=" + invoiceNo + ", date";
}

    public String getDataAsCSV() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return "" + getInvoiceNo() + "," + df.format(getInvoiceDate()) + "," + getName();
    }

}
