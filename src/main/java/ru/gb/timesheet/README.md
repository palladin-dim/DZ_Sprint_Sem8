# Фреймворк Spring (семинары)

## Урок 8. Spring AOP, управление транзакциями.
#### Задание:<br> Продемонстрируйте применение Spring AOP для логирования операций в вашем приложении. <br>Подумайте, как можно применить управление транзакциями для обеспечения целостности данных.<br><br>
### Описание<br>
- Практическая работа:<br>
- Реализация простого примера использования Spring AOP для логирования.<br>
- Реализация управления транзакциями с использованием аннотаций Spring @Transactional.<br>
- Создание сценария, который требует сложного управления транзакциями и реализация этого сценария с использованием Spring.<br>
  <br>

## Домашнее задание
<br>

1. В LoggingAspect добавить логирование типов и значений аргументов.
   Например (пример вывода):
   ```
   TimesheetService.findById(Long = 3)
   Эту информацию можно достать из joinPoint.getArgs()
   ```
   <br>
2. Создать аспект, который аспектирует методы, помеченные аннотацией Recover, и делает следующее:<br>
   2.1 Если в процессе исполнения метода был exception (любой), то его нужно залогировать <br>
   ```("Recovering TimesheetService#findById after Exception[RuntimeException.class, "exception message"]")```<br>
   и вернуть default-значение наружу Default-значение: для примитивов значение по умолчанию, для ссылочных типов - null. <br>
   Для void-методов возвращать не нужно.<br><br>

3. В аннотацию Recover добавить атрибут ```Class<?>[] noRecoverFor```, в которое можно записать список классов исключений, которые НЕ нужно отлавливать.<br>
   Это вхождение должно учитывать иерархию классов.<br>
   Пример:

```

@Recover(noRecoverFor = {NoSuchElementException.class, IllegalStateException.class})
public Timesheet getById(Long id) {...}


  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface Recover { // recover - восстанавливать
//    Class<?>[] noRecoverFor() default {};

  }  
```
<br>

## Решение

<br><br>
### Добавление зависимости

- Для создания метода обработки Ошибок, необходимо первично установить в файл pom.xml зависимость:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
или
```gradle
implementation 'org.springframework.boot:spring-boot-starter-aop'
```
<br><br>
### Добавление классов обработки аннотации

- Для выполнения аннотации ```@Recover```, указать класс интерфейса Recover.<br>
- Обработка аннотации производится в классе RecoverAspect.<br>
- Для проверкие наличия настройки и поддержки AOP для приложения Spring, <br>
  можно добавить аннотацию ```@EnableAspectJAutoProxy``` в основной класс приложения или <br>
  класс конфигурации, например, в TimesheetApplication.java

<br><br>
### Указываем аннотации в требуемом классе

С помощью аннотации @Recover можно обрабатывать ошибки в форме исключений для методов внутри класса service.<br> 
Добавлять аннотацию @Recover, чтобы воспользоваться механизмом восстановления, <br>
следует к методам, которые, могут получать ошибки и давать соответствующие исключения.

<br><br>
### Пояснение к аннотации @Recover(noRecoverFor = {}) для TimesheetService

- findById(Long id) проверка восстановления работы с id, за исключением NoSuchElementException и IllegalStateException.
- findAll() или findAll(LocalDate createdAtBefore, LocalDate createdAtAfter) установленна аннотация для поиска ошибки, кроме IllegalArgumentException.
- create(Timesheet timesheet) здесь проверяем ошибки, за исключением IllegalArgumentException и NoSuchElementException.
- delete(Long id) проверка ошибки восстановления работы кода после удаления id, за исключением исключения NoSuchElementException.
- getById(Long id): проверка исключений, кроме NoSuchElementException и IllegalStateException.
- findByEmployeeId(Long id): проверяем исключения, кроме IllegalArgumentException.


<br><br>
### Пояснение к аннотации @Recover(noRecoverFor = {}) для ProjectService

- findById(Long id) здесь метод позволяет обрабатывать сценарии, для которых в базе данных проекта не существует или не создан id, кроме исключения NoSuchElementException.
- findAll() здесь возможна обработка исключения, если возникает ошибка при выборке всех проектов в приложении, за исключением IllegalStateException.
- create(Project project) методом обрабатываются потенциальные ошибки при создании нового проекта в приложении, за исключением IllegalArgumentException.
- delete(Long id) здесь перехватываем ошибку при удалении проекта, если возникает какая-либо непредвиденная проблема с состоянием удаления, за исключением IllegalStateException.
- getTimesheets(Long id) здесь можно обработать случаи, когда project не существует, за исключением исключения NoSuchElementException.

<br><br>
## Дополнительная информация
<br>

- Начальная страница локального сервера (Таблицы)<br><br>

  http://localhost:8080/login<br><br>
  или<br><br>
  http://localhost:8080/home/timesheets<br>

```
http://localhost:8080/home/timesheets

```

- Страница проекта, например :

```
http://localhost:8080/home/projects/1

```

- Страница записи, например :

```
http://localhost:8080/home/timesheets/1

```
<br><br><br>