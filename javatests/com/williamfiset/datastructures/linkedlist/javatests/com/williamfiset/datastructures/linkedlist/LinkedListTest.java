package javatests.com.williamfiset.datastructures.linkedlist;

import static org.junit.Assert.*;

import com.williamfiset.datastructures.linkedlist.DoublyLinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.*;

public class LinkedListTest {

  private static final int LOOPS = 10000;
  private static final int TEST_SZ = 40;
  private static final int NUM_NULLS = TEST_SZ / 5;
  private static final int MAX_RAND_NUM = 250;

  private DoublyLinkedList<Integer> list;

  @Before
  public void setup() {
    list = new DoublyLinkedList<>();
  }

  @Test
  public void testEmptyList() {
    assertTrue(list.isEmpty());
    assertEquals(list.size(), 0);
  }

  @Test(expected = Exception.class)
  public void testRemoveFirstOfEmpty() {
    list.removeFirst();
  }

  @Test(expected = Exception.class)
  public void testRemoveLastOfEmpty() {
    list.removeLast();
  }

  @Test(expected = Exception.class)
  public void testPeekFirstOfEmpty() {
    list.peekFirst();
  }

  @Test(expected = Exception.class)
  public void testPeekLastOfEmpty() {
    list.peekLast();
  }

  @Test
  public void testAddFirst() {
    list.addFirst(3);
    assertEquals(list.size(), 1);
    list.addFirst(5);
    assertEquals(list.size(), 2);
  }

  @Test
  public void testAddLast() {
    list.addLast(3);
    assertEquals(list.size(), 1);
    list.addLast(5);
    assertEquals(list.size(), 2);
  }

  @Test
  public void testRemoveFirst() {
    list.addFirst(3);
    assertEquals(3, (int) list.removeFirst());
    assertTrue(list.isEmpty());
  }

  @Test
  public void testRemoveLast() {
    list.addLast(4);
    assertEquals(4, (int) list.removeLast());
    assertTrue(list.isEmpty());
  }

  @Test
  public void testPeekFirst() {
    list.addFirst(4);
    assertEquals(4, (int) list.peekFirst());
    assertEquals(list.size(), 1);
  }

  @Test
  public void testPeekLast() {
    list.addLast(4);
    assertEquals(4, (int) list.peekLast());
    assertEquals(list.size(), 1);
  }

  @Test
  public void testPeeking() {

    // 5
    list.addFirst(5);
    assertEquals(5, (int) list.peekFirst());
    assertEquals(5, (int) list.peekLast());

    // 6 - 5
    list.addFirst(6);
    assertEquals(6, (int) list.peekFirst());
    assertEquals(5, (int) list.peekLast());

    // 7 - 6 - 5
    list.addFirst(7);
    assertEquals(7, (int) list.peekFirst());
    assertEquals(5, (int) list.peekLast());

    // 7 - 6 - 5 - 8
    list.addLast(8);
    assertEquals(7, (int) list.peekFirst());
    assertEquals(8, (int) list.peekLast());

    // 7 - 6 - 5
    list.removeLast();
    assertEquals(7, (int) list.peekFirst());
    assertEquals(5, (int) list.peekLast());

    // 7 - 6
    list.removeLast();
    assertEquals(7, (int) list.peekFirst());
    assertEquals(6, (int) list.peekLast());

    // 6
    list.removeFirst();
    assertEquals(6, (int) list.peekFirst());
    assertEquals(6, (int) list.peekLast());
  }

  @Test
  public void testRemoving() {

    DoublyLinkedList<String> strs = new DoublyLinkedList<>();
    strs.add("a");
    strs.add("b");
    strs.add("c");
    strs.add("d");
    strs.add("e");
    strs.add("f");
    strs.remove("b");
    strs.remove("a");
    strs.remove("d");
    strs.remove("e");
    strs.remove("c");
    strs.remove("f");
    assertEquals(0, strs.size());
  }

  @Test
  public void testRemoveAt() {

    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.removeAt(0);
    list.removeAt(2);
    assertEquals(2, (int) list.peekFirst());
    assertEquals(3, (int) list.peekLast());
    list.removeAt(1);
    list.removeAt(0);
    assertEquals(list.size(), 0);
  }

