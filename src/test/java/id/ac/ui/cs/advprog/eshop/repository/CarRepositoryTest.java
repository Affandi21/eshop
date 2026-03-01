package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        car.setCarName("Toyota");

        Car result = carRepository.create(car);

        assertNotNull(result.getCarId());
        assertEquals("Toyota", result.getCarName());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarName("A");

        Car car2 = new Car();
        car2.setCarName("B");

        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarName("Honda");

        Car created = carRepository.create(car);

        Car found = carRepository.findById(created.getCarId());

        assertNotNull(found);
        assertEquals("Honda", found.getCarName());
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarName("Old");

        Car created = carRepository.create(car);

        Car updated = new Car();
        updated.setCarName("New");
        updated.setCarColor("Red");
        updated.setCarQuantity(5);

        Car result = carRepository.update(created.getCarId(), updated);

        assertEquals("New", result.getCarName());
        assertEquals("Red", result.getCarColor());
        assertEquals(5, result.getCarQuantity());
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarName("DeleteMe");

        Car created = carRepository.create(car);

        carRepository.delete(created.getCarId());

        assertNull(carRepository.findById(created.getCarId()));
    }
}