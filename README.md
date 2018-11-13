# hotel
Start:  mvn install
        spring-boot:run
        
Application has the following endpoints:</br>

###Manipulating rooms
#####Get all available rooms
if parameters like `start`, `end` are not present then all rooms are displayed
    1. http://localhost:8080/rooms</br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    (optional) `start` - date in format `YYYY-MM-DD`</br>
    (optional) `end` - date in format `YYYY-MM-DD`</br>
    (optional) `category` - `SINGLE`, `DOUBLE`, `FAMILY`, `PRESIDENT`
    2. http://localhost:8080/rooms/id</br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    `id` - alpha numeric value of room id</br>
###Manipulating user
#####Get information for the registration page
    1. http://localhost:8080/register /n
    **method** `GET`
    2. http://localhost:8080/register /n
    **method** `POST`
    3. http://localhost:8080/login /n
    **method** `GET`
    4. http://localhost:8080/login /n
    **method** `POST`
