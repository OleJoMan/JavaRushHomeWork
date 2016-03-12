package com.javarush.test.level17.lesson10.bonus01;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* CRUD
CrUD - Create, Update, Delete
Программа запускается с одним из следующих наборов параметров:
-c name sex bd
-u id name sex bd
-d id
-i id
Значения параметров:
name - имя, String
sex - пол, "м" или "ж", одна буква
bd - дата рождения в следующем формате 15/04/1990
-c  - добавляет человека с заданными параметрами в конец allPeople, выводит id (index) на экран
-u  - обновляет данные человека с данным id
-d  - производит логическое удаление человека с id
-i  - выводит на экран информацию о человеке с id: name sex (м/ж) bd (формат 15-Apr-1990)

id соответствует индексу в списке
Все люди должны храниться в allPeople
Используйте Locale.ENGLISH в качестве второго параметра для SimpleDateFormat

Пример параметров: -c Миронов м 15/04/1990
*/

public class Solution {
    public static List<Person> allPeople = new ArrayList<Person>();
    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) throws ParseException, IOException
    {
        if (args.length > 0)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            if (args[0].equals("-c") && args.length == 4)
            {


                Date date = dateFormat.parse(args[3]);
                if (args[2].equals("м"))
                {
                    allPeople.add(Person.createMale(args[1],date ));
                    System.out.println(allPeople.size() - 1);
                } else if (args[2].equals("ж"))
                {
                    allPeople.add(Person.createFemale(args[1],date ));
                    System.out.println(allPeople.size() - 1);
                }
            } else if (args[0].equals("-c") && args.length == 5)
            {

                Date date = dateFormat.parse(args[4]);
                if (args[3].equals("м"))
                {
                    allPeople.add(Person.createMale(args[1]+ " " + args[2],date ));
                    System.out.println(allPeople.size() - 1);
                } else if (args[3].equals("ж"))
                {
                    allPeople.add(Person.createFemale(args[1] + " " + args[2],date ));
                    System.out.println(allPeople.size() - 1);
                }
            }


            if (args[0].equals("-u") && args.length == 5)
            {
                Date date = dateFormat.parse(args[4]);

                int id = Integer.parseInt(args[1]);
                allPeople.get(id).setName(args[2]);
                allPeople.get(id).setBirthDay(date);
                if (args[3].equals("м")) {
                    allPeople.get(id).setSex(Sex.MALE);
                } else {
                    allPeople.get(id).setSex(Sex.FEMALE);
                }
            } else if (args[0].equals("-u") && args.length == 6)
            {
                Date date = dateFormat.parse(args[5]);

                int id = Integer.parseInt(args[1]);
                allPeople.get(id).setName(args[2]+ " " + args[3]);
                allPeople.get(id).setBirthDay(date);
                if (args[4].equals("м")) {
                    allPeople.get(id).setSex(Sex.MALE);
                } else {
                    allPeople.get(id).setSex(Sex.FEMALE);
                }
            }

            if (args[0].equals("-d")&& args.length==2){
                int index = Integer.parseInt(args[1]);
                if (index<0 || index>= allPeople.size())
                    throw new IOException("Wrong data");
                allPeople.get(Integer.parseInt(args[1])).setBirthDay(null);
                allPeople.get(Integer.parseInt(args[1])).setName(""); // null попробуй
                allPeople.get(Integer.parseInt(args[1])).setSex(null);
            }

            if (args[0].equals("-i"))
            {
                int id = Integer.parseInt(args[1]);

                SimpleDateFormat outFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                String sexOut = "";
                if (allPeople.get(id).getSex().equals(Sex.MALE)) sexOut = "м";
                else sexOut = "ж";
                System.out.println(allPeople.get(id).getName() + " " + sexOut + " " + outFormat.format(allPeople.get(id).getBirthDay()));
            }

        }
    }
}
