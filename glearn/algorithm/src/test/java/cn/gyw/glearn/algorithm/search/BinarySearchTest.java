package cn.gyw.glearn.algorithm.search;

import cn.gyw.glearn.algorithm.util.DataGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTest {

    @Test
    public void search() {
        BinarySearch binarySearch = new BinarySearch();
        binarySearch.search(DataGenerator.generateOrderedArray(1_000_000), 1);
    }

    @Test
    public void testSearch() {
    }
}