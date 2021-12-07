package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.OutputFormatter;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.UnknownClientKeyCoreException;

/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  private Input<String> _id;

  public DoLookupPaymentsByClient(Storefront storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _id = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      for(var t : _receiver.getPaidPurchasesByClient(_id.value())) {
        _display.addLine(t.accept(new OutputFormatter()));
      }
    } catch (UnknownClientKeyCoreException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
    _display.display();
  }

}
