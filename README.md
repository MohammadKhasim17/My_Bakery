# MyBakery

MyBakery is a Spring Boot web application for managing bakery products, with both an admin panel and a public storefront.

## Features

- Public homepage with product listings grouped by category
- Admin product management with create, update, and delete operations
- Image upload support for bakery products
- REST API endpoints for product CRUD operations
- MySQL persistence with Spring Data JPA
- Thymeleaf templates for server-side rendered pages

## Project Structure

- `src/main/java/com/mybakery`
  - `controller` — web controllers for admin pages, public storefront, and REST API
  - `model` — JPA `Product` entity
  - `repository` — Spring Data JPA repository
  - `service` — business logic and product management
  - `config` — web configuration and resource handlers
  - `exception` — error handling for API and web pages
- `src/main/resources/templates` — Thymeleaf templates for admin and public pages
- `src/main/resources/static/css` — stylesheet assets
- `src/main/resources/static/images` — static image assets
- `uploads/` — runtime directory for uploaded product images

## Prerequisites

- Java 17 or later
- Maven
- MySQL database

## Database Configuration

Update the database connection values in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mybakery?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

The application uses `spring.jpa.hibernate.ddl-auto=update` for development schema updates.

## Run the Application

From the project root:

```powershell
./mvnw spring-boot:run
```

Then open:

- Public site: `http://localhost:8080/`
- Admin panel: `http://localhost:8080/admin/products`

## Admin Product Image Upload

- Use the admin form to add or update products
- Upload product images through the `Product Image` field
- Uploaded files are stored in the runtime `uploads/` directory
- Images are served from `/product-images/{filename}`

## REST API Endpoints

- `GET /api/products` — list all products
- `GET /api/products/{id}` — get a product by ID
- `POST /api/products` — create a product
- `PUT /api/products/{id}` — update a product
- `DELETE /api/products/{id}` — delete a product

## Notes

- `src/main/resources/templates/public/index.html` contains the homepage layout and product card rendering.
- Product prices are shown with the `₹` symbol.
- If you need to upload the project to GitHub manually, initialize a repository locally and push to a remote using your GitHub repository URL.

## Troubleshooting

- If images do not appear after upload, check that the `uploads/` directory exists and that the application has permission to write to it.
- If the app cannot connect to MySQL, verify the connection URL, username, and password in `application.properties`.
