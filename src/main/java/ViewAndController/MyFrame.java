package ViewAndController;
import FileOperations.LoadFile;
import FileOperations.ReadWriteFiles;
import FileOperations.SaveFile;
import Model.Invoice;
import Model.Service;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class MyFrame extends JFrame implements ActionListener{
    public LeftPanel leftPanel = new LeftPanel();
    private RightPanel rightPanel = new RightPanel();

    LoadFile loadCSV = new LoadFile(this);
    SaveFile saveCSV = new SaveFile(this);
    Dialogs dialogObject = new Dialogs(this,"Invoice");

    secDialog itemDialogObject = new secDialog(this,"Item");

    private ReadWriteFiles rw = new ReadWriteFiles();
JMenuBar  menuBar;
    JMenuItem loadFileItem;
    JMenuItem saveFileItem;
    public MyFrame() {

        setSize(1000, 1000);
        setLayout(new GridLayout(1, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         menuBar= new JMenuBar();

        JMenu fileMenu= new JMenu("File");
        loadFileItem=new JMenuItem("Load");
        fileMenu.add(loadFileItem);
        loadFileItem.addActionListener(this);
        saveFileItem= new JMenuItem("save");;
        fileMenu.add(saveFileItem);
        saveFileItem.addActionListener(this);
        this.menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        add(leftPanel.mainLeftPanel);
        add(rightPanel.mainRightPanel);


        try {

            rw.readInvoiceCSV();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        leftPanel.addRows(rw.invoices);

        dialogObject.getButtonOK().addActionListener(this);
        itemDialogObject.getButtonOK().addActionListener(this);
        leftPanel.getCreateNewInvoiceButton().addActionListener(this);
        leftPanel.getDeleteInvoiceButton().addActionListener(this);
        rightPanel.getSaveButton().addActionListener(this);
        rightPanel.getDeleteButton().addActionListener(this);
        //rightPanel.getItemeTableModel().fireTableDataChanged();
        leftPanel.getInvoiceTable().getSelectionModel().addListSelectionListener(leftPanel);

        //leftPanel.getInvoiceTableModel().fireTableDataChanged();
    }


    public void showDialog()
    {
        dialogObject.setSize(500,500);
        dialogObject.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(dialogObject.getButtonOK())) {
            try {
                Invoice invoiceObject = new Invoice();
                invoiceObject = dialogObject.onOK();
                leftPanel.addOneRow(invoiceObject);

            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource().equals(leftPanel.getCreateNewInvoiceButton())) {
            dialogObject.setSize(500, 500);
            dialogObject.setVisible(true);
        } else if (e.getSource().equals(leftPanel.getDeleteInvoiceButton())) {
            int selRow = leftPanel.getInvoiceTable().getSelectedRow();

            if (leftPanel.getInvoiceTable().getSelectedRow() != -1) {

                DefaultTableModel model = (DefaultTableModel) leftPanel.getInvoiceTable().getModel();
                model.removeRow(leftPanel.getInvoiceTable().getSelectedRow());
                rw.invoices.remove(selRow);
                rightPanel.getItemeTableModel().setRowCount(0);
                rightPanel.getInvoiceNo().setText("");
                rightPanel.getCustomerName().setText("");
                rightPanel.getInvoiceDate().setText("");
                rightPanel.getInvoiceTotal().setText("");

            }

        }
        else if (e.getSource().equals(rightPanel.getSaveButton()))
        {
            itemDialogObject.setSize(500, 500);
            itemDialogObject.setVisible(true);
        }
        else if (e.getSource().equals(rightPanel.getDeleteButton()))
        {
            int selRow = rightPanel.getItemTable().getSelectedRow();
            if (rightPanel.getItemTable().getSelectedRow() != -1) {
                DefaultTableModel model = (DefaultTableModel) rightPanel.getItemTable().getModel();
                model.removeRow(rightPanel.getItemTable().getSelectedRow());
                rw.items.remove(selRow);
            }
        } else if (e.getSource().equals(loadFileItem)) {
            //System.out.println("Fatma");
            loadCSV.loadFileMenuBar();

        }
        else if (e.getSource().equals(itemDialogObject.getButtonOK())) {
              //System.out.println("hii");
            int invoiceNumber=0;
            DefaultTableModel model = (DefaultTableModel) leftPanel.getInvoiceTable().getModel();;
            int index = leftPanel.getInvoiceTable().getSelectedRow();
            if (index != -1) {

                invoiceNumber= Integer.parseInt(model.getValueAt(index, 0).toString());
            }
            Service itemObject = new Service();
            itemObject = itemDialogObject.onOK();
            itemObject.setInvNum(invoiceNumber);

            rw.items.add(itemObject);
            System.out.println("Frame size" + rw.items.size());

            for(int i = 0 ;i< rw.invoices.size();i++)
            {
                if(rw.invoices.get(i).getInvoiceNo()==invoiceNumber)
                {
                    rw.invoices.get(i).getLines().add(itemObject);

                }

            }
            //System.out.println(rw.invoices.get(index).getTotal());
            model.setValueAt(rw.invoices.get(index).getTotal(), index, 3);




            rightPanel.addNewItem(itemObject);

        }
        else if (e.getSource().equals(saveFileItem)) {
            //System.out.println("Fatma");
            saveCSV.saveFileMenuBar();

        }

    }
}