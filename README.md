# hotel
Start:  mvn install
        spring-boot:run
        
Application has following endpoints:</br>
1. <a>http://localhost:8080/rooms</a></br>
            **method:** GET</br>
            **accepts parameters:**</br>
            (optional) _start_ - date in format YYYY-MM-DD</br>
            (optional) _end_ - date in format YYYY-MM-DD</br>
            (optional) _category_ - _SINGLE_, _DOUBLE_, _FAMILY_, _PRESIDENT_
