package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceLevelException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateProductKeyCoreException;
import woo.exceptions.UnknownServiceLevelCoreException;
import woo.exceptions.UnknownServiceTypeCoreException;
import woo.exceptions.UnknownSupplierKeyCoreException;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

  private Input<String> _id;
  private Input<Integer> _price;
  private Input<Integer> _stockCriticalValue;
  private Input<String> _supKey;
  private Input<String> _serviceType;
  private Input<String> _serviceLevel;

  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _stockCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supKey = _form.addStringInput(Message.requestSupplierKey());
    _serviceType = _form.addStringInput(Message.requestServiceType());
    _serviceLevel = _form.addStringInput(Message.requestServiceLevel());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerProductContainer(_id.value(), _price.value(), _stockCriticalValue.value(), _supKey.value(), _serviceType.value(), _serviceLevel.value());
    } catch (UnknownServiceTypeCoreException e) {
      e.printStackTrace();
    } catch (DuplicateProductKeyCoreException e) {
      throw new DuplicateProductKeyException(e.getKey());
    } catch (UnknownServiceLevelCoreException e) {
      throw new UnknownServiceLevelException(e.getServiceLevel());
    } catch (UnknownSupplierKeyCoreException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
  }
}
