package ViewAndController;
import FileOperations.ReadWriteFiles;
import Model.Invoice;
import Model.Service;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LeftPanel implements ListSelectionListener {

    public JPanel mainLeftPanel= new JPanel();
    public static final String[] invoiceTablecolumns = {
            "No.", "Date", "Customer","Total"
    };
    private JButton createNewInvoiceButton;
    private JButton deleteInvoiceButton;
    private static DefaultTableModel invoiceTableModel;
    private static JTable invoiceTable;
    private ReadWriteFiles rw = new ReadWriteFiles();
    private RightPanel rightPanel = new RightPanel();

    public LeftPanel()
    {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(new JLabel("invoice Table"),BorderLayout.NORTH);

        invoiceTableModel = new DefaultTableModel(invoiceTablecolumns, 0);
        invoiceTable = new JTable(invoiceTableModel);

        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel buttonPanel = new JPanel();
        createNewInvoiceButton = new JButton("Create New Invoice");
        buttonPanel.add(createNewInvoiceButton);
        //createNewInvoiceButton.addActionListener(this);
        deleteInvoiceButton = new JButton("Delete Invoice");
        buttonPanel.add(deleteInvoiceButton);
        //deleteInvoiceButton.addActionListener(this);

        mainLeftPanel.add(labelPanel, BorderLayout.PAGE_START);
        mainLeftPanel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);
        mainLeftPanel.add(buttonPanel,BorderLayout.AFTER_LAST_LINE);
    }

    public void addOneRow (Invoice invObject)
    {
        rw.invoices.add(invObject);
        ArrayList<Service>  itemList = new ArrayList<>();
        invObject.setLines(itemList);
        addRows(rw.invoices);
    }
    public void addRows(ArrayList<Invoice> invList)
    {
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        model.setRowCount(0);
        Object rowData [] = new Object[4];
        for (int i =0; i < invList.size(); i++){
            rowData[0] = invList.get(i).getInvoiceNo();
            rowData[1] = invList.get(i).getInvoiceDate();
            rowData[2] = invList.get(i).getName();
           rowData[3] = invList.get(i).getTotal();

            model.addRow(rowData);
        }
        //invoiceTableModel.fireTableDataChanged();

    }
    public void setCreateNewInvoiceButton(JButton createNewInvoiceButton) {
        this.createNewInvoiceButton = createNewInvoiceButton;
    }

    public void setDeleteInvoiceButton(JButton deleteInvoiceButton) {
        this.deleteInvoiceButton = deleteInvoiceButton;
    }

    public void setInvoiceTableModel(DefaultTableModel invoiceTableModel) {
        this.invoiceTableModel = invoiceTableModel;
    }

    public void setInvoiceTable(JTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public JButton getCreateNewInvoiceButton() {
        return createNewInvoiceButton;
    }

    public JButton getDeleteInvoiceButton() {
        return deleteInvoiceButton;
    }

    public DefaultTableModel getInvoiceTableModel() {
        return invoiceTableModel;
    }

    public JTable getInvoiceTable() {
        return invoiceTable;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (e.getValueIsAdjusting() == true) {
            int invoiceNum = 0;
            if (invoiceTable.getSelectedRow() != -1) {
                int index = invoiceTable.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
                invoiceNum = Integer.parseInt(model.getValueAt(index, 0).toString());
                rightPanel.getInvoiceDate().setText(model.getValueAt(index,1).toString());
                rightPanel.getCustomerName().setText(model.getValueAt(index,2).toString());
                //System.out.println(rightPanel.getCustomerName().getText());
                //System.out.println(model.getValueAt(index,3).toString() + "value Donia");
               rightPanel.getInvoiceTotal().setText(model.getValueAt(index,3).toString());

            }
            try {
                rightPanel.showItems(invoiceNum);
                //rightPanel.getItemeTableModel().fireTableDataChanged();

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}