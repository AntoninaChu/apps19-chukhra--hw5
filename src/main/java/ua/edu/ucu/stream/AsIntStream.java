package ua.edu.ucu.stream;

import ua.edu.ucu.function.IntBinaryOperator;
import ua.edu.ucu.function.IntConsumer;
import ua.edu.ucu.function.IntPredicate;
import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.function.IntUnaryOperator;

import java.util.ArrayList;

public class AsIntStream implements IntStream {

    private ArrayList<Integer> array;

    private AsIntStream() {
        this.array = new ArrayList<>();
    }

    public static IntStream of(int... values) {
        IntStream result = new AsIntStream();
        for (int i = 0; i < values.length; i++) {
            ((AsIntStream) result).array.add(values[i]);
        }
        return result;
    }

    @Override
    public Double average() {
        if (array.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return (double) this.sum() / this.count();
    }

    public Integer max() {
        if (array.isEmpty()) {
            throw new IllegalArgumentException();
        }
        IntBinaryOperator op = (left, right) -> {
            if (right > left) {
                left = right;
            }
            return left;
        };
        return this.reduce(array.get(0), op);
    }

    @Override
    public Integer min() {
        if (array.isEmpty()) {
            throw new IllegalArgumentException();
        }
        IntBinaryOperator op = (left, right) -> {
            if (right < left) {
                left = right;
            }
            return left;
        };
        return this.reduce(array.get(0), op);
    }

    @Override
    public long count() {
        return array.size();
    }

    @Override
    public Integer sum() {
        if (array.isEmpty()) {
            throw new IllegalArgumentException();
        }
        IntBinaryOperator op = (left, right) -> left + right;
        return this.reduce(0, op);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> newArr = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if (predicate.test(array.get(i))) {
                newArr.add(array.get(i));
            }
        }
        IntStream result = new AsIntStream();
        ((AsIntStream) result).array = newArr;
        return result;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i = 0; i < array.size(); i++) {
            action.accept(array.get(i));
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        ArrayList<Integer> newArr = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            newArr.add(mapper.apply(array.get(i)));
        }
        IntStream result = new AsIntStream();
        ((AsIntStream) result).array = newArr;
        return result;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<Integer> newArr = new ArrayList<>();
        int[] arrayFromStream = this.toArray();
        for (int i = 0; i < arrayFromStream.length; i++) {
            IntStream subStream = func.applyAsIntStream(arrayFromStream[i]);
            IntConsumer action = value -> newArr.add(value);
            subStream.forEach(action);
        }
        IntStream result = new AsIntStream();
        ((AsIntStream) result).array = newArr;
        return result;
    }

    @Override
    public Integer reduce(int identity, IntBinaryOperator op) {
        int left = identity;
        for (int i = 0; i < array.size(); i++) {
            left = op.apply(left, array.get(i));
        }
        return left;
    }

    @Override
    public int[] toArray() {
        int[] result = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i);
        }
        return result;
    }
}
