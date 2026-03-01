package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarControllerTest {

    private CarService carService;
    private CarController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        carService = mock(CarService.class);
        controller = new CarController(carService);
        model = mock(Model.class);
    }

    @Test
    void testCreateCarPage() {
        String view = controller.createCarPage(model);

        assertEquals("createCar", view);
        verify(model).addAttribute(eq("car"), any(Car.class));
    }

    @Test
    void testCreateCarPost() {
        Car car = new Car();

        String view = controller.createCarPost(car, model);

        verify(carService).create(car);
        assertEquals("redirect:listCar", view);
    }

    @Test
    void testCarListPage() {
        when(carService.findAll()).thenReturn(List.of(new Car()));

        String view = controller.carListPage(model);

        assertEquals("carList", view);
        verify(model).addAttribute(eq("cars"), anyList());
    }

    @Test
    void testEditCarPage() {
        Car car = new Car();
        when(carService.findById("1")).thenReturn(car);

        String view = controller.editCarPage("1", model);

        assertEquals("editCar", view);
        verify(model).addAttribute("car", car);
    }

    @Test
    void testEditCarPost() {
        Car car = new Car();
        car.setCarId("1");

        String view = controller.editCarPost(car, model);

        verify(carService).update("1", car);
        assertEquals("redirect:listCar", view);
    }

    @Test
    void testDeleteCar() {
        String view = controller.deleteCar("1");

        verify(carService).deleteCarById("1");
        assertEquals("redirect:listCar", view);
    }
}