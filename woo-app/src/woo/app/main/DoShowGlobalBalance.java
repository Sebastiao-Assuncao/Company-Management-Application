package woo.app.main;

import pt.tecnico.po.ui.Command;
import woo.Storefront;

/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<Storefront> {

  public DoShowGlobalBalance(Storefront receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  @Override
  public final void execute() {
    _display.addLine(Message.currentBalance(_receiver.getGlobalBalance(), _receiver.getAccountingBalance()));
    _display.display();
  }
}
