package woo.app;

import woo.*;
import woo.app.suppliers.Message;
import java.util.Map;

public class OutputFormatter implements Visitor {

    public String visitClient(Client c) {
        return c.getKey() + "|" + c.getName() + "|" + c.getAddress() + "|" + c.getStatus().accept(this) + "|"+ (int)c.getSalesValue() +"|" + (int)c.getPaidSalesValue();
    }

    public String baseComment(Product p) {
        return p.getKey() + "|" + p.getSupplierKey() + "|" + p.getPrice() + "|" + p.getStockCriticalValue() + "|" + p.getAmount();
    }

    public String visitBook(Book b) {
        //PODEMOS CHAMAR VISITPRODUCT()?
        return "BOOK|" + baseComment(b) + "|" + b.getTitle() + "|" + b.getAuthor() + "|" + b.getISBN();
    }

    public String visitBox(Box b) {
        return "BOX|" + baseComment(b) + "|" + b.getServiceType();
    }

    public String visitContainer(Container c) {
        return  "CONTAINER|" + baseComment(c) + "|" + c.getServiceType() + "|" + c.getServiceLevel();
    }

    public String visitSupplier(Supplier s) {
        String status = s.getState() ? Message.yes() : Message.no();

        return s.getKey() + "|" + s.getName() + "|" + s.getAddress() + "|" + status;
    }

    public String visitTransaction(Order o) {
        String out = o.getKey() + "|" + o.getSupplierKey() + "|" + (int)o.getTotalCost() + "|" + o.getPaymentDate() + "\n";

            for(Map.Entry<Product, Integer> entry : o.getProducts().entrySet())
                out += entry.getKey().getKey() + "|" + entry.getValue() + "\n";

        return out;
    }

    public String visitTransaction(Sale s) {
        String out = s.getKey() + "|" + s.getClient().getKey() + "|" + s.getProduct().getKey() + "|" + s.getAmount() + "|" + (int)s.getBaseCost() + "|" + (int)s.getCurrentCost() + "|" + s.getPaymentDeadline();
        out = s.isPaid() ? (out + "|" + s.getPaymentDate()) : out;

        return out;
    }

    public String visitNotification(Notification n) {
        return n.getDescription() + "|" + n.getProductKey() + "|" + n.getProductPrice() + '\n';
    }

    @Override
    public String visitClientState(NormalState s) {
        return "NORMAL";
    }

    @Override
    public String visitClientState(SelectionState s) {
        return "SELECTION";
    }

    @Override
    public String visitClientState(EliteState s) {
        return "ELITE";
    }

}
