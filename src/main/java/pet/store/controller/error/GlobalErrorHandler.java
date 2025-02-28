package pet.store.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice // Tells Spring this is a global exception handler for REST controllers
@Slf4j // Lombok annotation for logging
public class GlobalErrorHandler {
	
	@ExceptionHandler(NoSuchElementException.class) // Handles NoSuchElementException globally
	@ResponseStatus(code = HttpStatus.NOT_FOUND) // Returns HTTP 404 status when exception occurs
	public Map<String, String> handleNSkuchElementException(NoSuchElementException ex) {
		log.error("Exception: {}", ex.toString()); // Logs the exception details
		return Map.of("message", ex.toString()); // Returns a simple JSON response with the error message
	}
}

