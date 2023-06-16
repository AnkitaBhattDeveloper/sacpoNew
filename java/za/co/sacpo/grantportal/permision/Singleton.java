package za.co.sacpo.grantportal.permision;

public class Singleton {
    private static Singleton instance;

    public String getFoo() {
        return Foo;
    }

    public void setFoo(String foo) {
        Foo = foo;
    }

    private String Foo;
    private Singleton(){

    }

    public static Singleton getInstance(){
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
    // your fields, getter and setter here

}