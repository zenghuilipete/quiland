package command;

import creative.fire.pattern.b.command.Customer;
import creative.fire.pattern.b.command.Waitress;
import org.junit.Test;

public class TestCookCommand {
    @Test
    public void testCookOrdering() {
        Customer xiaoMing = new Customer(new Waitress());
        xiaoMing.getService().takeOrder();
    }
}
