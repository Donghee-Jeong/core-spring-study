package hello.core.singleton;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){
     // 외부에서 생성자를 호출하는 것을 막기위해 private으로 생성자 선언
    }

    public void login(){
        System.out.println("싱클톤 객체 로직 호출");
    }
}
