## Дипломный прект профессии "Тестировщик ПО"


### Документация
1. [План тестирования](https://github.com/iva1111/Diplom-aqa/blob/main/Plan.md)
2. [Отчет по итогам тестирования](https://github.com/iva1111/Diplom-aqa/blob/main/Report.md)
3. [Отчет по итогам автоматизации](https://github.com/iva1111/Diplom-aqa/blob/main/Summary.md)

### Инструкция по запуску приложения и подготовки отчетов
Предварительно скачать и установить [Docker](https://www.docker.com)

1. Cклонировать репозиторий https://github.com/iva1111/Diplom-aqa 
2. Открыть проект в IntelliJ IDEA
3. Запустить контейнер, выполнив в терминале команду docker-compose up
4. Открыть новую вкладку в окне терминала
5. Запустить приложение, выполнить втерминале команду java -jar artifacts/aqa-shop.jar
6. Запустить тесты, выполнив команду ./gradlew clean test или с помощью зеленого треугольника (shift + F10).
7. Заустить отчет, выполнить команду: gradle allureReport 
8. Заустить отчет, выполнить команду: gradle allureServe (отчет откроется в браузере по умолчанию)
