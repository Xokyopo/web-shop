#language: ru
Функция: Проверка перехода на страницу с каталогом товара с главной
  Открываем главную страницу
  Нажимаем на кнопку "Shop page"
  Проверяем что открыта страница каталога товаров

  Сценарий: Проверки перехода на страницу каталога товара
    Пусть откроется страница "http://localhost:8080/"
    Затем нажмем кнопку "#navbarNav > ul > li:nth-child(2) > a"
    Тогда перейдем на "http://localhost:8080/shop"
