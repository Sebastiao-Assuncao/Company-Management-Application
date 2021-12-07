package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateProductKeyCoreException;
import woo.exceptions.UnknownSupplierKeyCoreException;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  private Input<String> _id;
  private Input<String> _bookTitle;
  private Input<String> _bookAuthor;
  private Input<String> _ISBN;
  private Input<Integer> _price;
  private Input<Integer> _stockCriticalLevel;
  private Input<String> _supKey;

  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _bookTitle = _form.addStringInput(Message.requestBookTitle());
    _bookAuthor = _form.addStringInput(Message.requestBookAuthor());
    _ISBN = _form.addStringInput(Message.requestISBN());
    _price = _form.addIntegerInput(Message.requestPrice());
    _stockCriticalLevel = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supKey = _form.addStringInput(Message.requestSupplierKey());

  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerProductBook(_id.value(), _bookTitle.value(), _bookAuthor.value(), _ISBN.value(), _price.value(), _stockCriticalLevel.value(), _supKey.value());
    } catch (UnknownSupplierKeyCoreException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    } catch (DuplicateProductKeyCoreException e) {
      throw new DuplicateProductKeyException(e.getKey());
    }
  }
}
