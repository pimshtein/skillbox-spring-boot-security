# Как написать и использовать собственный Spring Boot Starter (Gradle, пакет skillbox.springboot.demo)

## 1. Введение: зачем нужны стартеры

Spring Boot Starter — это специальный модуль, который позволяет быстро подключать готовую функциональность в ваше приложение. Например, вы хотите, чтобы в каждом проекте был свой сервис или набор бинов — для этого и создают собственные стартеры.

---

## 2. Структура проекта

У нас будет два модуля:
- **my-spring-boot-starter** — собственный стартер
- **spring-boot-demo** — демо-приложение, использующее стартер

```
.
├── my-spring-boot-starter/
│   ├── build.gradle
│   └── src/main/java/skillbox/springboot/demo/starter/
│       ├── MyAutoConfiguration.java
│       └── MyService.java
│   └── src/main/resources/META-INF/spring.factories
└── spring-boot-demo/
    ├── build.gradle
    └── src/main/java/skillbox/springboot/demo/
        ├── DemoApplication.java
        └── HelloController.java
    └── src/main/resources/application.properties
```

---

## 3. Шаг 1. Создаём стартер

### 3.1. build.gradle для стартера

```groovy
plugins {
    id 'java-library'
}

group = 'skillbox.springboot.demo'
version = '1.0-SNAPSHOT'
sourceCompatibility = '11'

def springBootVersion = '2.7.18'

dependencies {
    api "org.springframework.boot:spring-boot-autoconfigure:${springBootVersion}"
    implementation "org.springframework:spring-context"
}

repositories {
    mavenCentral()
}
```

### 3.2. Класс автоконфигурации

```java
package skillbox.springboot.demo.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfiguration {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

### 3.3. Сам сервис

```java
package skillbox.springboot.demo.starter;

public class MyService {
    public String sayHello() {
        return "Hello from MyService in custom starter!";
    }
}
```

### 3.4. Регистрация автоконфигурации

`src/main/resources/META-INF/spring.factories`:

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
skillbox.springboot.demo.starter.MyAutoConfiguration
```

---

## 4. Шаг 2. Собираем и публикуем стартер

Выполните команду в папке стартера:

```bash
./gradlew publishToMavenLocal
```

Это опубликует стартер в локальный Maven-репозиторий, чтобы его можно было подключить в других проектах.

---

## 5. Шаг 3. Создаём демо-приложение

### 5.1. build.gradle для демо-приложения

```groovy
plugins {
    id 'org.springframework.boot' version '2.7.18'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    id 'java'
}

group = 'skillbox.springboot.demo'
version = '1.0-SNAPSHOT'
sourceCompatibility = '11'

def myStarterVersion = '1.0-SNAPSHOT'

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation "skillbox.springboot.demo:my-spring-boot-starter:${myStarterVersion}"
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
    useJUnitPlatform()
}
```

### 5.2. Основной класс приложения

```java
package skillbox.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### 5.3. Контроллер, использующий сервис из стартера

```java
package skillbox.springboot.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import skillbox.springboot.demo.starter.MyService;

@RestController
public class HelloController {
    private final MyService myService;

    @Autowired
    public HelloController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/custom")
    public String customHello() {
        return myService.sayHello();
    }
}
```

### 5.4. Настройки приложения

`src/main/resources/application.properties`:

```
server.port=8081
```

---

## 6. Шаг 4. Запуск и проверка

1. Соберите и запустите демо-приложение:
   ```bash
   ./gradlew bootRun
   ```

2. Проверьте работу:
   - [http://localhost:8081/hello](http://localhost:8081/hello) — обычный hello
   - [http://localhost:8081/custom](http://localhost:8081/custom) — ответ от собственного стартера

---

## 7. Краткие пояснения для видео

- **Что такое стартер:** это модуль, который автоматически добавляет нужные бины и конфигурацию в ваше приложение.
- **Как работает автоконфигурация:** Spring Boot находит файл `spring.factories` и автоматически подключает указанные классы-конфигурации.
- **Зачем нужен свой стартер:** чтобы переиспользовать логику или сервисы в разных проектах без копирования кода.
- **Как использовать:** просто добавьте зависимость на стартер — и сервисы уже доступны как бины.

---

## 8. Итог

- Вы научились создавать собственный Spring Boot Starter.
- Научились подключать его в приложение на Gradle.
- Поняли, как работает автоконфигурация и зачем нужны стартеры.

---

**Этот сценарий можно использовать как для объяснения на видео, так и для демонстрации на практике, показывая структуру файлов, код и результат работы.**
Если нужно добавить слайды, схемы или дополнительные пояснения — дайте знать! 