package com.javarush.test.level17.lesson10.bonus02;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/* CRUD 2
CrUD Batch - multiple Creation, Updates, Deletion
!!!РЕКОМЕНДУЕТСЯ выполнить level17.lesson10.bonus01 перед этой задачей!!!

Программа запускается с одним из следующих наборов параметров:
-c name1 sex1 bd1 name2 sex2 bd2 ...
-u id1 name1 sex1 bd1 id2 name2 sex2 bd2 ...
-d id1 id2 id3 id4 ...
-i id1 id2 id3 id4 ...
Значения параметров:
name - имя, String
sex - пол, "м" или "ж", одна буква
bd - дата рождения в следующем формате 15/04/1990
-с  - добавляет всех людей с заданными параметрами в конец allPeople, выводит id (index) на экран в соответствующем порядке
-u  - обновляет соответствующие данные людей с заданными id
-d  - производит логическое удаление всех людей с заданными id
-i  - выводит на экран информацию о всех людях с заданными id: name sex bd

id соответствует индексу в списке
Формат вывода даты рождения 15-Apr-1990
Все люди должны храниться в allPeople
Порядок вывода данных соответствует вводу данных
Обеспечить корректную работу с данными для множества нитей (чтоб не было затирания данных)
Используйте Locale.ENGLISH в качестве второго параметра для SimpleDateFormat
*/

public class Solution {
    public static List<Person> allPeople = new ArrayList<Person>();
    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) throws ParseException
    {

        if (args[0].equals("-c")) create(args);
        else if (args[0].equals("-u")) update(args);
        else if (args[0].equals("-d")) delete(args);
        else if (args[0].equals("-i")) info(args);
    }

    private static synchronized void create(String[] args) throws ParseException
    {
        SimpleDateFormat btFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        int personCount = args.length / 3;

        for (int i = 0; i < personCount;i++)
        {
            Date date = btFormat.parse(args[3 + i*3]);
            String name = args[1 + i*3];

            if (args[2 + i*3].equals("м"))
            allPeople.add(Person.createMale(name, date));
            else if(args[2 + i*3].equals("ж"))
                allPeople.add(Person.createFemale(name, date));

            System.out.println(allPeople.size() - 1);
        }



    }

    private static synchronized  void update(String[] args) throws ParseException
    {
        SimpleDateFormat btFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        int personCount = args.length / 4;

        for (int i = 0; i < personCount;i++)
        {
            int id = Integer.parseInt(args[1 + i*4]);
            Date date = btFormat.parse(args[4 + i*4]);
            String name = args[2 + i*4];

            if (args[3 + i*4].equals("м"))
            {
                allPeople.get(id).setBirthDay(date);
                allPeople.get(id).setName(name);
                allPeople.get(id).setSex(Sex.MALE);
            }
                else if (args[3 + i*4].equals("ж"))
            {
                allPeople.get(id).setBirthDay(date);
                allPeople.get(id).setName(name);
                allPeople.get(id).setSex(Sex.FEMALE);

            }

        }

    }

    private static synchronized void delete(String[] args)
    {
        for (int i = 1; i < args.length; i++)
        {
            int id = Integer.parseInt(args[i]);
            allPeople.get(id).setBirthDay(null);
            allPeople.get(id).setName(null);
            allPeople.get(id).setSex(null);
        }
    }

    private static void info(String[] args)
    {



        for (int i = 1; i < args.length; i++)
        {
            SimpleDateFormat outFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            String sexOut = "";
            if (allPeople.get(i).getSex().equals(Sex.MALE)) sexOut = "м";
            else sexOut = "ж";
            System.out.println(allPeople.get(i).getName() + " " + sexOut + " " + outFormat.format(allPeople.get(i).getBirthDay()));
        }

    }


}
