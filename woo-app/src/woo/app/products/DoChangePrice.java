package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.UnknownProductKeyCoreException;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  private Input<String> _id;
  private Input<Integer> _price;
  
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.changeProductPrice(_id.value(), _price.value());
    } catch (UnknownProductKeyCoreException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
  }
}
