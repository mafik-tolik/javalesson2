import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static void main(String[] args) {
        ex1And2();
        ex4();
    }

    static void ex1And2() {

        System.out.print("\nЗадание 1. Дана json строка {{\"фамилия\":\"Иванов\",\"оценка\":\"5\"," +
                "\"предмет\":\"Математика\"},{\"фамилия\":\"Петрова\",\"оценка\":\"4\"," +
                "\"предмет\":\"Информатика\"},{\"фамилия\":\"Краснов\",\"оценка\":\"5\"," +
                "\"предмет\":\"Физика\"}} Задача написать метод(ы), который распарсить " +
                "строку и выдаст ответ вида: Студент Иванов получил 5 по предмету Математика. " +
                "Студент Петрова получил 4 по предмету Информатика. " +
                "Студент Краснов получил 5 по предмету Физика. " +
                "Используйте StringBuilder для подготовки ответа.\n");
        System.out.println("Решение:");
        String filePath = "file.json";
        String result = parsJsonFile(filePath);
        System.out.println(result);

        System.out.print("Задание 2. Создать метод, который запишет результат работы в файл. " +
                "Обработайте исключения и запишите ошибки в лог файл.\n");
        System.out.println("Решение:\nРезультат в файле file.txt:");
        String file = "file.txt";
        writeResultAndLog(result, file);
    }

    static void ex4() {
        System.out.print("\nЗадание 4. Реализуйте алгоритм сортировки пузырьком числового массива, " +
                "результат после каждой итерации запишите в лог-файл.\n");
        System.out.println("Решение:");

        int[] arr = new int[]{3, 1, 5, 8, 7, 9, 0};

        System.out.print("Начальный массив: ");
        System.out.println(Arrays.toString(arr));

        try (FileWriter fileWriter = new FileWriter("logArray.txt", true)) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
                fileWriter.write(Arrays.toString(arr) + "\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Результат в файле logArray.txt\nОтсортированный массив: ");
        System.out.println(Arrays.toString(arr));
    }

    static String parsJsonFile(String fileP) {
        File file = new File(fileP);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String students = stringBuilder.toString().substring(4, stringBuilder.toString().length() - 4);
        stringBuilder.setLength(0);
        String oldStudents = students.replace(" ", "").replace("\"", "").replace("{", "");
        String[] newStudents = oldStudents.split("},");
        for (int i = 0; i < newStudents.length; i++) {
            String[] data1 = newStudents[i].split(",");
            for (int j = 0; j < data1.length; j++) {
                String[] data2 = data1[j].split(":");
                switch (j) {
                    case 0:
                        stringBuilder.append("Студент ");
                        break;
                    case 1:
                        stringBuilder.append(" получил ");

                        break;
                    case 2:
                        stringBuilder.append(" по предмету ");
                        break;
                    default:
                        break;
                }
                stringBuilder.append(data2[1]);
            }
            stringBuilder.append(".");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    static void writeResultAndLog(String res, String fPath) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.INFO, "Все хорошо");
        SimpleFormatter formatter = new SimpleFormatter();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fileHandler);

        try (FileWriter fileWriter = new FileWriter(fPath, false)) {
            fileWriter.write(res);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            e.printStackTrace();
        }
    }
}
