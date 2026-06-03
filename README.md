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

## Render Deployment

This project includes Render deployment support using `Dockerfile` and `render.yaml`.

1. Push this repository to GitHub.
2. Create a new Render web service and connect your GitHub repo.
3. Choose "Docker" as the environment and use the `Dockerfile` in the repository root.
4. Configure the following environment variables on Render:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `SPRING_DATASOURCE_DRIVER` (for example `org.postgresql.Driver` if using Render Postgres)

Render will set `PORT` automatically, and the application will use it.

### Recommended cloud database setup

For a free Render deployment, use the Render PostgreSQL add-on or another managed database provider. Then set:

- `SPRING_DATASOURCE_URL` to the database URL provided by your service
- `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` accordingly
- `SPRING_DATASOURCE_DRIVER` to `org.postgresql.Driver`

If you prefer to use MySQL on Render, set the driver to `com.mysql.cj.jdbc.Driver` and configure a compatible MySQL database.

## Notes

- Uploaded product images are stored in the app's runtime `uploads/` folder.
- On Render, runtime storage is ephemeral, so image uploads may not persist across deploys or service restarts.
- For a production-ready deployment, consider adding cloud file storage for uploaded images.

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
