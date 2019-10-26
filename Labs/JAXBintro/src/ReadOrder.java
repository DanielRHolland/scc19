import org.netbeans.xml.schema.books.Book;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReadOrder {
    public static void main(String[] args) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            InputStream inStream = null;
            inStream = new FileInputStream("book.xml");
            Book book = (Book) jaxbUnmarshaller.unmarshal(inStream);
            System.out.println(book.getTitle()+", "+book.getAuthor());
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
