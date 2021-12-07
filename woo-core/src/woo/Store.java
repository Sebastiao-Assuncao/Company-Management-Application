package woo;

import woo.exceptions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  private Map<String, Client> _clients = new HashMap<>();
  private Map<String, Product> _products = new HashMap<>();
  private Map<String, Supplier> _suppliers = new HashMap<>();
  private Map<Integer, Order> _orders = new HashMap<>();
  private Map<Integer, Sale> _sales = new HashMap<>();
  private enum _availableServiceTypes { NORMAL, AIR, EXPRESS, PERSONAL}
  private enum _availableServiceLevel { B4, C4, C5, DL}
  private int _globalBalance;
  private int _date;
  private int _transactionCount = 0;

  public Collection<Product> getAllProducts(){
   List<Product> products = new ArrayList<>(_products.values());
   Collections.sort(products);
   return products;
  }

  /**
   *
   * @param productKey
   * @param price
   * @param criticalValue
   * @param supplierKey
   * @param serviceType
   * @param amount
   * @throws DuplicateProductKeyCoreException
   * @throws UnknownSupplierKeyCoreException
   * @throws UnknownServiceTypeCoreException
   */
  public void registerProductBox(String productKey, int price, int criticalValue, String supplierKey, String serviceType, int amount) throws DuplicateProductKeyCoreException, UnknownSupplierKeyCoreException, UnknownServiceTypeCoreException {
    verifyRegisterProduct(productKey, supplierKey);

    boolean contains = false;
    for (var c : _availableServiceTypes.values())
      if (c.name().equals(serviceType))
        contains = true;

    if (!contains)
      throw new UnknownServiceTypeCoreException(serviceType);

    Box box = new Box(productKey, price, criticalValue, supplierKey, serviceType, amount);
    _products.put(productKey, box);

    for(Client c: _clients.values())
      box.toggleClientInterest(c);
  }

  /**
   *
   * @param productKey
   * @param price
   * @param criticalValue
   * @param supplierKey
   * @param serviceType
   * @param serviceLevel
   * @param amount
   * @throws DuplicateProductKeyCoreException
   * @throws UnknownSupplierKeyCoreException
   * @throws UnknownServiceTypeCoreException
   * @throws UnknownServiceLevelCoreException
   */
  public void registerProductContainer(String productKey, int price, int criticalValue, String supplierKey, String serviceType, String serviceLevel, int amount) throws DuplicateProductKeyCoreException, UnknownSupplierKeyCoreException, UnknownServiceTypeCoreException, UnknownServiceLevelCoreException {
    verifyRegisterProduct(productKey, supplierKey);

    boolean containsType = false;
    boolean containsLevel = false;
    for (var c : _availableServiceTypes.values())
      if (c.name().equals(serviceType))
        containsType = true;

    if (!containsType)
      throw new UnknownServiceTypeCoreException(serviceType);

    for (var c : _availableServiceLevel.values())
      if (c.name().equals(serviceLevel))
        containsLevel = true;

    if (!containsLevel)
      throw new UnknownServiceLevelCoreException(serviceLevel);

    Container container = new Container(productKey, price, criticalValue, supplierKey, serviceType, serviceLevel, amount);
    _products.put(productKey, container);

    for(Client c: _clients.values())
      container.toggleClientInterest(c);
  }

  /**
   *
   * @param productKey
   * @param title
   * @param author
   * @param ISBN
   * @param price
   * @param criticalValue
   * @param supplierKey
   * @param amount
   * @throws DuplicateProductKeyCoreException
   * @throws UnknownSupplierKeyCoreException
   */
  public void registerProductBook(String productKey, String title, String author, String ISBN, int price, int criticalValue, String supplierKey, int amount) throws DuplicateProductKeyCoreException, UnknownSupplierKeyCoreException {
    verifyRegisterProduct(productKey, supplierKey);

    Book book = new Book(productKey, price, criticalValue, supplierKey, author, title, ISBN, amount);
    _products.put(productKey, book);

    for(Client c: _clients.values())
      book.toggleClientInterest(c);
  }

  /**
   *
   * @param productKey
   * @param supplierKey
   * @throws DuplicateProductKeyCoreException
   * @throws UnknownSupplierKeyCoreException
   */
  private void verifyRegisterProduct(String productKey, String supplierKey) throws DuplicateProductKeyCoreException, UnknownSupplierKeyCoreException {

    if (containsIgnoreCase(_products.keySet(), productKey) != null)
      throw new DuplicateProductKeyCoreException(productKey);

    if (containsIgnoreCase(_suppliers.keySet(), supplierKey) == null)
      throw new UnknownSupplierKeyCoreException(supplierKey);
  }

  /**
   *
   * @param productKey
   * @param newPrice
   * @throws UnknownProductKeyCoreException
   */
  public void changeProductPrice(String productKey, int newPrice) throws UnknownProductKeyCoreException{
    String pkey = containsIgnoreCase(_products.keySet(), productKey);
    if (pkey == null)
      throw new UnknownProductKeyCoreException(productKey);
    if (newPrice >= 0)
      _products.get(pkey).changePrice(newPrice);
  }

  /**
   *
   * @param clientKey
   * @return
   * @throws UnknownClientKeyCoreException
   */
  public Client getClient(String clientKey) throws UnknownClientKeyCoreException {
    String key = containsIgnoreCase(_clients.keySet(), clientKey);

    if (key == null)
      throw new UnknownClientKeyCoreException(clientKey);

    for(Sale s : _clients.get(key).getPurchases())
      if (!s.isPaid())
        s.applyDiscountsAndFines(_date);

    return _clients.get(key);
  }

  /**
   *
   * @param clientKey
   * @return
   */
  public List<Notification> getClientNotifications(String clientKey) {
    String key = containsIgnoreCase(_clients.keySet(), clientKey);

    List<Notification> notifications = new ArrayList<>(_clients.get(key).getNotificationsList());
    _clients.get(key).clearNotifications();

    return notifications;
  }

  /**
   *
   * @return
   */
  public Collection<Client> getAllClients(){
    List<Client> clients = new ArrayList<>(_clients.values());
    Collections.sort(clients);

    return clients;
  }

  /**
   *
   * @param clientKey
   * @param name
   * @param address
   * @throws DuplicateClientKeyCoreException
   */
  public void registerClient(String clientKey, String name, String address) throws DuplicateClientKeyCoreException {
    if(containsIgnoreCase(_clients.keySet(), clientKey) != null)
      throw new DuplicateClientKeyCoreException(clientKey);

    Client client = new Client(clientKey, name, address);
    _clients.put(clientKey, client);

    for (Product p: _products.values())
      p.toggleClientInterest(client);
  }

  /**
   *
   * @param clientKey
   * @param productKey
   * @return
   * @throws UnknownClientKeyCoreException
   * @throws UnknownProductKeyCoreException
   */
  public boolean toggleProductNotifications(String clientKey, String productKey) throws UnknownClientKeyCoreException, UnknownProductKeyCoreException {
    String product_key = containsIgnoreCase(_products.keySet(), productKey);
    String client_key = containsIgnoreCase(_clients.keySet(), clientKey);

    if (product_key == null)
      throw new UnknownProductKeyCoreException(productKey);

    if (client_key == null)
      throw new UnknownClientKeyCoreException(clientKey);

    return _products.get(product_key).toggleClientInterest(_clients.get(client_key));
  }

  /**
   *
   * @param clientKey
   * @return
   * @throws UnknownClientKeyCoreException
   */
  public List<Sale> getClientTransactions(String clientKey) throws UnknownClientKeyCoreException {
    Client client = getClient(clientKey);
    List<Sale> purchases = new ArrayList<>(client.getPurchases());

    for (Sale s: purchases)
      s.applyDiscountsAndFines(_date);

    return purchases;
  }

  /**
   *
   * @return
   */
  public Collection<Supplier> getSuppliers(){
    List<Supplier> suppliers = new ArrayList<>(_suppliers.values());
    Collections.sort(suppliers);

    return suppliers;
  }

  /**
   *
   * @param supplierKey
   * @param name
   * @param address
   * @throws DuplicateSupplierKeyCoreException
   */
  public void registerSupplier(String supplierKey, String name, String address) throws DuplicateSupplierKeyCoreException {
    if (containsIgnoreCase(_suppliers.keySet(), supplierKey) != null)
      throw new DuplicateSupplierKeyCoreException(supplierKey);

    Supplier supplier = new Supplier(supplierKey, name, address);
    _suppliers.put(supplierKey, supplier);
  }

  /**
   *
   * @param supplierKey
   * @return
   * @throws UnknownSupplierKeyCoreException
   */
  public boolean toggleTransactions(String supplierKey) throws UnknownSupplierKeyCoreException {
    String key = containsIgnoreCase(_suppliers.keySet(), supplierKey);

    if (key == null)
      throw new UnknownSupplierKeyCoreException(supplierKey);

    _suppliers.get(key).toggleState();
    return _suppliers.get(key).getState();
  }

  /**
   *
   * @param supplierKey
   * @return
   * @throws UnknownSupplierKeyCoreException
   */
  public List<Order> getSupplierTransactions(String supplierKey) throws UnknownSupplierKeyCoreException {
    String key = containsIgnoreCase(_suppliers.keySet(), supplierKey);

    if (key == null)
      throw new UnknownSupplierKeyCoreException(supplierKey);

    return _suppliers.get(key).getOrders();
  }

  /**
   *
   * @param transactionKey
   * @return
   * @throws UnknownTransactionKeyCoreException
   */
  public Transaction getTransaction(int transactionKey) throws UnknownTransactionKeyCoreException {
    if (_orders.containsKey(transactionKey))
      return _orders.get(transactionKey);

    else if (_sales.containsKey(transactionKey)) {
      Sale s = _sales.get(transactionKey);
      if (!s.isPaid())
        s.applyDiscountsAndFines(_date);
      return s;
    }

    else
      throw new UnknownTransactionKeyCoreException(transactionKey);
  }

  /**
   *
   * @param clientKey
   * @param paymentDeadline
   * @param productKey
   * @param amount
   * @throws UnknownClientKeyCoreException
   * @throws UnknownProductKeyCoreException
   * @throws UnavailableProductCoreException
   */
  public void registerSale(String clientKey, int paymentDeadline, String productKey, int amount) throws UnknownClientKeyCoreException, UnknownProductKeyCoreException, UnavailableProductCoreException {
    String product_key = containsIgnoreCase(_products.keySet(), productKey);
    String client_key = containsIgnoreCase(_clients.keySet(), clientKey);
    if (product_key == null)
      throw new UnknownProductKeyCoreException(productKey);

    if (client_key == null)
      throw new UnknownClientKeyCoreException(clientKey);

    if (_products.get(product_key).getAmount() < amount)
      throw new UnavailableProductCoreException(productKey, amount, _products.get(product_key).getAmount());

    Sale sale = new Sale(_clients.get(client_key), paymentDeadline, _products.get(product_key), amount,_transactionCount );
    _sales.put(_transactionCount++, sale);
    _clients.get(client_key).addPurchase(sale);
    _products.get(product_key).changeAmount(-amount);
  }

  /**
   *
   * @param order
   * @param supplierKey
   * @param productKey
   * @param amount
   * @throws WrongSupplierCoreException
   * @throws UnknownProductKeyCoreException
   */
  public void addProductToOrder(Order order, String supplierKey, String productKey, int amount) throws WrongSupplierCoreException, UnknownProductKeyCoreException {
    String skey = containsIgnoreCase(_suppliers.keySet(), supplierKey);
    String pkey = containsIgnoreCase(_products.keySet(), productKey);

    if (pkey == null)
      throw new UnknownProductKeyCoreException(productKey);

    if (!_products.get(pkey).getSupplierKey().equals(skey))
      throw new WrongSupplierCoreException(supplierKey, productKey);

    order.getProducts().put(_products.get(pkey), amount);
  }

  /**
   *
   * @param supplierKey
   * @return
   * @throws UnknownSupplierKeyCoreException
   * @throws UnauthorizedSupplierCoreException
   */
  public Order registerOrder(String supplierKey) throws UnknownSupplierKeyCoreException, UnauthorizedSupplierCoreException {
    String skey = containsIgnoreCase(_suppliers.keySet(), supplierKey);

    if (skey == null)
      throw new UnknownSupplierKeyCoreException(supplierKey);

    if (!_suppliers.get(skey).getState())
      throw new UnauthorizedSupplierCoreException(supplierKey);

    Order order = new Order(skey, _date, _transactionCount);
    _orders.put(_transactionCount++, order);
    _suppliers.get(skey).addOrder(order);

    return order;
  }

  /**
   *
   * @param o
   */
  public void removeOrder(Order o) {
    _orders.remove(o);
    _transactionCount--;
    _suppliers.get(o.getSupplierKey()).getOrders().remove(o);
  }

  /**
   *
   * @param o
   */
  public void finishOrder(Order o) {
    o.calculateOrderCost();

    for (Map.Entry<Product, Integer> p: o.getProducts().entrySet())
      _products.get(p.getKey().getKey()).changeAmount(p.getValue());

    updateGlobalBalance(o.getTotalCost());
  }

  /**
   *
   * @param difference
   */
  private void updateGlobalBalance(int difference) {
    _globalBalance += difference;
  }

  /**
   *
   * @param saleKey
   * @throws UnknownTransactionKeyCoreException
   */
  public void paySale(int saleKey) throws UnknownTransactionKeyCoreException {
    if (_orders.containsKey(saleKey))
      return;

    if (!_sales.containsKey(saleKey))
      throw new UnknownTransactionKeyCoreException(saleKey);

    Sale sale = _sales.get(saleKey);

    if (!sale.isPaid()){
      sale.paySale(_date);
      updateGlobalBalance((int) Math.round(sale.getCurrentCost()));
    }
  }

  /**
   *
   * @param topPrice
   * @return
   */
  public List<Product> getProductsUnderTopPrice (int topPrice){
    Collection<Product> collection = _products.values();
    List<Product> productList = new ArrayList<>();

    for(Product prod : collection)
      if(prod.getPrice() < topPrice)
        productList.add(prod);

    return productList;
  }

  /**
   *
   * @param clientKey
   * @return
   * @throws UnknownClientKeyCoreException
   */
  public List<Sale> getPaidPurchasesByClient(String clientKey) throws UnknownClientKeyCoreException {
    String key = containsIgnoreCase(_clients.keySet(), clientKey);

    if (key == null)
      throw new UnknownClientKeyCoreException(clientKey);

    List<Sale> paidPurchases = new ArrayList<>();

    for(Sale sale : _clients.get(key).getPurchases())
      if(sale.isPaid())
        paidPurchases.add(sale);

    return paidPurchases;
  }

  /**
   *
   * @return: current date
   */
  public int getDate(){
    return _date;
  }

  /**
   *
   * @param daysToAdvance
   */
  public void advanceDate(int daysToAdvance) {
    _date += daysToAdvance;
  }

  /**
   *
   * @return: store's balance: paid sales - orders
   */
  public int getGlobalBalance(){
    return _globalBalance;
  }

  /**
   *
   * @return: stores's balance: sales - orders
   */
  public int getAccountingBalance(){
    int total = _globalBalance;

    for (Sale s : _sales.values())
      if(!s.isPaid()) {
        s.applyDiscountsAndFines(_date);
        total += s.getCurrentCost();
      }
    return total;
  }

  /**
   *
   * @param keys
   * @param soughtFor
   * @return
   */
  private String containsIgnoreCase(Set<String> keys, String soughtFor) {
    List<String> list = new ArrayList<>(keys);
    for (String current : list)
      if (current.equalsIgnoreCase(soughtFor))
        return current;

    return null;
  }

  /**
   *
   * @param txtfile
   * @throws IOException
   * @throws BadEntryException
   */
  public void importFile(String txtfile) throws IOException, BadEntryException{
    BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    String s;

    while ((s = reader.readLine()) != null) {
      String line = new String(s.getBytes(), "UTF-8");
      if (line.charAt(0) == '#')
        continue;
      String[] split = line.split("\\|");
      if (split[0].equals("BOX"))
        try {
          registerProductBox(split[1],Integer.parseInt(split[4]),Integer.parseInt(split[5]),split[3],split[2],Integer.parseInt(split[6]));
        } catch (DuplicateProductKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        } catch (UnknownSupplierKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        } catch (UnknownServiceTypeCoreException e) {
          throw new BadEntryException(e.getServiceType());
        }

      else if (split[0].equals("CONTAINER"))
        try {
          registerProductContainer(split[1], Integer.parseInt(split[5]), Integer.parseInt(split[6]), split[4], split[2], split[3], Integer.parseInt(split[7]));
        } catch (DuplicateProductKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        } catch (UnknownSupplierKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        } catch (UnknownServiceLevelCoreException e) {
          throw new BadEntryException(e.getServiceLevel());
        } catch (UnknownServiceTypeCoreException e) {
          throw new BadEntryException(e.getServiceType());
        }

      else if (split[0].equals("BOOK"))
        try {
          registerProductBook(split[1], split[2], split[3], split[4], Integer.parseInt(split[6]), Integer.parseInt(split[7]), split[5], Integer.parseInt(split[8]));
        } catch (DuplicateProductKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        } catch (UnknownSupplierKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        }

      else if (split[0].equals("CLIENT"))
        try {
          registerClient(split[1], split[2], split[3]);

        } catch (DuplicateClientKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        }

      else if (split[0].equals("SUPPLIER"))
        try {
          registerSupplier(split[1], split[2], split[3]);
        } catch (DuplicateSupplierKeyCoreException e) {
          throw new BadEntryException(e.getKey());
        }
    }
    reader.close();
  }
}