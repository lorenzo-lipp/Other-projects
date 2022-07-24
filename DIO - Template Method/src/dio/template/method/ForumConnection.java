package dio.template.method;

public abstract class ForumConnection {
    String username;
    String password;

    ForumConnection() {}

    abstract boolean logIn(String user, String passwd);
    abstract boolean sendMessage(String message);
    abstract void logOut();

    public boolean makeAPost(String message) {
        if (logIn(this.username, this.password)) {
            boolean sent = sendMessage(message);
            logOut();
            return sent;
        }
        return false;
    }
}
