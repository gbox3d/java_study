# java uart 예제

## 실행방법

**compile** 방법은 다음과 같다.  
-cp 옵션은 classpath를 지정하는 옵션이다.  
-d 옵션은 컴파일된 파일을 저장할 디렉토리를 지정하는 옵션이다.      
```bash
javac -cp lib/jSerialComm-2.11.0.jar -d bin src/App.java
```

**execution** 방법은 다음과 같다.  
bin 디렉토리에 있는 App.class 파일을 실행한다. 맨 마지막에 App은 실행할 클래스 이름이다.    
-cp 옵션은 classpath를 지정하는 옵션이다. 여기서는 bin 디렉토리와 jSerialComm-2.11.0.jar를 지정한다. jar를 폴더 개념으로 생각하면 된다.  
-d 옵션은 컴파일된 파일을 저장할 디렉토리를 지정하는 옵션이다. 

```bash
java -cp bin:lib/jSerialComm-2.11.0.jar App
```

## 디버그 방법  

launch.json 파일을 생성한다.  
```json
{
    "version": "0.0.1",
    "configurations": [
        {
            "type": "java",
            "name": "Launch App",
            "request": "launch",
            "mainClass": "App",
            "classPaths": [
                "${workspaceFolder}/bin",
                "${workspaceFolder}/lib/jSerialComm-2.11.0.jar"
            ]
        }
    ]
}
```

## jar 파일 만들기

jar 파일을 만들기 위해서는 manifest 파일을 만들어야 한다.  
아래와 같이 manifest.mf 파일을 만든다.
```bash
```manifest
Manifest-Version: 1.0
Main-Class: App
Class-Path: lib/jSerialComm-2.11.0.jar
```

jar 파일을 만들기 위해서는 아래와 같이 실행한다.  
```bash
jar cfm app.jar manifest.mf -C bin .
```

jar 파일을 실행하기 위해서는 아래와 같이 실행한다.  
-jar 옵션은 jar 파일을 실행할 때 사용하는 옵션이다.  
```bash
java -jar app.jar
```





## 참고자료

- [jSerialComm](https://fazecast.github.io/jSerialComm/)




