package students.logic;

//import java.io.FileNotFoundException;
//import java.io.PrintStream;
//import java.io.UnsupportedEncodingException;
//import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Андрій on 27.05.2017.
 */
public class ManagementSystem {
    private List<Group> groups;
    private Collection<Student> students;

    //Статична змінна для шаблона SingleTone
    private static ManagementSystem instance;

    //Закритий конструктор
    private ManagementSystem() {
        loadGroups();
        loadStudents();
    }

    //Метод getInstance - перевіряє, чи ініціалізована статична змінна (в випадку необхідності робить це) і повертає її
    public static synchronized ManagementSystem getInstance(){
        if (instance == null){
            instance = new ManagementSystem();
        }
        return instance;
    }

    //Метод, який викликається при запуску класу
    public static void main(String[] args) {
        //Подивитися в інтернеті
        try {
            System.setOut(new PrintStream("out.txt"));
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            return;
        }
        //
        ManagementSystem ms = ManagementSystem.getInstance();

        //Перегляд повного списку груп
        printString("Повний список груп");
        printString("******************");
        List<Group> allGroups = ms.getGroups();
        for (Group gi : allGroups) {
            printString(gi);
        }
        printString();

        //Перегляд повного списку студентів
        printString("Повний список студентів");
        printString("***********************");
        Collection<Student> allStudents = ms.getAllStudents();
        for (Student si : allStudents) {
            printString(si);
        }
        printString();

        //Перегляд списку студентів по групам
        printString("Список студентів по групам");
        printString("**************************");
        List<Group> groups = ms.getGroups();
        //Перевіряємо всі групи
        for (Group gi : groups) {
            printString("---> Група: " + gi.getNameGroup());
            //Отримуємо список студентів для конкретної групи
            Collection<Student> students = ms.getStudentsFromGroup(gi, 2006);
            for (Student si : students) {
                printString(si);
            }
        }
        printString();

        //Створимо нового студента і додамо його в список
        Student student1 = new Student();
        student1.setStudentId(5);
        student1.setFirstName("Andriy");
        student1.setPatronymic("Romanovych");
        student1.setSurName("Kovalchuk");
        student1.setSex('M');
        Calendar c = Calendar.getInstance();
        c.set(1997, 8, 23);
        student1.setDateOfBirth(c.getTime());
        student1.setGroupId(1);
        student1.setEducationYear(2006);
        printString("Додавання студента: " + student1);
        printString("*********************");
        ms.insertStudent(student1);
        printString("--->> Повний список студентів після додавання");
        allStudents = ms.getAllStudents();
        for (Student si : allStudents) {
            printString(si);
        }
        printString();

        //Змінимо дані про студента - Перебежкин стане Новоперебедкиным
        //Все решта залишається таким же - створюємо студента з таким же ID
        student1 = new Student();
        student1.setStudentId(5);
        student1.setFirstName("Igor");
        student1.setPatronymic("Bogdanovych");
        student1.setSurName("Holodyuk");
        student1.setSex('M');
        c = Calendar.getInstance();
        c.set(1991, 8, 31);
        student1.setDateOfBirth(c.getTime());
        student1.setGroupId(1);
        student1.setEducationYear(2006);
        printString("Редагування даних студента: " + student1);
        printString("****************************");
        ms.updateStudent(student1);
        printString("--->> Повний список студентів після редагування");
        allStudents = ms.getAllStudents();
        for (Student si : allStudents) {
            printString(si);
        }
        printString();

        //Видалення студента
        printString("Видалення студента: " + student1);
        printString("*********************");
        ms.deleteStudent(student1);
        printString("--->> Повний список студентів після видалення");
        allStudents = ms.getAllStudents();
        for (Student si : allStudents) {
            printString(si);
        }
        printString();

        //Переведення студентів в іншу групу
        Group g1 = groups.get(0);
        Group g2 = groups.get(1);
        printString("Переведення студентів з 1-ої групи в 2-гу групу");
        printString("***********************************************");
        ms.moveStudentsToGroup(g1, 2006, g2, 2007);
        printString("--->> Повний список студентів після переведення");
        allStudents = ms.getAllStudents();
        for (Student si: allStudents){
            printString(si);
        }
        printString();

        //Видалення студентів з групи
        printString("Видалення студентів з групи" + g2 + " в 2006 році");
        printString("*************************");
        ms.removeStudentsFromGroup(g2, 2006);
        printString("--->> Повний список студентів після видалення");
        allStudents = ms.getAllStudents();
        for (Student si : allStudents) {
            printString(si);
        }
        printString();
    }
    //Метод створює дві групи і поміщає їх в колекцію для груп
    public void loadGroups() {
        //Перевірка чи створений список
        if (groups == null) {
            groups = new ArrayList<Group>();
        } else {
            groups.clear();
        }
        Group group = null;
        group = new Group();
        group.setGroupId(1);
        group.setNameGroup("Первая");
        group.setCurator("Доктор Борменталь");
        group.setSpeciality("Создание собачек из человеков");
        groups.add(group);

        group = new Group();
        group.setGroupId(2);
        group.setNameGroup("Вторая");
        group.setCurator("Профессор Преображенский");
        group.setSpeciality("Создание человеков из собачек");
        groups.add(group);
    }

