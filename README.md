# Фреймворк Spring (семинары)

## Урок 8. Spring AOP, управление транзакциями.
#### Задание:<br> Продемонстрируйте применение Spring AOP для логирования операций в вашем приложении. <br>Подумайте, как можно применить управление транзакциями для обеспечения целостности данных.<br><br>
### Описание<br>
- Практическая работа:<br>
- Реализация простого примера использования Spring AOP для логирования.<br>
- Реализация управления транзакциями с использованием аннотаций Spring @Transactional.<br>
- Создание сценария, который требует сложного управления транзакциями и реализация этого сценария с использованием Spring.<br>
<br>

### Домашнее задание
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

### Решение

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

### Автоматическое форматирование кода в Intellij IDEA<br><br>

- В настройках IDE для автоматического переформатирования кода необходимо проверить наличие форматирования java-файла,<br> 
для этого нажать сочетание клавиш **Ctrl + Alt + S**, чтобы открыть настройки IDE, и выбрать **Tools | Actions on Save**, далее включить параметр **Reformat code**, отметив чекбокс 'Reformat code'.<br><br>

- Для форматирования содержимого java-файла, нажать сочетание клавиш
**Ctrl + Alt + L** - Reformat code

<br><br><br>