import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    @Test
    public void testAddToCart() {
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {30, 50, 150};
        Basket basket = new Basket(prices, products);
        basket.addToCart(2, 2);
        basket.addToCart(1, 1);
        basket.addToCart(0, 3);


        int[] actual = basket.getQuantities();
        int[] expected = {3,1,2};

        Assertions.assertArrayEquals(actual,expected);
    }

    @Test
    public void testTotalPrice () {
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {30, 50, 150};
        Basket basket = new Basket(prices,products);
        basket.addToCart(2, 2);
        basket.addToCart(1, 1);
        basket.addToCart(0, 3);
        basket.printCart();

        int actual = basket.getTotalPrice();
        int expected = (30*3 + 50 + 150*2);

        Assertions.assertEquals(actual,expected);
    }

    @Test
    public void testLoadFromJson (){
        Basket basket =Basket.loadFromJSONFile(new File("src/test/resources/testBasket.json"));

        String [] actualProduct = basket.getProducts();
        String [] expectedProduct = {"Хлеб", "Яблоки", "Молоко"};

        int [] actualPrices = basket.getPrices();
        int [] expectedPrices = {30, 50, 150};

        int [] actualQuantities = basket.getQuantities();
        int [] expectedQuentities = {6,0,0};

        Assertions.assertArrayEquals(actualProduct,expectedProduct);
        Assertions.assertArrayEquals(actualPrices,expectedPrices);
        Assertions.assertArrayEquals(actualQuantities,expectedQuentities);

    }

}
