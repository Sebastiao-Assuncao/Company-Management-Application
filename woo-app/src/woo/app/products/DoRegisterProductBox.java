package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateProductKeyCoreException;
import woo.exceptions.UnknownServiceTypeCoreException;
import woo.exceptions.UnknownSupplierKeyCoreException;

/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {

  private Input<String> _id;
  private Input<Integer> _price;
  private Input<Integer> _stockCriticalValue;
  private Input<String> _supKey;
  private Input<String> _serviceType;

  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _stockCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supKey = _form.addStringInput(Message.requestSupplierKey());
    _serviceType = _form.addStringInput(Message.requestServiceType());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerProductBox(_id.value(), _price.value(), _stockCriticalValue.value(), _supKey.value(), _serviceType.value());
    } catch (DuplicateProductKeyCoreException e) {
      throw new DuplicateProductKeyException(e.getKey());
    } catch (UnknownSupplierKeyCoreException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    } catch (UnknownServiceTypeCoreException e) {
      throw new UnknownServiceTypeException(e.getServiceType());
    }
  }
}
