package woo;

import java.io.*;
import java.util.*;
import woo.exceptions.*;

/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename == null) {
      throw new MissingFileAssociationException();
    }
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename));
    out.writeObject(_store);
    out.close();

  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
      _store = (Store) in.readObject();
      _filename = filename;
      in.close();
    }
    catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   *
   * @return: current opened file name
   */
  public String getFileName() {
    return _filename;
  }

  /**
   *
   * @param filename
   * @throws ImportFileException
   */

  public void importFile(String filename) throws ImportFileException {
    try {
      _store.importFile(filename);
    } catch (IOException e) {
      throw new ImportFileException(filename);
    } catch (BadEntryException e) {
      throw new ImportFileException(e);
    }
  }


  public Collection<Product> getAllProducts(){
    return _store.getAllProducts();
  }

  public void registerProductBox(String productKey, int price, int criticalValue, String supplierKey, String serviceType) throws DuplicateProductKeyCoreException, UnknownSupplierKeyCoreException, UnknownServiceTypeCoreException {
      _store.registerProductBox(productKey, price, criticalValue, supplierKey, serviceType, 0);
  }

  public void registerProductContainer(String productKey, int price, int criticalValue, String supplierKey, String serviceType, String serviceLevel) throws UnknownServiceTypeCoreException, DuplicateProductKeyCoreException, UnknownServiceLevelCoreException, UnknownSupplierKeyCoreException {
     _store.registerProductContainer(productKey, price, criticalValue, supplierKey, serviceType, serviceLevel, 0);
  }

  public void registerProductBook(String productKey, String title, String author, String ISBN, int price, int criticalValue, String supplierKey) throws UnknownSupplierKeyCoreException, DuplicateProductKeyCoreException {
      _store.registerProductBook(productKey, title, author, ISBN, price, criticalValue, supplierKey, 0);

  }

  public void changeProductPrice(String productKey, int newPrice) throws UnknownProductKeyCoreException { _store.changeProductPrice(productKey, newPrice);}

  public Client getClient(String clientKey) throws UnknownClientKeyCoreException { return _store.getClient(clientKey);}

  public List<Notification> getClientNotifications(String clientKey) { return _store.getClientNotifications(clientKey);}

  public Collection<Client> getAllClients(){
    return _store.getAllClients();
  }

  public void registerClient(String clientKey, String name, String address) throws DuplicateClientKeyCoreException {
      _store.registerClient(clientKey, name, address);
  }

  public boolean toggleProductNotifications(String clientKey, String productKey) throws UnknownClientKeyCoreException, UnknownProductKeyCoreException {
    return _store.toggleProductNotifications(clientKey, productKey);
  }

  public List<Sale> getClientTransactions(String clientKey) throws UnknownClientKeyCoreException { return _store.getClientTransactions(clientKey);}

  public Collection<Supplier> getSuppliers(){
    return _store.getSuppliers();
  }

  public void registerSupplier(String supplierKey, String name, String address) throws DuplicateSupplierKeyCoreException {
    _store.registerSupplier(supplierKey, name, address);
  }

  public boolean toggleTransactions(String supplierKey) throws UnknownSupplierKeyCoreException { return _store.toggleTransactions(supplierKey);}

  public List<Order> getSupplierTransactions(String supplierKey) throws UnknownSupplierKeyCoreException { return _store.getSupplierTransactions(supplierKey);}

  public Transaction getTransaction(int transactionKey) throws UnknownTransactionKeyCoreException { return _store.getTransaction(transactionKey);}

  public void registerSale(String clientKey, int paymentDeadline, String productKey, int amount) throws UnavailableProductCoreException, UnknownClientKeyCoreException, UnknownProductKeyCoreException {
      _store.registerSale(clientKey, paymentDeadline, productKey, amount);
  }

  public void addProductToOrder(Order order, String supplierKey, String productKey, int amount) throws WrongSupplierCoreException, UnknownProductKeyCoreException {
    _store.addProductToOrder(order, supplierKey, productKey, amount);
  }

  public Order registerOrder(String supplierKey) throws UnknownSupplierKeyCoreException, UnauthorizedSupplierCoreException { return _store.registerOrder(supplierKey);}

  public void removeOrder(Order o) {
    _store.removeOrder(o);
  }

  public void finishOrder(Order o) {
    _store.finishOrder(o);
  }

  public void paySale(int saleKey) throws UnknownTransactionKeyCoreException { _store.paySale(saleKey); }

  public List<Product> getProductsUnderTopPrice (int topPrice){
    return _store.getProductsUnderTopPrice(topPrice);
  }

  public List<Sale> getPaidPurchasesByClient(String clientKey) throws UnknownClientKeyCoreException { return _store.getPaidPurchasesByClient(clientKey);}

  public int getDate(){ return _store.getDate(); }

  public void advanceDate(int daysToAdvance){
    _store.advanceDate(daysToAdvance);
  }

  public int getGlobalBalance(){
    return _store.getGlobalBalance();
  }

  public int getAccountingBalance(){
    return _store.getAccountingBalance();
  }

}
