package io.vitorgsevero.project.javaclient;

import io.vitorgsevero.project.model.Student;

public class JavaSpringClientTest {
    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("John Wick 2");
        studentPost.setEmail("john@gmail.com");

       // studentPost.setId(29L);
        JavaClientDAO dao = new JavaClientDAO();
        System.out.println(dao.findById(111));
        //     System.out.println(dao.save(studentPost));
        //     System.out.println(dao.findById(8));
        //    System.out.println(dao.listAll());
       // dao.update(studentPost);
        dao.delete(29);

    }
}
