# hello world java

자바실행시키기  
자바는 컴파일하면 .class파일이 생성되고, 이를 실행시키면 된다.  
소스를 작성할때 package를 지정하면, 해당 디렉토리에 패키지명으로 디렉토리가 생성되고, 그안에 .class파일이 생성된다.  
```java
package helloworld;
public class HelloWorld2030 {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

helloworld라는 패키지명으로 디렉토리를 생성하고, 그안에 HelloWorld2030.class파일이 생성된다.  
이를 실행시키려면, 해당 디렉토리로 이동하여 실행시켜야 한다.  
```bash
$ java helloworld.HelloWorld2030
Hello, World!

#output 디랙토리가 bin이라면,  

$ java -cp bin helloworld.HelloWorld2030
Hello, World!
```
-cp 옵션은 classpath를 지정하는 옵션이다.  