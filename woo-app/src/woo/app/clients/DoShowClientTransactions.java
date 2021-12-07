package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes
import woo.app.OutputFormatter;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.UnknownClientKeyCoreException;

/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  private Input<String> _id;

  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _id = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      for (var t :_receiver.getClientTransactions(_id.value())){
        _display.addLine(t.accept(new OutputFormatter()));
      }
      _display.display();

    } catch (UnknownClientKeyCoreException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }

}
