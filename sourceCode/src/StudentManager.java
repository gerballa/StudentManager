
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


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
        System.out.println("Press 1 to log out");
        Scanner scanner = new Scanner(System.in);
        int userAction = scanner.nextInt();
        switch (userAction) {
            case 1:
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
    private static void displayTeacherSubject(User teacher) {
        subjects.forEach(subject -> {
            if (teacher.equals(subject.getTeacher())){
                System.out.println(subject.getName());
            }
        });
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
        u2.setPassword("G1");
        u2.setStudent(true);


        User u3 = new User();
        u3.setFirstName("Rei");
        u3.setLastName("Balla");
        u3.setPassword("RB1");
        u3.setStudent(true);

        User u4 = new User();
        u4.setFirstName("Sabom");
        u4.setLastName("Pupa");
        u4.setPassword("S1");
        u4.setStudent(false);

        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);

        Subject math = new Subject();
        math.setName("Math.");
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
        history.setStudents(List.of(u2));


        subjects.add(math);
        subjects.add(history);


    }



}

