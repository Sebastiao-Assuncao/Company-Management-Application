package woo;

public interface Visitor {

    String visitClient (Client c);

    String baseComment(Product p);

    String visitBook (Book b);

    String visitContainer(Container c);

    String visitBox (Box b);

    String visitSupplier (Supplier s);

    String visitTransaction (Order o);

    String visitTransaction (Sale s);

    String visitNotification (Notification n);

    String visitClientState(NormalState s);

    String visitClientState (SelectionState s);

    String visitClientState (EliteState s);

}
