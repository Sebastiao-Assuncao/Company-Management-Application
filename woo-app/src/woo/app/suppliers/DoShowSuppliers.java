package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;
import woo.app.OutputFormatter;

/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<Storefront> {


  public DoShowSuppliers(Storefront receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
  }

  @Override
  public void execute() throws DialogException {
    for (var s : _receiver.getSuppliers()) {
      _display.addLine(s.accept(new OutputFormatter()));
    }
    _display.display();
  }
}
