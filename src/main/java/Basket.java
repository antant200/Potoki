import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {
    protected int[] prices;
    protected String[] products;
    protected int[] quantities;

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
        int totalPrice = 0;
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < quantities.length; i++) {
            if (quantities[i] != 0) {
                int currentPrice = prices[i] * quantities[i];
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
        Basket basket =null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line =null;
            while ((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(stringBuilder.toString(),Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
