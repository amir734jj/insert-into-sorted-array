import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class Driver {
    public static void main(String[] args) {

    }

    static Integer[] array;
    static int count;

    @BeforeClass
    public static void setUpClass() {
        array = (Integer[]) Array.newInstance(Integer.class, 1000);
        count = 0;
    }

    @Test
    public void test01() {
        insert(3, array, count++, Integer::compareTo);
        assertEquals(1, getCount(array));
        assertTrue(isSorted(array, count, Integer::compareTo));
    }

    @Test
    public void test02() {
        insert(2, array, count++, Integer::compareTo);
        assertEquals(2, getCount(array));
        assertTrue(isSorted(array, count, Integer::compareTo));
    }

    @Test
    public void test03() {
        insert(4, array, count++, Integer::compareTo);
        assertEquals(3, getCount(array));
        assertTrue(isSorted(array, count, Integer::compareTo));
    }

    @Test
    public void test04() {
        insert(1, array, count++, Integer::compareTo);
        assertEquals(4, getCount(array));
        assertTrue(isSorted(array, count, Integer::compareTo));
    }

    @Test
    public void test05() {
        Random rand = new Random();
        int i = 100;
        while (i-- >= 0) insert(rand.nextInt(100), array, count++, Integer::compareTo);
        assertTrue(isSorted(array, count, Integer::compareTo));
    }

    /**
     * insert e into the array in sorted order
     * Assuming the contents of the array are sorted
     * and stored from 0 inclusive to count exclusive
     *
     * @param e     element to insert
     * @param arr   array to insert to
     * @param count number of elements in the array
     * @param comp  comparer function
     * @param <E>   generic parameter E
     */
    private <E> void insert(E e, E[] arr, int count, Comparator<E> comp) {
        if (count == 0) arr[0] = e;

        for (int i = 0; i < count; i++) {
            if (comp.compare(arr[i], e) >= 0) {
                // we found an element that is >= to e
                // we want to add new element at index i, currently arr[i] is occupied
                // by larger element, so we need to adjust
                if (i != 0) {
                    i--;
                } else {
                    System.out.println(i);
                }
            } else if (i + 1 == count) {
                // this is the last iteration of the loop so we want to add element at i + 1
                i++;
            } else {
                // keep looping to find an element
                continue;
            }

            // we need to move elements to the right to make space
            for (int j = count; j > i; j--) {
                arr[j] = arr[j - 1];
            }

            arr[i] = e;
            break;
        }
    }

    public <E> int getCount(E[] arr) {
        return (int) Arrays.stream(arr).filter(Objects::nonNull).count();
    }

    public <E> boolean isSorted(E[] arr, int count, Comparator<E> comp) {
        for (int i = 0; i < count - 1; i++) {
            if (comp.compare(arr[i], arr[i + 1]) > 0) {
                return false;
            }
        }

        return true;
    }
}
