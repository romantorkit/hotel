# hotel
Start:  mvn install
        spring-boot:run
        
Application has the following endpoints:</br>

### Manipulating rooms
##### 1. Get all available rooms
    if parameters like -start-, -end- are not present, then all rooms are displayed
http://localhost:8080/rooms</br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    `start` - date in format `YYYY-MM-DD` (optional)</br>
    `end` - date in format `YYYY-MM-DD` (optional)</br>
    `category` - `SINGLE`, `DOUBLE`, `FAMILY`, `PRESIDENT` (optional)</br>
##### 2. Get room by id
http://localhost:8080/rooms/`id`</br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    `id` - alpha numeric value of room's id  (required)</br>
### Manipulating user
##### 1. Get information for the registration page
http://localhost:8080/users/register </br>
    **method** `GET`</br>
##### 2. Register user
http://localhost:8080/users/register </br>
    **method** `POST`</br>
    **accepts parameters:**</br>
        `Content-Type` - `application/json` - representation of a user  (required)</br>
        `{
           "userName": "username",
           "password": "password",
           "firstName": "First",
           "lastName": "Last"
         }`
##### 3. Get information for the login page
http://localhost:8080/users/login </br>
    **method** `GET`</br>
##### 4. Login user
http://localhost:8080/users/login </br>
    **method** `POST`</br>
    **accepts parameters:**</br>
    `userName` - `username` (required)</br>
    `password` - `password` (required)</br>
##### 5. Get user by id
http://localhost:8080/users/`id` </br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    `id` - alpha numeric value of user's id  (required)</br>
##### 4. Get bookings for a specified user
http://localhost:8080/users/`id`/bookings </br>
     **method** `GET`</br>
     **accepts parameters:**</br>
     `id` - alpha numeric value of user's id  (required)</br>
