package FileOperations;
import Model.Invoice;
import Model.Service;
import ViewAndController.MyFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
public class LoadFile {
    private MyFrame frame;
    private ReadWriteFiles rw = new ReadWriteFiles();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public LoadFile(MyFrame frame) {
        this.frame = frame;
    }

    public void loadFileMenuBar() {
        rw.invoices.clear();
        rw.items.clear();
        JOptionPane.showMessageDialog(this.frame, "select Invoices  file!", "InvoiceHeader File", 1);
        JFileChooser openFileChooser = new JFileChooser();
        int result = openFileChooser.showOpenDialog(this.frame);
        LoadItemsCSV();


        if (result == 0) {
            File InvoicesheaderFile = openFileChooser.getSelectedFile();

            try {
                BufferedReader br = new BufferedReader(new FileReader(InvoicesheaderFile));
                String headerLines = "";
                String lineLines;
                while ((headerLines = br.readLine()) != null) {
                    String[] splitHeaderLines = headerLines.split(",");
                    String invoiceNumberString = splitHeaderLines[0];
                    lineLines = splitHeaderLines[1];
                    String invoiceCustomerName = splitHeaderLines[2];
                    int invoiceNumberInt = Integer.parseInt(invoiceNumberString);
                    Date invoiceDate = dateFormat.parse(lineLines);
                    Invoice invoice = new Invoice(invoiceNumberInt, invoiceDate, invoiceCustomerName);
                    ArrayList<Service> tempItemList = new ArrayList<>();

                    for(int i=0;i<rw.items.size();i++)
                    {
                        if(rw.items.get(i).getInvNum() == invoiceNumberInt)
                        {
                            tempItemList.add(rw.items.get(i));
                        }
                    }
                    invoice.setLines(tempItemList);

                    rw.invoices.add(invoice);

                  //  rw.invoices.add(invoice);
                }

                        DefaultTableModel model = (DefaultTableModel) this.frame.leftPanel.getInvoiceTable().getModel();
                        //this.frame.leftPanel.setInvoiceTableModel(new DefaultTableModel(this.frame.leftPanel.invoiceTablecolumns, 0));
                        model.setRowCount(0);
                        for (int i = 0; i < rw.invoices.size(); i++) {

                            Object rowData[] = new Object[4];
                            rowData[0] = rw.invoices.get(i).getInvoiceNo();
                            rowData[1] = rw.invoices.get(i).getInvoiceDate();
                            rowData[2] = rw.invoices.get(i).getName();
                            rowData[3] = rw.invoices.get(i).getTotal();
                            model.addRow(rowData);
                        }
                        this.frame.leftPanel.getInvoiceTable().validate();
                    } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
            }

    public void LoadItemsCSV(){
            JFileChooser openFileChooser = new JFileChooser();

        JOptionPane.showMessageDialog(this.frame,"Please, select Services File!","items File",1);
        int result = openFileChooser.showOpenDialog(this.frame);
        if(result==0){
        File lineFile=openFileChooser.getSelectedFile();
            BufferedReader linebr= null;
            try {
                linebr = new BufferedReader(new FileReader(lineFile));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String lineLines="";

        while(true){
            try {
                if (!((lineLines=linebr.readLine())!=null)) break;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[]splitLineLinesParts=lineLines.split(",");
        String ItemNumberString=splitLineLinesParts[0];
        String itemNameString=splitLineLinesParts[1];
        String itemPriceString=splitLineLinesParts[2];
        String itemCountString=splitLineLinesParts[3];
        int itemNumberInt=Integer.parseInt(ItemNumberString);
        int itemCountInt=Integer.parseInt(itemCountString);
        double itemPriceDouble=Double.parseDouble(itemPriceString);
        // Invoice header = this.findInvoiceHeaderByNumber(itemNumberInt);
        Service invoiceLine=new Service(itemNameString,itemPriceDouble,itemCountInt,itemNumberInt);
        rw.items.add(invoiceLine);
        }
        }
        }
}
