import domain.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainClase1 {
  void main() {
    // creo un Aircraft
    Flight.Aircraft aircraft = new Flight.Aircraft("Boeing 747", 11);
    // creo un vuelo usando getters y setters
    Flight flight = new Flight("FlyDeso");
    flight.setAircraft(aircraft);
    flight.setLength(1200);

    // creo un alojamiento
    Accommodation accommodation = new Accommodation("HospedateAr", 30, 2, 2);

    // imprimo info del vuelo
    IO.println("Avion asociado al vuelo:");
    IO.println(flight.getAircraft().name());
    IO.println("Precio del vuelo: ");
    IO.println(flight.calculatePrice());

    // Agrupacion de tipos diferentes en una misma lista
    IO.println("\nPolimorfismo - demostracion: ");
    List<TouristService> touristServices = List.of(accommodation, flight);
    touristServices.forEach(
        service -> {
          IO.println(service);
          IO.println(service.calculatePrice());
        });

    // Conectamos "cosas" diferentes que tienen cierta relacion
    Location location = new Location("id1", "ARG", "Santa Fe", "Calle 123", "UTN FRSF");
    List<Identifiable> identifiables = new ArrayList<>();
    identifiables.add(location);
    identifiables.addAll(touristServices);
    IO.println("\nLista ordenada de identifiables: ");
    identifiables.stream()
        .sorted(Comparator.comparing(Identifiable::getId))
        .forEach(i -> IO.println(i.getId()));
  }
}
