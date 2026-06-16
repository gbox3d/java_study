# chapter_ex02 - TextCanvas

콘솔의 고정 영역을 문자 단위로 갱신하기 위한 작은 텍스트 캔버스 예제입니다.
외부 라이브러리 없이 ANSI escape sequence 를 사용합니다.

## 실행

```powershell
javac -encoding UTF-8 -d bin app\chapter_ex02\TextCanvas.java app\chapter_ex02\Ex01.java
java -cp bin chapter_ex02.Ex01
```

## 핵심 사용법

```java
TextCanvas canvas = new TextCanvas(8, 8, 4, 2);

canvas.clearTerminal();
canvas.hideCursor();

canvas.writeCell(3, 2, 'A'); // 버퍼에만 기록
canvas.renderAll();          // 전체 버퍼 출력

canvas.setCell(4, 2, 'B');   // 버퍼 기록 + 해당 칸만 즉시 출력

canvas.showCursor();
canvas.moveCursorBelowCanvas();
```

좌표는 캔버스 안에서 `0, 0`부터 시작합니다.
`new TextCanvas(8, 8, 4, 2)`의 `4, 2`는 터미널에서 캔버스가 시작되는 실제 위치입니다.

`renderAll()`과 `setCell()`은 `println`을 사용하지 않습니다.
각 줄은 ANSI 커서 이동 후 `print`로만 출력되므로 렌더링 자체가 개행을 만들지 않습니다.
