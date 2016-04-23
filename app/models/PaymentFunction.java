package models;

public enum PaymentFunction {
    Charging("Пополнение баланса"), RatePayment("Оплата тарифа");
    
    private String value;
    
    private PaymentFunction(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    } 
};
