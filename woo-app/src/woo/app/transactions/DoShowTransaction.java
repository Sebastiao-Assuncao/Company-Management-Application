package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.OutputFormatter;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.exceptions.UnknownTransactionKeyCoreException;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<Storefront> {

  private Input<Integer> _id;

  public DoShowTransaction(Storefront receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _id = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    OutputFormatter visitor = new OutputFormatter();
    try {
      var t = _receiver.getTransaction(_id.value());
      _display.addLine(t.accept(visitor));
      _display.display();
    } catch (UnknownTransactionKeyCoreException e) {
      throw new UnknownTransactionKeyException(e.getKey());
    }
  }

}
