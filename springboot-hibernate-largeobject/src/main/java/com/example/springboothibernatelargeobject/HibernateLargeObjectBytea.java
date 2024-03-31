package com.example.springboothibernatelargeobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateLargeObjectBytea {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Save photo into database
        addImage(session);

        // Read the image and save it to file
        saveImage(session);

        session.close();

    }

    private static void addImage (Session session) {
    
        try(FileInputStream fis = new FileInputStream("target/classes/myimage.png"))
        {
            byte[] imageDataBytes = new byte[fis.available()];
            fis.read(imageDataBytes);
            ImageJDBCByteArray image1 = new ImageJDBCByteArray();
            image1.setImageName("myimage.png");
            image1.setImageData(imageDataBytes);
            session.beginTransaction();
            session.persist(image1);
            session.getTransaction().commit();
            System.out.println("Image inserted into database");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveImage(Session session) {
        
        ImageJDBCByteArray image1 = session.createQuery("FROM ImageJDBCByteArray WHERE imageName = :name",ImageJDBCByteArray.class)
        .setParameter("name", "myimage.png").getSingleResult();
        try(FileOutputStream fos = new FileOutputStream("target/classes/myimage-out.png")) {
            fos.write(image1.getImageData());
            System.out.println("Image written to 'target/classes/myimage-out.png'");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
