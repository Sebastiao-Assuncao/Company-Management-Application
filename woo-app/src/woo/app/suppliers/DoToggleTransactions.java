package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.UnknownSupplierKeyCoreException;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  Input<String> _supplierKey;

  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      boolean state = _receiver.toggleTransactions(_supplierKey.value());
      if(state) {
        _display.addLine(Message.transactionsOn(_supplierKey.value()));
      }
      else {
        _display.addLine(Message.transactionsOff(_supplierKey.value()));
      }
      _display.display();
    } catch (UnknownSupplierKeyCoreException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
  }
}
