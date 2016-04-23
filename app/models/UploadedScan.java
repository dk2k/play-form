package models;

import javax.persistence.*;
import play.data.validation.Constraints;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import java.io.FileOutputStream;

@Entity
@Table(name = "UPLOADED_SCAN") 
public class UploadedScan implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*public UploadedScan(){
     this.paymentFunction = PaymentFunction.Charging;
     }
     
     public UploadedScan(PaymentFunction paymentFunction){
     this.paymentFunction = paymentFunction;
     }*/
    
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    // integer, measured in kopeykas
    @Column(name = "MONEY_AMOUNT")
    @Constraints.Required
    Long moneyAmount;
    
    @Column(name = "COMMENT")
    String comment;
    
    @Column(name = "CONTENT_TYPE")
    String contentType;
    
    @Lob
    @Column(name = "SCAN_BYTES")
    private byte[] scanFile;
    
    @Column(name = "SCAN_FILENAME")
    @NotNull
    String scanFileName = "TBD";
    
    @Constraints.Required
    @NotNull
    @Column(name = "PAYMENT_FUNCTION")
    @Enumerated(EnumType.STRING)
    PaymentFunction paymentFunction;
    
    public void setScanFile(byte[] scanFile) {
        this.scanFile = scanFile;
    }
    
    public byte[] getScanFile() {
        return scanFile;
    }
    
    public void setScanFileName(String scanFileName) {
        this.scanFileName = scanFileName;
    }
    
    public String getScanFileName() {
        return scanFileName;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
    
    public Long getMoneyAmount() {
        return moneyAmount;
    }
    
    public void setPaymentFunction(PaymentFunction paymentFunction) {
        this.paymentFunction = paymentFunction;
    }
    
    public PaymentFunction getPaymentFunction() {
        return paymentFunction;
    }
    
    @Override
    public String toString(){
        String val = moneyAmount + " " 
            + comment + " " 
            + paymentFunction + " " 
            + scanFileName + " ";
        
        if (scanFile!=null) {
            val += scanFile.length;
        } else {
            val +="NULL";
        }
        return val;
    }
}
