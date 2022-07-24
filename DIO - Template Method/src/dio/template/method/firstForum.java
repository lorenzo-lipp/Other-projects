package dio.template.method;

public class firstForum extends ForumConnection {
    firstForum(String user, String passwd) {
        this.username = user;
        this.password = passwd;
    }

    public boolean logIn(String user, String passwd) {
        System.out.println("Forum 1 successful log in");
        return true;
    }

    public void logOut() {
        System.out.println("Forum 1 successful log out");
    }

    public boolean sendMessage(String message) {
        System.out.println("Message send: " + message);
        return true;
    }
}