  @Test
  public void testClear() {

    list.add(22);
    list.add(33);
    list.add(44);
    assertEquals(list.size(), 3);
    list.clear();
    assertEquals(list.size(), 0);
    list.add(22);
    list.add(33);
    list.add(44);
    assertEquals(list.size(), 3);
    list.clear();
    assertEquals(list.size(), 0);
  }

  @Test
  public void testRandomizedRemoving() {

    java.util.LinkedList<Integer> LIST = new java.util.LinkedList<>();
    for (int loops = 0; loops < LOOPS; loops++) {

      list.clear();
      LIST.clear();

      List<Integer> randNums = genRandList();
      for (Integer value : randNums) {
        LIST.add(value);
        list.add(value);
      }

      Collections.shuffle(randNums);

      for (Integer rm_val : randNums) {

        assertEquals(LIST.remove(rm_val), list.remove(rm_val));
        assertEquals(LIST.size(), list.size());

        java.util.Iterator<Integer> iter1 = LIST.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertEquals(iter1.next(), iter2.next());

        iter1 = LIST.iterator();
        iter2 = list.iterator();
        while (iter1.hasNext()) assertEquals(iter1.next(), iter2.next());
      }

      list.clear();
      LIST.clear();

      for (Integer value : randNums) {
        LIST.add(value);
        list.add(value);
      }

      // Try removing elements whether or not they exist
      for (int i = 0; i < randNums.size(); i++) {

        Integer rm_val = (int) (MAX_RAND_NUM * Math.random());
        assertEquals(LIST.remove(rm_val), list.remove(rm_val));
        assertEquals(LIST.size(), list.size());

        java.util.Iterator<Integer> iter1 = LIST.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertEquals(iter1.next(), iter2.next());
      }
    }
  }

  @Test
  public void testRandomizedRemoveAt() {

    java.util.LinkedList<Integer> LIST = new java.util.LinkedList<>();

    for (int loops = 0; loops < LOOPS; loops++) {

      list.clear();
      LIST.clear();

      List<Integer> randNums = genRandList();

      for (Integer value : randNums) {
        LIST.add(value);
        list.add(value);
      }

      for (int i = 0; i < randNums.size(); i++) {

        int rm_index = (int) (list.size() * Math.random());

        Integer num1 = LIST.remove(rm_index);
        Integer num2 = list.removeAt(rm_index);
        assertEquals(num1, num2);
        assertEquals(LIST.size(), list.size());

        java.util.Iterator<Integer> iter1 = LIST.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertEquals(iter1.next(), iter2.next());
      }
    }
  }

  @Test
  public void testRandomizedIndexOf() {

    java.util.LinkedList<Integer> LIST = new java.util.LinkedList<>();

    for (int loops = 0; loops < LOOPS; loops++) {

      LIST.clear();
      list.clear();

      List<Integer> randNums = genUniqueRandList();

      for (Integer value : randNums) {
        LIST.add(value);
        list.add(value);
      }

      Collections.shuffle(randNums);

      for (Integer elem : randNums) {

        Integer index1 = LIST.indexOf(elem);
        Integer index2 = list.indexOf(elem);

        assertEquals(index1, index2);
        assertEquals(LIST.size(), list.size());

        java.util.Iterator<Integer> iter1 = LIST.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertEquals(iter1.next(), iter2.next());
      }
    }
  }

  // Generate a list of random numbers
  private static List<Integer> genRandList() {
    List<Integer> lst = new ArrayList<>(LinkedListTest.TEST_SZ);
    for (int i = 0; i < LinkedListTest.TEST_SZ; i++) lst.add((int) (Math.random() * MAX_RAND_NUM));
    for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
    Collections.shuffle(lst);
    return lst;
  }

  // Generate a list of unique random numbers
  private static List<Integer> genUniqueRandList() {
    List<Integer> lst = new ArrayList<>(LinkedListTest.TEST_SZ);
    for (int i = 0; i < LinkedListTest.TEST_SZ; i++) lst.add(i);
    for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
    Collections.shuffle(lst);
    return lst;
  }
}
