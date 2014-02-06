package creative.fire.pattern.b.command;

public class Waitress {
    Order[] orders = new Order[2];

    public Waitress() {
        orders[0] = new SoapOrder();
        orders[1] = new DessertOrder();
    }

    public void takeOrder() {
        orders[0].execute();
        orders[1].execute();
    }
}
