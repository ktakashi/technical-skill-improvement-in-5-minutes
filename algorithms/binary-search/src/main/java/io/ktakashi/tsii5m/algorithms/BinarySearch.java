package io.ktakashi.tsii5m.algorithms;

import java.util.List;
import java.util.NoSuchElementException;

public class BinarySearch {
    public <T extends Comparable<T>> T search(List<T> list, T element) {
        return searchRecursive(list, element, 0, list.size());
    }

    private <T extends Comparable<T>> T searchRecursive(List<T> list, T element, int start, int end) {
        int pos = (start + end) / 2;
        T pivot = list.get(pos);
        int comp = element.compareTo(pivot);
        if (comp == 0) {
            if (pos == 0) {
                throw new NoSuchElementException("There's one smaller element");
            }
            // Improvement point: what if the order is descendant?
            return list.get(pos - 1);
        } else if (comp > 0) {
            return searchRecursive(list, element, pos, end);
        } else {
            return searchRecursive(list, element, start, pos);
        }
    }
}
