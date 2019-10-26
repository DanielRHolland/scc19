/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.netbeans.xml.schema.books.Book;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author taha-m
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        var book = new Book();
        book.setAuthor("Jim");
        book.setDescription("Woop woop");
        book.setISBN(Integer.MAX_VALUE);
        book.setPrice(Float.MAX_VALUE);
        book.setPublisher("Jim's Publisher");
        book.setTitle("Jim's Book's Title");

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(book.getClass().getPackage().getName());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(book, System.out);

            OutputStream os = new FileOutputStream( "book.xml" );
            marshaller.marshal(book, os);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
