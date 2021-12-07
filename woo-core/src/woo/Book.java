package woo;

public class Book extends Product{

    private String _author;
    private String _title;
    private String _ISBN;

    /**
     *  @param key
     * @param price
     * @param criticalValue
     * @param supplierKey
     * @param author
     * @param title
     * @param ISBN
     */
    public Book(String key, int price, int criticalValue, String supplierKey, String author, String title, String ISBN, int amount) {
        super(key, price, criticalValue, supplierKey, amount, 3);
        _author = author;
        _title = title;
        _ISBN = ISBN;
    }

    public String getAuthor() {
        return _author;
    }

    public String getTitle() {
        return _title;
    }

    public String getISBN() {
        return _ISBN;
    }

    @Override
    public String accept(Visitor v) {
        return v.visitBook(this);
    }
    
}