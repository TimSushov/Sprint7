import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import steps.StepsOrder;

public class ListOrderTest {

    StepsOrder stepsOrder = new StepsOrder();

    @Test
    @DisplayName("Запрашиваем список заказов")
    @Description("В тело ответа возвращается список заказов")
    public void getOrderList() {
        stepsOrder.getListOrder(200);
    }

}