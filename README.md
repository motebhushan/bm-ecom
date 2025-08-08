# 🔙 BM E-Commerce Backend (Spring Boot)

This is the **backend service** for the BM E-Commerce project.  
It is built using **Spring Boot**, **MySQL**, and provides a secure REST API consumed by the React frontend.

---

## 🚀 Features

- 🔐 User registration & login with JWT Authentication
- 📦 Product management APIs
- 🛒 Cart and Checkout flow
- 📊 Admin APIs (In Progress)

---

## ⚙️ Tech Stack

- **Backend**: Spring Boot, Spring Security, JPA/Hibernate
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Tools**: Postman, Maven

---

## 📚 Full Project & Frontend

👉 Full project documentation and frontend repo here:  
🔗 [Frontend Repository (Main README)](https://github.com/motebhushan/BMEcomFrontend)

---

## 🛠 Getting Started (Backend Only)

### 1. Clone the Repository

```bash
git clone https://github.com/motebhushan/bm-ecom.git
cd bm-ecom
```

### 2. Configure Database

Update the database credentials in:

`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DB_NAME
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

> Replace `YOUR_DB_NAME`, `YOUR_USERNAME`, and `YOUR_PASSWORD` with your local setup.

---

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The backend will run on [http://localhost:8080](http://localhost:8080)

---

## 🧪 API Testing

Use Postman to test APIs such as:

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/products`
- `POST /api/cart`
- (Add more as needed)

🔐 Make sure to include the JWT token in headers for protected routes.

---

## 👨‍💻 Developed by

**Bhushan Mote**  
📅 *June 2025 | Full-Stack Web Application*

---

## 📌 Note

- This backend must run alongside the React frontend to experience the full application.
- For complete documentation and usage, visit the [frontend repo](https://github.com/motebhushan/BMEcomFrontend).
