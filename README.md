# Work Action Service

Spring Boot clean/hexagonal-style sample with no Disclosure domain classes. It provides create and search APIs for the Work Action module and Azure SQL persistence.

## APIs

- `POST /api/work-actions/v1`
- `GET /api/work-actions/v1/search?refId=`

## Create request

```json
{
  "referenceId": "DISC-1001",
  "title": "Review submitted disclosure",
  "description": "Perform compliance review",
  "priority": "HIGH",
  "dueDate": "2026-09-15",
  "assignedTo": "review-team"
}
```

## Run

Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`, then run `./mvnw spring-boot:run` if you add Maven Wrapper, or `mvn spring-boot:run`.

## Notes

- Java 25 and Spring Boot 4.1.0 are configured to align with the requested environment.
- Search uses dynamic JPA specifications and returns a Spring Data page.
- Errors use Spring Problem Details with Work Action-specific error codes.
