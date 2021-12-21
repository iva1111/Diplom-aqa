## Дипломный прект профессии "Тестировщик ПО"


### Документация
1. [План тестирования](https://github.com/iva1111/Diplom-aqa/blob/main/Plan.md)
2. [Отчет по итогам тестирования](https://github.com/iva1111/Diplom-aqa/blob/main/Report.md)
3. [Отчет по итогам автоматизации](https://github.com/iva1111/Diplom-aqa/blob/main/Summary.md)

### Инструкция по запуску приложения и подготовки отчетов
Предварительно скачать и установить [Docker](https://www.docker.com)

1. Cклонировать репозиторий https://github.com/iva1111/Diplom-aqa 
2. Открыть проект в IntelliJ IDEA
3. Запустить контейнер, выполнив в терминале команду: docker-compose up -d
4. Открыть новую вкладку в окне терминала
5. Запустить приложение, выполнить в терминале команду: java -jar artifacts/aqa-shop.jar
6. Для интеграции с БД MySQL выполнить в терминале команду: java "-Dspring.datasource.url=jdbc:mysql://185.119.57.164:3306/app" -jar artifacts/aqa-shop.jar
7. Для интеграции с БД PostgreSQL выполнить в терминале команду: java "-Dspring.datasource.url=jdbc:postgresql://185.119.57.164:5432/app" -jar artifacts/aqa-shop.jar
8. Запустить тесты для БД MySQL, выполнив команду: ./gradlew clean test "-Ddatasource.url=jdbc:mysql://185.119.57.164:3306/app"
9. Запустить тесты для БД PostgreSQL, выполнив команду: ./gradlew clean test "-Ddatasource.url=jdbc:postgresql://185.119.57.164:5432/app"
10. Запустить отчет, выполнить команду: gradle allureReport 
11. Запустить отчет, выполнить команду: gradle allureServe (отчет откроется в браузере по умолчанию)
12. Остановить комбинацией клавиш CTRL+C
