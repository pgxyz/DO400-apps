package com.redhat.training;

import static org.mockito.Mockito.mock;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.redhat.training.service.SolverService;

import org.jboss.resteasy.client.exception.ResteasyWebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MultiplierResourceTest {
    
    SolverService solverService;
    MultiplierResource multiplierResource;

    @BeforeEach
    public void setup() {
        solverService = mock(SolverService.class);
        multiplierResource = new MultiplierResource(solverService);
    }

    @Test
    public void simpleMultiplication() {
        Mockito.when(solverService.solve("2")).thenReturn(Float.valueOf("2"));
        Mockito.when(solverService.solve("3")).thenReturn(Float.valueOf("3"));

        Float result = multiplierResource.multiply("2", "3");

        assertEquals(6.0f, result);

    }

    @Test
    public void negativeMultiply() {
        Mockito.when(solverService.solve("-2")).thenReturn(Float.valueOf("-2"));
        Mockito.when(solverService.solve("3")).thenReturn(Float.valueOf("3"));

        Float result = multiplierResource.multiply("-2", "3");

        assertEquals(-6.0f, result);

    }

    @Test
    public void wrongValue() {
        WebApplicationException cause = new WebApplicationException("Unknown error",
          Response.Status.BAD_REQUEST);
        Mockito.when(solverService.solve("a")).thenReturn(Float.valueOf("-2"));
        Mockito.when(solverService.solve("3")).thenReturn(Float.valueOf("3"));

        Executable multiplication = () -> multiplierResource.multiply("a", "3");

        assertThrows(ResteasyWebApplicationException.class, multiplication);

    }
}
