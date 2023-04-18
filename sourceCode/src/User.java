public class User {
    private String firstName;
    private String lastName;
    private String password;
    private boolean student;
    private int year;

    public User(String firstName, String lastName, String password, boolean student, int year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.student = student;
        this.year = year;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
