# hotel
Start:  mvn install
        spring-boot:run
        
Application has following endpoints:</br>
1. <a>http://localhost:8080/rooms</a></br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    (optional) `start` - date in format `YYYY-MM-DD`</br>
    (optional) `end` - date in format `YYYY-MM-DD`</br>
    (optional) `category` - `SINGLE`, `DOUBLE`, `FAMILY`, `PRESIDENT`
2. <a>http://localhost:8080/rooms/id</a></br>
    **method:** `GET`</br>
    **accepts parameters:**</br>
    `id` - alpha numeric value of room id`</br>
