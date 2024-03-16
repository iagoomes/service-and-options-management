# Service And Options Management
Swagger API Documentation: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## API Endpoints Service Option

### POST /v1/services_option

Creates a new service option.

```bash
curl -X POST --location "http://localhost:8080/v1/services_option" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Almoço",
          "pricePerPerson": 66.00
        }'
```

### GET /v1/services_option/{id}

Returns the service option details based on the service ID provided.

```bash
curl -X GET --location "http://localhost:8080/v1/services_option/{{id}}"
```

### GET /v1/services_option

Returns all the available services.

```bash
curl -X GET --location "http://localhost:8080/v1/services_option"
```

### PUT /v1/services_option/{id}

Updates the details of the service option based on the service ID provided.

```bash
curl -X PUT --location "http://localhost:8080/v1/services_option/2" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Almoço",
          "pricePerPerson": 70.00
        }'
```

### DELETE /v1/customers/{id}

Deletes the service option based on the service ID provided.

```bash
curl -X DELETE --location "http://localhost:8080/v1/services_option/2"
```

## API Endpoints Optional Service Option

### POST /v1/optional_services_option

Creates a new optional service option.

```bash
curl -X POST --location "http://localhost:8080/v1/optional_services_option" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Jantar",
          "price": 100.00
        }'
```

### GET /v1/optional_services_option/{id}

Returns the optional service option details based on the service ID provided.

```bash
curl -X GET --location "http://localhost:8080/v1/optional_services_option/2"
```

### GET /v1/optional_services_option

Returns all the available services.

```bash
curl -X GET --location "http://localhost:8080/v1/optional_services_option"
```

### PUT /v1/optional_services_option/{id}

Updates the details of the optional services option based on the service ID provided.

```bash
curl -X PUT --location "http://localhost:8080/v1/optional_services_option/2" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Jantar",
          "price": 80.00
        }'
```

### DELETE /v1/optional_services_option/{id}

Deletes the optional service option based on the service ID provided.

```bash
curl -X DELETE --location "http://localhost:8080/v1/optional_services_option/1"
```
