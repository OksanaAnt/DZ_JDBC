package src;

import db.MySqlExecutor;
import src.data.Curator;
import src.data.Group;
import src.data.Student;
import src.tables.AbsTable;
import src.tables.CuratorTable;
import src.tables.GroupTable;
import src.tables.StudentTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import db.IDBExecutor;

public class Runner {

    public static void main(String... args) {
        IDBExecutor dbExecutor = new MySqlExecutor();

        try {
//            Создание и заполнение таблицы "Студенты"
            AbsTable studentTable = new StudentTable(dbExecutor);

            List<String> columnsStudentTable = new ArrayList<>();
            columnsStudentTable.add("id int primary key");
            columnsStudentTable.add("fio varchar(100)");
            columnsStudentTable.add("sex varchar(1)");
            columnsStudentTable.add("id_group int");

            List<Student> students = new ArrayList<>();
            students.add(new Student(1, "Литвинов Максим Львович", "M", 1));
            students.add(new Student(2, "Кузнецова Варвара Александровна", "F", 2));
            students.add(new Student(3, "Симонов Семён Юрьевич", "M", 3));
            students.add(new Student(4, "Алексеев Фёдор Савельевич", "M", 1));
            students.add(new Student(5, "Кириллова Арина Владиславовна", "F", 2));
            students.add(new Student(6, "Игнатов Роман Алексеевич", "M", 3));
            students.add(new Student(7, "Гущина Мария Степановна", "F", 1));
            students.add(new Student(8, "Алексеев Леонид Егорович", "M", 2));
            students.add(new Student(9, "Крылов Георгий Михайлович", "M", 3));
            students.add(new Student(10, "Золотова Варвара Данииловна", "F", 1));
            students.add(new Student(11, "Курочкина Арина Матвеевна", "F", 2));
            students.add(new Student(12, "Ситников Михаил Артёмович", "M", 3));
            students.add(new Student(13, "Егоров Владимир Артурович", "M", 1));
            students.add(new Student(14, "Васильев Константин Макарович", "M", 2));
            students.add(new Student(15, "Попова Лея Мироновна", "F", 3));

            studentTable.create(columnsStudentTable);

            for (Student st : students) {
                studentTable.insert(st);
            }

//            Создание и заполнение таблицы "Группы"
            AbsTable groupTable = new GroupTable(dbExecutor);

            List<String> columnsGroupTable = new ArrayList<>();
            columnsGroupTable.add("id int primary key");
            columnsGroupTable.add("name varchar(5)");
            columnsGroupTable.add("id_curator int");

            groupTable.create(columnsGroupTable);

            List<Group> groups = new ArrayList<>();
            groups.add(new Group(1, "VS", 1));
            groups.add(new Group(2, "VA", 2));
            groups.add(new Group(3, "VM", 3));

            for (Group gr : groups) {
                groupTable.insert(gr);
            }

//            Создание и заполнение таблицы "Кураторы"
            AbsTable curatorTable = new CuratorTable(dbExecutor);

            List<String> columnsCuratorTable = new ArrayList<>();
            columnsCuratorTable.add("id int primary key");
            columnsCuratorTable.add("fio varchar(100)");

            curatorTable.create(columnsCuratorTable);

            List<Curator> curators = new ArrayList<>();
            curators.add(new Curator(1, "Петровский Мирон Владимирович"));
            curators.add(new Curator(2, "Козин Фёдор Глебович"));
            curators.add(new Curator(3, "Титов Кирилл Дмитриевич"));
            curators.add(new Curator(4, "Козлова Елизавета Марковна"));

            for (Curator cur : curators) {
                curatorTable.insert(cur);
            }

            System.out.println("\n");
            System.out.println("Результаты выполнения запросов" + System.lineSeparator());

            System.out.println("Количество студентов: ");
            studentTable.selectCountAll();
            System.out.println("\n");


            System.out.println("Студентки: ");
            studentTable.selectStudentsSex("F", "fio");
            System.out.println("\n");

            System.out.println("Инфо о студентах: ");
            studentTable.selectJoinThreeTable("student.fio,student.sex,group.name,curator.fio", "otusqa.student", "otusqa.group", "student.id_group = group.id", "otusqa.curator", "group.id_curator=curator.id");
            System.out.println("\n");

            groupTable.update("id_curator", "4", "id", "2");

            System.out.println("Инфо о группах и кураторах: ");
            groupTable.selectJoinTwoTable(" group.name, curator.fio", "otusqa.group", "otusqa.curator", "group.id_curator=curator.id");
            System.out.println("\n");
        } finally {
            dbExecutor.close();
        }
    }
}
