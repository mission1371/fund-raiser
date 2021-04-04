
# Technologies
- `Spring boot`
- `Java 11`
- `Gradle`
- `Liquibase`
- `Postgres` & `H2`

# Profiles
There are 3 profiles defined for the application. 2 for local development and one for production.
- `development` Uses **h2** as DB 
- `local` Uses **postgres** as DB
- `production` Uses postgres as DB on the Docker cluster

# Running the app
In order to run the application for development either provide local or development as an active profile to spring.

# Data Management
`initial-data.csv` file contains the initial products that are loaded on the initial application start.

# Limitations
- Paging for the products intentionally not implemented for the simplicity. 
