package se.quedro.challenge.service;

import org.springframework.stereotype.Component;

@Component
public class SaleObjectConsumerImpl implements SaleObjectConsumer {

    private SaleObjectConsumerImpl saleObjectConsumer;

    public SaleObjectConsumerImpl() {
    }

    public SaleObjectConsumerImpl(SaleObjectConsumerImpl saleObjectConsumer) {
        this.saleObjectConsumer = saleObjectConsumer;
    }

    @Override
    public PriorityOrderAttribute getPriorityOrderAttribute() {
        return PriorityOrderAttribute.PricePerSquareMeter;
    }

    @Override
    public void startSaleObjectTransaction() {

    }

    @Override
    public void reportSaleObject(int squareMeters, String pricePerSquareMeter, String city, String street, Integer floor) throws TechnicalException {
        System.out.println("city > " + city + " squareMeters " + squareMeters + " pricePerSquareMeter " + pricePerSquareMeter);
    }

    @Override
    public void commitSaleObjectTransaction() {

    }
}
