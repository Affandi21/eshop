package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    HomeController controller = new HomeController();

    @Test
    void testIndex() {
        assertEquals("index", controller.index());
    }
}