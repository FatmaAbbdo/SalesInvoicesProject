package Model;

public class Service {
    private String serviceName;
    private double price;
    private int invNum;
    private int serviceCount;
    private Invoice inv;

    public Service(String serviceName, double price, int serviceCount, int invNum){
        this.serviceName = serviceName;
        this.serviceCount = serviceCount;
        this.price = price;
        this.invNum = invNum;
        //this.inv = inv;
    }

    public Service() {

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }
    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

public double serviceTotal(){

        return price*serviceCount;
}
    public String getDataAsCSV()
    {
        return "" + invNum + "," + getServiceName() + "," + getPrice() + "," + getServiceCount();
    }




}
