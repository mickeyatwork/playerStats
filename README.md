# Guide

1. Clone this repository

2. Go to the PlayerStatsApplication and run the main method

3. Once application is running, go to Postman (or other preferred API platform), 
where you can trigger any of the following example calls:

**GET /metrics**
<br>
http://localhost:8080/metrics?system=player_stats
<br>
http://localhost:8080/metrics?system=player_stats&name=goals
<br>
http://localhost:8080/metrics?system=player_stats&name=assists
<br>
http://localhost:8080/metrics?system=player_stats&from=1699905918
<br>
http://localhost:8080/metrics?system=player_stats&to=1699905931
<br>
http://localhost:8080/metrics?system=player_stats&from=1699905918&to=1699905931
<br>


**GET /metrics/{id}**
<br>
http://localhost:8080/metrics/1

**POST /metrics**
<br>
http://localhost:8080/metrics 

Include JSON body, for example:

    {
    "system": "player_stats",
    "name":"assists",
    "value":2
    }

If a value is not provided, then the value will default to 1.

**PUT /metrics/{id}**
<br>
http://localhost:8080/metrics/2

Include JSON body, for example:

    {
    "system": "player_stats",
    "name":"assists",
    "timestamp":"1699897593"
    }


**GET /metricsummary**
<br>
http://localhost:8080/metricsummary?system=player_stats
<br>
(Further functionality to be provided for filtering with dates)

