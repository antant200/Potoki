import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    static int[] prices = {30, 50, 150};
    static int productCount;
    static int productNumber;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Setting setting = new Setting(new File("shop.xml"));
        File loadFile = new File(setting.loadFile);
        File saveFile = new File(setting.saveFile);
        File logFile = new File(setting.logFile);


        Basket basket = createBasket(loadFile, setting.isLoad, setting.loadFormat);
        ClientLog log = new ClientLog();
        while (true) {
            showProduct();
            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                System.out.println("Программа завершена!");
                if (setting.isLog) {
                    log.exportAsCSV(logFile);
                }

                break;
            }

            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            if (setting.isLog) {
                log.log(productNumber, productCount);
            }
            if (setting.isSave) {
                switch (setting.saveFormat) {
                    case "json" -> basket.saveJSON(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }
            basket.saveJSON(saveFile);
        }
    }


    protected static void showProduct() {
        System.out.println("Список доступных продуктов:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + ", цена: " + prices[i] + " руб/шт.");
        }
        System.out.println("Выберите товар и количество. Для завершения напишите end");
    }


    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJSONFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(prices, products);
            };
        } else {
            basket = new Basket(prices, products);
        }
        return basket;
    }
}
