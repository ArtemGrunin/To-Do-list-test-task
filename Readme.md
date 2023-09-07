# To-Do List Web Service

- [Description](#description)
- [Project Purpose](#project-purpose)
- [Requirements](#requirements)
- [Technologies](#technologies)
- [How to Run](#how-to-run)
- [Author](#author)
- [Українська версія](#українська-версія)

## Description

This web service is designed to manage a To-Do List. It allows users to create, retrieve, update, and delete tasks using a RESTful API. The backend is built with Java 17, Spring Boot, and integrates with an SQL database. All interactions are secured using Spring Security, with JWT tokens used for authentication.

## Requirements

The application is required to:

- Use Java 17 and adhere to Object-Oriented Programming principles.
- Implement a RESTful API for:
    - Creating new tasks.
    - Retrieving all tasks.
    - Retrieving a task by its ID.
    - Updating a task (changing status or description).
    - Deleting a task by its ID.
- Utilize Spring Framework, including Spring Core, Spring Security, and Spring Data for security and database interaction.
- Store data in an SQL database.
- Manage dependencies and build the project using Maven.
- Deploy the application in a Docker container.
- Host the code on a Git repository.

## Project Purpose
The primary objective of this application is to address the challenge of task management by providing a secure platform where users can efficiently manage their individual task lists. Notably, to ensure data privacy and security, each authorized user has exclusive access to their specific tasks. This isolation of user data is achieved through JWT-based authentication, which ensures that users can only view and manage tasks they've created themselves.

## Technologies

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Springdoc OpenAPI (Swagger)
- MySQL (Dialect 8)
- Liquibase
- Lombok
- jjwt
- Docker

## How to Run

1. ✅ Download and install Docker on your machine if you haven't already. Docker is required to create the project's environment. Check out Docker's [official website](https://www.docker.com/) for installation guides.
2. ✅ Clone the project repository to your local machine.
3. ✅ Inside the `src/main/resources` directory, copy the `application.properties.sample` file, rename the copy to `application.properties`, and fill in the necessary information (database connection parameters, bot tokens, JWT secret key, etc).
4. ✅ Create a `.env` file in the root directory of your project and populate it with necessary environment variables. These variables will be used by Docker Compose to setup the application.
5. ✅ From the root directory of the project, build your application by running the Maven package command: `mvn package`.
6. ✅ Once the application is built, use the following command to start the services using Docker Compose: `docker-compose up`.

Please ensure that all necessary configurations are correctly set up before running the application.

After successful startup, your application should be running on `localhost:6868`. Access Swagger UI by navigating to [http://localhost:6868/swagger-ui/index.html](http://localhost:6868/swagger-ui/index.html).

Remember, to stop running containers later on, you can use `docker-compose down` from the same directory where your `docker-compose.yml` file is located.

### Author

- Artem Grunin

---

# Українська версія

- [Опис](#опис)
- [Вимоги](#вимоги)
- [Технології](#технології)
- [Як запустити](#як-запустити)
- [Автор](#автор)
- [English version](#to-do-list-web-service)

## Опис

Цей веб-сервіс призначений для керування списком завдань (To-Do List). Він дозволяє користувачам створювати, отримувати, оновлювати та видаляти завдання за допомогою RESTful API. Бекенд побудований на Java 17, Spring Boot та інтегрується з базою даних SQL. Усі взаємодії захищені за допомогою Spring Security, для аутентифікації використовуються токени JWT.

## Вимоги

Додаток повинен:

- Використовувати Java 17 та дотримуватися принципів об'єктно-орієнтованого програмування.
- Реалізувати RESTful API для:
  - Створення нових завдань.
  - Отримання усіх завдань.
  - Отримання завдання за його ID.
  - Оновлення завдання (зміна статусу або опису).
  - Видалення завдання за його ID.
- Використовувати Spring Framework, включаючи Spring Core, Spring Security та Spring Data для забезпечення безпеки та взаємодії з базою даних.
- Зберігати дані в базі даних SQL.
- Керувати залежностями та збирати проект за допомогою Maven.
- Розгортати застосунок у Docker контейнері.
- Розміщувати код на репозиторії Git.

## Технології

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Springdoc OpenAPI (Swagger)
- MySQL (Dialect 8)
- Liquibase
- Lombok
- jjwt
- Docker

## Як запустити

1. ✅ Завантажте та встановіть Docker на ваш комп'ютер, якщо ви ще цього не зробили. Docker необхідний для створення середовища проекту. Дивіться посібники з встановлення на [офіційному сайті](https://www.docker.com/) Docker.
2. ✅ Клонуйте репозиторій проекту на ваш локальний комп'ютер.
3. ✅ У директорії `src/main/resources` скопіюйте файл `application.properties.sample`, перейменуйте копію на `application.properties` та введіть необхідну інформацію (параметри підключення до бази даних, токени ботів, секретний ключ JWT тощо).
4. ✅ Створіть файл `.env` у кореневій директорії вашого проекту та заповніть його необхідними змінними середовища. Ці змінні будуть використовуватися Docker Compose для налаштування додатку.
5. ✅ З кореневої директорії проекту зібрати ваш застосунок за допомогою команди Maven package: `mvn package`.
6. ✅ Після збірки застосунку використовуйте наступну команду для запуску служб за допомогою Docker Compose: `docker-compose up`.

Будь ласка, переконайтеся, що всі необхідні конфігурації правильно налаштовані перед запуском застосунку.

Після успішного запуску ваш застосунок повинен працювати за адресою `localhost:6868`. Доступ до Swagger UI можна отримати, перейшовши за посиланням [http://localhost:6868/swagger-ui/index.html](http://localhost:6868/swagger-ui/index.html).

Не забудьте, щоб зупинити робочі контейнери, ви можете використовувати `docker-compose down` з тієї ж директорії, де розташований ваш файл `docker-compose.yml`.

### Автор

- Артем Грунін
