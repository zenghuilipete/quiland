package creative.fire.pattern.b.observer.dem;

public class BabyEvent extends java.util.EventObject {
    private BabySource source;

    public BabyEvent(BabySource source) {
        super(source);
        this.source = source;
    }

    public BabySource getSource() {
        return source;
    }
}
