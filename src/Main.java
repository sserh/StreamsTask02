import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //Находим и выводим количество несовершеннолетних
        Stream<Person> stream_1 = persons.stream();

        long youngCount = stream_1
                .filter(person -> person.getAge() < 18)
                .count();

        System.out.println("Количество несовершеннолетних: " + youngCount + "\n");

        //Находим и помещаем в список фамилии призывников
        Stream<Person> stream_2 = persons.stream();

        List<String> soldiersList =
                stream_2
                .filter(person -> (person.getSex() == Sex.MAN)) //оставляем только мужчин
                .filter(person -> person.getAge() >= 18) // затем отсекаем тех, кто младше 18
                .filter(person -> person.getAge() <= 27) // и старше 27
                .map(Person::getFamily) //преобразуем в стрим со строками
                .toList(); //помещаем в лист

        //интереса ради выведем часть списка призывников в консоль
        Stream<String> stream_3 = soldiersList.stream();

        System.out.println("Список первых 10 фамилий призывников: ");

        stream_3
                .limit(10)
                .forEach(System.out::println);

        System.out.println("...\n");

        //Находим и помещаем в список потенциально работоспособных людей с высшим образованием (с сортировкой по фамилиям)
        Stream<Person> stream_4 = persons.stream();

        List<Person> workersList = stream_4
                .filter(person -> person.getAge() >= 18) //отсекаем тех, кто младше 18
                .filter(person -> person.getEducation() == Education.HIGHER) //и не имеет высшего образования
                .filter(person -> ((person.getSex() == Sex.WOMAN) && (person.getAge() <= 60)) || ((person.getSex() == Sex.MAN) && (person.getAge() <= 65))) //оставляем только не пенсионеров
                .sorted(Comparator.comparing(Person::getFamily)) //сортируем по фамилии
                .collect(Collectors.toList()); //помещаем в лист

        //интереса ради выведем часть списка воркеров в консоль
        Stream<Person> stream_5 = workersList.stream();

        System.out.println("Список первых 10 фамилий потенциальных работников: ");

        stream_5
                .limit(10)
                .forEach(System.out::println);

        System.out.println("...\n");

    }
}