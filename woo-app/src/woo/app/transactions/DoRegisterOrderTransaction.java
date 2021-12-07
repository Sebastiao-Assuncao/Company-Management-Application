package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.WrongSupplierException;
import woo.exceptions.UnauthorizedSupplierCoreException;
import woo.exceptions.UnknownProductKeyCoreException;
import woo.exceptions.UnknownSupplierKeyCoreException;
import woo.exceptions.WrongSupplierCoreException;
/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  private Input<String> _supKey;
  private Input<String> _pKey;
  private Input<Integer> _amount;
  private Input<Boolean> _more;

  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
  }

  @Override
  public final void execute() throws DialogException {
    _form.clear();
    _supKey = _form.addStringInput(Message.requestSupplierKey());
    _pKey = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
    _more = _form.addBooleanInput(Message.requestMore());
    _form.parse();
    try {
      var o = _receiver.registerOrder(_supKey.value());
      try {
        _receiver.addProductToOrder(o, _supKey.value(), _pKey.value(), _amount.value());
        while (_more.value().equals(true)) {
          _form.clear();
          _pKey = _form.addStringInput(Message.requestProductKey());
          _amount = _form.addIntegerInput(Message.requestAmount());
          _more = _form.addBooleanInput(Message.requestMore());
          _form.parse();
          _receiver.addProductToOrder(o, _supKey.value(), _pKey.value(), _amount.value());
        }
        _receiver.finishOrder(o);
      } catch (WrongSupplierCoreException e) {
        _receiver.removeOrder(o);
        throw new WrongSupplierException(e.getSupplierKey(), e.getProductKey());
      } catch (UnknownProductKeyCoreException e) {
        _receiver.removeOrder(o);
        throw new UnknownProductKeyException(e.getKey());
      }
    } catch (UnknownSupplierKeyCoreException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    } catch (UnauthorizedSupplierCoreException e) {
      throw new UnauthorizedSupplierException(e.getKey());
    }
  }
}
