
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static praktikum.IngredientType.FILLING;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Burger burger;

    @Mock
    Bun bun;

    @Mock
    Ingredient ingredient;
    Ingredient ingredient2;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunTest() {
        burger.setBuns(bun);
        when(bun.getName()).thenReturn("Говядина");
        when(bun.getPrice()).thenReturn(7.29F);
        assertEquals("Название не совпадает", burger.bun.getName(), bun.getName());
        assertEquals("Цена не совпадает", burger.bun.getPrice(), bun.getPrice(), 0);
    }

    @Test
    public void addIngredientTest() {
        final List<Ingredient> ingredients = new ArrayList<>();
        burger.addIngredient(ingredient);
        assertTrue(ingredients.add(new Ingredient(IngredientType.SAUCE, "ketchup", 10)));
    }

    @Test
    public void removeIngredientTest() {
        ingredient = new Ingredient(IngredientType.SAUCE, "Кетчуп", 2.0F);
        ingredient2 = new Ingredient(IngredientType.SAUCE, "Паприка", 2.0F);
        burger.ingredients.add(ingredient2);
        burger.ingredients.add(ingredient);
        burger.removeIngredient(0);
        assertEquals("Длинна массива не совпадает", 1, burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        ingredient = new Ingredient(IngredientType.SAUCE, "Кетчуп", 2.0F);
        ingredient2 = new Ingredient(IngredientType.SAUCE, "Паприка", 2.0F);
        burger.ingredients.add(ingredient2);
        burger.ingredients.add(ingredient);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиент в массиве переместился не верно", ingredient, burger.ingredients.get(0));
    }

    @Test
    public void getPriceTest() {
        float price = 300;
        float expectedPrice = price * 2 + price;
        when(bun.getPrice()).thenReturn(price);
        when(ingredient.getPrice()).thenReturn(price);
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        assertEquals("Цена отличается", expectedPrice, burger.getPrice(), 0);
    }

    @Test
    public void getReceiptTest() {
        String nameBun = "Бигмак";
        String nameIngredient = "говядина";
        IngredientType ingredientType = FILLING;
        String expectedReceipt = String.format("(==== %s ====)" + "\r\n" +
                        "= %s %s =" + "\r\n" +
                        "(==== %s ====)" + "\r\n" + "\r\n" +
                        "Price: %f%n",
                nameBun, ingredientType.toString().toLowerCase(), nameIngredient, nameBun, ingredient.getPrice());
        burger.setBuns(bun);
        when(bun.getName()).thenReturn(nameBun);
        burger.addIngredient(ingredient);
        when(ingredient.getName()).thenReturn(nameIngredient);
        when(ingredient.getType()).thenReturn(ingredientType);
        assertEquals("Рецепт напечатан неверно", expectedReceipt, burger.getReceipt());
    }
}
