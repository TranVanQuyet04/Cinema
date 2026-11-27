# CinePremier | Cinema Booking Platform

CinePremier is a cinema management and online ticket booking project. Customers can explore movies and showtimes, choose seats and book tickets, while cinema staff, managers and administrators use dedicated interfaces to support daily operations.

**[Live demo](https://cinema-six-brown.vercel.app/)** · **[Backend setup](backend/README.md)**

> The frontend is hosted on Vercel. The backend currently runs on a local machine through a temporary tunnel, so the complete demo is available only while that machine, the backend services and the tunnel are running. External integrations also require their own configuration.

## What is included?

### For customers

- Browse movies, movie details, cinemas and showtimes.
- Select a showtime, ticket quantities and seats; a temporary hold protects selected seats during booking.
- Add food and drinks to an order and proceed to payment.
- Create an account, sign in with email or Google, and manage profile information.
- View tickets, orders and a movie watchlist.

### For cinema operations

- **Staff:** check in tickets and food orders.
- **Cinema managers:** manage screening rooms, showtimes and ticket prices for their cinema.
- **Administrators:** manage users, movies, cinemas, rooms, showtimes and promotional banners; view reports and transactions.

### Additional services

- Payment, wallet and loyalty services support the booking experience.
- A Python recommendation service provides movie recommendations.
- Email verification supports account-related flows.

These features are represented in the project; individual flows depend on service availability, data and third-party settings. VNPay is configured for sandbox testing, not real payments.

## Technology stack

| Area | Technologies |
| --- | --- |
| Frontend | React, JavaScript, Vite, Tailwind CSS, Zustand |
| Backend | Java, Spring Boot, Spring Cloud Gateway |
| Recommendations | Python, FastAPI |
| Data and messaging | PostgreSQL, RabbitMQ |
| Local infrastructure | Docker Compose |
| Frontend hosting | Vercel |

## Architecture

```text
                    CinePremier frontend
                     React + Vite
                           |
                      API Gateway
                           |
       +-----------+-------+-------+-----------+
       |           |               |           |
    Identity    Catalog          Booking     Payment
       |           |               |           |
       +-----------+---------------+-----------+
               PostgreSQL and RabbitMQ

       Python / FastAPI recommendation service
```

The gateway provides an entry point to the Java services. The services use PostgreSQL for data and RabbitMQ for asynchronous communication. Recommendation functionality is provided by a separate Python service.

## Project structure

```text
Cinema/
├── frontend/                 React application
├── backend/
│   ├── cinema-services/      Gateway, Java services and AI service
│   └── docker-compose.yml    Local service infrastructure
└── README.md
```

## Run locally

You will need Git, Node.js and Docker Desktop. External integrations such as Google sign-in, email and VNPay need valid configuration of their own.

1. Clone the repository and start the backend:

   ```bash
   git clone https://github.com/TranVanQuyet04/Cinema.git
   cd Cinema/backend
   docker compose up --build -d
   ```

   See the [backend README](backend/README.md) for configuration, prerequisites and health checks before starting the services.

2. In another terminal, start the frontend:

   ```bash
   cd Cinema/frontend
   npm ci
   npm run dev
   ```

   Open `http://localhost:3000`. The frontend defaults to the local gateway at `http://localhost:8080`; configure `VITE_API_BASE_URL` if your gateway uses another address.

Keep credentials in local environment files or deployment secrets. Variables prefixed with `VITE_` are exposed to the browser and must not contain secrets.

## Status and attribution

This repository is a portfolio snapshot of a team project and remains under development. Demo availability depends on local infrastructure and external providers; not every integration has been verified in a public deployment. Individual contributions are not yet documented.
