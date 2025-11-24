import java.util.Scanner;


/**
 * Основной класс программы.
 * Позволяет работать с массивом с помощью дерева Фенвика:
 * выполнять обновления, вычислять суммы на префиксе и отрезке,
 * выводить массив и подсчитывать количество инверсий
 */
public class Main {

    /**
     * Точка входа в программу. Реализует консольное меню и взаимодействие
     * пользователя с методами дерева Фенвика
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println ("Введите размер массива:");
        int n = readIntWithMin(scanner, 1);

        int[] arr = new int[n];

        System.out.println("Введите целые числа для массива:");
        for (int i = 0; i < n; i++) {
            arr[i] = readInt(scanner);
        }

        FenwickTree ft = new FenwickTree();
        ft.build(arr);

        while (true) {
            System.out.println();
            System.out.println("Выберите действие:");
            System.out.println("1: Сумма префикса");
            System.out.println("2: Сумма на отрезке");
            System.out.println("3: Обновить элемент");
            System.out.println("4: Показать текущий массив");
            System.out.println("5: Подсчитать количество инверсий в массиве");
            System.out.println("0: Закончить работу с массивом");


            int choice = readIntInRange(scanner, 0, 5);


            switch (choice) {
                case 0:
                    System.out.println("Работа с массивом завершена");
                    scanner.close();
                    return;
                case 1: {
                    System.out.println("Введите индекс:");
                    int index = readIntInRange(scanner, 0,n-1);
                    int sum = ft.prefixSum(index);
                    System.out.println("Сумма на префиксе: " + sum);
                    break;
                }

                case 2: {
                    System.out.println("Введите индексы начала и конца отрезка");

                    int left = readIntInRange(scanner,0, n-1);
                    int right = readIntInRange(scanner, left, n-1);

                    int sum = ft.rangeSum(left, right);
                    System.out.println("Сумма на отрезке: " + sum);
                    break;
                }

                case 3: {
                    System.out.println("Введите индекс элемента для обновления и delta (насколько изменить элемент)");
                    int index = readIntInRange(scanner, 0, n-1);
                    int delta = readInt(scanner);

                    ft.update(index, delta);
                    arr[index] += delta;
                    System.out.println("Элемент обновлён");
                    break;
                }

                case 4: {
                    System.out.print("Текущий массив: [");
                    for (int i = 0; i < n; i++) {
                        System.out.print(arr[i]+" ");
                    }
                    System.out.println("]");
                    break;
                }
                case 5: {
                    long inv = ft.countInversions(arr);
                    System.out.println("Количество инверсий в массиве: " + inv);
                    break;
                }

                default:
                    break;
            }
        }
    }


    /**
     * Безопасное чтение целого числа из ввода
     * Если пользователь вводит не int, метод выводит сообщение об ошибке и просит повторить ввод
     * @param scanner источник ввода
     * @return корректно введённое целое число
     */
    private static int readInt(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Ошибка: нужно ввести целое число!");
                System.out.println("Попробуйте еще раз");
                scanner.next();
            }
        }
    }

    /**
     * Считывает целое число, которое должно быть не меньше заданного значения
     * @param scanner  объект Scanner
     * @param minValue минимально допустимое значение
     * @return корректное число, не меньшее minValue
     */
    private static int readIntWithMin(Scanner scanner, int minValue) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();

                if (value >= minValue) {
                    return value;
                } else {
                    System.out.println("Ошибка: число меньше " + minValue);
                    System.out.println("Попробуйте еще раз");
                }
            } else {
                System.out.println("Ошибка: введите целое число!");
                System.out.println("Попробуйте еще раз");
                scanner.next();
            }
        }
    }

    /**
     * Считывает целое число, которое должно находиться в указанном диапазоне
     * @param scanner  объект Scanner
     * @param minValue минимально допустимое значение
     * @param maxValue максимально допустимое значение
     * @return корректное число в диапазоне [minValue, maxValue]
     */
    private static int readIntInRange(Scanner scanner, int minValue, int maxValue) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();

                if (value >= minValue && value <= maxValue) {
                    return value;
                } else {
                    System.out.println("Ошибка: число не в диапазоне!");
                    System.out.println("Попробуйте еще раз");
                }
            } else {
                System.out.println("Ошибка: нужно ввести целое число!");
                System.out.println("Попробуйте еще раз");
                scanner.next();
            }
        }
    }
}