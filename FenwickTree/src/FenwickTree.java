import java.util.Arrays;


/**
 * Класс реализует дерево Фенвика (Fenwick Tree)
 * Позволяет выполнять:
 * - точечные обновления
 * - суммы на префиксе
 * - суммы на отрезке
 * - подсчёт количества инверсий массива
 */
public class FenwickTree {
    private int[] tree;
    private int n;

    /**
     * Конструктор по умолчанию
     * Построение дерева выполняется методом build()
     */
    public FenwickTree() {
    }


    /**
     * Строит дерево Фенвика по исходному массиву
     * @param arr массив, на основе которого строится дерево
     */
    public void build(int[] arr) {
        this.n = arr.length;
        this.tree = new int[n + 1];

        for (int i = 0; i < n; i++) {
            update(i, arr[i]);
        }
    }


    /**
     * Выполняет точечное обновление: прибавляет значение delta
     * к элементу массива с индексом index
     * @param index индекс элемента (0-based)
     * @param delta значение, которое нужно прибавить
     */
    public void update(int index, int delta) {
        int i = index + 1;

        while (i <= n) {
            tree[i] += delta;
            i += i & -i;
        }
    }

    /**
     * Вычисляет сумму на префиксе массива: arr[0] + ... + arr[index]
     * @param index правый конец префикса
     * @return сумма элементов на префиксе
     */
    public int prefixSum(int index) {
        int i = index + 1;
        int sum = 0;

        while (i > 0) {
            sum += tree[i];
            i -= i & -i;
        }
        return sum;
    }

    /**
     * Вычисляет сумму элементов массива на отрезке [left, right]
     * @param left  левый индекс
     * @param right правый индекс
     * @return сумма на отрезке
     * @throws IllegalArgumentException если left > right
     */
    public int rangeSum(int left, int right) {
        if (left > right) {
            throw new IllegalArgumentException("left должен быть <= right");
        }
        int sumRight = prefixSum(right);
        int sumLeft = (left > 0) ? prefixSum(left - 1) : 0;
        return sumRight - sumLeft;
    }

    /**
     * Подсчитывает количество инверсий в массиве
     * @param arr массив, в котором нужно посчитать инверсии
     * @return количество инверсий
     */
    public long countInversions(int[] arr) {
        int len = arr.length;
        if (len <= 1) return 0L; //нет пары элементов, возвращаем 0

        int[] sorted = Arrays.copyOf(arr, len); //создаем новый массив и копируем в него все элементы arr
        Arrays.sort(sorted); //сортируем

        int[] bit = new int[len + 1]; //дерево фенвика для частот

        long inversions = 0L; //счетчик инверсий

        for (int i = len - 1; i >= 0; i--) { //идем справа налево
            int val = arr[i];

            int pos = Arrays.binarySearch(sorted, val); //ищем позицию val в отсортированном массиве
            while (pos > 0 && sorted[pos - 1] == val) { //сдвигаем влево, если есть одинаковые значения
                pos--;
            }
            int rank = pos + 1;

            int smallerCount = bitPrefixSum(bit, rank - 1); //смотрим есть ли числа справа меньше рассматриваемого
            inversions += smallerCount; // обновляем счетчик инверсий

            bitUpdate(bit, rank, 1); // регистрируем, что встретилось ещё одно значение с этим рангом
        }

        return inversions;
    }

    /**
     * Локальное обновление Фенвик дерева для подсчёта инверсий
     * @param bit   массив дерева
     * @param index индекс, который нужно обновить
     * @param delta значение, которое нужно прибавить
     */
    private void bitUpdate(int[] bit, int index, int delta) {
        int n = bit.length - 1;
        int i = index;
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    /**
     * Локальная префиксная сумма, используемая при подсчёте инверсий
     * @param bit   массив дерева
     * @param index правый конец префикса
     * @return сумма на префиксе дерева
     */
    private int bitPrefixSum(int[] bit, int index) {
        int sum = 0;
        int i = index;
        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }
}
