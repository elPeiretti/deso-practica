package domain;

import java.time.Instant;

public class Reservation implements Identifiable{
    private Long id;
    private Instant date;
    private TouristService service;

    public double calculatePrice() {
        return service.calculatePrice();
    }

    @Override
    public String getId() {
        return id.toString();
    }
}
