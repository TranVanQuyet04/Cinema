# CinePremier

A cinema booking application with a React frontend and a microservice backend. Visitors can explore movies and showtimes, reserve seats, and go through the booking flow. The application also includes tools for cinema managers and administrators.

**[Open the live demo](https://cinema-six-brown.vercel.app/)**

The frontend runs on Vercel. For this demo, the backend runs on a local machine and is exposed through a temporary tunnel, so API-dependent features are available only while that machine, its services and the tunnel are running. The demo may be unavailable without notice.

## Features

- Movie catalogue, cinema and showtime browsing.
- Seat selection and booking, with a time-limited seat hold.
- Customer accounts, email verification and Google sign-in.
- Manager tools for showtimes, rooms and ticket prices; administrator tools for users and catalogue management.
- Payment and wallet services, plus a movie recommendation service. Payment integration uses a sandbox for demo testing.

Availability of individual features depends on backend services and third-party configuration. This is a portfolio demo, not a production payment system.

## Architecture

```text
React + TypeScript + Vite (Vercel)
             |
         API Gateway
             |
  Identity | Catalog | Booking | Payment
             |
   PostgreSQL and RabbitMQ

  Python / FastAPI recommendation service
```

The gateway routes requests to Java services. PostgreSQL stores service data and RabbitMQ supports asynchronous communication. The recommendation service uses Python and FastAPI.

## Repository

- [`frontend/`](frontend/) — frontend application.
- [`backend/`](backend/) — API gateway, backend services and local Docker configuration.
- [Backend setup and service overview](backend/README.md) — prerequisites, configuration and local startup.

For frontend development, install the dependencies and start Vite from `frontend/`:

```bash
npm install
npm run dev
```

Configure frontend API and OAuth environment variables for your own environment. Do not commit `.env` files, passwords, API keys or payment credentials. Browser-exposed `VITE_` values are public and must not contain secrets.

## Project status and attribution

This repository is a portfolio snapshot of a team project and remains under development. The demo depends on local infrastructure and external providers; a successful page load does not guarantee all integrations are available. Individual contributions are not yet documented here.
