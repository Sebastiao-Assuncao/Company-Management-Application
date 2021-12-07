package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;
import woo.app.exceptions.DuplicateClientKeyException;
import woo.exceptions.DuplicateClientKeyCoreException;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront>{

  private Input<String> _id;
  private Input<String> _address;
  private Input<String> _name;

  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _id = _form.addStringInput(Message.requestClientKey());
    _name = _form.addStringInput(Message.requestClientName());
    _address = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerClient(_id.value(), _name.value(), _address.value());
    } catch (DuplicateClientKeyCoreException e) {
      throw new DuplicateClientKeyException(e.getKey());
    }
  }

}
