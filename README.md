## Дипломный прект профессии "Тестировщик ПО"


### Документация
1. [План тестирования](https://github.com/iva1111/Diplom-aqa/blob/main/Plan.md)
2. Отчет.....

### Инструкция по запуску приложения и подготовки отчетов
Предварительно скачать и установить Docker

1. Cклонировать репозиторий (скачать архив)
2. Запустить контейнер, выполнив в терминале команду docker-compose up
3. Открыть новую вкладку в окне терминала
4. Запустить приложение, выполнить втерминале команду java -jar artifacts/aqa-shop.jar
5. Запустить тесты, выполнив команду ./gradlew clean test или с помощью зеленого треугольника (shift + F10).
6. После окончания тестов завершить работу приложения (Ctrl+C), удалить контейнеры командой docker-compose down
7. Заустить отчет, выполнить команду: gradle allureReport 
8. Заустить отчет, выполнить команду: gradle allureServe (отчет откроется в браузере по умолчанию)