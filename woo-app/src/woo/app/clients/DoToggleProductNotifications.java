package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.UnknownClientKeyCoreException;
import woo.exceptions.UnknownProductKeyCoreException;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  private Input<String> _clientId;
  private Input<String> _productId;

  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _clientId = _form.addStringInput(Message.requestClientKey());
    _productId = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      if(_receiver.toggleProductNotifications(_clientId.value(), _productId.value())) {
        _display.addLine(Message.notificationsOn(_clientId.value(), _productId.value()));
      }
      else {
        _display.addLine(Message.notificationsOff(_clientId.value(), _productId.value()));
      }
      _display.display();
    } catch (UnknownClientKeyCoreException e) {
      throw new UnknownClientKeyException(e.getKey());
    } catch (UnknownProductKeyCoreException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
  }

}
