personal-api
============

An api to access personal information


Getting Started
---------------

Edit json data used to prepopulate database in development

    $ subl /conf/startup-data

Run the application (Uses H2 in memory database by default)
    
    $ play run


Configuration
-------------

Database configuration (H2)

    db.default.url="jdbc:h2:mem:play;MODE=MYSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false"
    db.default.driver=org.h2.Driver

Database configuration (MySql)

    db.default.url="jdbc:mysql://localhost:3306/personal_api"
    db.default.driver=com.mysql.jdbc.Driver
    db.default.user=root
    db.default.pass=root

Swagger base path (extenally accessible api base url)

    swagger.api.basepath="http://localhost:9000"

Default user slug (used if none is provided to an endpoint)

    default.user-slug="stevesmith"

Load json files into database on startup (Recommended only for dev H2)

    startup-data.load=true

Directory used to load json files from. If starts with $resources refers to jar resource

    startup-data.directory="$resources/startup-data"