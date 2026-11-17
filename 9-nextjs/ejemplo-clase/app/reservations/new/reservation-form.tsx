"use client";

import { useState, type FormEvent } from "react";
import { useSearchParams } from "next/navigation";
import {
  Alert,
  Button,
  Calendar,
  Card,
  DateField,
  DatePicker,
  Description,
  FieldError,
  Form,
  I18nProvider,
  Label,
  ListBox,
  Select,
} from "@heroui/react";
import { getLocalTimeZone, parseDate, today } from "@internationalized/date";
import { formatPrice, type CreateReservationRequest, type Flight, type User } from "@/lib/data";

type ReservationFormProps = {
  users: User[];
  flights: Flight[];
};

export function ReservationForm({ users, flights }: ReservationFormProps) {
  // Vuelo preseleccionado si se llega desde el botón "Reservar" de /flights
  const defaultFlight = useSearchParams().get("flight");
  const [request, setRequest] = useState<CreateReservationRequest | null>(null);

  const onSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);

    // Arma el body de POST /api/reservations
    setRequest({
      username: String(formData.get("username")),
      flightNumber: String(formData.get("flightNumber")),
      date: parseDate(String(formData.get("date")))
        .toDate(getLocalTimeZone())
        .toISOString(),
    });
  };

  return (
    // Formato de fechas en español (dd/mm/aaaa) sin importar el idioma del navegador
    <I18nProvider locale="es-AR">
      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <Card.Header>
            <Card.Title>Datos de la reserva</Card.Title>
            <Card.Description>Todos los campos son obligatorios.</Card.Description>
          </Card.Header>
          <Card.Content>
            <Form className="flex flex-col gap-4" onSubmit={onSubmit}>
              <Select isRequired name="username" placeholder="Elegí un usuario">
                <Label>Usuario</Label>
                <Select.Trigger>
                  <Select.Value />
                  <Select.Indicator />
                </Select.Trigger>
                <Select.Popover>
                  <ListBox>
                    {users.map((user) => (
                      <ListBox.Item key={user.id} id={user.username} textValue={user.username}>
                        {user.username}
                        <ListBox.ItemIndicator />
                      </ListBox.Item>
                    ))}
                  </ListBox>
                </Select.Popover>
                <FieldError>Elegí un usuario.</FieldError>
              </Select>

              <Select
                isRequired
                name="flightNumber"
                placeholder="Elegí un vuelo"
                defaultValue={defaultFlight}
              >
                <Label>Vuelo</Label>
                <Select.Trigger>
                  <Select.Value />
                  <Select.Indicator />
                </Select.Trigger>
                <Select.Popover>
                  <ListBox>
                    {flights.map((flight) => (
                      <ListBox.Item
                        key={flight.number}
                        id={flight.number}
                        textValue={flight.number}
                      >
                        <div className="flex flex-col">
                          <Label>{flight.number}</Label>
                          <Description>
                            {flight.company} · {formatPrice(flight.price)}
                          </Description>
                        </div>
                        <ListBox.ItemIndicator />
                      </ListBox.Item>
                    ))}
                  </ListBox>
                </Select.Popover>
                <FieldError>Elegí un vuelo.</FieldError>
              </Select>

              <DatePicker isRequired name="date" minValue={today(getLocalTimeZone())}>
                <Label>Fecha</Label>
                <DateField.Group fullWidth>
                  <DateField.Input>
                    {(segment) => <DateField.Segment segment={segment} />}
                  </DateField.Input>
                  <DateField.Suffix>
                    <DatePicker.Trigger>
                      <DatePicker.TriggerIndicator />
                    </DatePicker.Trigger>
                  </DateField.Suffix>
                </DateField.Group>
                <FieldError>La fecha tiene que ser hoy o posterior.</FieldError>
                <DatePicker.Popover>
                  <Calendar aria-label="Fecha de la reserva">
                    <Calendar.Header>
                      <Calendar.YearPickerTrigger>
                        <Calendar.YearPickerTriggerHeading />
                        <Calendar.YearPickerTriggerIndicator />
                      </Calendar.YearPickerTrigger>
                      <Calendar.NavButton slot="previous" />
                      <Calendar.NavButton slot="next" />
                    </Calendar.Header>
                    <Calendar.Grid>
                      <Calendar.GridHeader>
                        {(day) => <Calendar.HeaderCell>{day}</Calendar.HeaderCell>}
                      </Calendar.GridHeader>
                      <Calendar.GridBody>
                        {(date) => <Calendar.Cell date={date} />}
                      </Calendar.GridBody>
                    </Calendar.Grid>
                    <Calendar.YearPickerGrid>
                      <Calendar.YearPickerGridBody>
                        {({ year }) => <Calendar.YearPickerCell year={year} />}
                      </Calendar.YearPickerGridBody>
                    </Calendar.YearPickerGrid>
                  </Calendar>
                </DatePicker.Popover>
              </DatePicker>

              <Button type="submit" variant="primary" className="mt-2">
                Reservar
              </Button>
            </Form>
          </Card.Content>
        </Card>

        {request ? (
          <Alert status="success" className="h-fit">
            <Alert.Indicator />
            <Alert.Content>
              <Alert.Title>Reserva lista para enviar</Alert.Title>
              <Alert.Description>
                Este es el body que se enviaría a <code>POST /api/reservations</code>:
                <pre className="mt-2 overflow-x-auto rounded-lg bg-white/70 p-3 text-xs text-foreground">
                  {JSON.stringify(request, null, 2)}
                </pre>
              </Alert.Description>
            </Alert.Content>
          </Alert>
        ) : (
          <Alert className="h-fit">
            <Alert.Indicator />
            <Alert.Content>
              <Alert.Title>Sin enviar</Alert.Title>
              <Alert.Description>
                Completá el formulario para ver el request que se enviaría a la API.
              </Alert.Description>
            </Alert.Content>
          </Alert>
        )}
      </div>
    </I18nProvider>
  );
}
