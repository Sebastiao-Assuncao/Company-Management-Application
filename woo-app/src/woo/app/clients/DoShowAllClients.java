package woo.app.clients;

import pt.tecnico.po.ui.DialogException;
import woo.Storefront;
import pt.tecnico.po.ui.Command;
import woo.app.OutputFormatter;

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<Storefront> {

  public DoShowAllClients(Storefront storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);
  }

  @Override
  public void execute() throws DialogException {
    for (var c : _receiver.getAllClients())
      _display.addLine(c.accept(new OutputFormatter()));
    _display.display();
  }
}
