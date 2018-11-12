# hotel
Start:  mvn install
        spring-boot:run
        
Application has following endpoints:</br>
1. <a>http://localhost:8080/rooms</a></br>
            **method:** GET</br>
            _accepts parameters:_</br>
            (optional) **start** - date in format `YYYY-MM-DD`</br>
            (optional) **end** - date in format `YYYY-MM-DD`</br>
            (optional) **category** - `SINGLE`, `DOUBLE`, `FAMILY`, `PRESIDENT`
