package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnavailableProductException;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.UnavailableProductCoreException;
import woo.exceptions.UnknownClientKeyCoreException;
import woo.exceptions.UnknownProductKeyCoreException;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

  private Input<String> _cKey;
  private Input<Integer> _limitPaymentDate;
  private Input<String> _pKey;
  private Input<Integer> _amount;

  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _cKey = _form.addStringInput(Message.requestClientKey());
    _limitPaymentDate = _form.addIntegerInput(Message.requestPaymentDeadline());
    _pKey = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerSale(_cKey.value(), _limitPaymentDate.value(), _pKey.value(), _amount.value());
    } catch (UnavailableProductCoreException e) {
      throw new UnavailableProductException(e.getKey(), e.getRequested(), e.getAvailable());
    } catch (UnknownClientKeyCoreException e) {
      throw new UnknownClientKeyException(e.getKey());
    } catch (UnknownProductKeyCoreException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
  }

}
