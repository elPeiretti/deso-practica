"use client";

import { startTransition, useActionState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
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
import Link from "next/link";
import { Table, TableBody, TableCell, TableRow } from "@/components/ui/table";
import { formatDate, formatPrice } from "@/lib/format";
import { createReservationAction } from "./actions";

// Con cacheComponents activado, Next.js no desmonta la página al navegar a otra: la oculta
// y conserva su estado. Sin hacer nada, al volver se
// vería la reserva anterior y el valor del param "flight" no se aplicaría.
// Para empezar de cero, se usa bfcacheId como key: cambia cada vez que se llega a la página
// con un link (o router.push), y con otra key React crea el formulario de nuevo.
// Con los botones Atrás/Adelante del navegador no cambia, así que ahí se recupera lo cargado.
export function ReservationForm({ users, flights }) {
  const { bfcacheId } = useRouter();
  // Vuelo preseleccionado si se llega desde el botón "Reservar" de /flights
  const defaultFlight = useSearchParams().get("flight");

  return (
    <ReservationFormContent
      key={bfcacheId}
      users={users}
      flights={flights}
      defaultFlight={defaultFlight}
    />
  );
}

function ReservationFormContent({ users, flights, defaultFlight }) {
  const minDate = today(getLocalTimeZone());

  // useActionState guarda lo que devuelve la Server Action:
  // { reservation } si salió bien, { message } si la API devolvió un error,
  // o { errors } si falló la validación del servidor. pending es true mientras se envía.
  const [state, submitAction, pending] = useActionState(createReservationAction, {});

  const onSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);

    // La API espera un Instant: se convierte la fecha elegida (aaaa-mm-dd) a la medianoche
    // de la zona horaria del usuario, que solo se conoce acá en el navegador
    formData.set(
      "date",
      parseDate(String(formData.get("date"))).toDate(getLocalTimeZone()).toISOString(),
    );

    // Se llama a la acción a mano (en vez de <Form action={submitAction}>) porque con action
    // React vacía el formulario al terminar, y si hubo un error se perderían los datos cargados
    startTransition(() => submitAction(formData));
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
            <Form
              className="flex flex-col gap-4"
              onSubmit={onSubmit}
              // Errores de validación devueltos por el servidor: se muestran en cada campo
              validationErrors={state.errors}
            >
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

              <DatePicker isRequired name="date" minValue={minDate}>
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
                {/* El mensaje depende de qué validación falló */}
                <FieldError>
                  {({ validationDetails, validationErrors }) =>
                    validationDetails.valueMissing
                      ? "Elegí una fecha."
                      : validationDetails.rangeUnderflow
                        ? "La fecha tiene que ser hoy o posterior."
                        : validationErrors.join(" ")
                  }
                </FieldError>
                <DatePicker.Popover>
                  {/* El Calendar de HeroUI no toma el minValue del DatePicker: hay que pasarlo
                      también acá para que los días anteriores aparezcan deshabilitados */}
                  <Calendar aria-label="Fecha de la reserva" minValue={minDate}>
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

              <Button type="submit" variant="primary" className="mt-2" isPending={pending}>
                {pending ? "Reservando..." : "Reservar"}
              </Button>
            </Form>
          </Card.Content>
        </Card>

        <ReservationResult state={state} />
      </div>
    </I18nProvider>
  );
}

// Panel de la derecha: muestra la reserva que devolvió la API o el error
function ReservationResult({ state }) {
  const { reservation } = state;
  if (reservation) {
    const rows = [
      ["Número", <span className="font-mono">{reservation.reservationId}</span>],
      ["Usuario", reservation.ownerUsername],
      ["Servicio", reservation.serviceName],
      ["Fecha", formatDate(reservation.date)],
      ["Precio", formatPrice(reservation.price)],
    ];
    return (
      <Alert status="success" className="h-fit">
        <Alert.Indicator />
        <Alert.Content>
          <Alert.Title>Reserva creada</Alert.Title>
          <Alert.Description>
            <Table className="mt-2">
              <TableBody>
                {rows.map(([label, value]) => (
                  <TableRow key={label} className="hover:bg-transparent">
                    <TableCell className="font-medium">{label}</TableCell>
                    <TableCell>{value}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
            <Link
              href={`/reservations?username=${encodeURIComponent(reservation.ownerUsername)}`}
              className="mt-3 inline-block font-medium underline"
            >
              Ver las reservas de {reservation.ownerUsername}
            </Link>
          </Alert.Description>
        </Alert.Content>
      </Alert>
    );
  }

  if (state.message) {
    return (
      <Alert status="danger" className="h-fit">
        <Alert.Indicator />
        <Alert.Content>
          <Alert.Title>No se pudo crear la reserva</Alert.Title>
          <Alert.Description>{state.message}</Alert.Description>
        </Alert.Content>
      </Alert>
    );
  }

  return (
    <Alert className="h-fit">
      <Alert.Indicator />
      <Alert.Content>
        <Alert.Title>Sin enviar</Alert.Title>
        <Alert.Description>Completá el formulario para crear la reserva.</Alert.Description>
      </Alert.Content>
    </Alert>
  );
}
