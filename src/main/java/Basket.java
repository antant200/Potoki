import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {
    public int[] getPrices() {
        return prices;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    protected int[] prices;
    protected int currentPrice;
    protected String[] products;
    protected int[] quantities;
    protected int totalPrice = 0;


    public Basket() {
    }

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.quantities = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        quantities[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < quantities.length; i++) {
            if (quantities[i] != 0) {
                currentPrice = prices[i] * quantities[i];
                totalPrice += currentPrice;
                System.out.println("Продукт: " + products[i] + ". Цена: " + prices[i] + ". Количество товара: " + quantities[i] + ". Сумма: " + currentPrice);
            }
        }
        System.out.println("Итоговая сумма: " + totalPrice);
    }

    public void saveJSON(File file) {
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            out.print(json);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static Basket loadFromJSONFile(File file) {
        Basket basket = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(stringBuilder.toString(), Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products) {
                out.print(product + " ");
            }
            out.println();

            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();

            for (int quantity : quantities) {
                out.print(quantity + " ");
            }
            out.println();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productString = bufferedReader.readLine();
            String priceString = bufferedReader.readLine();
            String quantStr = bufferedReader.readLine();
            if (!productString.equals("")) {
                basket.products = productString.split(" ");
                basket.prices = Arrays.stream(priceString.split(" "))
                        .map(Integer::parseInt)
                        .mapToInt(Integer::intValue).toArray();
                basket.quantities = Arrays.stream(quantStr.split(" "))
                        .map(Integer::parseInt)
                        .mapToInt(Integer::intValue).toArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
