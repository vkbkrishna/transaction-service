# Error Knowledge Base for Transaction Service

This document explains common errors that may occur in the application and provides steps to resolve them.

---

## 1. ConstraintViolationException

**Description:**  
Occurs when a `CreditTransaction` fails validation (e.g., missing required fields, invalid amount, description too long).

**Symptoms:**
- Error message: `jakarta.validation.ConstraintViolationException`
- Details about which field failed validation.

**Steps to Fix:**
1. Check the error message for the specific field and reason.
2. Ensure all required fields are provided and valid.
3. For amount, ensure it is positive and not null.
4. For string fields, ensure they are not blank and within allowed length.

---

## 2. DataAccessResourceFailureException (DB Connection Error)

**Description:**  
Thrown when the application cannot connect to the database.

**Symptoms:**
- Error message: `org.springframework.dao.DataAccessResourceFailureException`
- Cause: `java.sql.SQLException: Connection refused` or similar.

**Steps to Fix:**
1. Check if the database server is running and accessible.
2. Verify database connection settings in `application.properties`.
3. Check network/firewall settings between app and DB.
4. Restart the application after fixing connectivity.

---

## 3. DataAccessException (General DB Error)

**Description:**  
Represents a general database error (e.g., SQL syntax error, constraint violation).

**Symptoms:**
- Error message: `org.springframework.dao.DataAccessException`

**Steps to Fix:**
1. Review the stack trace for the root cause.
2. Check database logs for more details.
3. Fix any invalid SQL or data issues.
4. If the error persists, escalate to the DBA.

---

## 4. ArrayIndexOutOfBoundsException

**Description:**  
Occurs when code tries to access an invalid index in an array.

**Symptoms:**
- Error message: `java.lang.ArrayIndexOutOfBoundsException`

**Steps to Fix:**
1. Check the code for array access logic.
2. Ensure indexes are within valid range.
3. Add proper bounds checking before accessing arrays.

---

## 5. NullPointerException

**Description:**  
Thrown when the code tries to use an object reference that is null.

**Symptoms:**
- Error message: `java.lang.NullPointerException`

**Steps to Fix:**
1. Check the stack trace for the variable that is null.
2. Ensure all objects are properly initialized before use.
3. Add null checks where appropriate.

---

## 6. MismatchedInputException

**Description:**  
Thrown when the code find an invalid field in the transaction

**Symptoms:**
- Error message: `com.fasterxml.jackson.databind.exc.MismatchedInputException`

**Steps to Fix:**
1. Check the stack trace for the variable that is invalid.
2. Ensure all fields are properly added to the object.
3. Configure the object to ignore all unknown fields.

---

## 7. JsonParseException

**Description:**  
Thrown when the code find an invalid character that it cannot in the transaction

**Symptoms:**
- Error message: `com.fasterxml.jackson.core.JsonParseException`

**Steps to Fix:**
1. Check the stack trace for the character that is invalid.
2. Strip the character from all responses prior to creating JSON objects
3. Configure the object to ignore all unknown fields.

---

## 8. JsonProcessingException

**Description:**  
Thrown when the code throws an exception when generatring or processing a Json Object

**Symptoms:**
- Error message: `com.fasterxml.jackson.core.JsonProcessingException`

**Steps to Fix:**
1. Check the stack trace for the field or value that is triggering the exception
2. Validate the Json Pojo to ensure it matches the string being sent to the process
3. Correct any inaccuracies so processing can be completed successfully.

---

## 9. IllegalStateException

**Description:**  
Thrown when timeout occurs in the processing of the transaction

**Symptoms:**
- Error message: `java.lang.IllegalStateException`

**Steps to Fix:**
1. Check the stack trace for the class where the delay occurred.
2. Check the transaction for any invalid/mssing fields cuasing the timeout
3. Check logs for any other timeouts occurring besides for this transaction.

---

## 10. IOException

**Description:**  
Thrown when file reads or writes failed, reading json schemas and/or updating them.

**Symptoms:**
- Error message: `java.io.IOException`

**Steps to Fix:**
1. Check the stack trace for the filename and path being used.
2. Check the file exists in the expected path, and is accessible by the process.
3. Check the file format that it matches the expected format.

---

## 11. RuntimeException

**Description:**  
Thrown when unchecked exception occurs in a running process

**Symptoms:**
- Error message: `java.lang.RuntimeException`

**Steps to Fix:**
1. Check the stack trace for the class and method where the exception occurred.
2. Add code to catch and handle the exception cleanly in the future.
3. Determine the cause from the trace, and fix accordingly.

---

## 12. Other Unexpected Exceptions

**Description:**  
Any other unhandled exceptions.

**Steps to Fix:**
1. Review the stack trace and error message.
2. Identify the code path and input that caused the error.
3. Add error handling or input validation as needed.
4. If unsure, escalate to the development team.

---

# General Troubleshooting Steps

- Always check application logs for detailed error messages and stack traces.
- Validate input data before processing.
- Ensure all external dependencies (DB, network, etc.) are healthy.
- For persistent or unclear issues, contact the development team with logs and error details.
