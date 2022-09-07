
# Web Quiz Engine

#### REST Web Service.  
It can store quizzes to be solved. Users can also post their quizzes.


## Tech Stack

**Spring Boot**

**Spring Security**

**Spring Data**

**Project Lombok**

**H2 database**
## API

### Register a new user

```
POST api/user/register
```
#### Request body

```json
{
  "email": "<email>",
  "password": "<password>"
}
```

### Verify login-password pair

```
POST api/user/authenticate
```
#### Request body

```json
{
  "email": "<email>",
  "password": "<password>"
}
```

#### Description

If correct, returns 200 OK code. If not - 401 UNAUTHORIZED

### Get a quiz by id

```
  GET api/quizzes/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`      | `@path`  | **Required**. Quiz id      |

#### Description

Returns one quiz

### Get a page of quizzes

```
  GET api/quizzes
```

| Parameter | Type     | Description                                           |
| :-------- | :------- | :---------------------------------------------------- |
| `page`    | `int`    | **Not required**. Id of a page to fetch (Default = 0) |

#### Description

Returns page with only 10 quizzes.

### Get a page of completed quizzes by user

```
  GET api/quizzes/completed
```

| Parameter | Type     | Description                                           |
| :-------- | :------- | :---------------------------------------------------- |
| `page`    | `int`    | **Not required**. Id of a page to fetch (Default = 0) |

#### Description

Returns page with only 10 completed quizzes by an authenticated user.

### Check an answer of a quiz

```
  POST api/quizzes/{id}/solve
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`      | `@path`  | **Required**. Id of a quiz |

#### Request body

```json
{
  "answer": ["<list of the correct answers>"]
}
```

#### Description

Checks answer. If correct saves it to db.

### Add a new quiz

```
  POST api/quizzes
```

#### Request body

```json
{
  "title": "<title>",
  "text": "<text>",
  "options": "<more than 2 (Example: 1, 2)>",
  "answer": "<correct options>"
}
```

#### Description

Saves a new quiz to db.

### Delete a posted quiz

```
  DELETE api/quizzes/{id}
```

| Parameter | Type     | Description                          |
| :-------- | :------- | :----------------------------------- |
| `id`      | `@path`  | **Required**. Id of a quiz to delete |

#### Description

Delets a quiz, posted by authenticated user.

### Patch quiz

```
  PATCH api/quizzes/{id}
```

| Parameter | Type     | Description                          |
| :-------- | :------- | :----------------------------------- |
| `id`      | `@path`  | **Required**. Id of a quiz to update |

#### Request body

Should contain at least one of these properties:

```json
{
  "title": "<title>",
  "text": "<text>",
  "options": "<more than 2 (Example: 1, 2)>",
  "answer": "<correct options>"
}
```

#### Description

Updates the quiz with specified id.
## Requirements

* Java 11 or higher

## Compile

1. Go to project directory
```bash
  cd my-project
```

2. Execute gradle tasks
```bash
  ./gradlew clean
  ./gradlew bootJar
```

## Run

1. Go to the project directory

```bash
  cd my-project
```

2. Start the application

```bash
  java -jar web_quiz_engine-1.2.jar
```

