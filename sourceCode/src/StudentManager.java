
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


public class StudentManager {
    public static List<User> userList = new ArrayList<>();
    public static User loggedInUser = null;
    public static List<Subject> subjects = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome Student Manager");
        initializeUsers();
        while (true) {
            int userAction = 0;
            if (loggedInUser == null) {
                userAction = welcomeMenu();
            } else {
                userAction = loggedInMenu();
            }
            if (userAction == 0) {
                System.out.println("Thank you");
                return;
            }
        }
    }

    private static int welcomeMenu() {
        System.out.println("-----------------------");
        System.out.println(" Press 1 to register. ");
        System.out.println(" Press 2 to login. ");
        System.out.println(" Pres 0 to exit. ");
        Scanner scanner = new Scanner(System.in);
        int userAction = scanner.nextInt();
        switch (userAction) {
            case 1:
                registerAs();
                break;
            case 2:
                login(scanner);
                break;
        }
        return userAction;
    }

    private static int loggedInMenu() {
        System.out.println("-----------------------");
        if (loggedInUser.isStudent()) {
            System.out.println("Press 1 to see the list of subject you are enrolled in.");
            System.out.println("Press 2 to see the list of subject you are not enrolled in.");
            System.out.println("Press 3 to enroll in a subject.");
            System.out.println("Press 4 to drop out.");
        } else {
            System.out.println("Press 1 to see your subject list.");
            System.out.println("Press 2 to add a subject.");
            System.out.println("Press 3 to delete a subject.");
        }

        System.out.println("Press 0 to log out");
        Scanner scanner = new Scanner(System.in);
        int userAction = scanner.nextInt();
        switch (userAction) {
            case 1:
                if (loggedInUser.isStudent()) {
                    listOfSubjects(loggedInUser);
                } else {
                    displaySubjects(loggedInUser);
                }
                break;

            case 2:
                if (loggedInUser.isStudent()) {
                    listOfSubjectsNotEnrolled(loggedInUser);
                } else {
                    addSubject(loggedInUser);
                }
                break;

            case 3:
                if (loggedInUser.isStudent()) {
                    enrollInSubject(loggedInUser);
                } else {
                    deleteSubject(loggedInUser);
                }
                break;

            case 4:
                if (loggedInUser.isStudent()) {
                    dropOutSubject(loggedInUser);
                }
                break;

            case 0:
                loggedInUser = null;
                break;
        }
        return userAction;

    }

    private static void login(Scanner scanner) {
        System.out.println("Enter your last name.");
        String lastName = scanner.next();
        System.out.println("Enter your password.");
        String password = scanner.next();
        userList.forEach(user -> {
            if (user.getPassword().equals(password) && user.getLastName().equals(lastName)) {
                loggedInUser = user;
                System.out.println("Welcome " + loggedInUser.getLastName());
            }
        });
        if (loggedInUser == null) {
            System.out.println("Wrong credentials. ");
        }
    }


    private static int registerAs() {
        System.out.println("-----------------------");
        System.out.println(" Press 1 for teacher. ");
        System.out.println(" Press 2 for student. ");
        System.out.println(" Pres 0 to exit. ");
        Scanner scanner = new Scanner(System.in);
        int userAction = scanner.nextInt();
        switch (userAction) {
            case 1:
                register(scanner, false);
                break;
            case 2:
                register(scanner, true);
                break;
            case 3:

        }
        return userAction;
    }

    private static void register(Scanner scanner, boolean isStudent) {
        User user = new User();
        System.out.println("Enter first name");
        String firstName = scanner.next();
        System.out.println("Enter last name");
        String lastName = scanner.next();
        System.out.println(firstName + " " + lastName);
        if (isStudent) {
            System.out.println("Enter your academic year");
            int year = scanner.nextInt();
            user.setYear(year);
        }
        String password = generatePassword();
        System.out.println("Your password is : " + password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setStudent(isStudent);
        userList.add(user);

    }

    private static void displaySubjects(User user) {
        if (!user.isStudent()) {
            subjects.forEach(subject -> {
                if (user.equals(subject.getTeacher())) {
                    System.out.println(subject.getName());
                }
            });
        } else {
            subjects.forEach(subject -> {
                if (subject.getStudents().contains(user)) {
                    System.out.println(subject.getName());
                }
            });
        }
    }

    private static void addSubject(User user) {
        if (!user.isStudent()) {
            Subject newSubject = new Subject();
            System.out.println("Please write the name of the new subject.");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.next();
            newSubject.setName(name);


            System.out.println("Please write the description of the subject.");
            String describtion = scanner.next();
            newSubject.setDescription(describtion);
            newSubject.setTeacher(user);
            newSubject.setStudents(new ArrayList<>());

            subjects.add(newSubject);
            System.out.println("You added a new subject! ");

        }
    }

    private static void deleteSubject(User user) {
        if (!user.isStudent()) {
            System.out.println("Please enter the name of the subject you want to delete. ");
            Scanner scanner = new Scanner(System.in);
            String delete = scanner.next();
            for (int i = 0; i < subjects.size(); i++) {
                if (subjects.get(i).getName().equals(delete)) {
                    subjects.remove(i);
                    System.out.println("You just deleted " + delete + "!");
                    return;
                }
            }
            System.out.println("No subject found with this name!");
        }
    }

    private static void listOfSubjects(User user) {
        if (user.isStudent()) {
            subjects.forEach(subject -> {
                if (subject.getStudents().contains(user)) {
                    System.out.println(subject.getName());
                }
            });
        }
    }

    private static void listOfSubjectsNotEnrolled(User user) {
        if (user.isStudent()) {
            subjects.forEach(subject -> {
                if (!subject.getStudents().contains(user)) {
                    System.out.println(subject.getName());
                }

            });
        }
    }

    private static void enrollInSubject(User user) {
        if (user.isStudent()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Write the name of subject you want to enroll.");
            String newSubjectName = scanner.next();
            AtomicBoolean isSubjectAvailable = new AtomicBoolean(false);
            subjects.forEach(subject -> {
                if (subject.getName().equals(newSubjectName))
                    if (subject.getStudents().contains(user)) {
                        System.out.println("You are already enrolled!");
                        return;
                    } else {
                        List<User> studentList = subject.getStudents();
                        studentList.add(user);
                        subject.setStudents(studentList);
                        System.out.println("You have enrolled in " + newSubjectName);
                        isSubjectAvailable.set(true);
                    }
            });
            if (!isSubjectAvailable.get()) {
                System.out.println("Subject doesn't exist.");
            }
        }
    }

    private static void dropOutSubject(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the name of the subject you want to drop out.");
        String dropOut = scanner.next();
        AtomicBoolean isSubjectAvailable = new AtomicBoolean(false);
        subjects.forEach(subject -> {
            if (subject.getName().equals(dropOut)) {
                if (subject.getStudents().contains(loggedInUser)) {
                    for (int i = 0; i < subject.getStudents().size(); i++) {
                        if (subject.getStudents().get(i).getLastName().equals(loggedInUser.getLastName()) && subject.getStudents().get(i).getPassword().equals(loggedInUser.getPassword())) {
                            List<User> studentList = subject.getStudents();
                            studentList.remove(i);
                            subject.setStudents(studentList);
                            System.out.println("You just dropout of " + dropOut);
                            isSubjectAvailable.set(true);
                            return;
                        }
                    }
                } else {
                    System.out.println("You are not enrolled in this subject!");
                    return;
                }

            }

        });
        if (!isSubjectAvailable.get()) {
            System.out.println("Subject doesn't exist.");
        }
    }

    private static String generatePassword() {
        String password = UUID.randomUUID().toString().replace("-", "");
        return password.substring(0, 8);
    }

    private static void initializeUsers() {
        User u1 = new User();
        u1.setFirstName("Ger");
        u1.setLastName("Balla");
        u1.setPassword("GG1");
        u1.setStudent(false);

        User u2 = new User();
        u2.setFirstName("Kristi");
        u2.setLastName("Bualli");
        u2.setPassword("KB1");
        u2.setStudent(true);


        User u3 = new User();
        u3.setFirstName("Rei");
        u3.setLastName("Balla");
        u3.setPassword("RB1");
        u3.setStudent(true);

        User u4 = new User();
        u4.setFirstName("Sabom");
        u4.setLastName("Pupa");
        u4.setPassword("SP1");
        u4.setStudent(false);

        User u5 = new User();
        u5.setFirstName("Ledio");
        u5.setLastName("Papi");
        u5.setPassword("LP1");
        u5.setStudent(true);

        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5);

        Subject math = new Subject();
        math.setName("Math");
        math.setDescription("Advanced mathematics subject.");
        math.setTeacher(u1);
        List<User> studentsInMath = new ArrayList<>();
        studentsInMath.add(u2);
        studentsInMath.add(u3);
        math.setStudents(studentsInMath);

        Subject history = new Subject();
        history.setName("History");
        history.setDescription("World history.");
        history.setTeacher(u4);
        history.setStudents(List.of(u2, u3));

        Subject lecture = new Subject();
        lecture.setName("Lecture");
        lecture.setDescription("Learning lecture");
        lecture.setTeacher(u4);
        lecture.setStudents(List.of(u5));


        subjects.add(math);
        subjects.add(history);
        subjects.add(lecture);


    }


}

