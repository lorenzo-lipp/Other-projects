package dio.template.method;

public class Main {
    public static void main(String[] args) {
        ForumConnection forum1 = new firstForum("user", "user123");
        ForumConnection forum2 = new secondForum("admin", "12345");

        forum1.makeAPost("I like it!");
        forum2.makeAPost("Nice!");
    }
}