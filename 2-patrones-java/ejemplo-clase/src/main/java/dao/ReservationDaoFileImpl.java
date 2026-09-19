package dao;

import domain.Reservation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReservationDaoFileImpl implements ReservationDao {
  private static ReservationDaoFileImpl instance;
  private final File file = new File("reservations.csv");

  private ReservationDaoFileImpl() {
  }

  public static synchronized ReservationDaoFileImpl getInstance() {
    if (instance == null) {
      instance = new ReservationDaoFileImpl();
    }

    return instance;
  }

  @Override
  public void save(Reservation reservation) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
      String line = String.format(
          "%s,%s,%s,%s\n",
          reservation.getId(),
          reservation.getService().getId(),
          reservation.getOwner().getId(),
          reservation.getDate());
      bw.write(line);
    } catch (IOException e) {
      throw new RuntimeException("Error al guardar la reserva en el archivo", e);
    }
  }
}
