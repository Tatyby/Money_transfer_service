# Курсовой проект "Сервис перевода денег"

## Задача проекта:

1. Разработать приложение - REST-сервис 
2. Сервис должен предоставляет интерфейс для перевода денег с одной карты на другую по описанной спецификации
3. Заранее подготовленное веб-приложение (FRONT) должно подключаеться к разработанному сервису без доработок
   и использовать его функционал для перевода денег

### Требования к приложению:

1. Сервис должен предоставлять REST-интерфейс для интеграции с FRONT.
2. Сервис должен реализовывать все методы перевода с одной банковской карты на другую,
   описанные в
   протоколе (https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml).
3. Все изменения должны записываться в файл — лог переводов в произвольном формате с указанием:
   даты;
   времени;
   карты, с которой было списание;
   карты зачисления;
   суммы;
   комиссии;
   результата операции, если был.

### Реализация:
1. Приложение (REST-сервис) разработано с использованием Spring Boot.
2. Использован сборщик пакетов maven.
3. Для запуска используется Docker, Docker Compose.
4. Код покрыт юнит-тестами с использованием mockito.
5. Добавлены интеграционные тесты с использованием testcontainers.
6. Развернутое приложение FRONT находится по адресу: https://serp-ya.github.io/card-transfer/
7. Протестировао с помощью Postman, примеры запросов находятся в файле ExampleRequest.http.



