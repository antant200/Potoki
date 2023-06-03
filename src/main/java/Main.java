import java.io.*;
import java.util.Scanner;

public class Main {
    static File saveFile = new File("basket.json");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {30, 50, 150};
        int productCount, productNumber;
        Basket basket = null;
        if (saveFile.exists() && saveFile.length() > 0) {
            basket = Basket.loadFromJSONFile(saveFile);
        } else {
            basket = new Basket(prices, products);
        }
        ClientLog clientLog = new ClientLog();

        while (true) {
            System.out.println("Список доступных продуктов:");
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i] + ", цена: " + prices[i] + " руб/шт.");
            }

            System.out.println("Выберите товар и количество. Для завершения напишите end");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                System.out.println("Программа завершена!");
                clientLog.exportAsCSV(new File("log.csv"));
                break;
            }

            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            clientLog.log(productNumber,productCount);
            basket.saveJSON(saveFile);
        }
    }
}
