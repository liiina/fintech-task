# fintech-task
Сборка проекта осуществляется через Maven.
Запуск осуществляется на любом ПК с Java 1.8
1. Скачать архив с проектом
2. Открыть командную строку, перейти в папку с проектом (cd /fintech-task-master)
3. Выполнить команду mvn package (собирает артефакт fintech-task-1.0-jar-with-dependencies.jar, 
  который является запускаемой пограммой)
4. Выполнить команду java -jar <путь до fintech-task-1.0-jar-with-dependencies.jar> 
  (ex. java -jar target/fintech-task-1.0-jar-with-dependencies.jar)
5.  При успешном выполнении в консоль выведется информация, содержащая пути к сгенерированным файлам. 
  (ex. Excel spreadsheet created at: /Users/lina/fintech-task-master/target/users.xlsx
       Pdf document created at: /Users/lina/fintech-task-master/target/users.pdf)
  Если в процессе произойдёт ошибка - детальная информация о ней будет выведена в консоль.
P.S. итоговый файл перезаписывается после каждого запуска программы
