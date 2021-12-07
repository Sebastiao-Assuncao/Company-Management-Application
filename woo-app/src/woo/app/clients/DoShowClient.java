package woo.app.clients;

import woo.Storefront;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import woo.app.OutputFormatter;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.UnknownClientKeyCoreException;

/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {

  private Input<String> _id;

  public DoShowClient(Storefront storefront) {
    super(Label.SHOW_CLIENT, storefront);
    _id = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      OutputFormatter visitor = new OutputFormatter();
      _display.addLine(_receiver.getClient(_id.value()).accept(visitor));
      for (var n: _receiver.getClientNotifications(_id.value()))
        _display.addLine(n.accept(visitor));
      _display.display();
    } catch (UnknownClientKeyCoreException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }

}
