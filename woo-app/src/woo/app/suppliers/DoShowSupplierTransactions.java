package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.OutputFormatter;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.UnknownSupplierKeyCoreException;

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  private Input<String> _id;

  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _id = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    OutputFormatter visitor = new OutputFormatter();
    try {
      for (var t: _receiver.getSupplierTransactions(_id.value())) {
        _display.addLine(t.accept(visitor));
      }
      _display.display();
    } catch (UnknownSupplierKeyCoreException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
  }

}
