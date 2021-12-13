### Инструкция по запуску 
Предварительно скачать и запустить Docker

1. Открыть Intellij IDEA.
2. В терминале выполнить команду docker-compose up
3. Открыть новую вкладку в окне терминала
4. В терминале выполнить команду java -jar artifacts/aqa-shop.jar
5. Запустить тесты. С помощью команды ./gradlew clean test или с помощью зеленого треугольника (shift + F10).
6. Выполнить команду: gradle allureReport 
7. Выполнить команду: gradle allureServe
