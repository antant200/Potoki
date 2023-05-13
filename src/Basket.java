import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    @Serial
    private static final long serialVersionUID =1L;
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

    public void saveBin (File file){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Basket loadFromBinFile (File file){
        Basket basket;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            basket = (Basket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