    //Метод створює декількох студентів і поміщає їх в колекцію
    public void loadStudents() {
        if (students == null) {
            students = new TreeSet<Student>();
        }else {
            students.clear();
        }

        Student student = null;
        Calendar c = Calendar.getInstance();

        //Перша група
        student = new Student();
        student.setStudentId(3);
        student.setFirstName("Ivan");
        student.setPatronymic("Volodymyrovych");
        student.setSurName("Glushko");
        student.setSex('M');
        c.set(1991, 3, 12);
        student.setDateOfBirth(c.getTime());
        student.setEducationYear(2006);
        student.setGroupId(1);
        students.add(student);

        student = new Student();
        student.setStudentId(4);
        student.setFirstName("Svitlana");
        student.setPatronymic("Mykolayivna");
        student.setSurName("Blyznyuk");
        student.setSex('W');
        c.set(1991, 7, 19);
        student.setDateOfBirth(c.getTime());
        student.setEducationYear(2006);
        student.setGroupId(1);
        students.add(student);

        //Друга група
        student = new Student();
        student.setStudentId(1);
        student.setFirstName("Mykola");
        student.setPatronymic("Sergiyovych");
        student.setSurName("Stepaniv");
        student.setSex('M');
        c.set(1990, 3, 20);
        student.setDateOfBirth(c.getTime());
        student.setGroupId(2);
        student.setEducationYear(2006);
        students.add(student);

        student = new Student();
        student.setStudentId(2);
        student.setFirstName("Viktoriya");
        student.setPatronymic("Romanivna");
        student.setSurName("Sologub");
        student.setSex('W');
        c.set(1990, 6, 10);
        student.setDateOfBirth(c.getTime());
        student.setGroupId(2);
        student.setEducationYear(2006);
        students.add(student);
    }

    //Отримуємо список груп
    public List<Group> getGroups() {
        return groups;
    }

    //Отримуємо список всіх студентів
    public Collection<Student> getAllStudents() {
        return students;
    }

    //Отримуємо список студентів для певної групи
    public Collection<Student> getStudentsFromGroup (Group group, int year){
        Collection<Student> l = new TreeSet<Student>();
        for (Student si: students){
            if (si.getGroupId() == group.getGroupId() && si.getEducationYear() == year){
                l.add(si);
            }
        }
        return l;
    }

    //Переведення студентів з однієї групи з певним роком навчання в іншу з іншим роком навчання
    public void moveStudentsToGroup (Group oldGroup, int oldYear, Group newGroup, int newYear){
        for (Student si: students){
            if (si.getGroupId() == oldGroup.getGroupId() && si.getEducationYear() == oldYear){
                si.setGroupId(newGroup.getGroupId());
                si.setEducationYear(newYear);
            }
        }
    }

    //Видалення усіх студентів з групи
    public void removeStudentsFromGroup(Group group, int year){
        Collection<Student> tmp = new TreeSet<Student>();
        for (Student si: students){
            if (si.getGroupId() != group.getGroupId() || si.getEducationYear() != year){
                tmp.add(si);
            }
        }
        students = tmp;
    }

    //Додавання студента
    public void insertStudent(Student student){
        //Додаємо студента в колекцію
        students.add(student);
    }

    //Оновлення данних про студента
    public void updateStudent(Student student){
        //Шукаємо потрібного студента по його ID та заміняємо поля
        Student updStudent = null;
        for (Student si: students){
            //Знаходимо студента по ID
            if (si.getStudentId() == student.getStudentId()){
                updStudent = si;
                break;
            }
        }
        updStudent.setFirstName(student.getFirstName());
        updStudent.setPatronymic(student.getPatronymic());
        updStudent.setSurName(student.getSurName());
        updStudent.setSex(student.getSex());
        updStudent.setDateOfBirth(student.getDateOfBirth());
        updStudent.setGroupId(student.getGroupId());
        updStudent.setEducationYear(student.getEducationYear());
    }

    //Видалення студента
    public void deleteStudent(Student student){
        //Пошук потрібного студента по його ID та видалення
        Student delStudent = null;
        for(Student si: students){
            if (si.getStudentId()== student.getStudentId()){
                delStudent = si;
                break;
            }
        }
        students.remove(delStudent);
    }

    public static void printString(Object s){
        try{
            System.out.println(new String(s.toString().getBytes("windows-1251"), "windows-1252"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
    public static void printString() {
        System.out.println();
    }
}
