import models.FloorArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.MyLinkedList;

public class MyLinkedListTest {
    private MyLinkedList<FloorArea> list = new MyLinkedList<>();
    private FloorArea householdFloorArea = new FloorArea("Household", 1);
    private FloorArea dairyFloorArea = new FloorArea("Dairy", 2);

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @AfterEach
    void tearDown() {
        list = null;
        householdFloorArea = null;
        dairyFloorArea = null;
    }

    @Test
    void add() {
        assertEquals(0, list.size());

        list.add(householdFloorArea);
        assertEquals("Household", list.get(0).getTitle());
        assertEquals(1, list.get(0).getLevel());
        assertEquals(1, list.size());

        list.add(new FloorArea("Dairy", 2));
        assertEquals("Dairy", list.get(1).getTitle());
        assertEquals(2, list.get(1).getLevel());
        assertEquals(2, list.size());
    }

    @Test
    void remove() {
        assertEquals(0, list.size());

        list.add(householdFloorArea);
        list.add(dairyFloorArea);
        assertEquals(2, list.size());
        list.remove(householdFloorArea);
        assertEquals(dairyFloorArea, list.get(0));
        assertEquals(1, list.size());
        list.remove(dairyFloorArea);
        assertNull(list.get(1));
        assertEquals(0, list.size());
    }

    @Test
    void get() {
        assertEquals(0, list.size());
        list.add(householdFloorArea);
        assertEquals("Household", list.get(0).getTitle());
        assertEquals(1, list.get(0).getLevel());

        list.add(dairyFloorArea);
        assertEquals("Dairy", list.get(1).getTitle());
        assertEquals(2, list.get(1).getLevel());
    }

    @Test
    void size() {
        assertEquals(0, list.size());
        list.add(householdFloorArea);
        assertEquals(1, list.size());
        list.add(dairyFloorArea);
        assertEquals(2, list.size());
        list.remove(householdFloorArea);
        assertEquals(1, list.size());
        list.remove(dairyFloorArea);
        assertEquals(0, list.size());
    }

    @Test
    void clear() {
        assertEquals(0, list.size());
        list.add(householdFloorArea);
        list.add(dairyFloorArea);
        list.clear();

        assertEquals(0, list.size());
    }
}
