package edu.isu.cs.cs3308.impl;

import edu.isu.cs.cs3308.structures.SinglyLinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Isaac Griffith
 */
public class SinglyLinkedListTest {

    @Test
    public void testIndexOf() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(list.indexOf(1), 0);
        assertEquals(list.indexOf(2), 1);
        assertEquals(list.indexOf(3), 2);
        assertEquals(list.indexOf(null), -1);
        assertEquals(list.indexOf(4), -1);
    }
}
