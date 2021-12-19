package com;

public class Kindle {

    public Kindle() {
        //write your code here
    }

    public String readBook(Book book) throws Exception {
        EBookReaderFactory factory = new EBookReaderFactory();
        EBookReader reader = factory.createReader(book);

        return reader.readBook();
    }

    public void downloadBook(Book b) {
        //write your code here
    }

    public void uploadBook(Book b) {
        //write your code here
    }

    public void deleteBook(Book b) {
        //write your code here
    }
}

enum Format {
    EPUB("epub"), PDF("pdf"), MOBI("mobi");

    private String content;

    Format(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

class Book {
    private Format format;

    public Book(Format format) {
        this.format = format;
    }

    public Format getFormat() {
        return format;
    }
}

abstract class EBookReader {

    protected Book book;

    public EBookReader(Book b){
        this.book = b;
    }

    public abstract String readBook();
    public abstract String displayReaderType();
}

class EBookReaderFactory {

    public EBookReader createReader(Book b) {
        Format format = b.getFormat();
        switch (format) {
            case EPUB:
                return new EpubReader(b);
            case PDF:
                return new PdfReader(b);
            default:
                return new MobiReader(b);
        }
    }
}

class EpubReader extends EBookReader{

    public EpubReader(Book b) {
        super(b);
    }

    @Override
    public String readBook() {
        String content = book.getFormat().getContent();
        return displayReaderType() + ", book content is: " + content;
    }

    @Override
    public String displayReaderType() {
        return "Using EPUB reader";
    }
}

class MobiReader extends EBookReader {

    public MobiReader(Book b) {
        super(b);
    }

    @Override
    public String readBook() {
        String content = book.getFormat().getContent();
        return displayReaderType() + ", book content is: " + content;
    }

    @Override
    public String displayReaderType() {
        return "Using MOBI reader";
    }

}
class PdfReader extends EBookReader{

    public PdfReader(Book b) {
        super(b);
    }

    @Override
    public String readBook() {
        String content = book.getFormat().getContent();
        return displayReaderType() + ", book content is: " + content;
    }

    @Override
    public String displayReaderType() {
        return "Using PDF reader";
    }
}
