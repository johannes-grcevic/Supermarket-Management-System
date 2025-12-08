import controllers.Supermarket;
import models.FloorArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SupermarketTest {
    private Supermarket supermarket;

    @BeforeEach
    void setUp() {
        supermarket = new Supermarket();
    }

    @AfterEach
    void tearDown() {
        supermarket = null;
    }

    @Test
    void addFloorArea() {
        FloorArea floorAreaAreaNull = supermarket.addFloorArea("", -1);
        assertNull(floorAreaAreaNull);

        FloorArea floorArea = supermarket.addFloorArea("Household", 1);
        assertEquals("Household", floorArea.getTitle());
        assertEquals(1, floorArea.getLevel());

        FloorArea floorArea2 = supermarket.addFloorArea("Dairy", 2);
        assertEquals("Dairy", floorArea2.getTitle());
        assertEquals(2, floorArea2.getLevel());
    }

    @Test
    void getFloorAreaIndex() {
        supermarket.addFloorArea("Household", 1);
        supermarket.addFloorArea("Dairy", 2);

        FloorArea floorArea = supermarket.getFloorArea(0);
        assertEquals("Household", floorArea.getTitle());
        FloorArea floorArea2 = supermarket.getFloorArea(1);
        assertEquals("Dairy", floorArea2.getTitle());
    }

    @Test
    void getFloorAreaName() {
        supermarket.addFloorArea("Household", 1);
        supermarket.addFloorArea("Dairy", 2);

        FloorArea floorArea = supermarket.getFloorArea("Household");
        assertEquals("Household", floorArea.getTitle());
        FloorArea floorArea2 = supermarket.getFloorArea("Dairy");
        assertEquals("Dairy", floorArea2.getTitle());
    }
}
