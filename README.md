# E-Commerce Website

## Overview
This is a full-stack e-commerce web application built with **Spring Boot (Java)** for the backend, **Angular** for the frontend, and **PostgreSQL** for the database. The application allows users to browse products, manage a shopping cart, place orders, and process payments. Admin users can manage products, inventory, and orders.

---

## Features

### User Features
- Browse products with search and filter options.  
- Add items to shopping cart.  
- Place orders and checkout using Stripe or PayPal (test mode).  
- View order history.  

### Admin Features
- CRUD operations for products.  
- Manage inventory and stock levels.  
- View all orders and their statuses.  

### Authentication & Security
- JWT-based authentication.  
- Role-based access control (`ROLE_USER`, `ROLE_ADMIN`).  
- Secure handling of sensitive user data.  

---

## Technology Stack
- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security  
- **Frontend:** Angular, HTML, CSS, Bootstrap  
- **Database:** PostgreSQL  
- **Payment Integration:** Stripe / PayPal (test mode)  
- **Build Tools:** Maven for backend, npm for frontend
