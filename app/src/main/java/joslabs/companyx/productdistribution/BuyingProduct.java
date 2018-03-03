package joslabs.companyx.productdistribution;

/**
 * Created by OSCAR on 8/24/2017.
 */

public class BuyingProduct {
    String clientkey,ptype,pnumber,amount,discout,paymentmode,paid,time,receiptNo;

    public BuyingProduct() {
    }

    public BuyingProduct(String clientkey, String ptype, String pnumber, String amount, String discout, String paymentmode, String paid, String time, String receiptNo) {
        this.clientkey = clientkey;
        this.ptype = ptype;
        this.pnumber = pnumber;
        this.amount = amount;
        this.discout = discout;
        this.paymentmode = paymentmode;
        this.paid = paid;
        this.time = time;
        this.receiptNo = receiptNo;
    }

    public String getClientkey() {
        return clientkey;
    }

    public void setClientkey(String clientkey) {
        this.clientkey = clientkey;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscout() {
        return discout;
    }

    public void setDiscout(String discout) {
        this.discout = discout;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }
}
