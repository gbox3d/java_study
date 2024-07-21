module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    
    // VLCJ 관련 모듈 추가
    requires uk.co.caprica.vlcj;
    requires uk.co.caprica.vlcj.javafx;

    // VLCJ가 reflection을 사용할 수 있도록 패키지 열기
    opens com.example to javafx.fxml, uk.co.caprica.vlcj;
    exports com.example;

    // VLCJ Native 라이브러리 사용을 위한 설정
    requires com.sun.jna;
    requires com.sun.jna.platform;
}