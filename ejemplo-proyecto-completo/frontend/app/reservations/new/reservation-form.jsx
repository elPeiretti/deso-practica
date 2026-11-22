"use client";

import { startTransition, useActionState, useLayoutEffect, useState } from "react";
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
import Link from "next/link";
import { formatDate, formatPrice } from "@/lib/format";
import { createReservationAction } from "./actions";

// Con cacheComponents, Next.js no desmonta la página al navegar a otra: la oculta con <Activity>
// y conserva su estado (los campos, el resultado de la reserva). Al volver se vería la reserva
// anterior, y el vuelo de ?flight= no se aplicaría porque el Select ya estaba creado.
// Para empezar de cero cada vez, se cambia la key del formulario cuando la página se oculta:
// con otra key, React descarta el componente y crea uno nuevo, con su estado inicial.
export function ReservationForm({ users, flights }) {
  // Vuelo preseleccionado si se llega desde el botón "Reservar" de /flights
  const defaultFlight = useSearchParams().get("flight");
  const [resetCount, setResetCount] = useState(0);

  // El cleanup corre cuando Activity oculta la página (al navegar a otra)
  useLayoutEffect(() => {
    return () => setResetCount((count) => count + 1);
  }, []);

  // defaultFlight también va en la key: si cambia el ?flight= estando en la página
  // (por ejemplo, con el link "Nueva reserva" de la barra), el formulario se reinicia
  return (
    <ReservationFormContent
      key={`${resetCount}-${defaultFlight}`}
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
    return (
      <Alert status="success" className="h-fit">
        <Alert.Indicator />
        <Alert.Content>
          <Alert.Title>Reserva creada</Alert.Title>
          <Alert.Description>
            <dl className="mt-2 grid grid-cols-[auto_1fr] gap-x-4 gap-y-1">
              <dt>Número</dt>
              <dd className="font-mono">{reservation.reservationId}</dd>
              <dt>Usuario</dt>
              <dd>{reservation.ownerUsername}</dd>
              <dt>Servicio</dt>
              <dd>{reservation.serviceName}</dd>
              <dt>Fecha</dt>
              <dd>{formatDate(reservation.date)}</dd>
              <dt>Precio</dt>
              <dd>{formatPrice(reservation.price)}</dd>
            </dl>
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
