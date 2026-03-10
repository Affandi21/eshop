package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceImplTest {

    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(new CarRepository());
    }

    @Test
    void testCreate() {
        Car car = new Car();
        car.setCarName("BMW");

        Car result = carService.create(car);

        assertNotNull(result.getCarId());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarName("A");

        carService.create(car1);

        List<Car> cars = carService.findAll();

        assertEquals(1, cars.size());
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarName("Mazda");

        Car created = carService.create(car);

        Car found = carService.findById(created.getCarId());

        assertEquals("Mazda", found.getCarName());
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarName("Old");

        Car created = carService.create(car);

        Car updated = new Car();
        updated.setCarName("Updated");

        carService.update(created.getCarId(), updated);

        Car result = carService.findById(created.getCarId());

        assertEquals("Updated", result.getCarName());
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarName("Delete");

        Car created = carService.create(car);

        carService.deleteCarById(created.getCarId());

        assertNull(carService.findById(created.getCarId()));
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("existing-id");
        car.setCarName("Toyota");

        Car result = carService.create(car);

        assertEquals("existing-id", result.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(carService.findById("not-exist"));
    }
}